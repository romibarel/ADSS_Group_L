package DataAccess;

import Business.DeliverDoc;
import com.sun.istack.internal.localization.NullLocalizable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DALDeliveryDoc {
    private int num;
    private List<DalSupply> deliveryList;
    private String destination;
    private Date estimatedTimeOfArrival;
    private Date estimatedDayOfArrival;

    public DALDeliveryDoc(int num, List<DalSupply> deliveryList, String destination, Date estimatedTimeOfArrival, Date estimatedDayOfArrival) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.destination = destination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.estimatedDayOfArrival = estimatedDayOfArrival;
        safe();
    }

    private void safe() {
        if (deliveryList == null)
            deliveryList = new LinkedList<>();
    }

    public DALDeliveryDoc(DeliverDoc deliveryDoc) {
        this.num = getNum();
        this.deliveryList = getDeliveryList();
        this.destination = getDestination();
        this.estimatedTimeOfArrival = getEstimatedTimeOfArrival();
        this.estimatedDayOfArrival = getEstimatedDayOfArrival();
        safe();
    }

    public DALDeliveryDoc() {
        safe();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<DalSupply> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<DalSupply> deliveryList) {
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
