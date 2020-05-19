package DataAccess;

import Business.Supply;

public class DalSupply {
    private String name;
    private int quant;

    public DalSupply(Supply sup) {
        this.name = sup.getName();
        this.quant = sup.getQuant();
    }

    public DalSupply() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
