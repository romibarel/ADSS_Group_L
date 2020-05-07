package Storage.DAL.InventoryDAL;

import java.util.Date;

public class ProductDAL {
    private int barCode;
    private String productName;
    private String manufactor;
    private int amount;
    private int minAmount;
    private Date nextSupplyTime;

    public ProductDAL(int barCode, String productName, String manufactor, int amount, int minAmount, Date nextSupplyTime) {
        this.barCode = barCode;
        this.productName = productName;
        this.manufactor = manufactor;
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

    public String getManufactor() {
        return manufactor;
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

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
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
