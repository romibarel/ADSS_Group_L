import java.util.Date;

public class ProductPurchase {
    private int barCode;
    private String productName;
    private String supplier;
    private double price;
    private double discount;
    private int amount;
    private Date expirationDate;
    private int location;

    public ProductPurchase(int barCode, String productName, String supplier, double price, double discount, int amount, Date expirationDate, int location) {
        this.barCode = barCode;
        this.productName = productName;
        this.supplier = supplier;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.location = location;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}

