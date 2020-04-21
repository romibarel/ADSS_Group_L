package DAL.InventoryDAL;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.DataSaleProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductControllerDAL {
    private List<CategoryDAL> categoryDALS;
    private Map<Integer , DataSaleProductDAL> saleData; //<barCode, DataSaleProduct>
    private static ProductControllerDAL instance;

    private ProductControllerDAL(){
        this.categoryDALS = new ArrayList<>();
        this.saleData = new HashMap<>();
    }

    public static ProductControllerDAL getInstance(){

        if (instance == null)
            instance = new ProductControllerDAL();

        return instance;
    }

    public List<CategoryDAL> getCategoryDALS() {
        return categoryDALS;
    }

    public void setCategoryDALS(List<CategoryDAL> categoryDALS) {
        this.categoryDALS = categoryDALS;
    }

    public Map<Integer, DataSaleProductDAL> getSaleData() {
        return saleData;
    }

    public void setSaleData(Map<Integer, DataSaleProductDAL> saleData) {
        this.saleData = saleData;
    }

    public void addCategory(CategoryDAL dal) {
        this.categoryDALS.add(dal);
    }

    public void createNewCategory(CategoryDAL dal) {
        this.categoryDALS.add(dal);
    }

    public CategoryDAL getCategoryDALByName(String name){
        for (CategoryDAL category:this.categoryDALS) {
            if (category.getName().equals(name)){
                return category;
            }
        }
        return null; /*the category doesn't exists ->this case never happens because we reach DAL only if
                    exists but it won't harm*/
    }

    public void appendSubCategory(String main, String sub) {
        CategoryDAL mainC = getCategoryDALByName(main);
        CategoryDAL subC = getCategoryDALByName(sub);
        mainC.appendSubCategory(subC);
    }

    public void appendProductToCategoryDAL(String name, ProductDAL productDAL) {
        CategoryDAL c = getCategoryDALByName(name);
        c.appendProductToCategoryDAL(productDAL);
    }

    public void addNewDataSaleProduct(int barcode, DataSaleProductDAL dal) {
        this.saleData.put(barcode, dal);
    }

    public void updateDataSaleProduct(int barcode, DataSaleProductDAL dataSaleProductDAL){
        this.saleData.replace(barcode, dataSaleProductDAL);
    }

    public void deleteProductFromCategory (ProductDAL productDAL, CategoryDAL categoryDAL){
        categoryDAL.deleteProduct(productDAL);
    }

    public void addNewProduct (ProductDAL productDAL){ }//in this case we don't ave to do something, but whenever we'll add database so we use this function

    public void updateProduct(ProductDAL productDAL){
        ProductDAL p = searchProduct(productDAL.getBarCode());
        p.setBarCode(productDAL.getBarCode());
        p.setAmount(productDAL.getAmount());
        p.setManufactor(productDAL.getManufactor());
        p.setMinAmount(productDAL.getMinAmount());
        p.setNextSupplyTime(productDAL.getNextSupplyTime());
        p.setProductName(productDAL.getProductName());
    }
    public ProductDAL searchProduct (int barCode){
        for (CategoryDAL category : this.categoryDALS) {
            if (category.hasProduct(barCode)){
                return category.getProduct(barCode);
            }
        }
        return null; //the category doesn't exists
    }

    public void updateCategory (CategoryDAL categoryDAL){
        CategoryDAL c = getCategoryDALByName(categoryDAL.getName());
        if (c==null){return;}
        c.setName(categoryDAL.getName());
        c.setProductListDAL(categoryDAL.getProductListDAL());
        c.setSubCategoriesDAL(categoryDAL.getSubCategoriesDAL());
    }

    public void deleteCategory(CategoryDAL categoryDAL){
        for(int i=0; i<this.categoryDALS.size(); i=i+1){
            if (this.categoryDALS.get(i).getName().equals(categoryDAL.getName())){
                this.categoryDALS.remove(i);
                return;
            }
        }
    }
}
