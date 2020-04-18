package Buisness.Invenrory;


import java.util.*;

public class ProductController {
    public static final String DEFAULT = "Default category";
    public static final String PRODUCT_NOT_FOUND = "Buisness.Invenrory.Product not found";
    public static final int DEFAULT_MIN_AMOUNT = Integer.MAX_VALUE;

    private List<Category> categories;
    private Map<Integer , DataSaleProduct> saleData; //<barCode , Buisness.Invenrory.DataSaleProduct>

    public ProductController(){
        saleData = new HashMap<>();
        this.categories = new ArrayList<>();
        Category missingCategory = new Category(DEFAULT);
        this.categories.add(missingCategory);
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
        if (!categories.contains(mainCategoryName)) {
            Category category = new Category(mainCategoryName);
            this.categories.add(category);
        }
    }

    public void setNewSubCategory(String subcategoryName, String mainCategoryName) {
        if (containsCatagoryByName(mainCategoryName) && !containsCatagoryByName(subcategoryName)){ //main exists sub doesn't exists
            Category mainC = getCategoryByName(mainCategoryName);
            Category subC = new Category(subcategoryName);
            mainC.appendSubCategory(subC);
            this.categories.add(subC);
        }
        else if (containsCatagoryByName(mainCategoryName) && containsCatagoryByName(subcategoryName)){//main exists and sub exists
            Category mainC = getCategoryByName(mainCategoryName);
            Category subC = getCategoryByName(subcategoryName);
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
        if (p!=null){
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
    }

    public void setPriceOfExistingProduct(int barcode, double newPrice) { //if barcode doesn't exists -> doesn't do anything
        if(!this.saleData.containsKey(barcode)){return;}
        this.saleData.get(barcode).setPrice(newPrice);
    }

    public void setDiscountOfExistingProduct(int barcode, double newDiscount) { //if barcode doesn't exists -> doesn't do anything
        if(!this.saleData.containsKey(barcode)){return;}
        this.saleData.get(barcode).setDiscount(newDiscount);
    }

    public void connectProductToCategory(String categoryName, int barcode){
        Product p = null;
        if (getCategoryByName(categoryName)==null){ return;}//if category name doesn't exists -> doesn't do anything
        for (Category category: this.categories) { //remove from current category
            if (category.getProduct(barcode)!=null) {
                p = category.getProduct(barcode);
                category.removeProduct(barcode);
            }
        }
        if (p==null){return;} //if the product doesn't exists -> doesn't do anything
        for (Category category: this.categories) { //add to destination category
            if (category.getName().equals(categoryName)) {
                category.appendProduct(p);
            }
        }
    }

    public void purchaseProduct(int barCode, String productName, String supplier, int amount){
        if(searchProduct(barCode)==null) { //this product doesn't exists yet
            Product p = new Product(barCode, productName, supplier, amount, DEFAULT_MIN_AMOUNT, null);//initialize with min amount = 0 and next supply time = null
            for (Category category: this.categories){
                if (category.getName().equals(DEFAULT)){
                    category.appendProduct(p);
                }
            }
        }
        else{       //TODO: decide what happens when the product exists in some category and now a purchase happened

        }
    }

    public void setMinimumAmount(int barcode,int minimumAmount){
        searchProduct(barcode).setMinAmount(minimumAmount);
    }

    public void setManufactorForProduct(int barcode , String newManufactor){
        Product p = searchProduct(barcode);
        if(p==null){return;}
        p.setManufactor(newManufactor);
    }

    public void setNextSupply(int barcode , Date nextSupply){
        Product p = searchProduct(barcode);
        if(p==null){return;}
        p.setNextSupplyTime(nextSupply);
    }

    public boolean sale(int barCode, int amount){
        Product p = searchProduct(barCode);
        if (p==null){return false;} //can't sale then no need to alert
        p.setAmount(p.getAmount()-amount); //it's legal because we've checked before that there are at least @amount on shelf
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

    public String getProductManufactor(int barcode){
        Product p = searchProduct(barcode);
        if (p==null){return PRODUCT_NOT_FOUND;}
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
        Date nextDate = p.getNextSupplyTime();
        if(nextDate == null)
          return "unknown next supply time";

        return nextDate.toString();
    }


    public void setCategoryName(String categoryName, String newName) {
        Category category = getCategoryByName(categoryName);
        if (category ==null){return;}
        category.setName(newName);
    }

    public void deleteCategory(String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null){return;}
        boolean hasSubCategories = category.getSubCategories().size()>0;
        boolean hasProducts = category.getProductList().size()>0;
        if (!hasProducts && !hasSubCategories){ //enable to delete category only if doesn't contains subCategories or products
            for (int i=0; i<this.categories.size(); i=i+1){
                if (categories.get(i).getName().equals(categoryName)){
                    this.categories.remove(i);
                    return;
                }
            }
        }
    }
}