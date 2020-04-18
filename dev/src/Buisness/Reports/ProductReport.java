package Buisness.Reports;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.Product;
import Buisness.Invenrory.ProductRepData;
import Buisness.Singletone_Storage_Management;

import java.util.*;


public class ProductReport {
    private Date date;
    private Map<String, List<String>> hierarchy; //Map<categoryName, List[subCategory]>
    private Map<String, List<ProductRepData>> reportData; //Map<categoryName, List[Buisness.Invenrory.ProductRepData]>

    public ProductReport(){
        this.hierarchy = new HashMap<>();
        this.reportData = new HashMap<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, List<String>> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Map<String, List<String>> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Map<String, List<ProductRepData>> getReportData() {
        return reportData;
    }

    public void setReportData(Map<String, List<ProductRepData>> reportData) {
        this.reportData = reportData;
    }

    public void makeNewReport(Date today){
        this.date = today;
        List <Category> categories = Singletone_Storage_Management.getInstance().getInventory().getCategories();
        for (Category category: categories) {
            List<String> subCategoriesNames = category.getSubCategoriesNames();
            this.hierarchy.put(category.getName(), subCategoriesNames); //add for the hierarchy the sub categories of each category
            List<Product> productsOfCategory = category.getProductList();
            this.reportData.putIfAbsent(category.getName(), new ArrayList<>());
            for (Product product:productsOfCategory ) { //add List of all product of each category
                ProductRepData productRepData = new ProductRepData(product.getBarCode(), product.getProductName(), product.getAmount());
                this.reportData.get(category.getName()).add(productRepData);
            }
        }
    }
}
