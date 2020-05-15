package Storage.Buisness.Reports;

import Storage.DAL.ReportsDAL.ProductRepDataDAL;

public class ProductRepData {
    private int barCode;
    private String productName;
    private int amount;


    public ProductRepData( int barCode, String productName, int amount) {
        this.barCode = barCode;
        this.productName = productName;
        this.amount = amount;

    }

    public ProductRepData (ProductRepDataDAL productRepDataDAL){
        this.barCode = productRepDataDAL.getBarCode();
        this.productName = productRepDataDAL.getProductName();
        this.amount = productRepDataDAL.getAmount();
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

    public ProductRepDataDAL createDAL() {
        return new ProductRepDataDAL(barCode, productName,amount);
    }
}
