package DataAccess;
//import Business.Truck;

public class Truck {
    private int truckNum;
    private int plate;
    private int weighNeto;
    private int maxWeight;
    private String type;


    public Truck(){

    }

    public Truck(int truckNum, int plate, int weighNeto, int maxWeight, String type) {
        this.truckNum = truckNum;
        this.plate = plate;
        this.weighNeto = weighNeto;
        this.maxWeight = maxWeight;
        this.type = type;
    }

    public Truck(Truck truck) {
        this.truckNum = truck.getTruckNum();
        this.plate = truck.getPlate();
        this.weighNeto = truck.getWeighNeto();
        this.maxWeight = truck.getMaxWeight();
        this.type = truck.getType();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
