import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionController {
    private List<PurchaseTransaction> purchaseTransactions;
    private List<SaleTransaction> saleTransactions;
    private int transactionID;

    public TransactionController(){
        this.purchaseTransactions = new ArrayList<>();
        this.saleTransactions = new ArrayList<>();
        this.transactionID = 0;
    }

    public void purchase(int barCode, String productName, String supplier, double price, double discount, Date expirationDate, int amount, Date date, int location) {
        PurchaseTransaction p = new PurchaseTransaction(transactionID++, date); //create new Purchase transaction
        p.purchase(barCode, productName, supplier, price, discount, expirationDate, amount, date, location); //make purchase
        purchaseTransactions.add(p); //add to purchase list
    }

    public void sellProduct(Date date, int barCode, int amount, Date expirationDate){
        SaleTransaction s = new SaleTransaction(transactionID++, date);
        if (s.sell(barCode, amount, expirationDate)) {
            saleTransactions.add(s);
        }
    }
}