package StorageAndSupplier.Storage.DAL.InventoryDAL;

import java.util.Date;

public class ProductDAL {
    private int barCode;
    private String productName;
    private int supplierID;
    private int amount;
    private int minAmount;
    private Date nextSupplyTime;

    public ProductDAL(int barCode, String productName, int supplierID, int amount, int minAmount, Date nextSupplyTime) {
        this.barCode = barCode;
        this.productName = productName;
        this.supplierID = supplierID;
        this.amount = amount;
        this.minAmount = minAmount;
        this.nextSupplyTime = nextSupplyTime;
    }

    public int getBarCode() {
        return barCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getManufactor() {
        return supplierID;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public Date getNextSupplyTime() {
        return nextSupplyTime;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setManufactor(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public void setNextSupplyTime(Date nextSupplyTime) {
        this.nextSupplyTime = nextSupplyTime;
    }
}
