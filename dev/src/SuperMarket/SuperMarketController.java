package SuperMarket;

import DeliveryAndWorkers.Presentation.PTIDelController;
import DeliveryAndWorkers.Presentation.WorkerMenu;
import StorageAndSupplier.Presentation.PdataInventoryReport;
import StorageAndSupplier.Presentation.Pdefect;
import StorageAndSupplier.Presentation.Pproduct;
import StorageAndSupplier.Singltone_Supplier_Storage_Manager;
import StorageAndSupplier.Storage.Buisness.Reports.DefectReport;
import StorageAndSupplier.Storage.Buisness.Reports.ProductReport;
import StorageAndSupplier.Suppliers.BusinessLayer.Order;
import StorageAndSupplier.Suppliers.BusinessLayer.Report;
import StorageAndSupplier.Suppliers.BusinessLayer.Supplier;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.*;

public class SuperMarketController implements SuperMarket {

    private static SuperMarketController instance;
    private Singltone_Supplier_Storage_Manager SandSController;
    private PTIDelController deliveryController;

    private SuperMarketController() {
        SandSController = Singltone_Supplier_Storage_Manager.getInstance();
        deliveryController = PTIDelController.getPTI();
    }

    public static SuperMarketController getInstance(){
        if (instance == null)
            instance = new SuperMarketController();
        return instance;
    }

    @Override
    public int checkPermission(String username, String password) {
        return SandSController.checkPermission(username, password);
    }

    @Override
    public void initialize() {
        SandSController.initialize();
    }

