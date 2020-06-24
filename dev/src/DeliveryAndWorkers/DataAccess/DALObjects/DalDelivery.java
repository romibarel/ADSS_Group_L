package DeliveryAndWorkers.DataAccess.DALObjects;


import DeliveryAndWorkers.Business.BuisnessObjects.DeliverDoc;
import DeliveryAndWorkers.Business.BuisnessObjects.Delivery;

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
    private boolean approved;
    private boolean delivered;


    public DalDelivery(int id, Date date, Date departureTime, int truckNum, int truckWeight, String driver, DalLocation source, int approved, int delivered) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.truckNum = truckNum;
        this.truckWeight = truckWeight;
        this.driver = driver;
        this.source = source;
        docs = new LinkedList<>();
        if (approved == 1)
            this.approved = true;
        else this.approved = false;
        if (delivered == 1)
            this.delivered = true;
        else this.delivered = false;
    }

    public DalDelivery(){

    }

    public DalDelivery(Delivery delivery) {
        this.id = delivery.getID();
        this.date = delivery.getDate();
        this.departureTime = delivery.getDepartureTime();
        this.truckNum = delivery.getTruckNum();
        this.truckWeight = delivery.getTruckWeight();
        this.driver = delivery.getDriver();
        this.source = new DalLocation(delivery.getSource(), false);
        this.approved = delivery.isApproved();
        this.delivered = delivery.isDelivered();
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

    public void setApproved(int approved){
        if (approved==1)
            this.approved = true;
        else this.approved = false;
    }

    public void setDelivered(int delivered){
        if (delivered==1)
            this.delivered = true;
        else this.delivered = false;
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

    public boolean getApproved() {
        return this.approved;
    }

    public boolean getDelivered() {
        return this.delivered;
    }
}
