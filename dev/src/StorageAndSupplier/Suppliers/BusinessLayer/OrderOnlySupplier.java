package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanAgreement;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanOrder;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanProduct;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanSupplier;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class OrderOnlySupplier extends Supplier {
    public OrderOnlySupplier(String name, int companyID, String bankAccNum, String payCond, String phoneNum){
        super(name, companyID, bankAccNum, payCond, phoneNum);
    }

    public OrderOnlySupplier(LoanSupplier la){
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
        return new LoanSupplier("OrderOnly", getID(), name, getCompanyID(), getBankAccNum(), getPayCond(), getPhoneNum(), getContacts(), la, lo, lp);
    }

    public Order removeOrder(int orderID){
        for(Order o : orders){
            if(o.getID() == orderID){
                orders.remove(o);
                break;
            }
        }
        return null;
    }

    public boolean setProductAmount(int productID, int amount, Order o){
        return o.setProductAmount(productID, amount);
    }

    public boolean setOrderETA(Order o, LocalDateTime ETA){
        return o.setETA(ETA);
    }

    public boolean addProductToOrder(Order o, Product p, int amount) {
        return o.addNewProduct(p, amount);
    }

    public LocalDateTime assessOrderETA(){
        return LocalDateTime.now().plusDays(3);
    }
}