    @Override
    public void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation) {
        SandSController.moveProduct(barCode, expiration, amount, fromLocation, toLocation);
    }

    @Override
    public void setMainCategory(String mainCategoryName) {
        SandSController.setMainCategory(mainCategoryName);
    }

    @Override
    public void setNewSubCategory(String subcategoryName, String MainCategoryName) {
        SandSController.setNewSubCategory(subcategoryName, MainCategoryName);
    }

    @Override
    public void appendProductToCategory(int barCode, String categoryName) {
        SandSController.appendProductToCategory(barCode, categoryName);
    }

    @Override
    public List<String> getListOfCategoriesNames() {
        return SandSController.getListOfProductsNames();
    }

    @Override
    public void buyProduct(int barCode, String productName, int supplierID, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        SandSController.buyProduct(barCode, productName, supplierID, price, discount, expirationDate, amount, date, location);
    }

    @Override
    public boolean sellProduct(Date date, int barCode, int amount, Date expirationDate) {
        return SandSController.sellProduct(date, barCode, amount, expirationDate);
    }

    @Override
    public void connectProductToCategory(String categoryName, int barcode) {
        SandSController.connectProductToCategory(categoryName, barcode);
    }

    @Override
    public void setMinimumAmount(int barcode, int minimumAmount) {
        SandSController.setMinimumAmount(barcode, minimumAmount);
    }

    @Override
    public List<String> getListOfProductsNames() {
        return SandSController.getListOfProductsNames();
    }

    @Override
    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        SandSController.addDefect(date, barCode, amount, reason, creator, location, expiration);
    }

    @Override
    public void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount) {
        SandSController.setSaleInfoOfNewProduct(barcode, productName, price, discount);
    }

    @Override
    public void setPriceOfExistingProduct(int barcode, double newPrice) {
        SandSController.setPriceOfExistingProduct(barcode, newPrice);
    }

    @Override
    public void setDiscountOfExistingProduct(int barcode, double newDiscount) {
        SandSController.setDiscountOfExistingProduct(barcode, newDiscount);
    }

    @Override
    public DefectReport getDefectReport(Date today, Date fromDate) {
        return SandSController.getDefectReport(today, fromDate);
    }

    @Override
    public List<Pdefect> creatDefectReport(Date today, Date fromDate) {
        return SandSController.creatDefectReport(today, fromDate);
    }

    @Override
    public ProductReport getTimeReport(Date today) {
        return SandSController.getTimeReport(today);
    }

    @Override
    public String getProducteName(Integer barcode) {
        return SandSController.getProducteName(barcode);
    }

    @Override
    public int getProducteManufactor(Integer barcode) {
        return SandSController.getProducteManufactor(barcode);
    }

    @Override
    public String getProducteAmount(Integer barcode) {
        return SandSController.getProducteAmount(barcode);
    }

    @Override
    public String getProducteMinAmount(Integer barcode) {
        return SandSController.getProducteMinAmount(barcode);
    }

    @Override
    public String getProducteDate(Integer barcode) {
        return SandSController.getProducteDate(barcode);
    }

    @Override
    public void setManufactorforProduct(int barcode, int supplierID) {
        SandSController.setManufactorforProduct(barcode, supplierID);
    }

    @Override
    public String getDataSaleName(Integer barcode) {
        return SandSController.getDataSaleName(barcode);
    }

    @Override
    public void setNextSupply(int barcode, Date nextSupply) {
        SandSController.setNextSupply(barcode, nextSupply);
    }

    @Override
    public String getDataSalePrice(Integer barcode) {
        return SandSController.getDataSalePrice(barcode);
    }

    @Override
    public String getDataSaleDiscount(Integer barcode) {
        return SandSController.getDataSaleDiscount(barcode);
    }

    @Override
    public void setCategoryName(String categoryName, String newName) {
        SandSController.setCategoryName(categoryName, newName);
    }

    @Override
    public void deleteCategory(String categoryName) {
        SandSController.deleteCategory(categoryName);
    }

    @Override
    public List<Date> getBarcodDates(int barcode) {
        return SandSController.getBarcodDates(barcode);
    }

    @Override
    public List<Integer> getLocationsByDate(int barcode, Date date) {
        return SandSController.getLocationsByDate(barcode, date);
    }

    @Override
    public Integer getAmountByLocation(int barcode, Date date, Integer location) {
        return SandSController.getAmountByLocation(barcode, date, location);
    }

    @Override
    public List<String> CategoriesOfInventoryReport(Date today) {
        return SandSController.CategoriesOfInventoryReport(today);
    }

    @Override
    public List<PdataInventoryReport> RepProdofInventoryReport(Date date, String category) {
        return SandSController.RepProdofInventoryReport(date, category);
    }

    @Override
    public List<String> CategoriesOfInventoryReport(Date today, String category) {
        return SandSController.CategoriesOfInventoryReport(today, category);
    }

    @Override
    public Collection<List<String>> subcat(Date date) {
        return SandSController.subcat(date);
    }

    @Override
    public void creatInventoryReport(Date today) {
        SandSController.creatInventoryReport(today);
    }

    @Override
    public void exit() {
        SandSController.exit();
    }

    @Override
    public void loadSystem() {
        SandSController.loadSystem();
    }

    @Override
    public void updateStatIDs() {
        SandSController.updateStatIDs();
    }

    @Override
    public boolean addOrder(int supplierID, LocalDateTime dateIssued, HashMap<Pproduct, Pair<Integer, Integer>> products) {
        return SandSController.addOrder(supplierID, dateIssued, products);
    }

    @Override
    public boolean addSupplier(String tag, String name, int id, String bankAccNum, String payCond, String phoneNum, String location) {
        return SandSController.addSupplier(tag, name, id, bankAccNum, payCond, phoneNum, location);
    }

    @Override
    public boolean addAgreement(int supplierID, Pair<Pproduct, Pair<Integer, Integer>> agreementDetails) {
        return SandSController.addAgreement(supplierID, agreementDetails);
    }

    @Override
    public boolean addProduct(int supplierID, int productID, double price, String name, String manufacturer, LocalDateTime expiration) {
        return SandSController.addProduct(supplierID, productID, price, name, manufacturer, expiration);
    }

    @Override
    public boolean addNewProductToOrder(int productID, int orderID, int supplierID, int amount) {
        return SandSController.addNewProductToOrder(productID, orderID, supplierID, amount);
    }

    @Override
    public boolean removeOrder(int orderID) {
        return SandSController.removeOrder(orderID);
    }

    @Override
    public boolean removeSupplier(int supplierID) {
        return SandSController.removeSupplier(supplierID);
    }

    @Override
    public boolean removeProductFromOrder(int supplierID, int orderID, int productID) {
        return SandSController.removeProductFromOrder(supplierID, orderID, productID);
    }

    @Override
    public boolean removeSupplierContact(int supplierID, String phoneNum) {
        return SandSController.removeSupplierContact(supplierID, phoneNum);
    }

    @Override
    public boolean removeSupplierProduct(int supplierID, int productID) {
        return SandSController.removeSupplierProduct(supplierID, productID);
    }

    @Override
    public boolean removeSupplierAgreement(int supplierID, int agreementID) {
        return SandSController.removeSupplierAgreement(supplierID, agreementID);
    }

    @Override
    public boolean reportArrival(Order arrivedOrder) {
        return SandSController.reportArrival(arrivedOrder);
    }

    @Override
    public boolean reportCancellation(Order cancelledOrder) {
        return SandSController.reportCancellation(cancelledOrder);
    }

    @Override
    public boolean setSupplierCompanyID(int supplierID, int companyID) {
        return SandSController.setSupplierCompanyID(supplierID, companyID);
    }

    @Override
    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum) {
        return SandSController.setSupplierBankAccNum(supplierID, bankAccNum);
    }

    @Override
    public boolean setSupplierPayCond(int supplierID, String payCond) {
        return SandSController.setSupplierPayCond(supplierID, payCond);
    }

    @Override
    public boolean setSupplierPhoneNum(int supplierID, String phoneNum) {
        return SandSController.setSupplierPhoneNum(supplierID, phoneNum);
    }

    @Override
    public boolean addSupplierContact(int supplierID, Pair<String, String> contact) {
        return SandSController.addSupplierContact(supplierID, contact);
    }

    @Override
    public boolean setAmountOfProductInOrder(int orderID, int productID, int amount) {
        return SandSController.setAmountOfProductInOrder(orderID, productID, amount);
    }

    @Override
    public boolean setOrderETA(int orderID, LocalDateTime ETA) {
        return SandSController.setOrderETA(orderID, ETA);
    }

    @Override
    public boolean setAgreementProdAmount(int agreementID, int amount) {
        return SandSController.setAgreementProdAmount(agreementID, amount);
    }

    @Override
    public boolean setAgreementProdSale(int agreementID, int sale) {
        return SandSController.setAgreementProdSale(agreementID, sale);
    }

    @Override
    public Supplier getSupplier(int supplierID) {
        return SandSController.getSupplier(supplierID);
    }

    @Override
    public Order getOrder(int orderID) {
        return SandSController.getOrder(orderID);
    }

    @Override
    public LinkedList<Supplier> getSuppliers() {
        return SandSController.getSuppliers();
    }

    @Override
    public Report getReport(int reportID) {
        return SandSController.getReport(reportID);
    }

    @Override
    public LinkedList<Order> getOrders() {
        return SandSController.getOrders();
    }

    @Override
    public LinkedList<Report> getReports() {
        return SandSController.getReports();
    }

    @Override
    public void checkOrdersArrivalStatus() {
        SandSController.checkOrdersArrivalStatus();
    }

    @Override
    public void closeConnection() {
        SandSController.closeConnection();
    }

    /*
    * Delivery and Workers Section
    * */

    public PTIDelController getPTI() {
        return this.deliveryController;
    }

    @Override
    public void setup() {
        deliveryController.setup();
    }

    @Override
    public void start() {
        deliveryController.start();
    }

    @Override
    public void mainFunc() {
        WorkerMenu.mainFunc();
    }
}
