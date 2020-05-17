package DataAccess;

import Business.DeliverDoc;

import java.util.Date;
import java.util.List;

public class DALDeliveryDoc {
    private int num;
    private List<Supply> deliveryList;
    private String destination;
    private Date estimatedTimeOfArrival;
    private Date estimatedDayOfArrival;

    public DALDeliveryDoc(int num, List<Supply> deliveryList, String destination, Date estimatedTimeOfArrival, Date estimatedDayOfArrival) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.destination = destination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.estimatedDayOfArrival = estimatedDayOfArrival;
    }

    public DALDeliveryDoc(DeliverDoc deliveryDoc) {
        this.num = getNum();
        this.deliveryList = getDeliveryList();
        this.destination = getDestination();
        this.estimatedTimeOfArrival = getEstimatedTimeOfArrival();
        this.estimatedDayOfArrival = getEstimatedDayOfArrival();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Supply> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Supply> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public void setEstimatedTimeOfArrival(Date estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public Date getEstimatedDayOfArrival() {
        return estimatedDayOfArrival;
    }

    public void setEstimatedDayOfArrival(Date estimatedDayOfArrival) {
        this.estimatedDayOfArrival = estimatedDayOfArrival;
    }
}
