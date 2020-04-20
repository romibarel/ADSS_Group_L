package DataAccess;

import Business.BTDController;
import Business.BTIController;

import java.util.LinkedList;
import java.util.List;

public class DTBController {
    private static BTDController btdController;
    private static DTBController thisOne;
    private List<Driver> drivers;
    private DeliveryArchive archive;
    private List<Truck> trucks;
    private List<Location> locations;
    private Sections sections;

    private DTBController() {
    }

    public static DTBController getDTB() {
        if (thisOne == null) {
            thisOne = new DTBController();
            btdController = BTDController.getBTD();
        }
        return thisOne;
    }


  /*  *//**
     * @param drivers
     * @param archive
     * @param trucks
     * @param locations
     * @param sections
     *//*
    public void save(List<Business.Driver> drivers, Business.DeliveryArchive archive, List<Business.Truck> trucks, List<Business.Location> locations, Business.Sections sections) {
        this.drivers = save(drivers);
        this.archive = save(archive);
        this.trucks = save(trucks);
        this.locations = save(locations);
        this.sections = save(sections);
    }*/

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

    /*public void save(List<Business.Location> bLocations) {
        locations = new LinkedList<>();
        for (Business.Location l: bLocations ){
            locations.add(new Location(l.getAddress(), l.getPhone(), l.getAssociate()));
        }
    }*/

}
