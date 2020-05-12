package Storage.Buisness.Invenrory;

import Storage.DAL.InventoryDAL.ProductDAL;

import java.util.Date;

public class Product {

    private int barCode;
    private String productName;
    private int supplierID;
    private int amount;
    private int minAmount;
    private  Date nextSupplyTime;

    public Product(int barCode, String productName, int supplierID, int amount, int minAmount, Date nextSupplyTime) {
        this.barCode = barCode;
        this.productName = productName;
        this.supplierID = supplierID;
        this.amount = amount;
        this.minAmount = minAmount;
        this.nextSupplyTime = nextSupplyTime;
    }

    public Product(ProductDAL productDAL) {
        this.barCode = productDAL.getBarCode();
        this.productName = productDAL.getProductName();
        this.supplierID = productDAL.getManufactor();
        this.amount = productDAL.getAmount();
        this.minAmount = productDAL.getMinAmount();
        this.nextSupplyTime = productDAL.getNextSupplyTime();
    }

    public ProductDAL createDAL(){
        return new ProductDAL(barCode, productName, supplierID, amount, minAmount, nextSupplyTime);
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
