package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import Suppliers.PersistenceLayer.LoanOrder;
import Suppliers.PersistenceLayer.LoanProduct;
import Suppliers.PersistenceLayer.LoanSupplier;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.LinkedList;

public abstract class Supplier {
    private static int statID = 1;
    private int supplierID;
    private int companyID;
    protected String name;
    private String bankAccNum;
    private String payCond;
    private String phoneNum;
    private LinkedList<Pair<String, String>> contacts;
    private LinkedList<Agreement> agreements;
    protected LinkedList<Product> products;
    protected LinkedList<Order> orders;

    public Supplier(String name, int companyID, String bankAccNum, String payCond, String phoneNum){
        supplierID = statID++;
        this.companyID = companyID;
        this.bankAccNum = bankAccNum;
        this.payCond = payCond;
        contacts = new LinkedList<>();
        this.phoneNum = phoneNum;
        agreements = new LinkedList<>();
        products = new LinkedList<>();
        orders = new LinkedList<>();
        this.name = name;
    }

    public Supplier(LoanSupplier la){
        agreements = new LinkedList<>();
        products = new LinkedList<>();
        orders = new LinkedList<>();
        supplierID = la.getID();
        companyID = la.getCompanyID();
        bankAccNum = la.getBankAccNum();
        payCond = la.getPayCond();
        contacts = la.getContants();
        phoneNum = la.getPhoneNum();
        for(LoanAgreement a : la.getAgreements())
            agreements.add(new Agreement(a));
        for(LoanProduct p : la.getProducts())
            products.add(new Product(p));
        for(LoanOrder o : la.getOrders())
            orders.add(new Order(o));
        this.name = la.getName();
    }

    public abstract LoanSupplier getLoan();

    public int getID(){
        return supplierID;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Agreement> getAgreements(){
        return agreements;
    }

    public int getCompanyID(){
        return companyID;
    }

    public String getPayCond() {
        return payCond;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getBankAccNum() {
        return bankAccNum;
    }

    public LinkedList<Order> getOrders(){
        return orders;
    }

    public LinkedList<Pair<String, String>> getContacts() {
        return contacts;
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public boolean doesSupplyProduct(int productID){
        for(Product p : products){
            if(p.getCatalogID() == productID)
                return true;
        }
        return false;

    }

    public double pitchPriceForProduct(int productID, int amount){
        for(Agreement a : agreements){
            if(a.getProduct().getCatalogID() == productID)
                return a.applyAgreementOnProduct(amount);
        }
        return -1;
    }

    public void addAgreement(Agreement a){
        agreements.add(a);
    }

    public void addContact(Pair<String, String> contact){
        contacts.add(contact);
    }

    public LocalDateTime addOrder(Order o){
        o.setETA(assessOrderETA());
        orders.add(o);
        return o.getETA();
    }

    public void addProduct(Product p) { products.add(p); }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public void setBankAccNum(String bankAccNum) {
        this.bankAccNum = bankAccNum;
    }

    public void setPayCond(String payCond) {
        this.payCond = payCond;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setContacts(Pair<String, String> contact){ contacts.add(contact); }

    public abstract boolean setProductAmount(int productID, int amount, Order o);

    public Agreement getAgreementByID(int agreementID){
        for(Agreement a : agreements){
            if(a.getAgreementID() == agreementID)
                return a;
        }
        return null;
    }

    public Product getProductByID(int productID){
        for(Product a : products){
            if(a.getCatalogID() == productID)
                return a.duplicate();
        }
        return null;
    }

    public boolean removeAgreement(int agreementID){
        for(Agreement a : agreements){
            if(a.getAgreementID() == agreementID) {
                agreements.remove(a);
                return true;
            }
        }
        return false;
    }

    public abstract Order removeOrder(int orderID);

    public boolean removeContact(String phoneNum){
        for(Pair<String, String> c : contacts){
            if(c.getValue().equals(phoneNum)){
                contacts.remove(c);
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(int productID){
        for(Product p : products){
            if(p.getCatalogID() == productID){
                products.remove(p);
                return true;
            }
        }
        return false;
    }

    public boolean removeProductFromOrder(int orderID, int productID){
        for(Order o : orders){
            if(o.getID() == orderID) {
                o.removeProduct(productID);
                return true;
            }
        }
        return false;
    }

    public abstract LocalDateTime assessOrderETA();
}
