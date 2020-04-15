import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SaleTransaction extends Transaction {
    private Map<Integer , ProductSale> saleTransactions;    // <barCode , SalePurchase>

    public SaleTransaction(int i, Date date) {
        super(i, date);
        saleTransactions = new HashMap<>();
    }

    public boolean sell(int barCode, int amount, Date expirationDate){
        if (!Singletone_Storage_Management.getInstance().getLocations().reduceFromShelf(barCode, amount, expirationDate)){return false;} //Reduce amount from shelf and if its illegal return false -> not possible
        DataSaleProduct dataSaleProduct = Singletone_Storage_Management.getInstance().getInventory().getDataSale(barCode);
        ProductSale productSale = new ProductSale(barCode, dataSaleProduct.getProductName(), dataSaleProduct.getPrice(),
                dataSaleProduct.getDiscount(), amount); //create Product sale using DataSaleProduct
        this.saleTransactions.put(barCode, productSale);
        return true;
    }
}
