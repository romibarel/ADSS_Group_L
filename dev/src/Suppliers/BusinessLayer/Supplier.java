package Suppliers.BusinessLayer;

import java.util.LinkedList;

public abstract class Supplier {
    private static int statID = 1;
    private int supplierID;
    private int companyID;
    private String bankAccNum;
    private String payCond;
    private String phoneNum;
    private LinkedList<String> contactNames;
    private LinkedList<Agreement> agreements;
    protected LinkedList<Order> orders;

    public Supplier(int companyID, String bankAccNum, String payCond, LinkedList<String> contactNames, String phoneNum){
        supplierID = statID++;
        this.companyID = companyID;
        this.bankAccNum = bankAccNum;
        this.payCond = payCond;
        this.contactNames = contactNames;
        this.phoneNum = phoneNum;
        agreements = new LinkedList<>();
        orders = new LinkedList<>();
    }

    public int getID(){
        return supplierID;
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

    public void addAgreement(Agreement a){
        agreements.add(a);
    }

    public void addAgreements(LinkedList<Agreement> a){
        agreements.addAll(a);
    }

    public void addOrder(Order o){
        orders.add(o);
    }

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

    public void setContactNames(LinkedList<String> contactNames){
        this.contactNames = contactNames;
    }

    public Agreement getAgreementByID(int agreementID){
        for(Agreement a : agreements){
            if(a.getAgreementID() == agreementID)
                return a;
        }
        return null;
    }

    public void removeAgreementByID(int agreementID){
        agreements.removeIf(a -> a.getAgreementID() == agreementID);
    }

    public abstract void removeOrder(int orderID);
}
