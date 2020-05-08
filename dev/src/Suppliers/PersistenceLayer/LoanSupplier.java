package Suppliers.PersistenceLayer;

import javafx.util.Pair;

import java.util.LinkedList;

public class LoanSupplier {
    private int supplierID;
    private int companyID;
    private String name;
    private String bankAccNum;
    private String payCond;
    private String phoneNum;
    private LinkedList<Pair<String, String>> contacts;
    private LinkedList<LoanAgreement> agreements;
    private LinkedList<LoanOrder> orders;
    private LinkedList<LoanProduct> products;
    private String tag;

    public LoanSupplier(String tag, int supplierID, String name, int companyID, String bankAccNum, String payCond, String phoneNum, LinkedList<Pair<String, String>> contacts,
                        LinkedList<LoanAgreement> agreements, LinkedList<LoanOrder> orders, LinkedList<LoanProduct> products){
        this.supplierID = supplierID;
        this.companyID = companyID;
        this.bankAccNum = bankAccNum;
        this.payCond = payCond;
        this.contacts = contacts;
        this.phoneNum = phoneNum;
        this.agreements = agreements;
        this.orders = orders;
        this.products = products;
        this.tag = tag;
        this.name = name;
    }

    public int getID() {
        return supplierID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public String getBankAccNum() {
        return bankAccNum;
    }

    public String getPayCond() {
        return payCond;
    }

    public LinkedList<Pair<String, String>> getContants() {
        return contacts;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public LinkedList<LoanAgreement> getAgreements() {
        return agreements;
    }

    public LinkedList<LoanOrder> getOrders() {
        return orders;
    }

    public LinkedList<LoanProduct> getProducts() {
        return products;
    }

    public LinkedList<Pair<String, String>> getContacts() {
        return contacts;
    }
}
