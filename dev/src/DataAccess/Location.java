package DataAccess;

public class Location {
    private String address;
    private int phone;
    private String associate;

    public Location(String address, int phone, String associate) {
        this.address = address;
        this.phone = phone;
        this.associate = associate;
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
