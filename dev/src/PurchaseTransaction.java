import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class PurchaseTransaction extends Transaction {
    private Map<Integer , ProductPurchase> purchseTransactions;// <barCode , ProductPurchase>

    public PurchaseTransaction(int i, Date date) {
        super(i, date);
        this.purchseTransactions = new HashMap<>();
    }

    public void purchase(int barCode, String productName, String supplier, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        Singletone_Storage_Management.getInstance().getLocations().addProduct(barCode, expirationDate, location, amount); //add product to locations
        Singletone_Storage_Management.getInstance().getInventory().purchaseProduct(barCode, productName, supplier, amount);
        ///TODO: understands where the ProductPurchase comes from -> for now it's null
        ProductPurchase pp = null;
        this.purchseTransactions.put(barCode, pp);
    }

}
