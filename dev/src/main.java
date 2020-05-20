import Storage.Tests.CategoryTest;
import Storage.Tests.LocationControllerTest;
import Storage.Tests.ProductControllerTest;
import Storage.Tests.PurchaseTransactionTest;
import StorageAndSupplier.API_Buisness;
import StorageAndSupplier.Presentation.Presentation;
import StorageAndSupplier.Singltone_Supplier_Storage_Manager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;


public class main {

    //public static final String PATTERN = "yyyy/MM/dd"; // comment
    //public static final Integer SupplierID = 1;

    public static void main (String[] args){
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("1)Run system of 'Storage'");
        System.out.println("2)Run system of 'Suppliers'");
        System.out.println("4)Exit");
        int selected = 0;
        boolean error = false;
        do {
            try {
                selected = Integer.parseInt(scanner.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        while (true) {
            if (selected == 1) {
                runStorage(scanner);
                break;
            } else if (selected == 2) {
                runSuppliers(scanner);
                break;
            }
            else {
                break;
            }
        }*/
        Presentation p = new Presentation();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1)Initialize system");
        System.out.println("2)Uninitialized system");
        System.out.println("3)Exit");
        int selected = 0;
        boolean error = false;
        do {
            try {
                selected = Integer.parseInt(scanner.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        while (true) {
            if (selected == 1) {
                initialize();
                break;
            } else if (selected == 2) {
                updateStatIDs();
                break;
            }
            else {
                break;
            }
        }
        System.out.println("1)Storage system");
        System.out.println("2)Suppliers system");
        System.out.println("3)Exit");
        do {
            try {
                selected = Integer.parseInt(scanner.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        while (true) {
            if (selected == 1) {
                runStorage(scanner, p);
                break;
            } else if (selected == 2) {
                runSuppliers(p);
                break;
            }
            else {
                break;
            }
        }
    }

    private static void runStorage(Scanner scanner, Presentation p) {
        while(true){
            System.out.println("1)Run System");
            System.out.println("2)Run tests");
            System.out.println("3)Exit");
            int initialized = 0;
            boolean error = false;
            do {
                try {
                    initialized = Integer.parseInt(scanner.nextLine());
                    error = false;
                }
                catch (Exception e){
                    error = true;
                    System.out.println("Illegal input try again.");
                }
            }while (error);

            if(initialized == 1){
                p.startProgramMenu();
                break;
            }

            else if(initialized ==2){
                Result result1 = JUnitCore.runClasses(CategoryTest.class);
                System.out.println("Run category test: " + result1.getRunCount());
                for(Failure failure : result1.getFailures()){
                    System.out.println(failure.toString());
                }

                Result result2 = JUnitCore.runClasses(LocationControllerTest.class);
                System.out.println("Run Location Controller Tests: " + result2.getRunCount());
                for(Failure failure : result2.getFailures()){
                    System.out.println(failure.toString());
                }

                Result result3 = JUnitCore.runClasses(ProductControllerTest.class);
                System.out.println("Run Product Controller Tests: " + result3.getRunCount());
                for(Failure failure : result3.getFailures()){
                    System.out.println(failure.toString());
                }

                Result result4 = JUnitCore.runClasses(PurchaseTransactionTest.class);
                System.out.println("Run Purchase Transaction Tests: " + result4.getRunCount());
                for(Failure failure : result4.getFailures()){
                    System.out.println(failure.toString());
                }

                System.exit(0);

            }
            else if(initialized ==3){
                System.exit(0);
            }
        }
    }

    private static void runSuppliers(Presentation p){
        //SystemController sc = SystemController.getInstance();
        //System.out.println("Would you like to load pre-made data? (y/n)");
        //if(scanner.next().equals("y"))
            //sc.loadSystem();
        p.run();
        //System.out.println("NO");
        //sc.unloadSystem();
        //sc.closeConnection();
    }

    private static void initialize(){

        API_Buisness manager = Singltone_Supplier_Storage_Manager.getInstance();
        manager.initialize();
        manager.loadSystem();

       /* Date expiration1 = null;
        Date supply1 = null;
        Date defect1 = null;
        Date sell1 = null;
        try {
            expiration1 = new SimpleDateFormat(PATTERN).parse("17/01/2020");
            supply1 = new SimpleDateFormat(PATTERN).parse("10/01/2020");
            defect1 = new SimpleDateFormat(PATTERN).parse("13/01/2020");
            sell1 = new SimpleDateFormat(PATTERN).parse("15/01/2020");

        }
        catch (Exception ignored){}*/



        /*
        set 3 main categories: 'Dairy' 'Canning' 'Personal care'
        set sub categories 'Milk' under 'Dairy',  'Large' under 'Milk' and 'Shower' under 'Personal care'
        */

            /*
        manager.setMainCategory("Dairy");
        manager.setMainCategory("Canning");
        manager.setMainCategory("Personal care");
        manager.setNewSubCategory("Milk", "Dairy");
        manager.setNewSubCategory("Large", "Milk");
        manager.setNewSubCategory("Shower", "Personal care");
        */

        /*  story:
        Milk: barcode 1 tnuva price:10 discount:0 expiration:"17/05/2020" amount:200 supplyTime: 10/05/2020
               location: SHELF (2)
        move 50 Milk from location 2 to location 3
        connect Milk to 'Large'
        set sale product info of Milk to be: 6.5 -> no discount
        */
        /*
        manager.buyProduct(1, "Milk",SupplierID,
                10,0,expiration1,
                200, supply1, LocationController.SHELF);
        manager.moveProduct(1, expiration1, 50, 2, 3);
        manager.connectProductToCategory("Large",1 );
        manager.setSaleInfoOfNewProduct(1, "Milk", 6.5, 0);
        */
        /*  story:
        Tuna: barcode 2 StarKist price:6.5 discount:2 expiration:"17/05/2020" amount:800 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 600 Tuna from location 1 to location 2
        connect Tuna to 'Canning'
        set sale product info of Tuna to be: 12.5 -> 2.5
        */
        /*
        manager.buyProduct(2, "Tuna",SupplierID,
                6.5,2,expiration1,
                800, supply1, LocationController.STORAGE);
        manager.moveProduct(2, expiration1, 600, 1, 2);
        manager.connectProductToCategory("Canning",2 );
        manager.setSaleInfoOfNewProduct(2, "Tuna", 12.5, 2.5);
        */

        /*  story:
        Tuna: barcode 3 Dove price:20 discount:5.5 expiration:"17/05/2020" amount:900 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 600 Shampoo from location 1 to location 2
        connect Shampoo to 'Shower'
        set sale product info of Shampoo to be: 25 -> 3
        */
        /*
        manager.buyProduct(3, "Shampoo",SupplierID,
                20,5.5,expiration1,
                900, supply1, LocationController.STORAGE);
        manager.moveProduct(3, expiration1, 600, 1, 2);
        manager.connectProductToCategory("Shower",3 );
        manager.setSaleInfoOfNewProduct(3, "Shampoo", 25, 3);
        */
        /*  story:
        Tuna: barcode 4 tnuva price:8 discount:3 expiration:"17/05/2020" amount:1000 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 450 Cheese from location 1 to location 2
        connect cheese to 'Dairy'
        set sale product info of Cheese to be: 6 -> 0
        */
        /*
        manager.buyProduct(4, "Cheese",SupplierID,
                8,3,expiration1,
                1000, supply1, LocationController.STORAGE);
        manager.moveProduct(4, expiration1, 450, 1, 2);
        manager.connectProductToCategory("Dairy",4 );
        manager.setSaleInfoOfNewProduct(4, "Dairy", 6, 0);
        */
        /*  story:
        Tuna: barcode 5 tnuva price:10 discount:1.5 expiration:"17/05/2020" amount:1000 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 555 Shocko from location 1 to location 2
        connect Shocko to 'Milk'
        set sale product info of Shocko to be: 14 -> 0
        */
         /*
        manager.buyProduct(5, "Shocko",SupplierID,
                10,1.5,expiration1,
                1000, supply1, LocationController.STORAGE);
        manager.moveProduct(5, expiration1, 555, 1, 2);
        manager.connectProductToCategory("Milk",5 );
        manager.setSaleInfoOfNewProduct(5, "Shocko", 14, 0);
        */
        /*
         * Set defects: a lot of products dropped from track because the driver was drunk :\
         * */
        /*
        manager.addDefect(supply1, 5, 200, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 4, 50, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 3, 155, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 2, 40, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(defect1, 2, 5, "costumer dropped from shelf", "Avi Ferdman", 2, expiration1);
        */
        /*
         * Sell products: Milk sold 12, Tuna Sold 21, Shocko sold 22
         * */

        /*

        manager.sellProduct(sell1, 1, 3, expiration1);
        manager.sellProduct(sell1, 1, 2, expiration1);
        manager.sellProduct(sell1, 1, 7, expiration1);
        manager.sellProduct(sell1, 2, 9, expiration1);
        manager.sellProduct(sell1, 2, 12, expiration1);
        manager.sellProduct(sell1, 5, 14, expiration1);
        manager.sellProduct(sell1, 5, 3, expiration1);
        manager.sellProduct(sell1, 5, 3, expiration1);
        manager.sellProduct(sell1, 5, 2, expiration1);

        */


    }

    private static void updateStatIDs(){
        API_Buisness manager = Singltone_Supplier_Storage_Manager.getInstance();
        manager.updateStatIDs();
    }
}
