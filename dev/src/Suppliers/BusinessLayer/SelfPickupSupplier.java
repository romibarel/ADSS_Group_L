package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanSupplier;

import java.util.LinkedList;

public class SelfPickupSupplier extends Supplier {
    private String location;

    public SelfPickupSupplier(int companyID, String bankAccNum, String payCond, LinkedList<String> contactNames, String phoneNum, String location){
        super(companyID, bankAccNum, payCond, contactNames, phoneNum);
        this.location = location;
    }

    public SelfPickupSupplier(LoanSupplier la){
        super(la.getCompanyID(), la.getBankAccNum(), la.getPayCond(), la.getContantNames(), la.getPhoneNum());
    }

    public void removeOrder(int orderID){
        orders.removeIf(o -> o.getID() == orderID);
    }
}
