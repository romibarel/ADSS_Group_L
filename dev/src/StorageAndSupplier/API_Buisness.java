package StorageAndSupplier;

import Storage.Buisness.Reports.DefectReport;
import Storage.Buisness.Reports.ProductReport;
import StorageAndSupplier.Presentation.PdataInventoryReport;
import StorageAndSupplier.Presentation.Pdefect;
import Suppliers.BusinessLayer.*;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;


public interface API_Buisness {

    /*
     * Storage section
     * */

    void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation);

    void setMainCategory(String mainCategoryName);

    void setNewSubCategory(String subcategoryName, String MainCategoryName);

    void appendProductToCategory(int barCode, String categoryName);

    List<String> getListOfCategoriesNames ();

    void buyProduct(int barCode, String productName, String supplier,
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

    String getProducteManufactor(Integer barcode);

    String getProducteAmount(Integer barcode);

    String getProducteMinAmount(Integer barcode);

    String getProducteDate(Integer barcode);

    void setManufactorforProduct(int barcode, String newName);

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

    public void loadSystem();

    public void addOrder(Order order);

    public void addSupplier(Supplier supplier);

    public void removeOrder(int orderID);

    public void removeSupplier(int supplierID);

    public void reportArrival(Order arrivedOrder);

    public void reportCancellation(Order cancelledOrder);

    public boolean setSupplierCompanyID(int supplierID, int companyID);

    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum);

    public boolean setSupplierPayCond(int supplierID, String payCond);

    public boolean setSupplierPhoneNum(int supplierID, String phoneNum);

    public boolean setSupplierContactNames(int supplierID, LinkedList<String> contacts);

    public boolean setOrderProducts(int orderID, HashMap<Product, Pair<Integer, Integer>> products);

    public boolean setOrderETA(int orderID, LocalDate ETA);

    public boolean setAgreementProdAmount(int agreementID, int amount);

    public boolean setAgreementProdCond(int agreementID, int cond);

    public Supplier getSupplierByID(int supplierID);

    public Order getOrderByID(int orderID);

    public LinkedList<Supplier> getSuppliers();

    public LinkedList<Order> getOrders();

    public LinkedList<Report> getReports();
}
