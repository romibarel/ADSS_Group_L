package Buisness.Transactions;

import Buisness.Singletone_Storage_Management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionController {
    private List<PurchaseTransaction> purchaseTransactions;
    private List<SaleTransaction> saleTransactions;
    private int transactionID;

    public TransactionController(){
        this.purchaseTransactions = new ArrayList<>();
        this.saleTransactions = new ArrayList<>();
        this.transactionID = 0;
    }

    public void purchase(int barCode, String productName, String supplier, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        PurchaseTransaction p = new PurchaseTransaction(transactionID++, date); //create new Purchase transaction
        p.purchase(barCode, productName, supplier, price, discount, expirationDate, amount, date, location); //make purchase
        purchaseTransactions.add(p); //add to purchase list
        }

    public boolean sellProduct(Date date, int barCode, int amount, Date expirationDate){ //return if need to alert -> if product under minimum amount now
        SaleTransaction s = new SaleTransaction(transactionID++, date);
        int codeOfSell = s.sell(barCode, amount, expirationDate);// 0 - represent not legal , 1 - represents legal and no alert , 2 - represents legal and alert
        if (codeOfSell==1||codeOfSell==2) {
            saleTransactions.add(s);
            if (codeOfSell == 1){
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }

    public List<PurchaseTransaction> getPurchaseTransactions() {
        return purchaseTransactions;
    }

    public void setPurchaseTransactions(List<PurchaseTransaction> purchaseTransactions) {
        this.purchaseTransactions = purchaseTransactions;
    }

    public List<SaleTransaction> getSaleTransactions() {
        return saleTransactions;
    }

    public void setSaleTransactions(List<SaleTransaction> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
}