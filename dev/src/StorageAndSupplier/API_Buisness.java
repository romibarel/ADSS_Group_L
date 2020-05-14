package StorageAndSupplier;

import Storage.Buisness.Reports.DefectReport;
import Storage.Buisness.Reports.ProductReport;
import StorageAndSupplier.Presentation.PdataInventoryReport;
import StorageAndSupplier.Presentation.Pdefect;
import StorageAndSupplier.Presentation.Pproduct;
import Suppliers.BusinessLayer.*;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.*;


public interface API_Buisness {

    /*
     * Storage section
     * */

    void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation);

    void setMainCategory(String mainCategoryName);

    void setNewSubCategory(String subcategoryName, String MainCategoryName);

    void appendProductToCategory(int barCode, String categoryName);

    List<String> getListOfCategoriesNames ();

    void buyProduct(int barCode, String productName, int supplierID,
                    double price,double discount,Date expirationDate,
                    int amount, Date date, int location);

    boolean sellProduct(Date date, int barCode, int amount, Date expirationDate); //return true if need to alert, otherwise false

    void connectProductToCategory(String categoryName, int barcode);

    void setMinimumAmount(int barcode, int minimumAmount);

    List<String> getListOfProductsNames ();

    void addDefect(Date date, int barCode, int amount,
                   String reason, String creator, int location, Date expiration);

    void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount);

    void setPriceOfExistingProduct(int barcode, double newPrice);

    void setDiscountOfExistingProduct(int barcode, double newDiscount);

    DefectReport getDefectReport(Date today, Date fromDate);

    List<Pdefect> creatDefectReport(Date today, Date fromDate);

    ProductReport getTimeReport (Date today);

    String getProducteName(Integer barcode);

    int getProducteManufactor(Integer barcode);

    String getProducteAmount(Integer barcode);

    String getProducteMinAmount(Integer barcode);

    String getProducteDate(Integer barcode);

    void setManufactorforProduct(int barcode, int supplierID);

    String getDataSaleName(Integer barcode);

    void setNextSupply(int barcode, Date nextSupply);

    String getDataSalePrice(Integer barcode);

    String getDataSaleDiscount(Integer barcode);

    void setCategoryName(String categoryName, String newName);

    void deleteCategory(String categoryName);

    List<Date> getBarcodDates (int barcode);

    List<Integer> getLocationsByDate(int barcode , Date date);

    Integer getAmountByLocation(int barcode , Date date , Integer location);

    List<String> CategoriesOfInventoryReport(Date today);

    List<PdataInventoryReport> RepProdofInventoryReport (Date date , String category );

    List<String> CategoriesOfInventoryReport(Date today ,String category);

    Collection<List<String>> subcat(Date date);

    void creatInventoryReport(Date today);

    void exit();

    /*
    * SUPPLIERS SECTION
    * */

    void loadSystem();

    void addOrder(int orderID, LocalDateTime dateIssued, HashMap<Pproduct, Pair<Integer, Integer>> products);

    void addSupplier(String tag, String name, int id, String bankAccNum, String payCond, String phoneNum);

    boolean addAgreement(int supplierID, Pair<Pproduct, Pair<Integer, Integer>> agreementDetails);

    void addProduct(int supplierID, int productID, double price, String name, String manufacturer, LocalDateTime expiration);

    boolean removeOrder(int orderID);

    boolean removeSupplier(int supplierID);

    boolean removeProductFromOrder(int supplierID, int orderID, int productID);

    boolean removeSupplierContact(int supplierID, String phoneNum);

    boolean removeSupplierProduct(int supplierID, int productID);

    boolean removeSupplierAgreement(int supplierID, int agreementID);

    void reportArrival(Order arrivedOrder);

    void reportCancellation(Order cancelledOrder);

    boolean setSupplierCompanyID(int supplierID, int companyID);

    boolean setSupplierBankAccNum(int supplierID, String bankAccNum);

    boolean setSupplierPayCond(int supplierID, String payCond);

    boolean setSupplierPhoneNum(int supplierID, String phoneNum);

    boolean addSupplierContact(int supplierID, Pair<String, String> contact);

    boolean setAmountOfProductInOrder(int orderID, int productID, int amount);

    boolean setOrderETA(int orderID, LocalDateTime ETA);

    boolean setAgreementProdAmount(int agreementID, int amount);

    boolean setAgreementProdSale(int agreementID, int sale);

    Supplier getSupplier(int supplierID);

    Order getOrder(int orderID);

    LinkedList<Supplier> getSuppliers();

    LinkedList<Order> getOrders();

    LinkedList<Report> getReports();

    void checkOrdersArrivalStatus();

    void closeConnection();
}
