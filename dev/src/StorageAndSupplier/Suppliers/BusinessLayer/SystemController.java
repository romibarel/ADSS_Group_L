package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.DataController;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanReport;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanSupplier;
import javafx.util.Pair;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SystemController {
    private static SystemController instance = null;
    private LinkedList<Supplier> suppliers;
    private LinkedList<Report> reports;
    private DataController dc;

    public static SystemController getInstance(){
        if(instance == null) {
            return new SystemController();
        }
        return instance;
    }

    private SystemController(){
        suppliers = new LinkedList<>();
        reports = new LinkedList<>();
        dc = DataController.getInstance();
    }

    public void loadSystem(){
        for(LoanSupplier ls : dc.pullSupplierData())
            addSupplierFromLoan(ls);
        for(LoanReport lr : dc.pullReports()){
            switch (lr.getTag()){
                case "Arrival":
                    reports.add(new ArrivalReport(lr));
                    break;
                case "Cancel":
                    reports.add(new CancellationReport(lr));
                    break;
            }
        }
    }

    public void updateStatIDs(){
        for(Map.Entry<String, Integer> e : dc.updateStaticIDs().entrySet()){
            switch (e.getKey()){
                case "StorageAndSupplier/Suppliers":
                    Supplier.setStatID(e.getValue() + 1);
                    break;
                case "Agreements":
                    Agreement.setStatID(e.getValue() + 1);
                    break;
                case "Orders":
                    Order.setStatID(e.getValue() + 1);
                    break;
                case "Reports":
                    Report.setStatID(e.getValue() + 1);
                    break;
            }
        }
    }

    public LocalDateTime addOrder(Order order){
        Supplier s = getSupplier(order.getSupplierID());
        if(s == null)
            return null;
        order.calcTotal(s.getAgreements());
        order.setETA(s.assessOrderETA());
        if(!dc.addSupplierOrder(order.getLoan()))
            return null;
        return s.addOrder(order);
    }

    public boolean addSupplier(Supplier supplier){
        suppliers.add(supplier);
        return dc.addSupplier(supplier.getLoan());
    }

    public boolean addAgreement(int supplierID, Agreement a){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(s.doesSupplyProduct(a.getProduct().getCatalogID())) {
            if(!dc.addSupplierAgreement(a.getLoan(supplierID), supplierID))
                return false;
            s.addAgreement(a);
            return true;
        }
        return false;
    }

    public boolean addProduct(int supplierID, Product p){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.addSupplierProduct(p.getLoan(supplierID), supplierID))
            return false;
        s.addProduct(p);
        return true;
    }

    public boolean addNewProductToOrder(int productID, int orderID, int supplierID, int amount){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!s.doesSupplyProduct(productID))
            return false;
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        Product p = s.getProductByID(productID);
        if(!s.addProductToOrder(o, p, amount))
            return false;
        o.calcTotal(s.getAgreements());
        return dc.addProductToOrder(orderID, productID, amount, o.getTotal(), o.getDiscountOf(productID));
    }

    public boolean addSupplierContact(int supplierID, Pair<String, String> contact){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.addSupplierContact(contact.getKey(), contact.getValue(), supplierID))
            return false;
        s.addContact(contact);
        return true;
    }

    public boolean removeOrder(int orderID) {
        for (Supplier s : suppliers) {
            if (getOrder(orderID).getSupplierID() == s.getID()) {
                if(!dc.removeSupplierOrder(orderID))
                    return false;
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
                if(!dc.removeSupplier(supplierID))
                    return false;
                suppliers.remove(s);
                return true;
            }
        }
        return false;
    }

    public boolean removeProductFromOrder(int supplierID, int orderID, int productID){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!s.removeProductFromOrder(orderID, productID))
            return false;
        return dc.removeProductFromOrder(orderID, productID, getOrder(orderID).getTotal());
    }

    public boolean removeSupplierContact(int supplierID, String phoneNum){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeContact(phoneNum))
                return dc.removeSupplierContact(supplierID, phoneNum);
        }
        return false;
    }

    public boolean removeSupplierProduct(int supplierID, int productID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeProduct(productID))
                return dc.removeSupplierProduct(productID, supplierID);
        }
        return false;
    }

    public boolean removeSupplierAgreement(int supplierID, int agreementID){
        for(Supplier s : suppliers){
            if(s.getID() == supplierID && s.removeAgreement(agreementID))
                return dc.removeSupplierAgreement(agreementID);
        }
        return false;
    }

    public boolean reportArrival(Order arrivedOrder){
        if(!dc.removeSupplierOrder(arrivedOrder.getID()))
            return false;
        Report r = new ArrivalReport(LocalDateTime.now(), arrivedOrder);
        if(!dc.addReport(r.getLoan()))
            return false;
        removeOrder(arrivedOrder.getID());
        reports.add(r);
        return true;
    }

    public boolean reportCancellation(Order cancelledOrder){
        if(!dc.removeSupplierOrder(cancelledOrder.getID()))
            return false;
        Report r = new ArrivalReport(LocalDateTime.now(), cancelledOrder);
        if(!dc.addReport(r.getLoan()))
            return false;
        removeOrder(cancelledOrder.getID());
        reports.add(r);
        return true;
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
        if(sup == null || minPrice == -1)
            return null;
        p = sup.getProductByID(barCode);
        HashMap<Product, Pair<Integer, Integer>> products = new HashMap<>();
        products.put(p, new Pair<>(amount, 0));
        Order o = new Order(sup.getID(), LocalDateTime.now(), products, "", "");
        o.setETA(sup.assessOrderETA());
        addOrder(o);
        return o.getETA();
    }

    public boolean setSupplierCompanyID(int supplierID, int companyID){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.updateSupplierCompanyID(companyID, supplierID))
            return false;
        s.setCompanyID(companyID);
        return true;
    }

    public boolean setSupplierBankAccNum(int supplierID, String bankAccNum){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.updateSupplierBankAccNum(bankAccNum, supplierID))
            return false;
        s.setBankAccNum(bankAccNum);
        return true;
    }

    public boolean setSupplierPayCond(int supplierID, String payCond){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.updateSupplierPayCond(payCond, supplierID))
            return false;
        s.setPayCond(payCond);
        return true;
    }

    public boolean setSupplierPhoneNum(int supplierID, String phoneNum){
        Supplier s = getSupplier(supplierID);
        if(s == null)
            return false;
        if(!dc.updateSupplierPhoneNum(phoneNum, supplierID))
            return false;
        s.setPhoneNum(phoneNum);
        return true;
    }

    public boolean setAmountOfProductInOrder(int orderID, int productID, int amount){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        Supplier s = getSupplier(o.getSupplierID());
        if(s == null)
            return false;
        if(!s.setProductAmount(productID, amount, o))
            return false;
        o.calcTotal(s.getAgreements());
        return dc.updateAmountOfProductInOrder(orderID, productID, amount, o.getTotal());
    }

    public boolean setOrderETA(int orderID, LocalDateTime ETA){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        Supplier s = getSupplier(o.getSupplierID());
        if(s == null)
            return false;
        if(!s.setOrderETA(o, ETA))
            return false;
        return dc.updateOrderETA(ETA, orderID);
    }

    public boolean setAgreementProdAmount(int agreementID, int amount){
        Agreement a = getAgreement(agreementID);
        if(a == null)
            return false;
        if(!dc.updateAgreementProdAmount(amount, agreementID))
            return false;
        a.setProdAmount(amount);
        return true;
    }

    public boolean setAgreementProdSale(int agreementID, int sale){
        Agreement a = getAgreement(agreementID);
        if(a == null)
            return false;
        if(!dc.updateAgreementProdSale(sale, agreementID))
            return false;
        a.setProdSale(sale);
        return true;
    }

    public boolean setOrderSourceAddress(int orderID, String src){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        if(!dc.updateOrderSourceAddress(orderID, src))
            return false;
        o.setSourceAddress(src);
        return true;
    }

    public boolean setOrderDestinationAddress(int orderID, String dest){
        Order o = getOrder(orderID);
        if(o == null)
            return false;
        if(!dc.updateOrderDestinationAddress(orderID, dest))
            return false;
        o.setDestinationAddress(dest);
        return true;
    }

    public Supplier getSupplier(int supplierID){
        for(Supplier s : suppliers) {
            if (s.getID() == supplierID)
                return s;
        }
        LoanSupplier ls =  dc.getLoanSupplier(supplierID);
        if(ls == null) return null;
        return addSupplierFromLoan(ls);
    }

    public LinkedList<Supplier> getSuppliers() {
        return suppliers;
    }

    public Order getOrder(int orderID){
        for(Supplier s : suppliers){
            for(Order o : s.getOrders()){
                if(o.getID() == orderID)
                    return o;
            }
        }
        LoanSupplier ls = dc.getSupplierByOrder(orderID);
        if(ls == null)
            return null;
        addSupplierFromLoan(ls);
        return getOrder(orderID);
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

    public LinkedList<Order> getAllOrders() {
        LinkedList<Order> orders = new LinkedList<>();
        for(Supplier s : suppliers)
            orders.addAll(s.getOrders());
        return orders;
    }

    public Report getReport(int reportID){
        for(Report r : reports) {
            if (r.getID() == reportID)
                return r;
        }
        LoanReport lr = dc.getReport(reportID);
        if(lr == null) return null;
        Report r = null;
        switch (lr.getTag()){
            case "Cancel":
                r = new CancellationReport(lr);
                reports.add(r);
                break;
            case "Arrival":
                r = new ArrivalReport(lr);
                reports.add(r);
                break;
        }
        return r;
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

    public void setConnection(Connection conn) {
        DataController.getInstance().setConnection(conn);
    }

    private Supplier addSupplierFromLoan(LoanSupplier ls){
        Supplier s = null;
        switch (ls.getTag()){
            case "FixedDays":
                s = new FixedDaysSupplier(ls);
                suppliers.add(s);
                break;
            case "SelfPickup":
                s = new SelfPickupSupplier(ls);
                suppliers.add(s);
                break;
            case "OrderOnly":
                s = new OrderOnlySupplier(ls);
                suppliers.add(s);
                break;
        }
        return s;
    }
}
