package DeliveryAndWorkers.DataAccess.DALObjects;

import DeliveryAndWorkers.Business.BuisnessObjects.Branch;
import DeliveryAndWorkers.Business.BuisnessObjects.DeliverDoc;
import DeliveryAndWorkers.Business.BuisnessObjects.Supply;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DALDeliveryDoc {
    private int num;
    private int deliveryID;
    private List<DalSupply> supplyList;
    private DalLocation destination;
    private Date estimatedTimeOfArrival;
    private Date estimatedDayOfArrival;

    public DALDeliveryDoc(int num, List<DalSupply> deliveryList, DalLocation destination, Date estimatedTimeOfArrival, Date estimatedDayOfArrival) {
        this.num = num;
        this.supplyList = deliveryList;
        this.destination = destination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.estimatedDayOfArrival = estimatedDayOfArrival;
        safe();
    }

    private void safe() {
        if (supplyList == null)
            supplyList = new LinkedList<>();
    }

    public DALDeliveryDoc(DeliverDoc deliveryDoc) {
        this.num = deliveryDoc.getNum();
        boolean isBranch = deliveryDoc.getDestination() instanceof Branch;
        this.destination = new DalLocation(deliveryDoc.getDestination(), isBranch);
        this.estimatedTimeOfArrival = deliveryDoc.getEstimatedTimeOfArrival();
        this.estimatedDayOfArrival = deliveryDoc.getEstimatedDayOfArrival();
        supplyList = new LinkedList<>();
        for (Supply sup :deliveryDoc.getDeliveryList()) {
            supplyList.add(new DalSupply(sup));
        }
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
        return supplyList;
    }

    public void setDeliveryList(List<DalSupply> deliveryList) {
        this.supplyList = deliveryList;
    }

    public DalLocation getDestination() {
        return destination;
    }

    public void setDestination(DalLocation destination) {
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

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }
}
