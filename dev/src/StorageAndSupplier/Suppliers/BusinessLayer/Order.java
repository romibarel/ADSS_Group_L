package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanOrder;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
    private String srcAddress;
    private String destAddress;

    public Order(int supplierID, LocalDateTime dateIssued, HashMap<Product, Pair<Integer, Integer>> products, String srcAddress, String destAddress){
        orderID = statID++;
        this.supplierID = supplierID;
        total = 0;
        this.ETA = null;
        this.dateIssued = dateIssued;
        this.products = products;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
    }

    public Order(LoanOrder lo){
        statID++;
        orderID = lo.getOrderID();
        supplierID = lo.getSupplierID();
        total = lo.getTotal();
        ETA = lo.getETA();
        dateIssued = lo.getDateIssued();
        HashMap<LoanProduct, Pair<Integer, Integer>> loanProducts = lo.getProducts();
        products = new HashMap<>();
        for(Map.Entry<LoanProduct, Pair<Integer, Integer>> e : loanProducts.entrySet())
            products.put(new Product(e.getKey()), new Pair<>(e.getValue().getKey(), e.getValue().getValue()));
        srcAddress = lo.getSrcAddress();
        destAddress = lo.getDestAddress();
    }

    public static void setStatID(int ID){
        statID = ID;
    }

    public LoanOrder getLoan(){
        HashMap<LoanProduct, Pair<Integer, Integer>> loanProducts = new HashMap<>();
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet())
            loanProducts.put(e.getKey().getLoan(supplierID), new Pair<>(e.getValue().getKey(), e.getValue().getValue()));
        return new LoanOrder(supplierID, orderID, total, ETA, dateIssued, loanProducts, srcAddress, destAddress);

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

    public String getDestAddress() {
        return destAddress;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public boolean setProductAmount(int productID, int amount) {
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()) {
            if (e.getKey().getCatalogID() == productID) {
                products.replace(e.getKey(), new Pair<>(amount, e.getValue().getValue()));
                return true;
            }
        }
        return false;
    }

    public boolean setETA(LocalDateTime ETA) {
        this.ETA = ETA;
        return true;
    }

    public boolean setSourceAddress(String src){
        srcAddress = src;
        return true;
    }

    public boolean setDestinationAddress(String dest){
        destAddress = dest;
        return true;
    }

    public boolean addNewProduct(Product p, int amount){
        return products.putIfAbsent(p, new Pair<>(amount, 0)) == null;
    }

    public void calcTotal(LinkedList<Agreement> agreements){
        total = 0;
        for(Agreement a : agreements)
            products = a.applyAgreement(products);
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()) {
            if(e.getValue().getValue() > 0)
                total += e.getKey().getFinalPrice() * e.getValue().getKey() * (1 - ((double) e.getValue().getValue() / 100));
            else total += e.getKey().getFinalPrice() * e.getValue().getKey();
        }
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

    public int getDiscountOf(int barCode){
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
                total -= e.getKey().getFinalPrice() * e.getValue().getKey();
                break;
            }
        }
    }

    public double calcWeight(){
        double weight = 0;
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet())
            weight += e.getKey().getWeight() * e.getValue().getKey();
        return weight;
    }
}
