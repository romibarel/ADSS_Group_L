package Storage.Buisness.Invenrory;


import Storage.DAL.DataAccess;
import Storage.DAL.InventoryDAL.CategoryDAL;

import java.util.*;

public class ProductController {
    public static final String DEFAULT = "Default category";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final int DEFAULT_MIN_AMOUNT = Integer.MIN_VALUE;

    private List<Category> categories;

    private Map<Integer , DataSaleProduct> saleData; //<barCode , DataSaleProduct>

    private DataAccess dataAccess;

    public ProductController(){
        saleData = new HashMap<>();
        this.categories = new ArrayList<>();
        this.dataAccess = DataAccess.getInstance();
        setMainCategory(DEFAULT);
    }

    public void restore() {
        List <CategoryDAL> l = dataAccess.getCategoryDALS();
        for (CategoryDAL categoryDAL: l){
            Category c = new Category(categoryDAL);
            this.categories.add(c);
        }
        for (Integer i : dataAccess .getSaleData().keySet()){
            this.saleData.put(i, new DataSaleProduct(dataAccess.getSaleData().get(i)));
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Map<Integer, DataSaleProduct> getSaleData() {
        return saleData;
    }

    public void setSaleData(Map<Integer, DataSaleProduct> saleData) {
        this.saleData = saleData;
    }

    public void setMainCategory(String mainCategoryName) {
        if (getCategoryByName(mainCategoryName)==null) {
            Category category = new Category(mainCategoryName);
            this.categories.add(category);
            this.dataAccess.addCategory(category.createDAL());
        }
    }

    public void setNewSubCategory(String subcategoryName, String mainCategoryName) {
        if (containsCatagoryByName(mainCategoryName) && !containsCatagoryByName(subcategoryName)){ //main exists sub doesn't exists
            Category mainC = getCategoryByName(mainCategoryName);
            Category subC = new Category(subcategoryName);
            mainC.appendSubCategory(subC);
            this.dataAccess.addCategory(subC.createDAL());  //DAL issues, add the new category to data layer
            this.dataAccess.appendSubCategory(mainC.getName(), subC.getName());
            this.categories.add(subC);
        }
        else if (containsCatagoryByName(mainCategoryName) && containsCatagoryByName(subcategoryName)){//main exists and sub exists
            Category mainC = getCategoryByName(mainCategoryName);
            Category subC = getCategoryByName(subcategoryName);
            this.dataAccess.appendSubCategory(mainC.getName(), subC.getName()); //DAL update
            mainC.appendSubCategory(subC);
        }
    }

    private Category getCategoryByName(String categoryName) {
        for (Category category:categories) {
            if (category.getName().equals(categoryName)){
                return category;
            }
        }
        return null; //the category doesn't exists
    }

    private boolean containsCatagoryByName(String categoryName) {
        for (Category category:categories) {
            if (category.getName().equals(categoryName)){
                return true; //the category exists
            }
        }
        return false; //the category doesn't exists
    }

    public void appendProductToCategory(int barCode, String categoryName) { //assume the product already exists, otherwise doesn't do nothing
        Category c = getCategoryByName(categoryName);
        Product p = searchProduct(barCode);
        if (p!=null && c!=null){
            this.dataAccess.appendProductToCategoryDAL(c.getName(), p.createDAL());//DAL update
            c.appendProduct(p);
        }
    }

    public Product searchProduct (int barCode){
        for (Category category:categories) {
            if (category.hasProduct(barCode)){
                return category.getProduct(barCode);
            }
        }
        return null; //the category doesn't exists
    }

    public List<String> getListOfCategoriesNames() {
        List <String> ret = new ArrayList<>();
        for (Category category:categories) {
            ret.add(category.getName());
        }
        return ret;
    }

    public List<String> getListOfProductsNames() {
        List<String> ret = new ArrayList<>();
        for (Category category : categories) {
            List<String> toAdd = category.getProductNames();
            for (String name : toAdd) {
                ret.add(name);
            }
        }
        return ret;
    }

    public DataSaleProduct getDataSale(int barCode){
        return this.saleData.get(barCode);
    }

    public void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount) { //if the product exists already -> doesn't do nothing
        if(this.saleData.containsKey(barcode)){return;}
        DataSaleProduct dataSaleProduct = new DataSaleProduct(barcode, productName, price , discount);
        this.saleData.put(barcode, dataSaleProduct);
        this.dataAccess.addNewDataSaleProduct(barcode, dataSaleProduct.createDAL()); //add to DAL
    }

    public void setPriceOfExistingProduct(int barcode, double newPrice) { //if barcode doesn't exists -> doesn't do anything
        if(!this.saleData.containsKey(barcode)){return;}
        this.saleData.get(barcode).setPrice(newPrice);
        this.dataAccess.updateDataSaleProduct(barcode , this.saleData.get(barcode).createDAL()); //update DAL
    }

    public void setDiscountOfExistingProduct(int barcode, double newDiscount) { //if barcode doesn't exists -> doesn't do anything
        if(!this.saleData.containsKey(barcode)){return;}
        this.saleData.get(barcode).setDiscount(newDiscount);
        this.dataAccess.updateDataSaleProduct(barcode , this.saleData.get(barcode).createDAL());
    }

    public void connectProductToCategory(String categoryName, int barcode){
        Product p = null;
        if (getCategoryByName(categoryName)==null){ return;}//if category name doesn't exists -> doesn't do anything
        for (Category category: this.categories) { //remove from current category
            if (category.getProduct(barcode)!=null) {
                p = category.getProduct(barcode);
                category.removeProduct(barcode);
                this.dataAccess.deleteProductFromCategory(p.createDAL(), category.createDAL());   //DAL delete
            }
        }
        if (p==null){return;} //if the product doesn't exists -> doesn't do anything
        for (Category category: this.categories) { //add to destination category
            if (category.getName().equals(categoryName)) {
                category.appendProduct(p);
                this.dataAccess.appendProductToCategoryDAL(categoryName , p.createDAL());     //DAL insert
            }
        }
    }

    public void purchaseProduct(int barCode, String productName, int supplierID, int amount){
        if(searchProduct(barCode)==null) { //this product doesn't exists yet
            Product p = new Product(barCode, productName, supplierID, amount, DEFAULT_MIN_AMOUNT, null);//initialize with min amount = 0 and next supply time = null
            this.dataAccess.addNewProduct(p.createDAL()); //insert to DAL
            for (Category category: this.categories){
                if (category.getName().equals(DEFAULT)){
                    category.appendProduct(p);
                    this.dataAccess.appendProductToCategoryDAL(category.getName() , p.createDAL());   //update DAL
                }
            }
        }
        else{       //decide what happens when the product exists in some category and now a purchase happened
            searchProduct(barCode).setAmount(searchProduct(barCode).getAmount()+amount);
            searchProduct(barCode).setManufactor(supplierID);
        }
    }

    public void setMinimumAmount(int barcode,int minimumAmount){
        if (searchProduct(barcode) == null){return;}
        searchProduct(barcode).setMinAmount(minimumAmount);
        this.dataAccess.updateProduct(searchProduct(barcode).createDAL());
    }

    public void setManufactorForProduct(int barcode , int SupplierID){
        Product p = searchProduct(barcode);
        if(p==null){return;}
        p.setManufactor(SupplierID);
        this.dataAccess.updateProduct(p.createDAL());
    }

    public void setNextSupply(int barcode , Date nextSupply){
        Product p = searchProduct(barcode);
        if(p==null){return;}
        p.setNextSupplyTime(nextSupply);
        this.dataAccess.updateProduct(p.createDAL());
    }

    public boolean sale(int barCode, int amount){
        Product p = searchProduct(barCode);
        if (p==null){return false;} //can't sale then no need to alert
        p.setAmount(p.getAmount()-amount); //it's legal because we've checked before that there are at least @amount on shelf
        this.dataAccess.updateProduct(p.createDAL()); //update DAL
        if (p.getAmount()<=p.getMinAmount()){
            return true;    //need to alert
        }
        return false;   //no need to alert
    }

    public String getSaleDataName(int barcode){
        DataSaleProduct p = getDataSale(barcode);
        if(p==null){return PRODUCT_NOT_FOUND;}
        return p.getProductName();
    }

    public double getSaleDataPrice(int barcode){
        DataSaleProduct p = getDataSale(barcode);
        if (p==null){return 0;}
        return p.getPrice();
    }

    public double getSaleDataDiscount(int barcode){
        DataSaleProduct p = getDataSale(barcode);
        if (p==null){return 0;}
        return p.getDiscount();
    }

    public String getProductName(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return PRODUCT_NOT_FOUND;}
        return p.getProductName();
    }

