package Business;

import java.util.List;

public class DeliverDoc {
    private int num;
    private List<Supply> deliveryList;
    private int truckWeight;
    private Truck truck;
    private Location location;
    private boolean approved;//todo add

    public Object getDestination() {
        return null;//todo
    }
//todo: haim we don't need truckWeight and truck here remember?? we need to have Location dest instead :D
    public DeliverDoc(int num, List<Supply> deliveryList, int truckWeight, Truck truck) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.truckWeight = truckWeight;
        this.truck = truck;
        approved = truckWeight < truck.getMaxWeight();
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

    /**
     * you need to check if the driver is good for the truck as well
     * @return
     */
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
