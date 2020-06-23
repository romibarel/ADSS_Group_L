package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanAgreement;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanOrder;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanProduct;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanSupplier;

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
                Order nextOrder = new Order(o.getSupplierID(), o.getDateIssued(), o.getProducts(), o.getSrcAddress(), o.getDestAddress());
                nextOrder.setETA(assessOrderETA());
                orders.remove(o);
                orders.add(nextOrder);
                return nextOrder;
            }
        }
        return null;
    }

    public boolean setProductAmount(int productID, int amount, Order o){
        if(o.getETA().isAfter(LocalDateTime.now().plusDays(1))) // Check if order is at least one day before arrival
            return o.setProductAmount(productID, amount);
        System.out.println("BAAAAADD1");
        return false;
    }

    public boolean setOrderETA(Order o, LocalDateTime ETA){
        System.out.println(o.getETA().toString());
        System.out.println(LocalDateTime.now().plusDays(1).toString());
        if(o.getETA().isAfter(LocalDateTime.now().plusDays(1)))
            return o.setETA(ETA);
        System.out.println("BAAAAADD2");
        return false;
    }

    public boolean addProductToOrder(Order o, Product p, int amount) {
        if(o.getETA().isAfter(LocalDateTime.now().plusDays(1)))
            return o.addNewProduct(p, amount);
        System.out.println("BAAAAADD3");
        return false;
    }

    public LocalDateTime assessOrderETA(){
        return LocalDateTime.now().plusDays(7);
    }
}
