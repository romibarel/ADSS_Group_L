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
    ProductReport getTimeReport (Date today);
}
