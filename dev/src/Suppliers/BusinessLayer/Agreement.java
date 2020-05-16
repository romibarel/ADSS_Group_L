package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class Agreement {
    private static int statID = 1;
    private int agreementID;
    protected Pair<Product, Pair<Integer, Integer>> agreementDetails; // <Product, <Amount for trigger, Sale amount>>

    public Agreement(Pair<Product, Pair<Integer, Integer>> agreementDetails){
        agreementID = statID++;
        this.agreementDetails = agreementDetails;
    }

    public Agreement(LoanAgreement la){
        statID++;
        agreementID = la.getAgreementID();
        Pair<LoanProduct, Pair<Integer, Integer>> ad = la.getAgreementDetails();
        agreementDetails = new Pair<>(new Product(ad.getKey()), new Pair<>(ad.getValue().getKey(), ad.getValue().getValue()));
    }

    public LoanAgreement getLoan(int supplierID){
        return new LoanAgreement(supplierID, agreementID, new Pair<>(agreementDetails.getKey().getLoan(supplierID), new Pair<>(agreementDetails.getValue().getKey(), agreementDetails.getValue().getValue())));
    }

    public void setProdAmount(int amount){
        agreementDetails = new Pair<>(agreementDetails.getKey(), new Pair<>(amount, agreementDetails.getValue().getValue()));
    }

    public void setProdSale(int sale){
        agreementDetails = new Pair<>(agreementDetails.getKey(), new Pair<>(agreementDetails.getValue().getKey(), sale));
    }

    public int getAgreementID(){
        return agreementID;
    }

    public Product getProduct(){
        return agreementDetails.getKey();
    }

    public String toString(){
        return "If you order more than " + agreementDetails.getValue().getKey() + " "
                + agreementDetails.getKey().getName() + ", you get "
                + agreementDetails.getValue().getValue()+" percent off of it.";
    }

    public HashMap<Product, Pair<Integer, Integer>> applyAgreement(HashMap<Product, Pair<Integer, Integer>> products) {
        HashMap<Product, Pair<Integer, Integer>> appliedProd = new HashMap<>();
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            Product orderProd = e.getKey();
            int orderProdAmount = e.getValue().getKey();
            Product agreementProd = agreementDetails.getKey();
            int agreementProdAmount = agreementDetails.getValue().getKey();
            int sale = agreementDetails.getValue().getValue();
            if(orderProd.getCatalogID() == agreementProd.getCatalogID() && orderProdAmount >= agreementProdAmount && e.getValue().getValue() == 0) {
                orderProd.setFinalPrice(orderProd.getOriginalPrice() * (1 - ((double) sale / 100)));
                appliedProd.put(orderProd, new Pair<>(orderProdAmount, sale));
            }
            else appliedProd.put(orderProd, new Pair<>(orderProdAmount, 0));
        }
        return appliedProd;
    }

    public double applyAgreementOnProduct(int amount) {
        if(amount >= agreementDetails.getValue().getKey())
            return getProduct().getOriginalPrice() * (1 - ((double)agreementDetails.getValue().getValue() / 100)) * amount;
        return getProduct().getFinalPrice() * amount;
    }
}
