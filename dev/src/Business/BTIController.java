package Business;

import Interface.ITBController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BTIController {
    private static BTIController bti = null;
    private static ITBController itb;
    private static BTDController btd;
    private List<Delivery> deliveries;
    private List<Supply> supplies;
    private List<DeliverDoc> documents;
    private List<Driver> drivers;
    private Sections sections;
    private List<Location> locations;
    private List<Truck> trucks;

    private BTIController(){

    }

    public static BTIController getBTI(){
        if (bti == null)
            bti = new BTIController();
        return bti;
    }

    public void set(ITBController itb, BTDController btd, List<String[]> supplies, List<String[]> drivers, List<String[]> sections, List<String[]> locations, List<String[]> trucks){
        BTIController.itb = itb;
        BTIController.btd = btd;

        this.supplies = new LinkedList<>();
        for (String[] combo : supplies){
            Supply sup = new Supply(combo[0], Integer.parseInt(combo[1]));
            this.supplies.add(sup);
        }

        this.drivers = new LinkedList<>();
        for (String[] combo : drivers){
            Driver driver = new Driver();
            this.drivers.add(driver);
        }

        this.sections = new Sections();

        this.locations = new LinkedList<>();
        for (String[] combo : locations){
            Location loc = new Location();
            this.locations.add(loc);
        }

        this.trucks = new LinkedList<>();
        for (String[] combo : trucks){
            Truck truck = new Truck();
            this.trucks.add(truck);
        }
    }

    public void createDoc(int docNum, String[] doc){

    }

    public String createDelivery(Date date, Date time, int truckInt, String driverName, String sourceName, List<Integer> docNums){
        Truck truck = null;
        for (Truck t : trucks){
            if (t.getTruckNum() == truckInt)
                truck = t;
        }
        if (truck == null)
            return "This truck doesn't exist.";

        Driver driver = null;
        for(Driver d : drivers){
            if (d.getName.eqauls(driverName))
                driver = d;
        }
        if (driver == null)
            return "This driver doesn't exist.";

        Location source = null;
        for (Location l : locations){
            if (l.getAddress().equals(sourceName))
                source = l;
        }
        if (source == null)
            return "The source doesn't exist.";

        List<DeliverDoc> docs =new LinkedList<>();
        for (DeliverDoc doc : documents){
            if (docNums.contains(doc.getNum()))
                docs.add(doc);
        }
        if (docs.size() != docNums.size())
            return "Some delivery docs weren't added.";

        List<Location> destinations = new LinkedList<>();
        for (DeliverDoc doc : documents){
            if (locations.contains(doc.getDestination()))
                destinations.add(doc.getDestination());
        }
        if (locations.size() != docs.size())
            return "Some of the destinations wenren't added.";
        Delivery delivery = new Delivery(date, time, truck, driver, source, destinations, docs);
        if(!(delivery.isApproved()))
            return false;
        deliveries.add(delivery);
        return true;
    }

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
