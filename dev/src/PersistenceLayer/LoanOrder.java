package PersistenceLayer;

import BusinessLayer.Product;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;

public class LoanOrder {
    private static int statID = 1;
    private int orderID;
    private int supplierID;
    private double total;
    private LocalDate ETA;
    private LocalDate dateIssued;
    private HashMap<LoanProduct, Pair<Integer, Integer>> products;

    public LoanOrder(int supplierID, LocalDate ETA, LocalDate dateIssued, HashMap<LoanProduct, Pair<Integer, Integer>> products){
        orderID = statID++;
        this.supplierID = supplierID;
        total = 0;
        this.ETA = ETA;
        this.dateIssued = dateIssued;
        this.products = products;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public LocalDate getETA() {
        return ETA;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public HashMap<LoanProduct, Pair<Integer, Integer>> getProducts() {
        return products;
    }
}
