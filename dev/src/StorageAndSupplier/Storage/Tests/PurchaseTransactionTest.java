package StorageAndSupplier.Storage.Tests;

import StorageAndSupplier.Storage.Buisness.Transactions.PurchaseTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class PurchaseTransactionTest {

    public static final Integer supplierID = 1;
    private PurchaseTransaction purchaseTransaction;
    private Date date;
    @Before
    public void setUp() throws Exception {
        date = new Date();
        purchaseTransaction = new PurchaseTransaction(1, date);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void purchase() {
        Assert.assertEquals(purchaseTransaction.getPurchseTransactions().size(),0);
        purchaseTransaction.purchase(2,"Test product", supplierID,
                10, 2, date, 100, date, 2);
        Assert.assertEquals(purchaseTransaction.getPurchseTransactions().size(),1);
        Assert.assertTrue(purchaseTransaction.getPurchseTransactions().keySet().contains(2));
    }
}
