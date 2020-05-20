package Business.BuisnessObjects;

import DataAccess.DALObjects.DALDeliveryDoc;
import DataAccess.DALObjects.DalDelivery;
import javafx.util.Pair;

import java.util.*;

public class Delivery {
    private Date date;
    private Date departureTime;
    private int truckNum;
    private int truckWeight;
    private String driver;
    private Location source;
    private HashMap<DeliverDoc, Location> docLoc;
    private boolean approved;


    public Delivery(Date date, Date departureTime, Truck truck, String driver, boolean goodLicenses, Location source, List<DeliverDoc> docs, int truckWeight) {
        this.date = date;
        this.departureTime = departureTime;
        this.truckNum = truck.getTruckNum();
        this.driver = driver;
        this.approved = goodLicenses;
        this.source = source;
        this.truckWeight = truckWeight;
        docLoc = new HashMap<>();
        for (DeliverDoc doc : docs) {
            docLoc.put(doc, doc.getDestination());
        }

    }

    public Delivery(DalDelivery dalDelivery, List<DALDeliveryDoc> docs) {
        this.date = dalDelivery.getDate();
        this.departureTime = dalDelivery.getDepartureTime();
        this.truckNum = dalDelivery.getTruckNum();
        this.driver = dalDelivery.getDriver();
        source = null;
        if (dalDelivery.getSource().getIsBranch())
            source = new Branch(dalDelivery.getSource());
        else source= new Supplier(dalDelivery.getSource());
        this.truckWeight = dalDelivery.getTruckWeight();
        this.approved = true;
        docLoc = new HashMap<>();
        for (DALDeliveryDoc daldoc : docs)
        {
            Location dest = null;
            if (daldoc.getDestination().getIsBranch())
                dest = new Branch(daldoc.getDestination());
            else dest= new Supplier(daldoc.getDestination());
            docLoc.put(new DeliverDoc(daldoc), dest);
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
            if (date.before(doc.getEstimatedDayOfArrival())) {
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

    public List<DeliverDoc> getDocsWOSource(){
        List<DeliverDoc> docs = new LinkedList<>();
        List<DeliverDoc> withSource = getDocs();
        for (int i=1; i<withSource.size(); i++)
            docs.add(withSource.get(i));
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

    public int getTruckWeight() {
        return truckWeight;
    }

    public void setTruckWeight(int truckWeight) {
        this.truckWeight = truckWeight;
    }
}
