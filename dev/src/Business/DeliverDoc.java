package Business;

import java.util.List;

public class DeliverDoc {
    private int num;
    private List<Supply> deliveryList;
    private Truck truck;    //todo Romi we need it?
    private Location destination;

    /**
     * without Truck
     * @param num
     * @param deliveryList
     * @param destination
     */
    public DeliverDoc(int num, List<Supply> deliveryList, Location destination) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.destination = destination;
    }

    /**
     * with Truck
     * @param num
     * @param deliveryList
     * @param truck
     * @param destination
     */
    public DeliverDoc(int num, List<Supply> deliveryList, Truck truck, Location destination) {
        this.num = num;
        this.deliveryList = deliveryList;
        this.truck = truck;
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


    public Truck getTruck() {
        return truck;
    }   //todo delete?

    public void setTruck(Truck truck) {
        this.truck = truck;
    }      //todo delete?


}
