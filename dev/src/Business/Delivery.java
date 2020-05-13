package Business;

import java.sql.Time;
import java.util.*;

public class Delivery {
    private Date date;
    private Date departureTime;
    private int truckNum;
    private String driver;
    private Location source;
    private HashMap<DeliverDoc, Location> docLoc;
    private boolean approved;

    public Delivery(Date date, Date time, Truck truck, Driver driver, Location source, List<Location> destinations, List<DeliverDoc> docs, int truckWeight){
        //need to check if date and time are acceptable dont know how
        if (!driver.getLicenses().contains(truck.getType())){
            approved = false;
        }
        else if (truck.getMaxWeight()<truckWeight)
            approved = false;
        else {
            this.date = date;
            this.departureTime = time;
            truckNum = truck.getTruckNum();
            this.driver = driver.getName();
            this.source = source;
            docLoc = new HashMap<>();
            for (DeliverDoc doc : docs) {
                docLoc.put(doc, doc.getDestination());
            }
            approved = true;
        }
    }

    public List<DeliverDoc> getDocs(){
        List<DeliverDoc> docs = new LinkedList<>();
        Iterator<DeliverDoc> iter = docLoc.keySet().iterator();
        docs.addAll(docLoc.keySet());
        return docs;
    }

    public void logDel(Delivery delivery) {
        //todo
    }

    public Date getDate() {
        return date;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public int getTruckNum() {
        return truckNum;
    }

    public String getDriver() {
        return driver;
    }

    public Location getSource() {
        return source;
    }

    public HashMap<DeliverDoc, Location> getDocLoc() {
        return docLoc;
    }

    public Collection<Location> getDestinations(){
        return docLoc.values();
    }

    public boolean isApproved(){
        return approved;
    }
}
