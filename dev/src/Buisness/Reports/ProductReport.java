package Buisness.Reports;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.Product;
import Buisness.Singletone_Storage_Management;
import DAL.ReportsDAL.ProductRepDataDAL;
import DAL.ReportsDAL.ProductReportDAL;

import java.util.*;

public class ProductReport {
    private Date date;
    private Map<String, List<String>> hierarchy; //Map<categoryName, List[subCategory]>
    private Map<String, List<ProductRepData>> reportData; //Map<categoryName, List[ProductRepData]>

    public ProductReport(){
        this.hierarchy = new HashMap<>();
        this.reportData = new HashMap<>();
    }

    public ProductReport (ProductReportDAL productReportDAL){
        this.date = productReportDAL.getDate();
        this.hierarchy = new HashMap<>();
        this.reportData = new HashMap<>();
        for (String categoryName : productReportDAL.getHierarchy().keySet()) {
            List<String> subCat = new ArrayList<>();
            for (String sub:productReportDAL.getHierarchy().get(categoryName)) {
                subCat.add(sub);
            }
            this.hierarchy.put(categoryName , subCat);
        }

        for (String categoryName : productReportDAL.getReportData().keySet()) {
            List<ProductRepData> productRepDataList = new ArrayList<>();
            for (ProductRepDataDAL productRepDataDAL:productReportDAL.getReportData().get(categoryName)) {
                productRepDataList.add(new ProductRepData(productRepDataDAL));
            }
            this.reportData.put(categoryName , productRepDataList);
        }
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

    public ProductReportDAL createDAL() {
        Map<String, List<String>> hierarchyDAL = new HashMap<>(); //Map<categoryName, List[subCategory]>
        Map<String, List<ProductRepDataDAL>> reportDataDAL = new HashMap<>(); //Map<categoryName, List[ProductRepData]>
        for (String categoryName : this.getHierarchy().keySet()) {
            List<String> subCatDAL = new ArrayList<>();
            for (String sub : this.getHierarchy().get(categoryName)) {
                subCatDAL.add(sub);
            }
            hierarchyDAL.put(categoryName , subCatDAL);
        }
        for (String categoryName : getReportData().keySet()) {
            List<ProductRepDataDAL> productRepDataDALList = new ArrayList<>();
            for (ProductRepData productRepData : getReportData().get(categoryName)) {
                productRepDataDALList.add(productRepData.createDAL());
            }
        }
        return new ProductReportDAL(this.date, hierarchyDAL, reportDataDAL);
    }
}
