package StorageAndSupplier.Storage.DAL.ReportsDAL;

public class ProductRepDataDAL {
    private int barCode;
    private String productName;
    private int amount;


    public ProductRepDataDAL(int barCode, String productName, int amount) {
        this.barCode = barCode;
        this.productName = productName;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
