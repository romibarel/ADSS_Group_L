package BusinessLayer;

import PersistenceLayer.LoanProduct;

public class Product {
    private int catalogID;
    private double price;
    private String name;
    private String manufacturer;

    public Product(int catalogID, double price, String name, String manufacturer){
        this.catalogID = catalogID;
        this.price = price;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public Product(LoanProduct lp){
        catalogID = lp.getCatalogID();
        price = lp.getPrice();
        name = lp.getName();
        manufacturer = lp.getManufacturer();
    }

    public int getCatalogID(){
        return catalogID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getName() {
        return name;
    }
}
