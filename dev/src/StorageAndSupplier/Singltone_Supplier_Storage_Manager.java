package StorageAndSupplier;

import Storage.Buisness.Reports.DefectReport;
import Storage.Buisness.Reports.ProductReport;
import Storage.Buisness.Singletone_Storage_Management;
import StorageAndSupplier.Presentation.PdataInventoryReport;
import StorageAndSupplier.Presentation.Pdefect;
import StorageAndSupplier.Presentation.Pproduct;
import Suppliers.BusinessLayer.*;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class Singltone_Supplier_Storage_Manager implements API_Buisness{

    public static  final int MILK = 1;
    public static  final int TUNA = 2;
    public static  final int SHAMPOO = 3;
    public static  final int CHEESE = 4;
    public static  final int SHOCKO = 5;

    public static final int STORAGE = 1;

    private static Singltone_Supplier_Storage_Manager instance;
    private Singletone_Storage_Management storage_management;
    private SystemController supplier_management;

    private Singltone_Supplier_Storage_Manager() {
        this.storage_management = Singletone_Storage_Management.getInstance();
        this.supplier_management = SystemController.getInstance();
        Connection conn;
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") +"\\dev\\data\\storage.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            storage_management.setConnection(conn);
            supplier_management.setConnection(conn);
        } catch ( Exception e) { }
    }

    public static Singltone_Supplier_Storage_Manager getInstance(){
        if (instance == null)
            instance = new Singltone_Supplier_Storage_Manager();
        return instance;
    }

    /*
     * Storage section
     * */

    public void initialize(){
        this.storage_management.restor();
    }

    @Override
    public void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation) {
        this.storage_management.moveProduct(barCode, expiration, amount, fromLocation, toLocation);
    }

    @Override
    public void setMainCategory(String mainCategoryName) {
        this.storage_management.setMainCategory(mainCategoryName);
    }

    @Override
    public void setNewSubCategory(String subcategoryName, String MainCategoryName) {
        this.storage_management.setNewSubCategory(subcategoryName, MainCategoryName);
    }

    @Override
    public void appendProductToCategory(int barCode, String categoryName) {
        this.storage_management.appendProductToCategory(barCode, categoryName);
    }

    @Override
    public List<String> getListOfCategoriesNames() {
        return this.storage_management.getListOfCategoriesNames();
    }

    @Override
    public void buyProduct(int barCode, String productName, int supplierID, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        this.storage_management.buyProduct(barCode, productName, supplierID, price, discount, expirationDate, amount, date, location);
    }

    @Override
    public boolean sellProduct(Date date, int barCode, int amount, Date expirationDate) {
        if (this.storage_management.sellProduct(date, barCode, amount, expirationDate)){ //need to make urgent order -> product under minimum
            LocalDateTime ETA = this.supplier_management.urgentOrder(barCode, Integer.parseInt(getProducteMinAmount(barCode))*2);
            //TODO: urgent order now returns the date ^^^^^
            if (ETA!=null){
                Date supplyTime = convertToDateViaSqlTimestamp(ETA);
                this.storage_management.setNextSupply(barCode, supplyTime);
            }

            return true; //need to alert to presentation
        }
        else{
            return false;  //no need to alert to presentation
        }
    }

    @Override
    public void connectProductToCategory(String categoryName, int barcode) {
        this.storage_management.connectProductToCategory(categoryName, barcode);
    }

    @Override
    public void setMinimumAmount(int barcode, int minimumAmount) {
        this.storage_management.setMinimumAmount(barcode, minimumAmount);
    }

    @Override
    public List<String> getListOfProductsNames() {
        return this.storage_management.getListOfProductsNames();
    }

    @Override
    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        this.storage_management.addDefect(date, barCode, amount, reason, creator, location, expiration);
    }

    @Override
    public void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount) {
        this.storage_management.setSaleInfoOfNewProduct(barcode, productName, price, discount);
    }

    @Override
    public void setPriceOfExistingProduct(int barcode, double newPrice) {
        this.storage_management.setPriceOfExistingProduct(barcode, newPrice);
    }

    @Override
    public void setDiscountOfExistingProduct(int barcode, double newDiscount) {
        this.storage_management.setDiscountOfExistingProduct(barcode,newDiscount);
    }

    @Override
    public DefectReport getDefectReport(Date today, Date fromDate) {
        return this.storage_management.getDefectReport(today, fromDate);
    }

    @Override
    public List<Pdefect> creatDefectReport(Date today, Date fromDate) {
        return this.storage_management.creatDefectReport(today, fromDate);
    }

    @Override
    public ProductReport getTimeReport(Date today) {
        return this.storage_management.getTimeReport(today);
    }

    @Override
    public String getProducteName(Integer barcode) {
        return this.storage_management.getProducteName(barcode);
    }

    @Override
    public int getProducteManufactor(Integer barcode) {
        return this.storage_management.getProducteManufactor(barcode);
    }

    @Override
    public String getProducteAmount(Integer barcode) {
        return this.storage_management.getProducteAmount(barcode);
    }

    @Override
    public String getProducteMinAmount(Integer barcode) {
        return this.storage_management.getProducteMinAmount(barcode);
    }

    @Override
    public String getProducteDate(Integer barcode) {
        return this.storage_management.getProducteDate(barcode);
    }

    @Override
    public void setManufactorforProduct(int barcode, int supplierID) {
        this.storage_management.setManufactorforProduct(barcode, supplierID);
    }

    @Override
    public String getDataSaleName(Integer barcode) {
        return this.storage_management.getDataSaleName(barcode);
    }

    @Override
    public void setNextSupply(int barcode, Date nextSupply) {
        this.storage_management.setNextSupply(barcode, nextSupply);
    }

    @Override
    public String getDataSalePrice(Integer barcode) {
        return this.storage_management.getDataSalePrice(barcode);
    }

    @Override
    public String getDataSaleDiscount(Integer barcode) {
        return this.storage_management.getDataSaleDiscount(barcode);
    }

    @Override
    public void setCategoryName(String categoryName, String newName) {
        this.storage_management.setCategoryName(categoryName, newName);
    }

    @Override
    public void deleteCategory(String categoryName) {
        this.storage_management.deleteCategory(categoryName);
    }

    @Override
    public List<Date> getBarcodDates(int barcode) {
        return this.storage_management.getBarcodDates(barcode);
    }

    @Override
    public List<Integer> getLocationsByDate(int barcode, Date date) {
        return this.storage_management.getLocationsByDate(barcode, date);
    }

    @Override
    public Integer getAmountByLocation(int barcode, Date date, Integer location) {
        return this.storage_management.getAmountByLocation(barcode, date, location);
    }

    @Override
    public List<String> CategoriesOfInventoryReport(Date today) {
        return this.storage_management.CategoriesOfInventoryReport(today);
    }

    @Override
    public List<PdataInventoryReport> RepProdofInventoryReport(Date date, String category) {
        return this.storage_management.RepProdofInventoryReport(date, category);
    }

    @Override
    public List<String> CategoriesOfInventoryReport(Date today, String category) {
        return this.storage_management.CategoriesOfInventoryReport(today, category);
    }

    @Override
    public Collection<List<String>> subcat(Date date) {
        return this.storage_management.subcat(date);
    }

    @Override
    public void creatInventoryReport(Date today) {
        this.storage_management.creatInventoryReport(today);
    }

    @Override
    public void exit() {
        this.storage_management.exit();
    }

    /*
    * Suppliers section
    * */

    @Override
    public void loadSystem(){
        supplier_management.loadSystem();
    }

    @Override
    public void addOrder(int supplierID, LocalDateTime dateIssued, HashMap<Pproduct, Pair<Integer, Integer>> pproducts){
        //TODO: add order returns LocalDateTime or null if user entered an invalid order
        HashMap<Product, Pair<Integer, Integer>> products = new HashMap<>();
        for(Map.Entry<Pproduct, Pair<Integer, Integer>> e : pproducts.entrySet())
            products.put(new Product(e.getKey().getCatalogID(), e.getKey().getPrice(), e.getKey().getName(), e.getKey().getManufacturer(), e.getKey().getExpirationDate()), e.getValue());
        LocalDateTime ETA = supplier_management.addOrder(new Order(supplierID, dateIssued, products));
    }

    @Override
    public void addSupplier(String tag, String name, int id, String bankAccNum, String payCond, String phoneNum){
        if(tag.equals("FixedDays"))
            supplier_management.addSupplier(new FixedDaysSupplier(name, id, bankAccNum, payCond, phoneNum));
        else if(tag.equals("OrderOnly"))
            supplier_management.addSupplier(new OrderOnlySupplier(name, id, bankAccNum, payCond, phoneNum));
        else supplier_management.addSupplier(new SelfPickupSupplier(name, id, bankAccNum, payCond, phoneNum, ""));
    }

    @Override
    public boolean addAgreement(int supplierID, Pair<Pproduct, Pair<Integer, Integer>> agreementDetails){
        Pproduct pp = agreementDetails.getKey();
        Product p = new Product(pp.getCatalogID(), pp.getPrice(), pp.getName(), pp.getManufacturer(), pp.getExpirationDate());
        Pair<Product, Pair<Integer, Integer>> details = new Pair<>(p, agreementDetails.getValue());
        return supplier_management.addAgreement(supplierID, new Agreement(details));
    }

    @Override
    public void addProduct(int supplierID, int productID, double price, String name, String manufacturer, LocalDateTime expiration){
        supplier_management.addProduct(supplierID, new Product(productID, price, name, manufacturer, expiration));
    }

    @Override
    public boolean removeOrder(int orderID){
        return supplier_management.removeOrder(orderID);
    }

    @Override
    public boolean removeSupplier(int supplierID){
        return supplier_management.removeSupplier(supplierID);
    }

    @Override
    public boolean removeProductFromOrder(int supplierID, int orderID, int productID){
        return supplier_management.removeProductFromOrder(supplierID, orderID, productID);
    }

    @Override
    public boolean removeSupplierContact(int supplierID, String phoneNum){
        return supplier_management.removeSupplierContact(supplierID, phoneNum);
    }

    @Override
    public boolean removeSupplierProduct(int supplierID, int productID){
        return supplier_management.removeSupplierProduct(supplierID, productID);
    }

    @Override
    public boolean removeSupplierAgreement(int supplierID, int agreementID){
        return supplier_management.removeSupplierAgreement(supplierID, agreementID);
    }

    @Override
    public void reportArrival(Order arrivedOrder){
        supplier_management.reportArrival(arrivedOrder);
    }

    @Override
    public void reportCancellation(Order cancelledOrder){
        supplier_management.reportCancellation(cancelledOrder);
    }

    @Override
    public boolean setSupplierCompanyID(int supplierID, int companyID){
        return supplier_management.setSupplierCompanyID(supplierID, companyID);
    }

    @Override
    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum){
        return supplier_management.setSupplierBankAccNum(supplierID, bankAccNum);
    }

    @Override
    public boolean setSupplierPayCond(int supplierID, String payCond){
        return supplier_management.setSupplierPayCond(supplierID, payCond);
    }

    @Override
    public boolean setSupplierPhoneNum(int supplierID, String phoneNum){
        return supplier_management.setSupplierPhoneNum(supplierID, phoneNum);
    }

    @Override
    public boolean addSupplierContact(int supplierID, Pair<String, String> contact){
        return supplier_management.addSupplierContact(supplierID, contact);
    }

    @Override
    public boolean setAmountOfProductInOrder(int orderID, int productID, int amount){
        return supplier_management.setAmountOfProductInOrder(orderID, productID, amount);
    }

    @Override
    public boolean setOrderETA(int orderID, LocalDateTime ETA){
        //TODO: sets ETA of the order associated with orderID to a new one, return true is succeeded and false otherwise
        if(supplier_management.setOrderETA(orderID, ETA)){

        }
        return false;
    }

    @Override
    public boolean setAgreementProdAmount(int agreementID, int amount){
        return supplier_management.setAgreementProdAmount(agreementID, amount);
    }

    @Override
    public boolean setAgreementProdSale(int agreementID, int sale){
        return supplier_management.setAgreementProdSale(agreementID, sale);
    }

    @Override
    public Supplier getSupplier(int supplierID){
        return supplier_management.getSupplier(supplierID);
    }

    @Override
    public Order getOrder(int orderID){
        return supplier_management.getOrder(orderID);
    }

    @Override
    public LinkedList<Supplier> getSuppliers(){
        return supplier_management.getSuppliers();
    }

    @Override
    public LinkedList<Order> getOrders(){
        return supplier_management.getAllOrders();
    }

    @Override
    public Report getReport(int reportID){
        return supplier_management.getReport(reportID);
    }

    @Override
    public LinkedList<Report> getReports(){
        return supplier_management.getReports();
    }

    @Override
    public void checkOrdersArrivalStatus(){
        //TODO: returned a list of all arrived orders with all the details you will ever need
        /*
        Order.getSupplierID() - returns the supplierID of the supplier that supplied this order
        Order.getDateIssued() - returns the date the order was ordered

        Order.getProducts() - return a hashmap: <Product, Pair<Integer, Integer>> -> <The Product, Pair<Amount that was ordered, Discount given for the amount (may be 0)>>

        for(Map.Entry<Product, Pair<Integer, Integer>> e : Order.getProducts().entrySet()){
            From the Product (e.getKey()) you can get the barCode (Product.getCatalogID()), price (Product.getOriginalPrice()) and expiration date (Product.getExpirationDate())
            From the Pair (e.getValue()) you can get the amount (getKey()) and the discount (getValue())
        }
        */
        LinkedList<Order> arrivedOrders = supplier_management.checkOrdersArrivalStatus();
        for (Order o : arrivedOrders){
            int supplierID = o.getSupplierID();
            for (Map.Entry<Product, Pair<Integer, Integer>> e : o.getProducts().entrySet()){
                int barcode = e.getKey().getCatalogID();
                String productName = e.getKey().getName();
                double price = e.getKey().getOriginalPrice();
                Date expiration = convertToDateViaSqlTimestamp(e.getKey().getExpirationDate());
                Date today = convertToDateViaSqlTimestamp(LocalDateTime.now());
                int amount = e.getValue().getKey();
                double discount = e.getValue().getValue();
                this.storage_management.buyProduct(barcode, productName, supplierID, price, discount, expiration, amount, today, STORAGE);
            }
        }
    }

    @Override
    public void closeConnection(){
        supplier_management.closeConnection();
    }


    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}