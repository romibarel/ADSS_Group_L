package Suppliers.PersistenceLayer;

public class LoanProduct {
    private int catalogID;
    private double price;
    private String name;
    private String manufacturer;

    public LoanProduct(int catalogID, double price, String name, String manufacturer){
        this.catalogID = catalogID;
        this.price = price;
        this.name = name;
        this.manufacturer = manufacturer;
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
}
