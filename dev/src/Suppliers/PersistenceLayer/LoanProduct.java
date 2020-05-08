package Suppliers.PersistenceLayer;

import java.time.LocalDateTime;

public class LoanProduct {
    private int catalogID;
    private int supplierID;
    private double finalPrice;
    private double price;
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;

    public LoanProduct(int supplierID, int catalogID, double price, String name, String manufacturer, LocalDateTime expirationDate){
        this.supplierID = supplierID;
        this.catalogID = catalogID;
        this.price = price;
        finalPrice = price;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getSupplierID() {
        return supplierID;
    }
}
