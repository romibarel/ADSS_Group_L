package DataAccess;


import Business.Delivery;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class DalDelivery {
    private int id;
    private Date date;
    private Date departureTime;
    private int truckNum;
    private int truckWeight;
    private String driver;
    private String source;
    private HashMap<Integer, String> docToLocation;

//    private List<String> destinations;
//    private List<Integer> docs;

    /**
     * @param date
     * @param departureTime
     * @param truckNum
     * @param driver
     * @param source
     * @param destinations can be null for empty list
     * @param docs can be null for empty list
     */
    public DalDelivery(Date date, Date departureTime, int truckNum, String driver, String source, List<String> destinations, List<Integer> docs) {
        this.date = date;
        this.departureTime = departureTime;
        this.truckNum = truckNum;
        this.driver = driver;
        this.source = source;
        this.destinations = destinations!=null ? destinations : new LinkedList<>();
        this.docs = docs!=null ? docs : new LinkedList<>();
    }

    public DalDelivery(){

    }

    public DalDelivery(Delivery delivery) {

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

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public List<Integer> getDocs() {
        return docs;
    }

    public void setDocs(List<Integer> docs) {
        this.docs = docs;
    }
}
