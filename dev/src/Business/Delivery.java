package Business;

import javafx.util.Pair;

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

    public Delivery(Date date, Date time, Truck truck, String driver, boolean goodLicenses, Location source, List<Location> destinations, List<DeliverDoc> docs, int truckWeight){
        if (!goodLicenses)
            approved = false;
        else {
            this.date = date;
            this.departureTime = time;
            truckNum = truck.getTruckNum();
            this.driver = driver;
            this.source = source;
            docLoc = new HashMap<>();
            for (DeliverDoc doc : docs) {
                docLoc.put(doc, doc.getDestination());
            }
            approved = true;
        }
    }

    public Date[] getDuration(){
        Date[] duration = new Date[4];
        duration[0] = date;
        duration[1] = departureTime;
        duration[2] = getLastDateDoc().getEstimatedDayOfArrival();
        duration[3] = getLastTime();
        return duration;
    }

    private DeliverDoc getLastDateDoc(){
        DeliverDoc ret = getDocs().get(0);
        Date date = getDocs().get(0).getEstimatedDayOfArrival();
        for (DeliverDoc doc : getDocs()){
            if (date.compareTo(doc.getEstimatedDayOfArrival()) < 0) {
                date = doc.getEstimatedDayOfArrival();
                ret = doc;
            }
        }
        return ret;
    }

    private Date getLastTime(){
        Date time = getLastDateDoc().getEstimatedTimeOfArrival();
        for (DeliverDoc doc : getDocs()){
            if (doc.getEstimatedDayOfArrival().compareTo(getLastDateDoc().getEstimatedDayOfArrival()) == 0
            && time.compareTo(doc.getEstimatedTimeOfArrival()) < 0)
                time = doc.getEstimatedTimeOfArrival();
        }
        return time;
    }

    public List<Pair<String, Date[]>> getEstimatedArrivals(){
        List<Pair<String, Date[]>> estimatedArrivals = new LinkedList<>();
        for (DeliverDoc doc : getDocs()){
            Date[] dateTime = new Date[2];
            dateTime[0] = doc.getEstimatedDayOfArrival();
            dateTime[1] = doc.getEstimatedTimeOfArrival();
            Pair<String, Date[]> newPair = new Pair<>(doc.getDestination().getAddress(), dateTime);
            estimatedArrivals.add(newPair);
        }
        return estimatedArrivals;
    }

    public List<DeliverDoc> getDocs(){
        List<DeliverDoc> docs = new LinkedList<>();
        Iterator<DeliverDoc> iter = docLoc.keySet().iterator();
        docs.addAll(docLoc.keySet());
        return docs;
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
