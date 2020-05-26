package StorageAndSupplier.Storage.Tests;

import StorageAndSupplier.Storage.Buisness.Invenrory.Category;
import StorageAndSupplier.Storage.Buisness.Invenrory.Product;
import org.junit.Assert;
import org.junit.Before;

import java.util.Date;

public class CategoryTest {

    public static String CATEGORY_NAME = "Test category";
    public static int SUPPLIER_ID = 1;
    private Category c;
    @Before
    public void setUp() {
        c = new Category(CATEGORY_NAME);
    }

    @org.junit.Test
    public void appendSubCategory() {
        c.clean(CATEGORY_NAME);
        Assert.assertEquals(c.getSubCategories().size(), 0);
        c.appendSubCategory(new Category("Test sub category"));
        Assert.assertEquals(c.getSubCategories().size(), 1);
        Category sub = c.getSubCategories().get(0);
        Assert.assertEquals(sub.getName(), "Test sub category");

    }

    @org.junit.Test
    public void appendProduct() {
        c.clean(CATEGORY_NAME);
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", SUPPLIER_ID, 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Product product = c.getProductList().get(0);
        Assert.assertEquals(product.getProductName(), "Test product");
    }

    @org.junit.Test
    public void hasProduct() {
        c.clean(CATEGORY_NAME);
        Assert.assertFalse(c.hasProduct(0));
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", SUPPLIER_ID, 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Assert.assertTrue(c.hasProduct(0));
    }

    @org.junit.Test
    public void removeProduct() {
        c.clean(CATEGORY_NAME);
        Assert.assertFalse(c.hasProduct(0));
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", SUPPLIER_ID, 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Assert.assertTrue(c.hasProduct(0));
        c.removeProduct(0);
        Assert.assertFalse(c.hasProduct(0));
    }

    @org.junit.Test
    public void getProductNames() {
        c.clean(CATEGORY_NAME);
        c.appendProduct(new Product(0, "Test product0", SUPPLIER_ID, 1000, 100, new Date()));
        c.appendProduct(new Product(1, "Test product1", SUPPLIER_ID, 1000, 100, new Date()));
        Assert.assertTrue(c.getProductNames().contains("Test product0"));
        Assert.assertTrue(c.getProductNames().contains("Test product1"));
    }
}