package DataAccess;

import Business.BTDController;

import java.util.List;

public class DTBController {
    private List<Driver> drivers;
    private DeliveryArchive archive;
    private List<Truck> trucks;
    private List<Location> locations;
    private Sections sections;
    private List<Delivery> deliveries;
    private BTDController bController = null;

    public DTBController(List<Driver> drivers, DeliveryArchive archive, List<Truck> trucks,
                         List<Location> locations, Sections sections, List<Delivery> deliveries) {
        this.drivers = drivers;
        this.archive = archive;
        this.trucks = trucks;
        this.locations = locations;
        this.sections = sections;
        this.deliveries = deliveries;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public DeliveryArchive getArchive() {
        return archive;
    }

    public void setArchive(DeliveryArchive archive) {
        this.archive = archive;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Sections getSections() {
        return sections;
    }

    public void setSections(Sections sections) {
        this.sections = sections;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
