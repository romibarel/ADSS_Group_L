package Business;

import java.util.Date;
import java.util.List;

public class DeliverDoc {
    private int num;
    private List<Supply> deliveryList;
    private Location destination;
    private Date estimatedTimeOfArrival;
    private Date estimatedDayOfArrival;

    public DeliverDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int num, List<Supply> deliveryList, Location destination) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.destination = destination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.estimatedDayOfArrival = estimatedDayOfArrival;
    }

    public DeliverDoc(int num, List<Supply> deliveryList, Location destination) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.destination = destination;
    }


    public Location getDestination() {
        return destination;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getNum() {
        return num;
    }

    public List<Supply> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Supply> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public Date getEstimatedDayOfArrival() {
        return estimatedDayOfArrival;
    }

    public Date getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }
}
