package Business;

import java.util.List;

public class DeliverDoc {
    private int num;
    private List<Supply> deliveryList;
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



    public DeliverDoc(int docNum, String s, List<Supply> supplies, Location destination) {

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




}
