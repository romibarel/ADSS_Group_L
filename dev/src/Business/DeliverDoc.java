package Business;

import DataAccess.DALDeliveryDoc;
import DataAccess.DalSupply;

import java.util.Date;
import java.util.LinkedList;
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

    public DeliverDoc(DALDeliveryDoc daldoc) {
        this.num = daldoc.getNum();
        this.deliveryList = new LinkedList<>();
        this.destination = new Location(daldoc.getDestination());
        this.estimatedTimeOfArrival = daldoc.getEstimatedTimeOfArrival();
        this.estimatedDayOfArrival = daldoc.getEstimatedDayOfArrival();

        for (DalSupply dalSup : daldoc.getDeliveryList()){
            deliveryList.add(new Supply(dalSup));
        }
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
