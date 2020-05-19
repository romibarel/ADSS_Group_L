package Business;

import DataAccess.DalSupply;

public class Supply {
    private String name;
    private int quant;

    public Supply(String name, int quant) {
        this.name = name;
        this.quant = quant;
    }

    public Supply(Supply other, int quant) {
        this.name = other.getName();
        this.quant = quant;
    }

    public Supply(DalSupply dalSup) {
        this.name = dalSup.getName();
        this.quant = dalSup.getQuant();
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
