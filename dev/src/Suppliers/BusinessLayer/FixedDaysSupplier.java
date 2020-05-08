package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import Suppliers.PersistenceLayer.LoanOrder;
import Suppliers.PersistenceLayer.LoanProduct;
import Suppliers.PersistenceLayer.LoanSupplier;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class FixedDaysSupplier extends Supplier {

    public FixedDaysSupplier(String name, int companyID, String bankAccNum, String payCond, String phoneNum){
        super(name, companyID, bankAccNum, payCond, phoneNum);
    }

    public FixedDaysSupplier(LoanSupplier la){
        super(la);
    }

    public LoanSupplier getLoan(){
        LinkedList<LoanAgreement> la = new LinkedList<>();
        LinkedList<LoanOrder> lo = new LinkedList<>();
        LinkedList<LoanProduct> lp = new LinkedList<>();
        for(Agreement a : getAgreements())
            la.add(a.getLoan(getID()));
        for(Order o : getOrders())
            lo.add(o.getLoan());
        for(Product p : getProducts())
            lp.add(p.getLoan(getID()));
        return new LoanSupplier("FixedDays", getID(), name, getCompanyID(), getBankAccNum(), getPayCond(), getPhoneNum(), getContacts(), la, lo, lp);
    }

    public Order removeOrder(int orderID){
        for(Order o : orders){
            if(o.getID() == orderID){
                Order nextOrder = new Order(name, o.getSupplierID(), o.getDateIssued(), o.getProducts());
                nextOrder.setETA(assessOrderETA());
                orders.remove(o);
                orders.add(nextOrder);
                return nextOrder;
            }
        }
        return null;
    }

    public LocalDateTime assessOrderETA(){
        return LocalDateTime.now().plusDays(7);
    }
}
