package Presentation;

import java.time.LocalDateTime;

public class Pproduct {
    private int catalogID;
    private double price;
    private String name;
    private String manufacturer;
    private LocalDateTime expirationDate;
    private double weight;

    public Pproduct(int catalogID, double price, String name, String manufacturer, LocalDateTime expirationDate, double weight){
        this.catalogID = catalogID;
        this.price = price;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }
}
