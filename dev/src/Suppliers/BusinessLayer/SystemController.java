package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.*;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class SystemController {
    private LinkedList<Supplier> suppliers = new LinkedList<>();
    private LinkedList<Report> reports = new LinkedList<>();
    private DataController dc;

    public SystemController(DataController dc){
        this.dc = dc;
    }

    public SystemController(){ }

    public void loadSystem(){
        LinkedList<LoanSupplier> loanSuppliers = dc.getLoanSuppliers();
        LinkedList<LoanOrder> loanOrders = dc.getLoanOrders();
        LinkedList<LoanAgreement> loanAgreements = dc.getLoanAgreements();

        suppliers.add(new FixedDaysSupplier(loanSuppliers.get(0)));
        suppliers.add(new InviteOnlySupplier(loanSuppliers.get(1)));
        suppliers.add(new SelfPickupSupplier(loanSuppliers.get(2)));

        suppliers.get(0).addAgreement(new SaleAgreement(loanAgreements.get(0)));
        suppliers.get(1).addAgreement(new GiftAgreement(loanAgreements.get(1)));
        suppliers.get(2).addAgreement(new SaleAgreement(loanAgreements.get(2)));

        suppliers.get(0).addOrder(new Order(loanOrders.get(0)));
        suppliers.get(0).getOrders().get(0).calcTotal(suppliers.get(0).getAgreements());
        suppliers.get(1).addOrder(new Order(loanOrders.get(1)));
        suppliers.get(1).getOrders().get(0).calcTotal(suppliers.get(1).getAgreements());
        suppliers.get(2).addOrder(new Order(loanOrders.get(2)));
        suppliers.get(2).getOrders().get(0).calcTotal(suppliers.get(2).getAgreements());
    }

    public void addOrder(Order order){
        for(Supplier s : suppliers){
            if(s.getID() == order.getSupplierID()) {
                order.calcTotal(s.getAgreements());
                s.addOrder(order);
            }
        }
    }

    public void addSupplier(Supplier supplier){
        suppliers.add(supplier);
    }

    public void removeOrder(int orderID){
        for(Supplier s : suppliers){
            for(Order o : s.getOrders()){
                if(o.getID() == orderID){
                    s.removeOrder(orderID);
                    break;
                }
            }
        }
    }

    public void removeSupplier(int supplierID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID){
                suppliers.remove(s);
                break;
            }
        }
    }

    public void reportArrival(Order arrivedOrder){
        removeOrder(arrivedOrder.getID());
        reports.add(new ArrivalReport(LocalDate.now(), arrivedOrder));
    }

    public void reportCancellation(Order cancelledOrder){
        removeOrder(cancelledOrder.getID());
        reports.add(new CancellationReport(LocalDate.now(), cancelledOrder));
    }

    public boolean setSupplierCompanyID(int supplierID, int companyID){
        Supplier s = getSupplierByID(supplierID);
        if(s == null)
            return false;
        s.setCompanyID(companyID);
        return true;
    }

    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum){
        Supplier s = getSupplierByID(supplierID);
        if(s == null)
            return false;
        s.setBankAccNum(bankAccNum);
        return true;
    }

    public boolean setSupplierPayCond(int supplierID, String payCond){
        Supplier s = getSupplierByID(supplierID);
        if(s == null)
            return false;
        s.setPayCond(payCond);
        return true;
    }

    public boolean setSupplierPhoneNum(int supplierID, String phoneNum){
        Supplier s = getSupplierByID(supplierID);
        if(s == null)
            return false;
        s.setPhoneNum(phoneNum);
        return true;
    }

    public boolean setSupplierContactNames(int supplierID, LinkedList<String> contacts){
        Supplier s = getSupplierByID(supplierID);
        if(s == null)
            return false;
        s.setContactNames(contacts);
        return true;
    }

    public boolean setOrderProducts(int orderID, HashMap<Product, Pair<Integer, Integer>> products){
        Order o = getOrderByID(orderID);
        if(o == null)
            return false;
        o.setProducts(products);
        for(Supplier s : suppliers){
            if(s.getID() == o.getSupplierID())
                o.calcTotal(s.getAgreements());
        }
        return true;
    }

    public boolean setOrderETA(int orderID, LocalDate ETA){
        Order o = getOrderByID(orderID);
        if(o == null)
            return false;
        o.setETA(ETA);
        return true;
    }

    public boolean setAgreementProdAmount(int agreementID, int amount){
        Agreement a = getAgreementByID(agreementID);
        if(a == null)
            return false;
        a.setProdAmount(amount);
        return true;
    }

    public boolean setAgreementProdCond(int agreementID, int cond){
        Agreement a = getAgreementByID(agreementID);
        if(a == null)
            return false;
        a.setProdCond(cond);
        return true;
    }

    public Supplier getSupplierByID(int supplierID){
        for(Supplier s : suppliers) {
            if (s.getID() == supplierID)
                return s;
        }
        return null;
    }

    public Order getOrderByID(int orderID){
        for(Order o : getOrders()) {
            if (o.getID() == orderID)
                return o;
        }
        return null;
    }

    private Agreement getAgreementByID(int agreementID){
        for(Supplier s : suppliers) {
            for(Agreement a : s.getAgreements()){
                if(a.getAgreementID() == agreementID)
                    return a;
            }
        }
        return null;
    }

    public LinkedList<Supplier> getSuppliers() {
        return suppliers;
    }

    public LinkedList<Order> getOrders() {
        LinkedList<Order> orders = new LinkedList<>();
        for(Supplier s : suppliers)
            orders.addAll(s.getOrders());
        return orders;
    }

    public LinkedList<Report> getReports() {
        return reports;
    }

    public Order urgentOrder(int barCode, int amount) {
        //TODO: implement this, make the cheapest order you can get
        return null;
    }
}
