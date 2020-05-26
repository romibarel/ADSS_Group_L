package StorageAndSupplier.Storage.Buisness.Invenrory;

import StorageAndSupplier.Storage.DAL.InventoryDAL.DataSaleProductDAL;

public class DataSaleProduct {
    private int barCode;
    private String productName;
    private double price;
    private double discount;

    public DataSaleProduct(int barCode, String productName, double price, double discount) {
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
    }

    public DataSaleProduct(DataSaleProductDAL dataSaleProduct) {
        this.barCode = dataSaleProduct.getBarCode();
        this.productName = dataSaleProduct.getProductName();
        this.price = dataSaleProduct.getPrice();
        this.discount = dataSaleProduct.getDiscount();
    }

    public DataSaleProductDAL createDAL() {
        return new DataSaleProductDAL(barCode, productName, price, discount);
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
