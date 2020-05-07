package Suppliers.PersistenceLayer;

import javafx.util.Pair;

public class LoanAgreement {
    private int agreementID;
    private int supplierID;
    private Pair<LoanProduct, Pair<Integer, Integer>> agreementDetails;

    public LoanAgreement(int supplierID, int agreementID, Pair<LoanProduct, Pair<Integer, Integer>> agreementDetails){
        this.agreementID = agreementID;
        this.supplierID = supplierID;
        this.agreementDetails = agreementDetails;
    }

    public Pair<LoanProduct, Pair<Integer, Integer>> getAgreementDetails() {
        return agreementDetails;
    }

    public int getAgreementID() {
        return agreementID;
    }

    public int getSupplierID() {
        return supplierID;
    }
}
