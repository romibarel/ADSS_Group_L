package Business;


import java.util.LinkedList;
import java.util.List;

public class BTDController {
    private static BTDController btd = null;//todo romi i think you wanted to do DTBcontroller
    private static BTIController bti;
    private List<Driver> drivers;
    private DeliveryArchive archive ;
    private Sections sections;
    private List<Location> locations;
    private List<Truck> trucks;
    private List<Delivery> deliveries;

    private BTDController(LinkedList<Driver> drivers, Sections sections, List<Location> locations, LinkedList<Truck> trucks){
        this.bti = BTIController.getBTI(null, drivers, sections, locations, trucks);
        this.drivers = drivers;
        this.sections = sections;
        this.trucks = trucks;
    }

    public static BTDController getBTD(LinkedList<Driver> drivers, Sections sections, List<Location> locations, LinkedList<Truck> trucks){
        if (btd == null)
            btd = new BTDController(drivers, sections, locations, trucks);
        return btd;
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
