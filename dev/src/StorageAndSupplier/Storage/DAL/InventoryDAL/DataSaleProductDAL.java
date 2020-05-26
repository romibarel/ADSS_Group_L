package StorageAndSupplier.Storage.DAL.InventoryDAL;

public class DataSaleProductDAL {
    private int barCode;
    private String productName;
    private double price;
    private double discount;

    public DataSaleProductDAL(int barCode, String productName, double price, double discount) {
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
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
}
