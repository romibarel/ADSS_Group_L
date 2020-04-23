package PersistenceLayer;

import BusinessLayer.Agreement;
import BusinessLayer.Order;

import java.util.LinkedList;

public class LoanSupplier {
    private static int statID = 1;
    private int supplierID;
    private int companyID;
    private String bankAccNum;
    private String payCond;
    private String phoneNum;
    private LinkedList<String> contactNames;
    private LinkedList<Agreement> agreements;
    private LinkedList<Order> orders;
    private String tag;

    public LoanSupplier(String tag, int companyID, String bankAccNum, String payCond, LinkedList<String> contactNames, String phoneNum){
        supplierID = statID++;
        this.companyID = companyID;
        this.bankAccNum = bankAccNum;
        this.payCond = payCond;
        this.contactNames = contactNames;
        this.phoneNum = phoneNum;
        agreements = new LinkedList<>();
        orders = new LinkedList<>();
        this.tag = tag;
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

    public LinkedList<String> getContantNames() {
        return contactNames;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getTag() {
        return tag;
    }
}
