package StorageAndSupplier.Storage.Tests;

import StorageAndSupplier.Storage.Buisness.Locations.LocationController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class LocationControllerTest {
    LocationController locationController;
    @Before
    public void setUp() throws Exception {
        locationController = new LocationController();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addProduct() {
        locationController.clean();
        Assert.assertEquals(locationController.getProductsLocation().size(), 0);
        Date date = new Date();
        locationController.addProduct(1, date, 1, 100);
        Assert.assertEquals(locationController.getProductsLocation().size(), 1);
        assertTrue(locationController.getProductsLocation().get(1).get(date).keySet().contains(1));
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(100));
        locationController.clean();
    }

    @Test
    public void moveProduct() {
        locationController.clean();
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
        locationController.clean();
        Assert.assertEquals(locationController.getProductsLocation().size(), 0);
        Date date = new Date();
        locationController.addProduct(1, date, 2, 100);
        assertTrue(locationController.getProductsLocation().get(1).get(date).keySet().contains(2));
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(100));
        assertTrue(locationController.reduceFromShelf(1, 30, date));
        assertTrue(locationController.getProductsLocation().get(1).get(date).values().contains(70));
    }
}