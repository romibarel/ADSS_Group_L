package StorageAndSupplier.Storage.DAL;

import java.sql.Connection;
import java.sql.SQLException;


import StorageAndSupplier.Storage.DAL.DefectsDAL.DefectControllerDAL;
import StorageAndSupplier.Storage.DAL.DefectsDAL.DefectDAL;
import StorageAndSupplier.Storage.DAL.InventoryDAL.CategoryDAL;
import StorageAndSupplier.Storage.DAL.InventoryDAL.DataSaleProductDAL;
import StorageAndSupplier.Storage.DAL.InventoryDAL.ProductControllerDAL;
import StorageAndSupplier.Storage.DAL.InventoryDAL.ProductDAL;
import StorageAndSupplier.Storage.DAL.LocationsDAL.LocationControllerDAL;
import StorageAndSupplier.Storage.DAL.ReportsDAL.DefectReportDAL;
import StorageAndSupplier.Storage.DAL.ReportsDAL.ProductReportDAL;
import StorageAndSupplier.Storage.DAL.ReportsDAL.ReportsDAL;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataAccess {
    private DefectControllerDAL defectControllerDAL;
    private ProductControllerDAL productControllerDAL;
    private LocationControllerDAL locationControllerDAL;
    private ReportsDAL reportsDAL;
    private static DataAccess instance;

    private Connection conn;

    private DataAccess(){
        this.defectControllerDAL = new DefectControllerDAL();
        this.productControllerDAL = new ProductControllerDAL();
        this.locationControllerDAL = new LocationControllerDAL();
        this.reportsDAL = new ReportsDAL();
    }


    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DataAccess getInstance(){

        if (instance == null)
            instance = new DataAccess();

        return instance;
    }

    public void addNewProductReport (ProductReportDAL productReportDAL){
        this.reportsDAL.addNewProductReport (productReportDAL, conn);
    }

    public void addDefectReport(DefectReportDAL defectReportDAL){
        this.reportsDAL.addDefectReport(defectReportDAL, conn);
    }

    public List<DefectReportDAL> restoreDefectsReportsList (){
        return this.reportsDAL.restoreDefectsReportsList(conn);
    }

    public List <ProductReportDAL> restoreProductsReportList(){
        return this.reportsDAL.restoreProductsReportList(conn);
    }

    public Map<Integer, Map<Date, Map<Integer, Integer>>> getProductsLocationDAL() {
        return this.locationControllerDAL.getProductsLocationDAL(conn);
    }

    public void setProductsLocationDAL(Map<Integer, Map<Date, Map<Integer, Integer>>> productsLocationDAL) {
        this.locationControllerDAL.setProductsLocationDAL(productsLocationDAL);
    }

    public Map<Integer, String> getLocationsDAL() {
        return this.locationControllerDAL.getLocationsDAL(conn);
    }

    public void setLocationsDAL(Map<Integer, String> locationsDAL) {
        this.locationControllerDAL.setLocationsDAL(locationsDAL);
    }

    public void updateProductsQuantityInLocation (int barCode, Date expirationDate, int location, int quantity){
        this.locationControllerDAL.updateProductsQuantityInLocation(barCode,expirationDate, location, quantity, conn);
    }

    public List<CategoryDAL> getCategoryDALS() {
        return this.productControllerDAL.getCategoryDALS(conn);
    }

    public void setCategoryDALS(List<CategoryDAL> categoryDALS) {
        this.productControllerDAL.setCategoryDALS(categoryDALS);
    }

    public Map<Integer, DataSaleProductDAL> getSaleData() {
        return this.productControllerDAL.getSaleData(conn);
    }

    public void setSaleData(Map<Integer, DataSaleProductDAL> saleData) {
        this.productControllerDAL.setSaleData(saleData);
    }

    public void addCategory(CategoryDAL dal) {
        this.productControllerDAL.addCategory(dal, conn);
    }

    public CategoryDAL getCategoryDALByName(String name){
        return this.productControllerDAL.getCategoryDALByName(name);
    }

    public void appendSubCategory(String main, String sub) {
        this.productControllerDAL.appendSubCategory(main, sub, conn);
    }

    public void appendProductToCategoryDAL(String name, ProductDAL productDAL) {
        this.productControllerDAL.appendProductToCategoryDAL(name, productDAL,conn);
    }

    public void addNewDataSaleProduct(int barcode, DataSaleProductDAL dal) {
        this.productControllerDAL.addNewDataSaleProduct(barcode, dal, conn);
    }

    public void updateDataSaleProduct(int barcode, DataSaleProductDAL dataSaleProductDAL){
        this.productControllerDAL.updateDataSaleProduct(barcode, dataSaleProductDAL, conn);
    }

    public void deleteProductFromCategory (ProductDAL productDAL, CategoryDAL categoryDAL){
        this.productControllerDAL.deleteProductFromCategory(productDAL, categoryDAL, conn);
    }

    public void addNewProduct (ProductDAL productDAL){
        this.productControllerDAL.addNewProduct(productDAL, conn);
    }

    public void updateProduct(ProductDAL productDAL){
        this.productControllerDAL.updateProduct(productDAL, conn);
    }

    public ProductDAL searchProduct (int barCode){
        return this.productControllerDAL.searchProduct(barCode, conn);
    }

    public void updateCategory (String categoryName, String newName){
        this.productControllerDAL.updateCategory(categoryName, newName, conn);
    }

    public void deleteCategory(String cName){
        this.productControllerDAL.deleteCategory(cName, conn);
    }

    public List<DefectDAL> getDefects() {
        return this.defectControllerDAL.getDefects();
    }

    public void setDefects(List<DefectDAL> defects) {
        this.defectControllerDAL.setDefects(defects);
    }

    public void addDefect (DefectDAL defectDAL){
        this.defectControllerDAL.addDefect(defectDAL, conn);
    }

    public List<CategoryDAL> getMySubCategoriesDAL(String name) {
        return this.productControllerDAL.getMySubCategoriesDAL(name, conn);
    }

    public List<ProductDAL>  getProductListDAL(String name) {
        return this.productControllerDAL.getProductListDAL(name, conn);
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
