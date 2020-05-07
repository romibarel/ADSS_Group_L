package StorageAndSupplier.Presentation;

public class PdataInventoryReport {
    private int barcode;
    private String productName;
    private int amount;

    public PdataInventoryReport(int barcode , String productName , int amount){
        this.barcode = barcode;
        this. productName = productName;
        this.amount = amount;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }
}
