package StorageAndSupplier.Suppliers.Tests;

import StorageAndSupplier.Suppliers.BusinessLayer.*;
import StorageAndSupplier.Suppliers.PersistenceLayer.DataController;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanAgreement;
import StorageAndSupplier.Suppliers.PersistenceLayer.LoanProduct;
import javafx.util.Pair;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

public class TestClass {

    SystemController sc = SystemController.getInstance();

    Product Milk1;
    Product Milk2;
    Product Tuna;
    Product Shampoo;
    Product Cheese;
    Product Cheese2;
    Product Shocko;

    Agreement a1;
    Agreement a12;
    Agreement a2;
    Agreement a3;

    LinkedList<Agreement> aList1;
    LinkedList<Agreement> aList2;
    LinkedList<Agreement> aList3;

    Supplier s1 = sc.getSupplier(1);
    Supplier s2 = sc.getSupplier(2);
    Supplier s3 = sc.getSupplier(3);

    HashMap<Product, Pair<Integer, Integer>> o1Products;
    HashMap<Product, Pair<Integer, Integer>> o2Products;
    HashMap<Product, Pair<Integer, Integer>> o3Products;

    Order o1 = sc.getOrder(1);
    Order o2 = sc.getOrder(2);
    Order o3 = sc.getOrder(3);


    @Before
    public void Init(){
    }

    @Test
    public void testAddOrderWithAgreement(){
        Assert.assertEquals(463.75, o1.getTotal(), 0.1);
        Assert.assertEquals(1079.25, o3.getTotal(), 0.1);
    }

    @Test
    public void testRemoveOrder(){
        sc.removeOrder(o2.getID());
        Assert.assertFalse(s2.getOrders().contains(o2));
    }

    @Test
    public void testSetAmountOfProductInOrder(){
        sc.setAmountOfProductInOrder(1,1, 15);
        Assert.assertEquals(o1.getAmountOf(1), 15);
    }

    @Test
    public void testSetOrderETA1(){
        sc.setOrderETA(1, o2.getETA());
        Assert.assertEquals(o1.getETA().toString(), o2.getETA().toString());
    }

    @Test
    public void testSetOrderETA2(){
        Order o = new Order(4, o1.getETA(), o1.getProducts(), "Costco", "Shufersal");
        Assert.assertFalse(sc.setOrderETA(o.getID(), o2.getETA()));
    }
}
