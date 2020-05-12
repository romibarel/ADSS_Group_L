package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanOrder;
import Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Order {
    private static int statID = 1;
    private int orderID;
    private int supplierID;
    private double total;
    private LocalDateTime ETA;
    private LocalDateTime dateIssued;
    private HashMap<Product, Pair<Integer, Integer>> products; // <Product, <Amount ordered, Sale percentage>>

    public Order(int supplierID, LocalDateTime dateIssued, HashMap<Product, Pair<Integer, Integer>> products){
        orderID = statID++;
        this.supplierID = supplierID;
        total = 0;
        this.ETA = null;
        this.dateIssued = dateIssued;
        this.products = products;
    }

    public Order(LoanOrder lo){
        orderID = lo.getOrderID();
        supplierID = lo.getSupplierID();
        total = lo.getTotal();
        ETA = lo.getETA();
        dateIssued = lo.getDateIssued();
        HashMap<LoanProduct, Pair<Integer, Integer>> loanProducts = lo.getProducts();
        products = new HashMap<>();
        for(Map.Entry<LoanProduct, Pair<Integer, Integer>> e : loanProducts.entrySet())
            products.put(new Product(e.getKey()), new Pair<>(e.getValue().getKey(), e.getValue().getValue()));
    }

    public LoanOrder getLoan(){
        HashMap<LoanProduct, Pair<Integer, Integer>> loanProducts = new HashMap<>();
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet())
            loanProducts.put(e.getKey().getLoan(supplierID), new Pair<>(e.getValue().getKey(), e.getValue().getValue()));
        return new LoanOrder(supplierID, orderID, total, ETA, dateIssued, loanProducts);

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

    public LocalDateTime getETA(){
        return ETA;
    }

    public HashMap<Product, Pair<Integer, Integer>> getProducts(){
        return products;
    }

    public boolean setProductAmount(int productID, int amount) {
        if(LocalDate.now().isBefore(ChronoLocalDate.from(dateIssued.plusDays(1)))) {
            for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
                if(e.getKey().getCatalogID() == productID)
                    products.replace(e.getKey(), new Pair<>(amount, e.getValue().getValue()));
            }
            return true;
        }
        return false;
    }

    public boolean setETA(LocalDateTime ETA) {
        if(this.ETA == null || ETA.isAfter(ChronoLocalDateTime.from(this.ETA))) {
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
            total += e.getKey().getFinalPrice() * e.getValue().getKey();
        total = Double.parseDouble(new DecimalFormat("##.##").format(total));
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public String toString(){
        String order =  "Order #" + orderID + " from supplier #" + supplierID +
                " from " + dateIssued.toString() + " to be delivered on " + ETA.toString() +
                " contains: \n";
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet())
            order += e.getKey().getName() + " x" + e.getValue().getKey() + "\n";
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

    public double getPriceOf(int barCode){
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            if(e.getKey().getCatalogID() == barCode)
                return e.getKey().getOriginalPrice();
        }
        return -1;
    }

    public double getDiscountOf(int barCode){
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            if(e.getKey().getCatalogID() == barCode)
                return e.getValue().getValue();
        }
        return -1;
    }

    public LocalDateTime getExpirationDateOf(int barCode){
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            if(e.getKey().getCatalogID() == barCode)
                return e.getKey().getExpirationDate();
        }
        return null;
    }

    public void removeProduct(int productID){
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            if(e.getKey().getCatalogID() == productID) {
                products.remove(e.getKey());
                break;
            }
        }
    }
}
