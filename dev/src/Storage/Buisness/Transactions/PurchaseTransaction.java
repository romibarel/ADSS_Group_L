package Storage.Buisness.Transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import Storage.Buisness.Singletone_Storage_Management;

public class PurchaseTransaction extends Transaction {
    private Map<Integer , ProductPurchase> purchseTransactions;// <barCode , Buisness.Transactions.ProductPurchase>

    public PurchaseTransaction(int i, Date date) {
        super(i, date);
        this.purchseTransactions = new HashMap<>();
    }

    public Map<Integer, ProductPurchase> getPurchseTransactions() {
        return purchseTransactions;
    }

    public void setPurchseTransactions(Map<Integer, ProductPurchase> purchseTransactions) {
        this.purchseTransactions = purchseTransactions;
    }

    public void purchase(int barCode, String productName, String supplier, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        Singletone_Storage_Management.getInstance().getLocations().addProduct(barCode, expirationDate, location, amount); //add product to locations
        Singletone_Storage_Management.getInstance().getInventory().purchaseProduct(barCode, productName, supplier, amount);
        //create new purchase product
        ProductPurchase pp = new ProductPurchase(barCode, productName, supplier, price, discount, amount, expirationDate, location);
        this.purchseTransactions.put(barCode, pp);
    }

}
