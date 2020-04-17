import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ProductControllerTest {

    Date date;
    ProductController productController;

    @Before
    public void setUp() throws Exception {
        date = new Date();
        productController = new ProductController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void connectProductToCategory() {
        productController.setMainCategory("Test new main category");
        Assert.assertEquals(productController.getCategories().get(1).getProductList().size(),0);
        productController.purchaseProduct(2, "Test Product", "Test Supplier", 100);
        productController.connectProductToCategory("Test new main category", 2);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().size(),1);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getBarCode(),2);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getProductName(),"Test Product");
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getManufactor(),"Test Supplier");
    }

    @Test
    public void sellNeedToAlert() {    //test the alert
        productController.purchaseProduct(1, "Test product", "Test supplier", 100);
        productController.setMinimumAmount(1, 15);
        Assert.assertEquals(productController.sale(1, 90), true);  //return alert under minimum
    }

    @Test
    public void sellNoNeedToAlert() {    //test the alert
        productController.purchaseProduct(1, "Test product", "Test supplier", 100);
        productController.setMinimumAmount(1, 15);
        Assert.assertEquals(productController.sale(1, 70), false);  //return alert under minimum
    }
}