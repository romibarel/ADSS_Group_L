package DataAccess;

import Business.DeliverDoc;
import Business.Location;
import Business.Supply;
import com.sun.istack.internal.localization.NullLocalizable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DALDeliveryDoc {
    private int num;
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
        this.destination = new DalLocation(deliveryDoc.getDestination(), null);
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
}
