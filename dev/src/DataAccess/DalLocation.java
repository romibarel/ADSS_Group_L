package DataAccess;

import Business.Location;

public class DalLocation {
    private Boolean isBranch;
    private String address;
    private int phone;
    private String associate;

    public DalLocation(){

    }

    public DalLocation(Boolean isBranch, String address, int phone, String associate) {
        this.isBranch = isBranch;
        this.address = address;
        this.phone = phone;
        this.associate = associate;
    }

    public DalLocation(Boolean isBranch, Location location) {
        this.isBranch = isBranch;
        this.address = location.getAddress();
        this.phone = location.getPhone();
        this.associate = location.getAssociate();
    }

    public DalLocation(Location location, Boolean isBranch){
        this.isBranch = isBranch;
        this.address = location.getAddress();
        this.associate = location.getAssociate();
        this.phone = location.getPhone();
    }

    public boolean getIsBranch(){
        return isBranch;
    }

    public void setIsBranch(Boolean isBranch){
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
