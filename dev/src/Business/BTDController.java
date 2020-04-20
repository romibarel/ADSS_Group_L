package Business;


import DataAccess.DTBController;

import java.util.LinkedList;
import java.util.List;

public class BTDController {
    private static BTDController thisOne;
    private static DTBController dataTb;
    private static BTIController bti;
    private List<Driver> drivers;
    private DeliveryArchive archive ;
    private Sections sections;
    private List<Location> locations;
    private List<Truck> trucks;
    private List<Delivery> deliveries;

    //todo deliveries moved inside archoce

    private BTDController(){
        BTDController.dataTb = DTBController.getDTB();
        BTDController.bti = BTIController.getBTI();
        drivers = new LinkedList<>();
        locations = new LinkedList<>();
        trucks = new LinkedList<>();
        deliveries = new LinkedList<>();
    }

    public static BTDController getBTD(){
        if (thisOne == null)
            thisOne = new BTDController();
        return thisOne;
    }

    /**
     * Sections and Archive must be given all other can be null for not changeing
     * @param drivers
     * @param archive
     * @param sections
     * @param locations
     * @param trucks
     * @param deliveries
     */
    public void set(List<Driver> drivers, DeliveryArchive archive, Sections sections, List<Location> locations, List<Truck> trucks, List<Delivery> deliveries) {
        this.archive = archive;
        this.sections = sections;
        if (drivers != null) {
            this.drivers = drivers;
        }
        if (locations != null) {
            this.locations = locations;
        }
        if (trucks != null) {
            this.trucks = trucks;
        }
        if (deliveries != null) {
            this.deliveries = deliveries;
        }
    }

    public boolean addDelivery(Delivery delivery)
    {
        if (deliveries == null)
            deliveries = new LinkedList<>();
        return deliveries.add(delivery);
    }

    public boolean addTruck(Truck truck)
    {
        if (trucks == null)
            trucks = new LinkedList<>();
        return trucks.add(truck);
    }

    public boolean addLocation(Location location)
    {
        if (locations == null)
            locations = new LinkedList<>();
        return locations.add(location);
    }

    public boolean addDriver(Driver driver)
    {
        if (drivers == null)
            drivers = new LinkedList<>();
        return drivers.add(driver);
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