    public int getProductManufactor(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return -1;}
        return p.getManufactor();
    }

    public String getProductAmount(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return PRODUCT_NOT_FOUND;}
        int amount = p.getAmount();
        return String.valueOf(amount);
    }

    public String getProductMinAmount(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return PRODUCT_NOT_FOUND;}
        int Minamount = p.getMinAmount();
        return String.valueOf(Minamount);
    }

    public String getProductNextSupplyTime(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return PRODUCT_NOT_FOUND;}
        Date nextDate = p.getNextSupplyTime();
        if(nextDate == null)
          return "unknown next supply time";

        return nextDate.toString();
    }

    public void setCategoryName(String categoryName, String newName) {
        Category category = getCategoryByName(categoryName);
        if (category ==null){return;}
        category.setName(newName);
        this.dataAccess.updateCategory(categoryName, newName); //update DAL
    }

    public void deleteCategory(String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null){return;}
        boolean hasSubCategories = category.getSubCategories().size()>0;
        boolean hasProducts = category.getProductList().size()>0;
        if (!hasProducts && !hasSubCategories){ //enable to delete category only if doesn't contains subCategories or products
            for (int i=0; i<this.categories.size(); i=i+1){
                if (categories.get(i).getName().equals(categoryName)){
                    this.dataAccess.deleteCategory(categories.get(i).getName());
                    this.categories.remove(i);
                    return;
                }
            }
        }
    }
    public void clean (){
        saleData = new HashMap<>();
        this.categories = new ArrayList<>();
        setMainCategory(DEFAULT);
    }
}