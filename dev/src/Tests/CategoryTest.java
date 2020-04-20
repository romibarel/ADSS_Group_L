package Tests;

import Buisness.Invenrory.Category;
import Buisness.Invenrory.Product;
import org.junit.Assert;
import org.junit.Before;

import java.util.Date;

public class CategoryTest {

    private Category c;
    @Before
    public void setUp() {
        c = new Category("Test category");
    }

    @org.junit.Test
    public void appendSubCategory() {
        Assert.assertEquals(c.getSubCategories().size(), 0);
        c.appendSubCategory(new Category("Test sub category"));
        Assert.assertEquals(c.getSubCategories().size(), 1);
        Category sub = c.getSubCategories().get(0);
        Assert.assertEquals(sub.getName(), "Test sub category");

    }

    @org.junit.Test
    public void appendProduct() {
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", "Test supplier", 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Product product = c.getProductList().get(0);
        Assert.assertEquals(product.getProductName(), "Test product");
    }

    @org.junit.Test
    public void hasProduct() {
        Assert.assertFalse(c.hasProduct(0));
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", "Test supplier", 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Assert.assertTrue(c.hasProduct(0));
    }

    @org.junit.Test
    public void removeProduct() {
        Assert.assertFalse(c.hasProduct(0));
        Assert.assertEquals(c.getProductList().size(), 0);
        c.appendProduct(new Product(0, "Test product", "Test supplier", 1000, 100, new Date()));
        Assert.assertEquals(c.getProductList().size(), 1);
        Assert.assertTrue(c.hasProduct(0));
        c.removeProduct(0);
        Assert.assertFalse(c.hasProduct(0));
    }

    @org.junit.Test
    public void getProductNames() {
        c.appendProduct(new Product(0, "Test product0", "Test supplier", 1000, 100, new Date()));
        c.appendProduct(new Product(1, "Test product1", "Test supplier", 1000, 100, new Date()));
        Assert.assertTrue(c.getProductNames().contains("Test product0"));
        Assert.assertTrue(c.getProductNames().contains("Test product1"));
    }
}