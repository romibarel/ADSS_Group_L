package Business;

public class Truck {
    private int truckNum;
    private int plate;
    private int weighNeto;
    private int maxWeight;


    public Truck(int truckNum, int plate, int weighNeto, int maxWeight, String type, int currentLoad) {
        this.truckNum = truckNum;
        this.plate = plate;
        this.weighNeto = weighNeto;
        this.maxWeight = maxWeight;
    }


    //the driver will check if he can use the truck?

    public int getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(int truckNum) {
        this.truckNum = truckNum;
    }

    public int getPlate() {
        return plate;
    }

    public void setPlate(int plate) {
        this.plate = plate;
    }

    public int getWeighNeto() {
        return weighNeto;
    }

    public void setWeighNeto(int weighNeto) {
        this.weighNeto = weighNeto;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

}
