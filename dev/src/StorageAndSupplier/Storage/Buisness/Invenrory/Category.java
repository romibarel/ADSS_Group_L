package StorageAndSupplier.Storage.Buisness.Invenrory;

import StorageAndSupplier.Storage.DAL.DataAccess;
import StorageAndSupplier.Storage.DAL.InventoryDAL.CategoryDAL;
import StorageAndSupplier.Storage.DAL.InventoryDAL.ProductDAL;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Category> subCategories;
    private List<Product> productList;
    private DataAccess dataAccess;

    public Category(String categoryName) {
        this.name = categoryName;
        this.subCategories = new ArrayList<>();
        this.productList = new ArrayList<>();
        this.dataAccess = DataAccess.getInstance();
    }

    public Category(CategoryDAL categoryDAL){
        this.name = categoryDAL.getName();
        this.subCategories = new ArrayList<>();
        this.productList = new ArrayList<>();
        this.dataAccess = DataAccess.getInstance();
        List <CategoryDAL> l = dataAccess.getMySubCategoriesDAL(this.name);
        for (CategoryDAL categoryDAL1 : l){
            this.subCategories.add(new Category(categoryDAL1));
        }
        for (ProductDAL productDAL : dataAccess.getProductListDAL(this.name)){
            this.productList.add(new Product(productDAL));
        }
    }

    public CategoryDAL createDAL(){
        List<CategoryDAL> categoryDALS = new ArrayList<>();
        List<ProductDAL> productDALS = new ArrayList<>();
        for (Category category : this.subCategories) {
            categoryDALS.add(category.createDAL());
        }
        for (Product product : this.productList) {
            productDALS.add(product.createDAL());
        }
        return new CategoryDAL(this.name, categoryDALS, productDALS);
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<String> getSubCategoriesNames (){
        List <String> names = new ArrayList<>();
        for (Category category: getSubCategories()) {
            names.add(category.getName());
        }
        return names;
    }

    public String getName() {
        return this.name;
    }

    public void appendSubCategory(Category subCategory) {
        if (this.subCategories==null){return;}
        if (!this.subCategories.contains(subCategory.getName())){
            this.subCategories.add(subCategory);
        }
    }

    public void appendProduct(Product product) {
        this.productList.add(product);
    }

    public boolean hasProduct(int barCode) {
        for (Product p : productList) {
            if (p.getBarCode()==(barCode)){
                return true; //product exists
            }
        }
        return false;    //product doesn't exists
    }

    public Product getProduct(int barCode) {
        for (Product p:productList) {
            if (p.getBarCode()==(barCode)){
                return p; //product exists
            }
        }
        return null;    //product doesn't exists
    }

    public void removeProduct(int barcode){
        Product toRemove = null;
        for (Product p:productList) {
            if (p.getBarCode()==(barcode)){
                toRemove = p;
            }
        }
        if (toRemove!=null) { //found item that need to remove
            productList.remove(toRemove);
        }
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (Product p : productList) {
            names.add(p.getProductName());
        }
        return names;
    }

    public void clean (String categoryName){
        this.name = categoryName;
        this.subCategories = new ArrayList<>();
        this.productList = new ArrayList<>();
    }
}
