package StorageAndSupplier.Presentation;

import java.time.LocalDateTime;

public class Pproduct {
    private int catalogID;
    private double price;
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;

    public Pproduct(int catalogID, double price, String name, String manufacturer, LocalDateTime expirationDate){
        this.catalogID = catalogID;
        this.price = price;
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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
