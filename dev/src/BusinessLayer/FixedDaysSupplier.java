package BusinessLayer;

import PersistenceLayer.LoanSupplier;

import java.util.LinkedList;

public class FixedDaysSupplier extends Supplier {

    public FixedDaysSupplier(int companyID, String bankAccNum, String payCond, LinkedList<String> contactNames, String phoneNum){
        super(companyID, bankAccNum, payCond, contactNames, phoneNum);
    }

    public FixedDaysSupplier(LoanSupplier la){
        super(la.getCompanyID(), la.getBankAccNum(), la.getPayCond(), la.getContantNames(), la.getPhoneNum());
    }

    public void removeOrder(int orderID){
        for(Order o : orders){
            if(o.getID() == orderID){
                Order nextOrder = new Order(o.getSupplierID(), o.getETA().plusDays(7), o.getDateIssued(), o.getProducts());
                orders.remove(o);
                orders.add(nextOrder);
            }
        }
    }
}
