package Suppliers.PersistenceLayer;

import Suppliers.BusinessLayer.Product;
import javafx.util.Pair;

public class LoanAgreement {
    private static int statID = 1;
    private int agreementID;
    private Pair<LoanProduct, Pair<Integer, Integer>> agreementDetails;
    private String tag;

    public LoanAgreement(String tag, Pair<LoanProduct, Pair<Integer, Integer>> agreementDetails){
        agreementID = statID++;
        this.agreementDetails = agreementDetails;
        this.tag = tag;
    }

    public Pair<LoanProduct, Pair<Integer, Integer>> getAgreementDescription() {
        return agreementDetails;
    }

    public String getTag(){
        return tag;
    }
}
