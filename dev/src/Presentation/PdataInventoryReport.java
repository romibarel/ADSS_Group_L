package Presentation;

public class PdataInventoryReport {
    int barcode;
    String productName;
    int amount;

    public PdataInventoryReport(int barcode , String productName , int amount){
        this.barcode =barcode;
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
