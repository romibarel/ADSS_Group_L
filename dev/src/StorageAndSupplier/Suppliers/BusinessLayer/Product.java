package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanProduct;

import java.time.LocalDateTime;

public class Product {
    private int catalogID;
    private double finalPrice;
    private double price;
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;
    private double weight;

    public Product(int catalogID, double price, String name, String manufacturer, LocalDateTime expirationDate, double weight){
        this.catalogID = catalogID;
        this.price = price;
        finalPrice = price;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    public Product(LoanProduct lp){
        catalogID = lp.getCatalogID();
        price = lp.getPrice();
        finalPrice = price;
        name = lp.getName();
        manufacturer = lp.getManufacturer();
        expirationDate = lp.getExpirationDate();
        weight = lp.getWeight();
    }

    public LoanProduct getLoan(int supplierID){
        return new LoanProduct(supplierID, catalogID, price, name, manufacturer, expirationDate, weight);
    }

    public String toString(){
        return "Product: " + name + " catalogID #" + catalogID + " for " + price + "nis from " + manufacturer;
    }

    public Product duplicate(){
        return new Product(catalogID, price, name, manufacturer, expirationDate, weight);
    }

    public int getCatalogID(){
        return catalogID;
    }

    public double getOriginalPrice() {
        return price;
    }

    public double getFinalPrice() { return finalPrice; }

    public void setFinalPrice(double finalPrice){
        this.finalPrice = finalPrice;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getExpirationDate(){ return expirationDate; }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getWeight() {
        return weight;
    }
}
