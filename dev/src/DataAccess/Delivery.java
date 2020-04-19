package DataAccess;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Delivery {
    private Date date;
    private Date departureTime;
    private int truckNum;
    private String driver;
    private Location source;
    private List<Location> destinations;
    private List<Integer> docs;

    /**
     * @param date
     * @param departureTime
     * @param truckNum
     * @param driver
     * @param source
     * @param destinations can be null for empty list
     * @param docs can be null for empty list
     */
    public Delivery(Date date, Date departureTime, int truckNum, String driver, Location source, List<Location> destinations, List<Integer> docs) {
        this.date = date;
        this.departureTime = departureTime;
        this.truckNum = truckNum;
        this.driver = driver;
        this.source = source;
        this.destinations = destinations!=null ? destinations : new LinkedList<>();
        this.docs = docs!=null ? docs : new LinkedList<>();
    }

    /**
     * @param docNumber
     * @return
     */
    public boolean addDeliveryDoc(int docNumber)
    {
        return docs.add(docNumber);
    }

    public boolean removeDeliveryDoc(Integer docNumber)
    {
        return docs.remove(docNumber);
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public int getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(int truckNum) {
        this.truckNum = truckNum;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public List<Location> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Location> destinations) {
        this.destinations = destinations;
    }

    public List<Integer> getDocs() {
        return docs;
    }

    public void setDocs(List<Integer> docs) {
        this.docs = docs;
    }
}
