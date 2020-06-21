package StorageAndSupplier.Storage.Buisness;

import StorageAndSupplier.Storage.Buisness.Defects.DefectController;
import StorageAndSupplier.Storage.Buisness.Invenrory.ProductController;
import StorageAndSupplier.Storage.Buisness.Locations.LocationController;
import StorageAndSupplier.Storage.Buisness.Reports.DefectReport;
import StorageAndSupplier.Storage.Buisness.Reports.ProductReport;
import StorageAndSupplier.Storage.Buisness.Reports.ReportController;
import StorageAndSupplier.Storage.Buisness.Transactions.TransactionController;
import StorageAndSupplier.Storage.DAL.DataAccess;
import Presentation.PdataInventoryReport;
import Presentation.Pdefect;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.*;

public class Singletone_Storage_Management {

    private static Singletone_Storage_Management instance = null;
    private ReportController report;
    private DefectController defects;
    private TransactionController transactions;
    private LocationController locations;
    private ProductController inventory;

    private Singletone_Storage_Management() {
        this.report = new ReportController();
        this.defects = new DefectController();
        this.transactions = new TransactionController();
        this.locations = new LocationController();
        this.inventory = new ProductController();
    }

    public void restor(){
        defects.restore();
        report.restore();
        locations.restore();
        inventory.restore();
    }

    public static Singletone_Storage_Management getInstance(){

        if (instance == null)
            instance = new Singletone_Storage_Management();

        return instance;
    }

    public ReportController getReport(){
        return this.report;
    }

    public DefectController getDefects(){
        return this.defects;
    }

    public TransactionController getTransactions(){
        return transactions;
    }

    public LocationController getLocations(){
        return locations;
    }

    public ProductController getInventory(){
        return inventory;
    }

    public void buyProduct(int barCode, String productName, int supplierID, double price,
                           double discount,Date expirationDate, int amount, Date date,int location) { //barCode, productName, price, amount, date, manufactor, location, expiratiomDate
        this.transactions.purchase(barCode, productName, supplierID, price,discount,expirationDate,amount,date,location);
    }


    public void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation) {
        this.locations.moveProduct(barCode, expiration, amount, fromLocation, toLocation);
    }


    public void setMainCategory(String mainCategoryName) {
        this.inventory.setMainCategory(mainCategoryName);
    }


    public void setNewSubCategory(String subcategoryName, String MainCategoryName) {
        this.inventory.setNewSubCategory(subcategoryName, MainCategoryName);
    }


    public void appendProductToCategory(int barCode, String categoryName) {
        this.inventory.appendProductToCategory(barCode, categoryName);
    }


    public List<String> getListOfCategoriesNames() {
        return this.inventory.getListOfCategoriesNames();
    }


    public List<String> getListOfProductsNames() {
        return this.inventory.getListOfProductsNames();
    }


    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        this.defects.addDefect(date, barCode, amount, reason, creator, location, expiration);
    }


    public boolean sellProduct(Date date, int barCode, int amount, Date expirationDate) {
        return this.transactions.sellProduct(date, barCode, amount, expirationDate);
    }


    public void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount) {
        this.inventory.setSaleInfoOfNewProduct(barcode, productName, price, discount);
    }


    public void setPriceOfExistingProduct(int barcode, double newPrice) {
        this.inventory.setPriceOfExistingProduct(barcode, newPrice);
    }


    public void setDiscountOfExistingProduct(int barcode, double newDiscount) {
        this.inventory.setDiscountOfExistingProduct(barcode, newDiscount);
    }


    public DefectReport getDefectReport(Date today, Date fromDate) {
        return this.report.getDefectReports(today, fromDate);
    }

    public List<Pdefect> creatDefectReport(Date today, Date fromDate) {
        return this.report.creatDefectReport(today, fromDate);
    }


    public ProductReport getTimeReport(Date today) {
        return this.report.getTimeReports(today);
    }


    public void connectProductToCategory(String categoryName, int barcode) {
        this.inventory.connectProductToCategory(categoryName, barcode);
    }


    public void setMinimumAmount(int barcode, int minimumAmount) {
        this.getInventory().setMinimumAmount(barcode, minimumAmount);
    }

    public void setManufactorforProduct(int barcode , int supplierID){
        this.getInventory().setManufactorForProduct(barcode, supplierID);
    }


    public void setNextSupply(int barcode , Date nextSupply){
        this.getInventory().setNextSupply(barcode, nextSupply);
    }


    public String getProducteName(Integer barcode) {
        return this.getInventory().getProductName(barcode);
    }


    public int getProducteManufactor(Integer barcode) {
        return this.getInventory().getProductManufactor(barcode);
    }


    public String getProducteAmount(Integer barcode) {
        return this.getInventory().getProductAmount(barcode);
    }


    public String getProducteMinAmount(Integer barcode) {
        return this.getInventory().getProductMinAmount(barcode);
    }


    public String getProducteDate(Integer barcode) {
        return this.getInventory().getProductNextSupplyTime(barcode);
    }


    public String getDataSaleName(Integer barcode) {
        return this.getInventory().getSaleDataName(barcode);
    }


    public String getDataSalePrice(Integer barcode) {
        double d = this.getInventory().getSaleDataPrice(barcode);
        return String.valueOf(d);
    }


    public String getDataSaleDiscount(Integer barcode) {
        double d = this.getInventory().getSaleDataDiscount(barcode);
        return String.valueOf(d);
    }


    public void setCategoryName(String categoryName, String newName) {
        this.inventory.setCategoryName(categoryName, newName);
    }


    public void deleteCategory(String categoryName) {
        this.inventory.deleteCategory(categoryName);
    }

    public List<Date> getBarcodDates (int barcode){return this.locations.getBarcodDates(barcode);}

    public List<Integer> getLocationsByDate(int barcode , Date date){
        return this.locations.getLocationsByDate(barcode , date);
    }

    public Integer getAmountByLocation(int barcode , Date date , Integer location){
        return this.locations.getAmountByLocation(barcode , date , location);
    }

    public List<String> CategoriesOfInventoryReport(Date today){
        return this.report.getMainCategories(today);
    }

    public List<String> CategoriesOfInventoryReport(Date today ,String category){
        return this.report.getSubCateroies(today , category);
    }

    public List<PdataInventoryReport> RepProdofInventoryReport (Date date , String category ){
       return report.dataOfReport(date , category);
    }

    public Collection<List<String>> subcat(Date date){return report.subcat(date);}

    public void creatInventoryReport(Date today){
        this.report.creatInventoryReport(today);
    }

    public void exit(){
        DataAccess.getInstance().close();
        System.exit(0);
    }

    public void setConnection(Connection conn) {
        DataAccess.getInstance().setConnection(conn);
    }
}

