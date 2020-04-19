package Tests;

import Buisness.Locations.LocationController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class LocationControllerTest {

    private LocationController locationController;
    @Before
    public void setUp() throws Exception {
        locationController = new LocationController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addProduct() {
        Assert.assertEquals(locationController.getProductsLocation().size(), 0);
        Date date = new Date();
        locationController.addProduct(1, date, 1, 100);
        Assert.assertEquals(locationController.getProductsLocation().size(), 1);
        Assert.assertEquals(locationController.getProductsLocation().size(), 1);
        Assert.assertEquals(locationController.getProductsLocation().get(1).get(date).keySet().contains(1), true);
        Assert.assertEquals(locationController.getProductsLocation().get(1).get(date).values().contains(100), true);
    }

    @Test
    public void moveProduct() {
        Assert.assertEquals(locationController.getProductsLocation().size(), 0);
        Date date = new Date();
        locationController.addProduct(1, date, 1, 100);
        Assert.assertEquals(locationController.getProductsLocation().size(), 1);
        Assert.assertEquals(locationController.getProductsLocation().size(), 1);
        assertTrue(locationController.getProductsLocation().get(1).get(date).keySet().contains(1));
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(100));
        locationController.moveProduct(1, date, 60, 1, 2);
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(40));
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(60));
    }

    @Test
    public void reduceFromShelf() {
        Assert.assertEquals(locationController.getProductsLocation().size(), 0);
        Date date = new Date();
        locationController.addProduct(1, date, 2, 100);
        Assert.assertEquals(locationController.getProductsLocation().get(1).get(date).keySet().contains(2), true);
        Assert.assertEquals(locationController.getProductsLocation().get(1).get(date).values().contains(100), true);
        Assert.assertEquals(locationController.reduceFromShelf(1, 30, date), true);
        Assert.assertEquals(locationController.getProductsLocation().get(1).get(date).values().contains(70), true);
    }
}