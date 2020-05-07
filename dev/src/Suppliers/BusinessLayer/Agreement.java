package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanAgreement;
import Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;
import java.util.HashMap;

public abstract class Agreement {
    private static int statID = 1;
    private int agreementID;
    protected Pair<Product, Pair<Integer, Integer>> agreementDetails;

    public Agreement(Pair<Product, Pair<Integer, Integer>> agreementDetails){
        agreementID = statID++;
        this.agreementDetails = agreementDetails;
    }

    public Agreement(LoanAgreement la){
        agreementID = statID++;
        Pair<LoanProduct, Pair<Integer, Integer>> ad = la.getAgreementDescription();
        agreementDetails = new Pair<>(new Product(ad.getKey()), new Pair<>(ad.getValue().getKey(), ad.getValue().getValue()));
    }

    public abstract Product getProduct();

    public abstract String getDescription();

    public void setProdAmount(int amount){
        agreementDetails = new Pair<>(agreementDetails.getKey(), new Pair<>(amount, agreementDetails.getValue().getValue()));
    }

    public void setProdCond(int cond){
        agreementDetails = new Pair<>(agreementDetails.getKey(), new Pair<>(agreementDetails.getValue().getKey(), cond));
    }

    public int getAgreementID(){
        return agreementID;
    }

    public abstract HashMap<Product, Pair<Integer, Integer>> applyAgreement(HashMap<Product, Pair<Integer, Integer>> products);
}
