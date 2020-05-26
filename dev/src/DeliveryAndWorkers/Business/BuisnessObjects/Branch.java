package DeliveryAndWorkers.Business.BuisnessObjects;

import DeliveryAndWorkers.DataAccess.DALObjects.DalLocation;

public class Branch extends Location {
    public Branch (String address, int phone, String associate){
        super(address, phone, associate);
    }
    public Branch(DalLocation dalLocation){
        super(dalLocation.getAddress(), dalLocation.getPhone(), dalLocation.getAssociate());
    }
}
