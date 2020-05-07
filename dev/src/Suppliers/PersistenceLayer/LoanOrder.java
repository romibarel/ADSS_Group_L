package Suppliers.PersistenceLayer;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;

public class LoanOrder {
    private int orderID;
    private int supplierID;
    private double total;
    private LocalDateTime ETA;
    private LocalDateTime dateIssued;
    private HashMap<LoanProduct, Pair<Integer, Integer>> products;

    public LoanOrder(int supplierID, int orderID, double total, LocalDateTime ETA, LocalDateTime dateIssued, HashMap<LoanProduct, Pair<Integer, Integer>> products){
        this.orderID = orderID;
        this.supplierID = supplierID;
        this.total = total;
        this.ETA = ETA;
        this.dateIssued = dateIssued;
        this.products = products;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public LocalDateTime getETA() {
        return ETA;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public HashMap<LoanProduct, Pair<Integer, Integer>> getProducts() {
        return products;
    }

    public int getOrderID() {
        return orderID;
    }

    public double getTotal() {
        return total;
    }
}
