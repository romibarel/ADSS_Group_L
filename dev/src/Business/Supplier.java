package Business;

import DataAccess.DalLocation;

public class Supplier extends Location {
    public Supplier (String address, int phone, String associate){
        super(address, phone, associate);
    }

    public Supplier(DalLocation dalLocation){
        super(dalLocation.getAddress(), dalLocation.getPhone(), dalLocation.getAssociate());
    }
}
