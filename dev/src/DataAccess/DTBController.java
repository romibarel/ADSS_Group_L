package DataAccess;

import Business.BTDController;
import Business.BTIController;

import java.util.List;

public class DTBController {
    private static BTDController BusinessBoss;
    private static DTBController thisOne;
    private static BTIController bti;
    private List<Driver> drivers;
    private DeliveryArchive archive;
    private List<Truck> trucks;
    private List<Location> locations;
    private Sections sections;
    private List<Delivery> deliveries;
    private BTDController bController = null;

    private DTBController() {

    }

    public static DTBController getDTB() {
        if (thisOne == null)
            thisOne = new DTBController();
        return thisOne;
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
