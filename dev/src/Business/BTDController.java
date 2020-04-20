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

    private BTDController(){

    }

    public static BTDController getBTD(){
        if (thisOne == null)
            thisOne = new BTDController();
        return thisOne;
    }


    public void set(List<Driver> drivers, Sections sections, List<Location> locations, List<Truck> trucks){
        BTDController.bti = BTIController.getBTI()
        BTDController.dataTb = DTBController.getDTB();
        this.drivers = drivers;
        this.sections = sections;
        this.trucks = trucks;

//todo contunue here
//        this.dataTb = dataTb;
        this.bti = bti;
        this.archive  = archive ;
        this.sections = sections;
        this.drivers = drivers;
        this.locations = locations;
        this.trucks = trucks;
        this.deliveries = deliveries;


    }

    public void set(DTBController dataTb, DeliveryArchive archive, List<Delivery> deliveries
            ,BTIController bti, List<Driver> drivers, Sections sections, List<Location> locations, List<Truck> trucks){
//        this.dataTb = dataTb;
//        this.bti = bti;
//        this.archive  = archive ;
//        this.sections = sections;
//        this.drivers = drivers;
//        this.locations = locations;
//        this.trucks = trucks;
//        this.deliveries = deliveries;
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
