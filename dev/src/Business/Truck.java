package Business;

public class Truck {
    private int truckNum;
    private int plate;
    private int weighNeto;
    private int maxWeight;

    private String type;//todo add to url
    private int currentLoad = 0;//todo is it neccessery


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }
}
