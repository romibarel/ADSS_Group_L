package DataAccess.DALObjects;


import Business.BuisnessObjects.DeliverDoc;
import Business.BuisnessObjects.Delivery;

import java.util.*;


public class DalDelivery {
    private int id;
    private Date date;
    private Date departureTime;
    private int truckNum;
    private int truckWeight;
    private String driver;
    private DalLocation source;
    private List<DALDeliveryDoc> docs;


    public DalDelivery(int id, Date date, Date departureTime, int truckNum, int truckWeight, String driver, DalLocation source) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckNum = truckNum;
        this.truckWeight = truckWeight;
        this.driver = driver;
        this.source = source;
        docs = new LinkedList<>();
    }

    public DalDelivery(){

    }

    public DalDelivery(Delivery delivery, int id) {
        this.id = id;
        this.date = delivery.getDate();
        this.departureTime = delivery.getDepartureTime();
        this.truckNum = delivery.getTruckNum();
        this.truckWeight = delivery.getTruckWeight();
        this.driver = delivery.getDriver();
        this.source = new DalLocation(delivery.getSource(), false);
        docs = new LinkedList<>();
        Set<DeliverDoc> realDocs =  delivery.getDocLoc().keySet();
        for (DeliverDoc document :realDocs) {
            docs.add(new DALDeliveryDoc(document));
        }

    }

    public boolean addDeliveryDoc(DALDeliveryDoc docNumber)
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

    public DalLocation getSource() {
        return source;
    }

    public void setSource(DalLocation source) {
        this.source = source;
    }

    public List<DALDeliveryDoc> getDocs() {
        return docs;
    }

    public int getTruckWeight() {
        return truckWeight;
    }

    public void setTruckWeight(int truckWeight) {
        this.truckWeight = truckWeight;
    }
}
