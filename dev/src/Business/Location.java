package Business;

import DataAccess.DalLocation;

public class Location {
    private String address;
    private int phone;
    private String associate;

    public Location(String address, int phone, String associate) {
        this.address = address;
        this.phone = phone;
        this.associate = associate;
    }

    public Location(DalLocation dalLocationDal) {
        if (dalLocationDal == null) {
            System.out.println("locations wasn't found");
            address = "address";
            associate = "associate";
        } else {
            this.address = dalLocationDal.getAddress();
            this.phone = dalLocationDal.getPhone();
            this.associate = dalLocationDal.getAssociate();
        }
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
