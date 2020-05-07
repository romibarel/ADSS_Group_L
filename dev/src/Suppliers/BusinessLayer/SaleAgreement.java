package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class SaleAgreement extends Agreement {

    public SaleAgreement(Pair<Product, Pair<Integer, Integer>> salePercentage){
        super(salePercentage);
    }

    public SaleAgreement(LoanAgreement la){
        super(la);
    }

    public Product getProduct(){
        return agreementDetails.getKey();
    }

    public String getDescription(){
        return "If you order more than " + agreementDetails.getValue().getKey() + " "
                + agreementDetails.getKey().getName() + ", you get "
                + agreementDetails.getValue().getValue()+" percent off of it. ";
    }

    public HashMap<Product, Pair<Integer, Integer>> applyAgreement(HashMap<Product, Pair<Integer, Integer>> products) {
        HashMap<Product, Pair<Integer, Integer>> appliedProd = new HashMap<>();
        for(Map.Entry<Product, Pair<Integer, Integer>> e : products.entrySet()){
            Product orderProd = e.getKey();
            int orderProdAmount = e.getValue().getKey();
            Product agreementProd = agreementDetails.getKey();
            int agreementProdAmount = agreementDetails.getValue().getKey();
            int sale = agreementDetails.getValue().getValue();
            if(orderProd.getCatalogID() == agreementProd.getCatalogID() && orderProdAmount >= agreementProdAmount)
                orderProd.setPrice(orderProd.getPrice() * (1 - ((double)sale / 100)));
            appliedProd.put(orderProd, new Pair<>(orderProdAmount, 0));
        }
        return appliedProd;
    }
}
