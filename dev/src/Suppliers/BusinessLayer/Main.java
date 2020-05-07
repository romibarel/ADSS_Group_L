package Suppliers.BusinessLayer;

public class Main {

    public static void main(String[] args) {
        SystemController sc = SystemController.getInstance();

//        Product oliveOil = new Product(1, 20, "Olive Oil", "Zeita", LocalDateTime.now().plusDays(7));
//        Product milk = new Product(2, 5, "Milk", "Tara", LocalDateTime.now().plusDays(7));
//        Product bread = new Product( 3, 10, "Bread", "Berman", LocalDateTime.now().plusDays(7));
//        Product butter = new Product( 4, 3, "Butter", "Tnuva", LocalDateTime.now().plusDays(7));
//        Product cheese = new Product( 5,25, "Cheese", "Emek", LocalDateTime.now().plusDays(7));
//
//        Supplier s1 = new FixedDaysSupplier("Rom", 1, "1-2-3", "Cash", "010");
//        Supplier s2 = new OrderOnlySupplier("Adir", 2, "2-3-1", "Credit", "020");
//        Supplier s3 = new SelfPickupSupplier("Din", 3, "3-2-1", "Credit", "030", "Ashkelon");
//
//
//        HashMap<Product, Pair<Integer, Integer>> o1Products = new HashMap<>();
//        HashMap<Product, Pair<Integer, Integer>> o2Products = new HashMap<>();
//        HashMap<Product, Pair<Integer, Integer>> o3Products = new HashMap<>();
//        o1Products.put(cheese, new Pair<>(20, 0));
//        o1Products.put(butter, new Pair<>(5, 0));
//        o2Products.put(milk, new Pair<>(20, 0));
//        o2Products.put(bread, new Pair<>(30, 0));
//        o3Products.put(oliveOil, new Pair<>(50, 0));
//        Order o1 = new Order("Rom", s1.getID(), LocalDateTime.now(), o1Products);
//        Order o2 = new Order("Adir", s2.getID(), LocalDateTime.now(), o2Products);
//        Order o3 = new Order("Din", s3.getID(), LocalDateTime.now(), o3Products);
//        o1.setETA(s1.assessOrderETA());
//        o2.setETA(s2.assessOrderETA());
//        o3.setETA(s3.assessOrderETA());
//
//        Agreement a1 = new Agreement(new Pair<>(cheese, new Pair<>(20, 6)));
//        Agreement a2 = new Agreement(new Pair<>(milk, new Pair<>(10, 3)));
//        Agreement a3 = new Agreement(new Pair<>(oliveOil, new Pair<>(40, 5)));
//
//        sc.addSupplier(s1);
//        sc.addSupplier(s2);
//        sc.addSupplier(s3);
//
//        sc.addSupplierContact(s1.getID(), new Pair<>("Adir Ben Shahar", "100"));
//
//        sc.addProduct(s1.getID(), cheese);
//        sc.addProduct(s1.getID(), butter);
//        sc.addProduct(s2.getID(), milk);
//        sc.addProduct(s2.getID(), bread);
//        sc.addProduct(s3.getID(), oliveOil);
//
//        sc.addAgreement(s1.getID(), a1);
//        sc.addAgreement(s2.getID(), a2);
//        sc.addAgreement(s3.getID(), a3);
//
//        sc.addOrder(o1);
//        sc.addOrder(o2);
//        sc.addOrder(o3);

        for(Supplier s : sc.getSuppliers()){
            System.out.println("Class: " + s.getClass().getName());
            System.out.println("supplierID: " + s.getID());
            System.out.println("companyID: " + s.getCompanyID());
            System.out.println("bankAccNum: " + s.getBankAccNum());
            System.out.println("payCond: " + s.getPayCond());
            System.out.println("Name: " + s.getName());
            System.out.println(s.getOrders().get(0).toString());
            System.out.println(s.getAgreements().get(0).toString());
            System.out.println(s.getProducts().get(0).toString());
        }

        sc.closeConnection();
//        dc.addSupplier(s1.getLoan());
//        dc.addSupplierProduct(milk.getLoan(), s1.getID());
//        dc.addSupplierContact("Adir Ben Shahar", "100", s1.getID());
//        dc.addSupplierAgreement(a1.getLoan(), s1.getID());
//        dc.addSupplierOrder(o1.getLoan());

        //Order o = sc.urgentOrder(2, 10);

        //System.out.println(o.toString());
    }
}
