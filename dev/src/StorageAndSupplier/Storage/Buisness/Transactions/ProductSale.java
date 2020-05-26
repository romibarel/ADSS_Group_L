package StorageAndSupplier.Storage.Buisness.Transactions;

public class ProductSale {
    private int barCode;
    private String productName;
    private double price;
    private double discount;
    private int amount;

    public ProductSale(int barCode, String productName, double price, double discount, int amount) {
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
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
}
