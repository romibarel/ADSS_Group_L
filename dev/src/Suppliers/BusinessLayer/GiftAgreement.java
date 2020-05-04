package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class GiftAgreement extends Agreement {

    public GiftAgreement(Pair<Product, Pair<Integer, Integer>> giftAmount){
        super(giftAmount);
    }

    public GiftAgreement(LoanAgreement la){
        super(la);
    }

    public Product getProduct(){
        return agreementDetails.getKey();
    }

    public String getDescription(){
        return "For every " + agreementDetails.getValue().getKey() + " "
                + agreementDetails.getKey().getName() + ", you get "
                + agreementDetails.getValue().getValue()+" with no extra charge. ";
    }

    public HashMap<Product, Pair<Integer, Integer>> applyAgreement(HashMap<Product, Pair<Integer, Integer>> products){
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            Product orderProd = e.getKey();
            int orderProdAmount = e.getValue().getKey();
            Product agreementProd = agreementDetails.getKey();
            int agreementProdAmount = agreementDetails.getValue().getKey();
            if(orderProd.getCatalogID() == agreementProd.getCatalogID() && orderProdAmount >= agreementProdAmount)
                products.replace(orderProd, new Pair<>(orderProdAmount, (orderProdAmount / agreementProdAmount) * agreementDetails.getValue().getValue()));
        }
        return products;
    }
}
