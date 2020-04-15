package Business;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Delivery {
    private Date date;
    private Time departureTime;
    private int truckNum;
    private String driver;
    private Location source;
    private HashMap<DeliverDoc, Location> docLoc;

    public Delivery(Date date, Time time, Truck truck, Driver driver, Location source, List<Location> locations, List<DeliverDoc> docs){
        //need to check if date and time are acceptable dont know how
        if (!driver.getLicenses().contains(truck.getType)){
            alert();
        }
        else {
            this.date = date;
            this.departureTime = time;
            truckNum = truck.getTruckNum();
            this.driver = driver.getName();
            this.source = source;
            docLoc = new HashMap<>();
            for (DeliverDoc doc : docs) {
                for (Location location : locations) {
                    if (doc.getDestination().equals(location.getAddress())) {
                        docLoc.put(doc, location);
                    }
                }
            }
        }
    }



    public Date getDate() {
        return date;
    }

    public Time getDepartureTime() {
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
}
