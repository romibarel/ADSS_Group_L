package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.DataController;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

public class SystemController {
    private static SystemController instance = null;
    private LinkedList<Supplier> suppliers;
    private LinkedList<Report> reports;
    private final DataController dc = DataController.getInstance();

    public static SystemController getInstance(){
        if(instance == null)
            return new SystemController();
        return instance;
    }

    private SystemController(){
        suppliers = new LinkedList<>();
        reports = new LinkedList<>();
//        for(LoanSupplier lp : dc.pullSupplierData()) {
//            switch (lp.getTag()){
//                case "FixedDays":
//                    suppliers.add(new FixedDaysSupplier(lp));
//                    break;
//                case "SelfPickup":
//                    suppliers.add(new SelfPickupSupplier(lp));
//                    break;
//                case "OrderOnly":
//                    suppliers.add(new OrderOnlySupplier(lp));
//                    break;
//            }
//        }
//
//        for(LoanReport lr : dc.pullReports()){
//            switch (lr.getTag()){
//                case "Arrival":
//                    reports.add(new ArrivalReport(lr));
//                    break;
//                case "Cancel":
//                    reports.add(new CancellationReport(lr));
//                    break;
//            }
//        }


    }

    public void loadSystem(){
        dc.loadSystem();
    }

    public LocalDateTime addOrder(Order order){
        for(Supplier s : suppliers){
            if(s.getID() == order.getSupplierID()) {
                order.calcTotal(s.getAgreements());
                dc.addSupplierOrder(order.getLoan());
                return s.addOrder(order);
            }
        }
        return null;
    }

    public void addSupplier(Supplier supplier){
        suppliers.add(supplier);
        dc.addSupplier(supplier.getLoan());
    }

