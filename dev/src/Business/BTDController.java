package Business;


import java.util.LinkedList;
import java.util.List;

public class BTDController {
    private static BTDController btd = null;
    private static BTIController bti;
    private List<Driver> drivers;
    private DeliveryArchive archive ;
    private Sections sections;
    private List<Location> locations;
    private List<Truck> trucks;
    private List<Delivery> deliveries;

    private BTDController(){

    }

    public static BTDController getBTD(){
        if (btd == null)
            btd = new BTDController();
        return btd;
    }

    public void setBTD(BTIController bti, LinkedList<Driver> drivers, Sections sections, List<Location> locations, LinkedList<Truck> trucks){
        BTDController.bti = bti;
        this.drivers = drivers;
        this.sections = sections;
        this.trucks = trucks;
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
