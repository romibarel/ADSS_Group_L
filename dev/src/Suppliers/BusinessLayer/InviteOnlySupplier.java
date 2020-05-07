package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanSupplier;

import java.util.LinkedList;

public class InviteOnlySupplier extends Supplier {

    public InviteOnlySupplier(int companyID, String bankAccNum, String payCond, LinkedList<String> contactNames, String phoneNum){
        super(companyID, bankAccNum, payCond, contactNames, phoneNum);
    }

    public InviteOnlySupplier(LoanSupplier la){
        super(la.getCompanyID(), la.getBankAccNum(), la.getPayCond(), la.getContantNames(), la.getPhoneNum());
    }

    public void removeOrder(int orderID){
        orders.removeIf(o -> o.getID() == orderID);
    }
}
