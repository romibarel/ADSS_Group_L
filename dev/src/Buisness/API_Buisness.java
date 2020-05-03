package Buisness;

import Buisness.Reports.DefectReport;
import Buisness.Reports.ProductReport;
import Presentation.PdataInventoryReport;
import Presentation.Pdefect;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface API_Buisness {

    void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation);

    void setMainCategory(String mainCategoryName);

    void setNewSubCategory(String subcategoryName, String MainCategoryName);

    void appendProductToCategory(int barCode, String categoryName);

    List<String> getListOfCategoriesNames ();


    //UNTIL HERE EVERYTHING TESTED


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
}
