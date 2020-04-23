package Tests;

import BusinessLayer.*;
import javafx.util.Pair;
import org.junit.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class TestClass {

    SystemController sc;

    Product oliveOil;
    Product milk;
    Product bread;
    Product butter;
    Product cheese;

    Agreement a1;
    Agreement a2;
    Agreement a3;

    LinkedList<Agreement> aList1;
    LinkedList<Agreement> aList2;
    LinkedList<Agreement> aList3;
    LinkedList<String> contacts;

    Supplier s1;
    Supplier s2;
    Supplier s3;

    HashMap<Product, Pair<Integer, Integer>> o1Products;
    HashMap<Product, Pair<Integer, Integer>> o2Products;
    HashMap<Product, Pair<Integer, Integer>> o3Products;

    Order o1;
    Order o2;
    Order o3;

    @Before
    public void Init(){
        sc = new SystemController();

        oliveOil = new Product(1, 20, "Olive Oil", "Zeita");
        milk = new Product(2, 5, "Milk", "Tara");
        bread = new Product(3, 10, "Bread", "Berman");
        butter = new Product(4, 3, "Butter", "Tnuva");
        cheese = new Product(5, 25, "Cheese", "Emek");

        a1 = new SaleAgreement(new Pair<>(cheese, new Pair<>(20, 5)));
        a2 = new GiftAgreement(new Pair<>(milk, new Pair<>(10, 1)));
        a3 = new SaleAgreement(new Pair<>(oliveOil, new Pair<>(40, 8)));

        aList1 = new LinkedList<>();
        aList2 = new LinkedList<>();
        aList3 = new LinkedList<>();
        contacts = new LinkedList<>();

        o1Products = new HashMap<>();
        o2Products = new HashMap<>();
        o3Products = new HashMap<>();

        aList1.add(a1);
        aList2.add(a2);
        aList3.add(a3);

        contacts.add("Yossi");

        o1Products.put(cheese, new Pair<>(20, 0));
        o1Products.put(butter, new Pair<>(5, 0));
        o2Products.put(milk, new Pair<>(20, 0));
        o2Products.put(bread, new Pair<>(30, 0));
        o3Products.put(oliveOil, new Pair<>(50, 0));

        s1 = new FixedDaysSupplier(1, "1-2-3", "Cash", contacts, "010");
        s2 = new InviteOnlySupplier(2, "2-3-1", "Credit", contacts, "020");
        s3 = new SelfPickupSupplier(3, "3-2-1", "Credit", contacts, "030", "BGU");

        s1.addAgreement(a1);
        s2.addAgreement(a2);
        s3.addAgreement(a3);

        sc.addSupplier(s1);
        sc.addSupplier(s2);
        sc.addSupplier(s3);

        o1 = new Order(s1.getID(), LocalDate.now().plusDays(1), LocalDate.now(), o1Products);
        o2 = new Order(s2.getID(), LocalDate.now().plusDays(2), LocalDate.now(), o2Products);
        o3 = new Order(s3.getID(), LocalDate.now().plusDays(3), LocalDate.now(), o3Products);

        sc.addOrder(o1);
        sc.addOrder(o2);
        sc.addOrder(o3);
    }

    @Test
    public void testAddOrderWithSaleAgreement(){
        Assert.assertEquals(490, o1.getTotal(), 0.1);
        Assert.assertEquals(920, o3.getTotal(), 0.1);
    }

    @Test
    public void testAddOrderWithGiftAgreement(){
        Assert.assertEquals(400, o2.getTotal(), 0.1);
        Assert.assertEquals(22, o2.getQuantityOf(milk));
    }

    @Test
    public void testRemoveOrder(){
        sc.removeOrder(o2.getID());
        Assert.assertFalse(s2.getOrders().contains(o2));
    }

    @Test
    public void testRemoveOrderFromFixedSupplier(){
        sc.removeOrder(o1.getID());
        Assert.assertEquals(s1.getOrders().get(0).getETA(), o1.getETA().plusDays(7));
    }

    @Test
    public void testReportArrival(){
        sc.reportArrival(o1);
        Assert.assertEquals(o1.getID(), sc.getReports().get(0).getReportedOrder().getID());
    }

    @Test
    public void testReportCancellation(){
        sc.reportCancellation(o1);
        Assert.assertEquals(o1.getID(), sc.getReports().get(0).getReportedOrder().getID());
    }

    @Test
    public void testSetOrderProducts1(){
        sc.setOrderProducts(o1.getID(), o2.getProducts());
        Assert.assertEquals(o1.productsToString(), o2.productsToString());
    }

    @Test
    public void testSetOrderProducts2(){
        Order o = new Order(4, o1.getETA(), LocalDate.now().minusDays(2), o1.getProducts());
        Assert.assertFalse(sc.setOrderProducts(o.getID(), o2.getProducts()));
    }

    @Test
    public void testSetOrderETA1(){
        sc.setOrderETA(o1.getID(), o2.getETA());
        Assert.assertEquals(o1.getETA().toString(), o2.getETA().toString());
    }

    @Test
    public void testSetOrderETA2(){
        Order o = new Order(4, o1.getETA(), LocalDate.now().plusDays(2), o1.getProducts());
        Assert.assertFalse(sc.setOrderETA(o.getID(), o2.getETA()));
    }
}
