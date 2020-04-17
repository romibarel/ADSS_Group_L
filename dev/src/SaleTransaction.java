import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SaleTransaction extends Transaction {
    private Map<Integer , ProductSale> saleTransactions;    // <barCode , SalePurchase>

    public SaleTransaction(int i, Date date) {
        super(i, date);
        saleTransactions = new HashMap<>();
    }

    public Map<Integer, ProductSale> getSaleTransactions() {
        return saleTransactions;
    }

    public void setSaleTransactions(Map<Integer, ProductSale> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }

    public int sell(int barCode, int amount, Date expirationDate){      // 0 - represent not legal , 1 - represents legal and no alert , 2 - represents legal and alert
        if (!Singletone_Storage_Management.getInstance().getLocations().reduceFromShelf(barCode, amount, expirationDate)){return 0;} //Reduce amount from shelf and if its illegal return false -> not possible
        boolean alert = Singletone_Storage_Management.getInstance().getInventory().sale(barCode, amount);
        DataSaleProduct dataSaleProduct = Singletone_Storage_Management.getInstance().getInventory().getDataSale(barCode);
        ProductSale productSale = new ProductSale(barCode, dataSaleProduct.getProductName(), dataSaleProduct.getPrice(),
                dataSaleProduct.getDiscount(), amount); //create Product sale using DataSaleProduct
        this.saleTransactions.put(barCode, productSale);
        if (alert){
            return 2;
        }
        return 1;
    }


}
