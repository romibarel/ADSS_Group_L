package DataAccess;

public class Location {
    private boolean isBranch;
    private String address;
    private int phone;
    private String associate;

    public Location(){

    }

    public Location(boolean isBranch, String address, int phone, String associate) {
        this.isBranch = isBranch;
        this.address = address;
        this.phone = phone;
        this.associate = associate;
    }

    public boolean getIsBranch(){
        return isBranch;
    }

    public void setIsBranch(boolean isBranch){
        this.isBranch = isBranch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
    }

}
