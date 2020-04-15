package Business;

import Interface.ITBController;

import java.util.Date;
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

    private BTIController(LinkedList<Supply> supplies, LinkedList<Driver> drivers, Sections sections, List<Location> locations, LinkedList<Truck> trucks){
        this.itb = ITBController.getITB();
        this.btd = BTDController.getBTD(drivers, sections, trucks);
        this.supplies = supplies;
        this.drivers = drivers;
        this.sections = sections;
        this.locations = locations;
        this.trucks = trucks;
    }

    public static BTIController getBTI(LinkedList<Supply> supplies, LinkedList<Driver> drivers, Sections sections, List<Location> locations, LinkedList<Truck> trucks){
        if (bti == null)
            bti = new BTIController(supplies, drivers, sections, locations, trucks);
        return bti;
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
