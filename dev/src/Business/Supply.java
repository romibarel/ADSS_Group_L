package Business;

public class Supply {
    private String name;
    private int quant;

    public Supply(String name, int quant) {
        this.name = name;
        this.quant = quant;
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
