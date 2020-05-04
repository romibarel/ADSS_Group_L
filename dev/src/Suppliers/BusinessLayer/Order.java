package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanOrder;
import Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Order {
    private static int statID = 1;
    private int orderID;
    private int supplierID;
    private double total;
    private LocalDate ETA;
    private LocalDate dateIssued;
    private HashMap<Product, Pair<Integer, Integer>> products;

    public Order(int supplierID, LocalDate ETA, LocalDate dateIssued, HashMap<Product, Pair<Integer, Integer>> products){
        orderID = statID++;
        this.supplierID = supplierID;
        total = 0;
        this.ETA = ETA;
        this.dateIssued = dateIssued;
        this.products = products;
    }

    public Order(LoanOrder lo){
        orderID = statID++;
        supplierID = lo.getSupplierID();
        total = 0;
        ETA = lo.getETA();
        dateIssued = lo.getDateIssued();
        HashMap<LoanProduct, Pair<Integer, Integer>> loanProducts = lo.getProducts();
        products = new HashMap<>();
        for(Map.Entry<LoanProduct, Pair<Integer, Integer>> e : loanProducts.entrySet())
            products.put(new Product(e.getKey()), new Pair<>(e.getValue().getKey(), e.getValue().getValue()));
    }

    public int getID(){
        return orderID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public double getTotal(){
        return total;
    }

    public int getQuantityOf(Product p){
        Pair<Integer, Integer> q = products.get(p);
        return q.getValue() + q.getKey();
    }

    public LocalDate getETA(){
        return ETA;
    }

    public HashMap<Product, Pair<Integer, Integer>> getProducts(){
        return products;
    }

    public boolean setProducts(HashMap<Product, Pair<Integer, Integer>> products) {
        if(LocalDate.now().isBefore(ChronoLocalDate.from(dateIssued.plusDays(1)))) {
            this.products = products;
            return true;
        }
        return false;
    }

    public boolean setETA(LocalDate ETA) {
        if(ETA.isAfter(ChronoLocalDate.from(this.ETA))) {
            this.ETA = ETA;
            return true;
        }
        return false;
    }

    public void calcTotal(LinkedList<Agreement> agreements){
        total = 0;
        for(Agreement a : agreements)
            products = a.applyAgreement(products);
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet())
            total += e.getKey().getPrice() * e.getValue().getKey();
        total = Double.parseDouble(new DecimalFormat("##.##").format(total));
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public String toString(){
        String order =  "Order #" + orderID + " from supplier #" + supplierID +
                        " from " + dateIssued.toString() + " to be delivered on " + ETA.toString() +
                        " contains: \n";
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            order += e.getKey().getName() + " x" + (e.getValue().getKey() + e.getValue().getValue()) + "\n";
        }
        order += "For a total of " + total + " nis.";
        return order;
    }

    public String productsToString(){
        String prods = "";
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            prods += e.getKey().getName() + "\n";
        }
        return prods;
    }
}
