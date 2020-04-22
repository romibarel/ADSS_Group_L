package Tests;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.ProductController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductControllerTest {


    private ProductController productController;

    @Before
    public void setUp() throws Exception {
        productController = new ProductController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void connectProductToCategory() {
        productController.clean();
        productController.setMainCategory("Test new main category");
        Assert.assertEquals(productController.getCategories().get(1).getProductList().size(),0);
        productController.purchaseProduct(2, "Test Buisness.Invenrory.Product", "Test Supplier", 100);
        productController.connectProductToCategory("Test new main category", 2);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().size(),1);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getBarCode(),2);
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getProductName(),"Test Buisness.Invenrory.Product");
        Assert.assertEquals(productController.getCategories().get(1).getProductList().get(0).getManufactor(),"Test Supplier");
    }

    @Test
    public void sellNeedToAlert() {    //test the alert
        productController.clean();
        productController.purchaseProduct(1, "Test product", "Test supplier", 100);
        productController.setMinimumAmount(1, 15);
        Assert.assertTrue(productController.sale(1, 90));  //return alert under minimum
    }

    @Test
    public void sellNoNeedToAlert() {    //test the alert
        productController.clean();
        productController.purchaseProduct(1, "Test product", "Test supplier", 100);
        productController.setMinimumAmount(1, 15);
        System.out.println(productController.searchProduct(1).getAmount());
        Assert.assertFalse(productController.sale(1, 70));  //return alert under minimum
    }

    @Test
    public void deleteCategory(){
        productController.clean();
        Category c = new Category("Test category to delete");
        List <Category> list = new ArrayList();
        list.add(c);
        Assert.assertEquals(productController.getCategories().size(),1);    //the default category
        productController.setCategories(list);
        Assert.assertEquals(productController.getCategories().size(),1);
        productController.deleteCategory("Test category to delete");
        Assert.assertEquals(productController.getCategories().size(),0);
    }
}