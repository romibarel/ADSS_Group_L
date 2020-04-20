package DAL.InventoryDAL;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAL {
    private String name;
    private List<CategoryDAL> subCategoriesDAL;
    private List<ProductDAL> productListDAL;

    public CategoryDAL(String categoryName, List<CategoryDAL> categoryDALS, List<ProductDAL> productDALS) {
        this.name = categoryName;
        this.subCategoriesDAL = categoryDALS;
        this.productListDAL = productDALS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryDAL> getSubCategoriesDAL() {
        return subCategoriesDAL;
    }

    public void setSubCategoriesDAL(List<CategoryDAL> subCategoriesDAL) {
        this.subCategoriesDAL = subCategoriesDAL;
    }

    public List<ProductDAL> getProductListDAL() {
        return productListDAL;
    }

    public void setProductListDAL(List<ProductDAL> productListDAL) {
        this.productListDAL = productListDAL;
    }

    public void appendSubCategory (CategoryDAL subCategoryDAL){
        if (subCategoryDAL == null){return;}
        if (!this.subCategoriesDAL.contains(subCategoryDAL.getName())){
            this.subCategoriesDAL.add(subCategoryDAL);
        }
    }

    public void appendProductToCategoryDAL(ProductDAL productDAL) {
        this.productListDAL.add(productDAL);
    }

    public void deleteProduct (ProductDAL productDAL){
        this.productListDAL.remove(productDAL);
    }

    public boolean hasProduct(int barCode) {
        for (ProductDAL p:productListDAL) {
            if (p.getBarCode()==(barCode)){
                return true; //product exists
            }
        }
        return false;    //product doesn't exists
    }

    public ProductDAL getProduct(int barCode) {
        for (ProductDAL p:productListDAL) {
            if (p.getBarCode()==(barCode)){
                return p; //product exists
            }
        }
        return null;    //product doesn't exists
    }
}