    public boolean addAgreement(int supplierID, Agreement a){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID) {
                if(s.doesSupplyProduct(a.getProduct().getCatalogID())) {
                    s.addAgreement(a);
                    dc.addSupplierAgreement(a.getLoan(supplierID), supplierID);
                    return true;
                }
            }
        }
        return false;
    }

    public void addProduct(int supplierID, Product p){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID) {
                s.addProduct(p);
                dc.addSupplierProduct(p.getLoan(supplierID), supplierID);
            }
        }
    }

    public boolean addSupplierContact(int supplierID, Pair<String, String> contact){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        s.addContact(contact);
        dc.addSupplierContact(contact.getKey(), contact.getValue(), supplierID);
        return true;
    }

    public boolean removeOrder(int orderID) {
        for (Supplier s : suppliers) {
            if (getOrder(orderID).getSupplierID() == s.getID()) {
                dc.removeSupplierOrder(orderID);
                Order o = s.removeOrder(orderID);
                if (o != null)
                    dc.addSupplierOrder(o.getLoan());
                return true;
            }
        }
        return false;
    }

    public boolean removeSupplier(int supplierID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID){
                suppliers.remove(s);
                dc.removeSupplier(supplierID);
                return true;
            }
        }
        return false;
    }

    public boolean removeProductFromOrder(int supplierID, int orderID, int productID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeProductFromOrder(orderID, productID)){
                dc.removeProductFromOrder(orderID, productID);
                return true;
            }
        }
        return false;
    }

    public boolean removeSupplierContact(int supplierID, String phoneNum){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeContact(phoneNum)) {
                dc.removeSupplierContact(supplierID, phoneNum);
                return true;
            }
        }
        return false;
    }

    public boolean removeSupplierProduct(int supplierID, int productID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeProduct(productID)) {
                dc.removeSupplierProduct(productID, supplierID);
                return true;
            }
        }
        return false;
    }

    public boolean removeSupplierAgreement(int supplierID, int agreementID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeAgreement(agreementID)) {
                dc.removeSupplierAgreement(agreementID);
                return true;
            }
        }
        return false;
    }

    public void reportArrival(Order arrivedOrder){
        removeOrder(arrivedOrder.getID());
        dc.removeSupplierOrder(arrivedOrder.getID());
        Report r = new ArrivalReport(LocalDateTime.now(), arrivedOrder);
        reports.add(r);
        dc.addReport(r.getLoan());
    }

    public void reportCancellation(Order cancelledOrder){
        removeOrder(cancelledOrder.getID());
        dc.removeSupplierOrder(cancelledOrder.getID());
        Report r = new CancellationReport(LocalDateTime.now(), cancelledOrder);
        reports.add(r);
        dc.addReport(r.getLoan());
    }

    public LocalDateTime urgentOrder(int barCode, int amount) {
        double minPrice = Double.MAX_VALUE;
        Supplier sup = null;
        Product p;
        for(Supplier s : suppliers){
            if(s.doesSupplyProduct(barCode)){
                double price = s.pitchPriceForProduct(barCode, amount);
                if(price < minPrice) {
                    minPrice = price;
                    sup = s;
                }
            }
        }
        if(sup == null)
            return null;
        p = sup.getProductByID(barCode);
        HashMap<Product, Pair<Integer, Integer>> products = new HashMap<>();
        products.put(p, new Pair<>(amount, 0));
        Order o = new Order(sup.getID(), LocalDateTime.now(), products);
        addOrder(o);
        return o.getETA();
    }

    public boolean setSupplierCompanyID(int supplierID, int companyID){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        s.setCompanyID(companyID);
        dc.updateSupplierCompanyID(companyID, supplierID);
        return true;
    }

    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        s.setBankAccNum(bankAccNum);
        dc.updateSupplierBankAccNum(bankAccNum, supplierID);
        return true;
    }

    public boolean setSupplierPayCond(int supplierID, String payCond){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        s.setPayCond(payCond);
        dc.updateSupplierPayCond(payCond, supplierID);
        return true;
    }

    public boolean setSupplierPhoneNum(int supplierID, String phoneNum){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        s.setPhoneNum(phoneNum);
        dc.updateSupplierPhoneNum(phoneNum, supplierID);
        return true;
    }

    public boolean setAmountOfProductInOrder(int orderID, int productID, int amount){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        for(Supplier s : suppliers){
            if(s.getID() == o.getSupplierID()) {
                s.setProductAmount(productID, amount, o);
                o.calcTotal(s.getAgreements());
            }
        }
        dc.updateAmountOfProductInOrder(orderID, productID, amount, o.getTotal());
        return true;
    }

    public boolean setOrderETA(int orderID, LocalDateTime ETA){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        o.setETA(ETA);
        dc.updateOrderETA(ETA, orderID);
        return true;
    }

    public boolean setAgreementProdAmount(int agreementID, int amount){
        Agreement a = getAgreement(agreementID);
        if(a == null)
            return false;
        a.setProdAmount(amount);
        dc.updateAgreementProdAmount(amount, agreementID);
        return true;
    }

    public boolean setAgreementProdSale(int agreementID, int sale){
        Agreement a = getAgreement(agreementID);
        if(a == null)
            return false;
        a.setProdSale(sale);
        dc.updateAgreementProdSale(sale, agreementID);
        return true;
    }

    public Supplier getSupplier(int supplierID){
        for(Supplier s : suppliers) {
            if (s.getID() == supplierID)
                return s;
        }
        return null;
    }

    public Order getOrder(int orderID){
        for(Order o : getAllOrders()) {
            if (o.getID() == orderID)
                return o;
        }
        return null;
    }

    private Agreement getAgreement(int agreementID){
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

    public LinkedList<Order> getAllOrders() {
        LinkedList<Order> orders = new LinkedList<>();
        for(Supplier s : suppliers)
            orders.addAll(s.getOrders());
        return orders;
    }

    public LinkedList<Report> getReports() {
        return reports;
    }

    public LinkedList<Order> checkOrdersArrivalStatus(){
        LinkedList<Order> arrivedOrders = new LinkedList<>();
        for(Order o : getAllOrders()){
            if(o.getETA().isBefore(LocalDateTime.now())){
                reportArrival(o);
                arrivedOrders.add(o);
            }
        }
        return arrivedOrders;
    }

    public void closeConnection(){
        dc.close();
    }
}
