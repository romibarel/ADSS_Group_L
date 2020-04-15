package Business;

import java.util.List;

public class DeliverDoc {
    private int num;
    private List<Supply> deliveryList;
    private int truckWeight;
    private Truck truck;
    private boolean approved;

    public int getNum() {
        return num;
    }

    public List<Supply> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Supply> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public int getTruckWeight() {
        return truckWeight;
    }

    public void setTruckWeight(int truckWeight) {
        this.truckWeight = truckWeight;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
