package Business;

import java.util.List;

public class BTIController {
    private List<Delivery> deliveries;
    private List<Supply> supplies;
    private List<DeliverDoc> documents;
    private List<Driver> drivers;
    private Sections sections;
    private List<Truck> trucks;

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    public List<DeliverDoc> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DeliverDoc> documents) {
        this.documents = documents;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Sections getSections() {
        return sections;
    }

    public void setSections(Sections sections) {
        this.sections = sections;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
