package Business;

public class Location {
    private String address;
    private int phone;
    private String associate;

    public Location(String address, int phone, String associate) {
        this.address = address;
        this.phone = phone;
        this.associate = associate;
    }

    public Location(DataAccess.Location locationDal) {
        this.address = locationDal.getAddress();
        this.phone = locationDal.getPhone();
        this.associate = locationDal.getAssociate();
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    public String getAssociate() {
        return associate;
    }

}
