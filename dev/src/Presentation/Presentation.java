package Presentation;

import StorageAndSupplier.Storage.Tests.CategoryTest;
import StorageAndSupplier.Storage.Tests.LocationControllerTest;
import StorageAndSupplier.Storage.Tests.ProductControllerTest;
import StorageAndSupplier.Storage.Tests.PurchaseTransactionTest;
import StorageAndSupplier.API_Buisness;
import StorageAndSupplier.*;
import StorageAndSupplier.Suppliers.BusinessLayer.*;
import SuperMarket.SuperMarket;
import javafx.util.Pair;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import DeliveryAndWorkers.Presentation.PTIDelController;
import SuperMarket.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Presentation {
    public static final int STORAGE_PERMISSION = 1;
    public static final int SUPPLIERS_PERMISSION = 2;
    public static final int WORKERS_PERMISSION = 3;
    public static final int DELIVERIES_PERMISSION = 4;
    public static final int MANAGER_STORAGE = 5;


    private SuperMarket superMarket;
    private int option;
    private Scanner in;
    String pattern;
    SimpleDateFormat simpleDateFormat;
    Date today;
    Boolean shouldTerminate = false;
    String choice;
    boolean quit;
    PTIDelController pti;
    int permission_NO;


    public Presentation() {
        this.superMarket = SuperMarketController.getInstance();
        superMarket.setup();
        quit=false;
        option = 0;
        in = new Scanner(System.in);
        pattern = "yyyy-mm-dd";
        simpleDateFormat = new SimpleDateFormat(pattern);
        //get today's date
        String date = simpleDateFormat.format(new Date());
        today = null;
        permission_NO = 0;
        try {
            today = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (Exception e) {}
    }


    public void loginUser (){
        System.out.println("\nWelcome, please login to the system\n");
        boolean error = false;
        do {
            try {
                System.out.print("Username:\t");
                String username = in.nextLine();
                System.out.print("Password:\t");
                String password = in.nextLine();
                error = false;
                permission_NO = this.superMarket.checkPermission(username, password);
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        if (permission_NO == 0){System.out.println("Permission Denied, sorry...");}
        else {
            startSystem(permission_NO);
        }
    }

    public void startSystem (int permission){
        List<String> initiateOptions = new ArrayList<>();
        initialize();
        int select = 0;
        switch (permission){
            case 1:
                initiateOptions = Arrays.asList("Workers system",
                        "Exit");
                printMenu(initiateOptions);
                select = getIntInput(1);
                if (select == 1) {
                    superMarket.mainFunc(false);
                }
                break;


            case 2:
                initiateOptions = Arrays.asList("Storage system",
                        "Exit");
                printMenu(initiateOptions);
                select = getIntInput(1);
                if (select == 1) {
                    runStorage();
                }
                break;

            case 3:
                initiateOptions = Arrays.asList("Suppliers system",
                        "Exit");
                printMenu(initiateOptions);
                select = getIntInput(1);
                if (select == 1) {
                    runSuppliers();
                }
                break;
            case 4:
                initiateOptions = Arrays.asList("Delivery system",
                        "Exit");
                printMenu(initiateOptions);
                select = getIntInput(1);
                if (select == 1) {
                    superMarket.start(false);
                }
                break;
            case 5:
                initiateOptions = Arrays.asList("Storage system",
                       "Workers system", "Suppliers system", "Delivery system",
                        "Exit");
                printMenu(initiateOptions);
                select = getIntInput(4);
                if (select == 1) {
                    runStorage();
                }
                else if (select == 2){
                    superMarket.mainFunc(true);
                }
                else if (select == 3){
                    runManager();
                }
                else if (select == 4){
                    superMarket.start(true);
                }

                break;

            default:
                break;
        }
    }

    private int getIntInput(int numberOfOptions) {
        int selected = 0;
        boolean error = false;
        do {
            try {
                selected = Integer.parseInt(in.nextLine());
                error = false;
                if (selected > numberOfOptions){
                    System.exit(0);
                }
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        return selected;
    }

    private void runStorage() {
        while(true){
            System.out.println("1)Run Storage System");
            System.out.println("2)Run Storage tests");
            System.out.println("3)Exit");
            int initialized = 0;
            boolean error = false;
            do {
                try {
                    initialized = Integer.parseInt(in.nextLine());
                    error = false;
                }
                catch (Exception e){
                    error = true;
                    System.out.println("Illegal input try again.");
                }
            }while (error);

            if(initialized == 1){
                startProgramMenu();
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

    private void runSuppliers(){
        //SystemController sc = SystemController.getInstance();
        //System.out.println("Would you like to load pre-made data? (y/n)");
        //if(scanner.next().equals("y"))
        //sc.loadSystem();
        run();
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

    /*
     * StorageAndSupplier.Storage Section
     * */

    public void startProgramMenu() {
        if (permission_NO == MANAGER_STORAGE){startProgramManagerMenu();}
        else {
            System.out.println("\nWelcome, please choose an action:\n");
            List<String> initiateOptions = Arrays.asList("Inventory section", "Location section",
                    "Transaction section", "Defect section", "Report section", "exit");
            printMenu(initiateOptions);
            boolean error = false;
            do {
                try {
                    option = Integer.parseInt(in.nextLine());
                    error = false;
                } catch (Exception e) {
                    error = true;
                    System.out.println("Illegal input try again.");
                }
            } while (error);

            switch (option) {
                case 1:
                    InventoryMenu();
                    break;
                case 2:
                    LocationMenu();
                    break;
                case 3:
                    TransactionMenu();
                    break;
                case 4:
                    DefectMenu();
                    break;
                case 5:
                    ReportMenu();
                    break;
                default:
                    superMarket.exit();
            }
            startProgramMenu();
        }
    }

    private void startProgramManagerMenu() {
        System.out.println("\nWelcome, please choose an action:\n");
        List<String> initiateOptions = Arrays.asList("Inventory section", "Location section",
                "Report section", "exit");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            } catch (Exception e) {
                error = true;
                System.out.println("Illegal input try again.");
            }
        } while (error);

        switch (option) {
            case 1:
                InventoryManagerMenu();
                break;
            case 2:
                LocationManagerMenu();
                break;
            case 3:
                ReportMenu();
                break;
            default:
                superMarket.exit();
        }
        startProgramMenu();
    }

    private void LocationManagerMenu() {
        System.out.println("\nLocation Section:\n");
        List<String> initiateOptions = Arrays.asList("Show locations by barcode" , "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                ShowProductLocation();
                break;
            default:
                break;
        }
    }

    private void InventoryManagerMenu() {
        System.out.println("\nInventory Section:\n");
        List<String> initiateOptions = Arrays.asList(
                "Alter product information", "Add category", "Product information",
                "print all categories name in store", "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                AlterProductMenu();
                break;
            case 2:
                addCatagoryToInventoryMenu();
                break;
            case 3:
                ShowProducInfoMenu();
                break;
            case 4:
                printAllExistingCategories();
                break;
            default:
                break;
        }
    }

    private void InventoryMenu() {
        System.out.println("\nInventory Section:\n");
        List<String> initiateOptions = Arrays.asList("Add new 'sale product'", "Edit existing 'sale product' information",
                "Alter product information", "Add category","Edit category", "Connect product to category", "Product information",
                "print all categories name in store", "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                addNewProductSale();
                break;
            case 2:
                editExistingSaleProductInformation();
                break;
            case 3:
                AlterProductMenu();
                break;
            case 4:
                addCatagoryToInventoryMenu();
                break;
            case 5:
                editCategory();
                break;
            case 6:
                connectProductToCategory();
                break;
            case 7:
                ShowProducInfoMenu();
                break;
            case 8:
                printAllExistingCategories();
                break;
            default:
                break;
        }
    }

    private void LocationMenu() {
        System.out.println("\nLocation Section:\n");
        List<String> initiateOptions = Arrays.asList("Move product", "Show locations by barcode" , "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                moveProduct();
                break;
            case 2:
                ShowProductLocation();
                break;
            default:
                break;
        }
    }

    private void TransactionMenu() {
        System.out.println("\nTransaction Section:\n");
        List<String> initiateOptions = Arrays.asList("Sell products", "Purchase products", "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                sellProducts();
                break;
            case 2:
                purchaseProducts();
                break;
            default:
                break;
        }
    }

    private void DefectMenu() {
        System.out.println("\nDefect Section:\n");
        List<String> initiateOptions = Arrays.asList("Add defects", "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                addDefect();
                break;
            default:
                break;
        }
    }

    private void ReportMenu() {
        System.out.println("\nReports section:\n");
        List<String> initiateOptions = Arrays.asList("Inventory report","Report By Categories" , "Defects report" ,  "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                showInventoryReport();
                break;
            case 2:
                showInventoryReportByCategories();
                break;
            case 3:
                getDefectsReports();
                break;
            default:
                break;
        }
    }

    private void printMenu(List<String> options) {
        int i = 1;
        for (String option : options) {
            System.out.println(i++ + ". " + option);
        }
    }

    private void AlterProductMenu(){
        List<String> initiateOptions = Arrays.asList(
                "Set Minimum amount of product",
                "Set manufactor of product",
                "Set next supply date of product",
                "Back to main menu");
        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                editMinumumAmountOfProduct();
                break;
            case 2:
                editManufactorOfProduct();
                break;
            case 3:
                editNextSupply();
            default:
                break;
        }

    }

    private void ShowProducInfoMenu(){
        List<String> initiateOptions = Arrays.asList(
                "General information",
                "Sale information",
                "Back to main menu");

        printMenu(initiateOptions);
        boolean error = false;
        do {
            try {
                option = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        switch (option) {
            case 1:
                ShowGeneralInfo();
                break;
            case 2:
                ShowSaleInfo();
                break;
            default:
                break;
        }
    }

    private void sellProducts() {
        boolean error = false;
        System.out.print("  Sell products:\n");
        System.out.print("  Type how many items (different bar codes) in this sale: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type expiration date of the " + i + " item (yyyy-mm-dd format): ");
                Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = simpleDateFormat.parse(expiDate);

                System.out.print("  Type amount of the " + i + " item: ");
                int amount = Integer.parseInt(in.nextLine());

                boolean alert = superMarket.sellProduct(today, barcode, amount, expirationDate);
                if (alert) { //alert to costumer that the product reached it's minimum amount limit
                    System.out.print("  Product " + barcode + " has reached under it's minimum amount!!!!!");
                }
            }
            catch(Exception e){
                error = true;
                break;
            }
        }
        if (error) {
            System.out.print("Illegal input");
        } else {
            System.out.print("\nSale complete successfully.\n");
        }
    }

    private void purchaseProducts() {

        /*int SupplierID, int catalogID, String productName, int price,
        int discount,Date expirationDate, int amount, Date date,int location -> parameters needed for each product*/
        boolean error = false;
        System.out.print("  Purchase products:\n");
        System.out.print("  Type how many items (different bar codes) in this purchase: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type supplier ID (number): ");
                int supplierID = Integer.parseInt(in.nextLine());
                System.out.print("  Type catalogID number of the " + i + " item: ");
                int catalogID = Integer.parseInt(in.nextLine());
                System.out.print("  Type product name of the " + i + " item: ");
                String productName = in.nextLine();
                System.out.print("  Type price of the " + i + " item: ");
                double price = Double.parseDouble(in.nextLine());
                System.out.print("  Type discount of the " + i + " item (if doesn't exists type 0): ");
                double discount = Double.parseDouble(in.nextLine());
                System.out.print("  Type expiration date of the " + i + " item (yyyy-mm-dd format): ");
                Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("yyyy-mm-dd").parse(expiDate);

                System.out.print("  Type amount of the " + i + " item: ");
                int amount = Integer.parseInt(in.nextLine());
                System.out.print("  Type the number of location to allocate the " + i + " item: ");
                int location = Integer.parseInt(in.nextLine());
                superMarket.buyProduct(supplierID, catalogID, productName,
                        price, discount, expirationDate,
                        amount, today, location);
            } catch (Exception e) {
                error = true;
                break;
            }
        }
        if (error) {
            System.out.print("Illegal input");
        } else {
            System.out.print("\nPurchase complete successfully.\n");
        }
    }

    private void addNewProductSale() {
        /*int barcode, String productName, double price, double discount -> the parameters should give*/
        boolean error = false;
        System.out.print("  Add products sale information:\n");
        System.out.print("  Type how many items (different bar codes) in this sale: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type product name of the " + i + " item: ");
                String productName = in.nextLine();
                System.out.print("  Type price of the " + i + " item: ");
                double price = Double.parseDouble(in.nextLine());
                System.out.print("  Type discount of the " + i + " item: ");
                double discount = Double.parseDouble(in.nextLine());
                superMarket.setSaleInfoOfNewProduct(barcode, productName, price, discount);
            }
            catch (Exception e){
                error = true;
                break;
            }
        }
        if (error) {
            System.out.println("Illegal input.");
        } else {
            System.out.print("\nAdd new product sale complete successfully.\n");
        }
    }

    private void editExistingSaleProductInformation() {
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit existing products sale information:\n");
        printMenu(Arrays.asList("Edit price", "Edit discount", "back to menu"));
        int chosenOption = 0;
        do {
            try {
                chosenOption = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        System.out.print("  Type how many items (different bar codes) you want to edit: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                if (chosenOption == 1) {
                    System.out.print("  Type the new price of the " + i + " item: ");
                    double newPrice = Double.parseDouble(in.nextLine());
                    superMarket.setPriceOfExistingProduct(barcode, newPrice);
                } else {
                    System.out.print("  Type the new discount of the " + i + " item: ");
                    double newDiscount = Double.parseDouble(in.nextLine());
                    superMarket.setDiscountOfExistingProduct(barcode, newDiscount);
                }
            }
            catch (Exception e){
                error = true;
                break;
            }

        }
        if (error) {
            System.out.println("Illegal input.");
        } else {
            System.out.print("\nEdit sale details complete successfully.\n");
        }
    }

    private void editMinumumAmountOfProduct(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit minimum amount of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type the minimum amount of the " + i + " item: ");
                int minimumAmount = Integer.parseInt(in.nextLine());
                superMarket.setMinimumAmount(barcode, minimumAmount);
            }
            catch (Exception e){
                error = true;
                break;
            }

        }
        if (error) {
            System.out.println("Illegal input.");
        } else {
            System.out.print("\nEdit minimum amount of product complete successfully.\n");
        }
    }

    private void editManufactorOfProduct(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit manufactor of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type new supplier's ID (number) of the " + i + " item: ");
                int supplierID = Integer.parseInt(in.nextLine());
                superMarket.setManufactorforProduct(barcode, supplierID);
            }
            catch (Exception e){
                error = true;
                break;
            }
        }
        if (error) {
            System.out.println("Illegal input.");
        } else {
            System.out.print("\nEdit Manufactor of product complete successfully.\n");
        }
    }

    private void editNextSupply(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit next Supply of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type next supply date of the " + i + " item: ");
                Date nextSupply = null;

                String expiDate = in.nextLine();
                nextSupply = new SimpleDateFormat("yyyy-mm-dd").parse(expiDate);

                superMarket.setNextSupply(barcode, nextSupply);
            } catch (Exception e) {
                error = true;
                break;
            }
        }
        if (error) {
            System.out.print("Illegal input");
        } else {
            System.out.print("\nEdit Manufactor of product complete successfully.\n");
        }
    }

    private void ShowGeneralInfo() {
        System.out.print("  Type how many items (different bar codes) you want to watch general information: ");
        int numberOfItems = 0;
        boolean error = false;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        List<Integer> productsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            try {
                int barcode = Integer.parseInt(in.nextLine());
                productsToShow.add(new Integer(barcode));
            }
            catch (Exception e){
                break;
            }
        }
        for(Integer barcode : productsToShow){
            System.out.print(
                    "   Barode: " + barcode + "\n" +
                    "   Product name :" + superMarket.getProducteName(barcode)+ "\n" +
                    "   Product manufactor :" + superMarket.getProducteManufactor(barcode)+ "\n"+
                    "   Product amount :" + superMarket.getProducteAmount(barcode)+ "\n"+
                    "   Product minimum amount :" + superMarket.getProducteMinAmount(barcode)+ "\n"+
                    "   Product next supply date :" + superMarket.getProducteDate(barcode) +
                     "\n\n");
        }
    }

    private void ShowSaleInfo() {
        System.out.print("  Type how many items (different bar codes) you want to watch sale information: ");
        int numberOfItems = 0;
        boolean error = false;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        List<Integer> productsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            try{
                int barcode = Integer.parseInt(in.nextLine());
                productsToShow.add(new Integer(barcode));
            }
            catch (Exception e){
                break;
            }
        }
        for(Integer barcode : productsToShow){
         System.out.print(
                 "  Barode: " + barcode + "\n" +
                 "  Product name: " + superMarket.getDataSaleName(barcode)+ "\n" +
                 "  Product price: " + superMarket.getDataSalePrice(barcode)+ "\n"+
                 "  Product discount: " + superMarket.getDataSaleDiscount(barcode) + "\n\n");
        }
    }

    private void ShowProductLocation(){
        System.out.print("  Type how many items (different bar codes) you want to watch location information: ");
        int numberOfItems = 0;
        boolean error = false;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        List<Integer> locationsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            try {
                int barcode = Integer.parseInt(in.nextLine());
                locationsToShow.add(new Integer(barcode));
            }
            catch (Exception e){
                break;
            }

        }
        System.out.print("\n\n");

        for(Integer barcode : locationsToShow){
            List<Date> dateList = superMarket.getBarcodDates(barcode);
            if(dateList!=null) {
                System.out.print("  Barode: " + barcode + "\n");


                for (Date d : dateList) {
                    System.out.print("  \tDate: " + d.toString() + "\n");
                    List<Integer> locationList = superMarket.getLocationsByDate(barcode, d);

                    for (Integer i : locationList) {
                        Integer j = superMarket.getAmountByLocation(barcode, d, i);
                        System.out.print("  \t\tIn location: " + i.toString() +
                                " the amount is: " + j.toString() + "\n");
                    }

                }

            }
            else{
                System.out.println("  Barode: " + barcode + " not exsist");
            }
            System.out.print("\n\n");
        }

    }

    private void addDefect() {
        //Date date, int barCode, int amount, String reason, String creator, int location, Date expiration -> the parameters
        boolean error = false;
        System.out.print("  Add defects:\n");
        System.out.print("  Type how many items (different bar codes) you want to declare as defects: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  barcode of defect item " + i + ": ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  amount of defect item " + i + ": ");
                int amount = Integer.parseInt(in.nextLine());
                System.out.print("  reason of defect item " + i + ": ");
                String reason = in.nextLine();
                System.out.print("  creator of defect item " + i + ": ");
                String creator = in.nextLine();
                System.out.print("  location (number) of defect item " + i + ": ");
                int location = Integer.parseInt(in.nextLine());
                System.out.print("  expiration date of defect item " + i + ": ");
                Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("yyyy-mm-dd").parse(expiDate);

                System.out.print("\n");
                superMarket.addDefect(today, barcode, amount, reason, creator, location, expirationDate);
            } catch (Exception e) {
                error = true;
                break;
            }
        }
        if (error) {
            System.out.print("Illegal input");
        } else {
            System.out.print("\nSet defects complete successfully.\n");
        }
    }


    private void getDefectsReports() {
        boolean error = false;
        System.out.print("  Defect report:\n");
        System.out.print("  Type date to from which you wish to find defects (yyyy-mm-dd format): ");
        Date fromDate = null;
        try {
            String fromDateS = in.nextLine();
            fromDate = new SimpleDateFormat(pattern).parse(fromDateS);
            List<Pdefect> defectsToShow = superMarket.creatDefectReport(today, fromDate); //THE END ISN'T RELEVANT
            showPdefects(defectsToShow , fromDate);
        } catch (Exception e) {
            error = true;
        }

        if (error) {
            System.out.print("Illegal date input");
        } else {
            System.out.print("\nDefect report complete successfully.\n");
        }
    }


    private void showPdefects(List<Pdefect> defectsToShow , Date start) {
        System.out.print("\nThe defect report from date " + start.toString() + " is:\n");
        System.out.print("sorted by dates:\n\n");
        List<Pdefect> defects = defectsToShow;
        defects.sort(Comparator.comparing(Pdefect::getDate));
        if (defects.size() == 0) {
            System.out.print("No defects to show.\n");
            return;
        }
        for (Pdefect defect : defects) {
            System.out.println(
                    " date accrued: " + defect.getDate().toString() +"\n" +
                            " barcode: " + defect.getBarCode() + "\n" +
                            " amount: " + defect.getAmount() + "\n" +
                            " reason: " + defect.getReason() + "\n" +
                            " creator: " + defect.getCreator() + "\n" +
                            " location: " + defect.getLocation() + "\n" +
                            " expiration date: " + defect.getExpiration().toString() + "\n\n");
        }
    }

    private void addCatagoryToInventoryMenu() {
        /*
        *   void setMainCategory(String mainCategoryName);
            void setNewSubCategory(String subcategoryName, String MainCategoryName);
        * */
        boolean error = false;
        System.out.print("  Add categories:\n");
        printMenu(Arrays.asList("add main category", "Add sub category", "back to main menu"));
        int chosenOption = 0;
        do {
            try {
                chosenOption = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        System.out.print("  Type how many categories you want to add: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  " + i + ". Category name: ");
            String categoryName = in.nextLine();
            if (chosenOption == 1) {
                superMarket.setMainCategory(categoryName);
            } else if (chosenOption == 2) {
                System.out.print("  " + i + ". Main category name (the category above this category): ");
                String mainCategoryName = in.nextLine();
                superMarket.setNewSubCategory(categoryName, mainCategoryName);
            }
            System.out.print("\n");
        }
        System.out.print("\nAdd categories complete successfully.\n");
    }

    private void editCategory(){
        /*
        *   void setCategoryName(String CategoryName, String changeToThisName);
            void deleteCategory(String categoryName);
        * */
        boolean error = false;
        System.out.print("  Edit categories:\n");
        printMenu(Arrays.asList("Edit existing category name", "delete category", "back to main menu"));
        int chosenOption = 0;
        do {
            try {
                chosenOption = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        System.out.print("  Type how many categories you want to edit: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  " + i + ". Category name: ");
            String categoryName = in.nextLine();
            if (chosenOption == 1) {
                System.out.print("  " + i + ". Type the new name of category: ");
                String newName = in.nextLine();
                superMarket.setCategoryName(categoryName, newName);
            } else if (chosenOption == 2) {
                superMarket.deleteCategory(categoryName);
            }
            System.out.print("\n");
        }
        System.out.print("\nEdit categories complete successfully.\n");
    }

    private void printAllExistingCategories() {
        {
            System.out.print("\nListing all categories in store:\n");
            List<String> names = superMarket.getListOfCategoriesNames();
            if (names != null) {
                printMenu(names);
            } else {
                System.out.print("no categories to show.\n");
            }
            System.out.print("\nprinting all categories names complete successfully.\n");
        }
    }

    private void moveProduct() {
        //int barCode, Date expiration, int amount, int fromLocation, int toLocation -> parameters
        boolean error = false;
        System.out.print("  Move products:\n");
        System.out.print("  Type how many items (different bar codes) you want to move place: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
            System.out.print("  " + i + ". barcode of item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". expiration date of item: ");
            Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("YYYY-MM-DD").parse(expiDate);

            System.out.print("  " + i + ". amount of items to move: ");
            int amount = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". from location (number): ");
            int fromLocation = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". To location (number): ");
            int toLocation = Integer.parseInt(in.nextLine());
            System.out.print("\n");
                superMarket.moveProduct(barcode, expirationDate, amount, fromLocation, toLocation);
            } catch (Exception e) {
                error = true;
                break;
            }
        }
        if (error) {
            System.out.print("Illegal input.");
        } else {
            System.out.print("\nMove products complete successfully.\n");
        }

    }

    private void connectProductToCategory() {
        boolean error = false;
        System.out.print("  Connect product to category:\n");
        System.out.print("  Type how many items (different bar codes) you want to connect to categories: ");
        int numberOfItems = 0;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  " + i + ". barcode of product: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  " + i + ". category name to connect product:");
                String categoryName = in.nextLine();
                System.out.print("\n");
                superMarket.connectProductToCategory(categoryName, barcode);
            }
            catch (Exception e){
                error = true;
                break;
            }
        }
        if (error) {
            System.out.println("Illegal input.");
        } else {
            System.out.print("\nConnect product to category successfully.\n");
        }
    }

    private List<String> getMainCategoriesByDate(Date date) {
        List<String> filterMainCategories = superMarket.getListOfCategoriesNames();
        for (List<String> listOfCategories : superMarket.subcat(date)) {
            for (String name : listOfCategories) {
                if (filterMainCategories.contains(name)) {
                    filterMainCategories.remove(name);      //remove from list all sub categories
                }
            }
        }
        return filterMainCategories;
    }


    public void showInventoryReport(){
        superMarket.creatInventoryReport(today);
        System.out.println("Updated inventory report: \n");
        System.out.println("report for date: " + today.toString() + "\n\n");
        List<String> mainCategories = getMainCategoriesByDate(today);
        for(String category : mainCategories){
            showRecursiveFromMainCategoryDowns(category , "");
        }

    }

    public void showInventoryReportByCategories(){
        superMarket.creatInventoryReport(today);
        System.out.println("Updated inventory report: \n");
        List<String> mainCategories = new ArrayList<>();
        System.out.print("  Type how many categories (different ctegories) you want to watch: ");
        int numberOfItems = 0;
        boolean error = false;
        do {
            try {
                numberOfItems = Integer.parseInt(in.nextLine());
                error = false;
            }
            catch (Exception e){
                error = true;
                System.out.println("Illegal input try again.");
            }
        }while (error);
        for (int i = 1; i <= numberOfItems; i++) {

            System.out.print("  " + i + ". category name:");
            String categoryName = in.nextLine();
            mainCategories.add(categoryName);
            System.out.print("\n");
        }
        System.out.println("report for date: " + today.toString() + "\n\n");

        for(String category : mainCategories){
            showRecursiveFromMainCategoryDowns(category , "");
        }

    }

    private void showRecursiveFromMainCategoryDowns(String FromHereAndDown, String offset) {
        System.out.print(offset + "- Products under category " + FromHereAndDown + ":\n");
        List<PdataInventoryReport> myCategoryProducts = superMarket.RepProdofInventoryReport(today , FromHereAndDown);
        for (PdataInventoryReport productRepData : myCategoryProducts) {
            System.out.print(offset + "- Barcode: " + productRepData.getBarcode() + ", Product name: " + productRepData.getProductName() + ", Product amount: " + productRepData.getAmount() + "\n");
        }
        for (String subCategories : superMarket.CategoriesOfInventoryReport(today ,FromHereAndDown)) {
            showRecursiveFromMainCategoryDowns(subCategories,offset + "        ");
        }
    }

    /*
    * StorageAndSupplier.Suppliers Section
    * */

    public void run(){
        boolean isTerminated = false;
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Would you like to load pre-made data? (y/n)");
        //if(scanner.next().equals("y")) {
        //    businessManager.loadSystem();
        //    isLoad = true;
        //}

        boolean error;
        while(!isTerminated){
            System.out.println("Choose 1 for suppliers");
            System.out.println("Choose 2 for orders");
            System.out.println("Choose 3 for reports");
            System.out.println("Choose 4 for agreements");
            System.out.println("Choose 0 to exit");
            int option = 0;
            do {
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    error = false;
                }
                catch (Exception e){
                    error = true;
                    System.out.println("Illegal input try again.");
                }
            }while (error);
            switch (option){
                case 1:
                    System.out.println("Choose 1 to view details about a supplier");
                    System.out.println("Choose 2 to add a new supplier to the system");
                    System.out.println("Choose 3 to edit the supplier's details");
                    System.out.println("Choose 4 to delete a supplier from the system");
                    System.out.println("Choose 5 to add a new contact to a supplier");
                    System.out.println("Choose 6 to remove a contact from a supplier");
                    System.out.println("Choose 7 to add a new product to a supplier");
                    System.out.println("Choose 8 to remove a product from a supplier");
                    System.out.println("Choose 9 to view all products a supplier has to offer");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int suppid = scanner.nextInt();
                            Supplier s = superMarket.getSupplier(suppid);
                            if(s == null) {
                                System.out.println("No such supplier");
                                break;
                            }
                            System.out.println("ID: " + s.getID());
                            System.out.println("Name: " + s.getName());
                            System.out.println("Company ID: " + s.getCompanyID());
                            System.out.println("Pay condition: " + s.getPayCond());
                            System.out.println("Phone number: " + s.getPhoneNum());
                            System.out.println("Bank account: " + s.getBankAccNum());
                            if(!s.getContacts().isEmpty()) {
                                System.out.print("Contacts: ");
                                for (Pair<String, String> contact : s.getContacts())
                                    System.out.print("<" + contact.getKey() + ", " + contact.getValue() + "> ");
                                System.out.println();
                            }
                            System.out.println();
                            break;
                        case 2:
                            System.out.println("Please type the name:");
                            String name = scanner.next();
                            if(!checkValidName(name))
                                break;
                            System.out.println("Please type the company ID:");
                            int cid = scanner.nextInt();
                            System.out.println("Please type the phone number:");
                            String pn = scanner.next();
                            System.out.println("Please type the bank account:");
                            String ba = scanner.next();
                            System.out.println("Please type the pay condition:");
                            String pc = scanner.next();
                            System.out.println("Choose 1 to create fixed days supplier");
                            System.out.println("Choose 2 to create order only supplier");
                            System.out.println("Choose 3 to create self pickup supplier");
                            System.out.println("Choose 0 to go back");
                            do {
                                try {
                                    option = Integer.parseInt(scanner.nextLine());
                                    error = false;
                                }
                                catch (Exception e){
                                    error = true;
                                    System.out.println("Illegal input try again.");
                                }
                            }while (error);
                            switch (option){
                                case 1:
                                    if(!superMarket.addSupplier("FixedDays",name, cid,ba,pc,pn,""))
                                        System.out.println("Error!");
                                    else System.out.println("Success!");
                                    break;
                                case 2:
                                    if(!superMarket.addSupplier("OrderOnly", name, cid,ba,pc,pn,""))
                                        System.out.println("Error!");
                                    else System.out.println("Success!");
                                    break;
                                case 3:
                                    System.out.println("Enter the pickup location");
                                    if(!superMarket.addSupplier("SelfPickup", name, cid,ba,pc,pn, scanner.nextLine()))
                                        System.out.println("Error!");
                                    else System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Choose 1 to edit Company ID");
                            System.out.println("Choose 2 to edit bank account");
                            System.out.println("Choose 3 to edit pay condition");
                            System.out.println("Choose 4 to edit phone number");
                            System.out.println("Choose 0 to go back");
                            do {
                                try {
                                    option = Integer.parseInt(scanner.nextLine());
                                    error = false;
                                }
                                catch (Exception e){
                                    error = true;
                                    System.out.println("Illegal input try again.");
                                }
                            }while (error);
                            int id;
                            switch (option){
                                case 1:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new Company ID");
                                    int cid1 = scanner.nextInt();
                                    if(superMarket.setSupplierCompanyID(id,cid1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error");
                                    break;
                                case 2:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new bank account");
                                    String ba1 = scanner.next();
                                    if(superMarket.setSupplierBankAccNum(id,ba1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error");
                                    break;
                                case 3:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new pay condition");
                                    String pc1 = scanner.next();
                                    if(superMarket.setSupplierPayCond(id,pc1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error");
                                    break;
                                case 4:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new phone number");
                                    String pn1 = scanner.next();
                                    if(superMarket.setSupplierPhoneNum(id,pn1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Please enter the supplier ID");
                            if(superMarket.removeSupplier(scanner.nextInt()))
                                System.out.println("Success!");
                            else System.out.println("Error!");
                            break;
                        case 5:
                            System.out.println("Enter the supplier's ID");
                            int sid = scanner.nextInt();
                            System.out.println("Enter the contact's full name");
                            String fn = scanner.next();
                            if(!checkValidName(fn))
                                break;
                            System.out.println("Enter the contact's phone number");
                            String pnum = scanner.next();
                            if(!superMarket.addSupplierContact(sid, new Pair<>(fn, pnum)))
                                System.out.println("Error");
                            else System.out.println("Success!");
                            break;
                        case 6:
                            System.out.println("Enter the supplierID");
                            int suid = scanner.nextInt();
                            System.out.println("Enter the contact's phone number");
                            if(superMarket.removeSupplierContact(suid, scanner.nextLine()))
                                System.out.println("Success!");
                            else System.out.println("Error!");
                            break;
                        case 7:
                            System.out.println("Enter the catalogID");
                            int catid = scanner.nextInt();
                            System.out.println("Enter the price (##.##)");
                            double price = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Enter the name");
                            String pname = scanner.nextLine();
                            if(!checkValidName(pname))
                                break;
                            System.out.println("Enter the manufacturer");
                            String manufacturer = scanner.nextLine();
                            if(!checkValidName(manufacturer))
                                break;
                            System.out.println("For the expiration date:");
                            System.out.println("Enter the year");
                            int year = scanner.nextInt();
                            System.out.println("Enter the month");
                            int month = scanner.nextInt();
                            System.out.println("Enter the day");
                            int day = scanner.nextInt();
                            System.out.println("Enter the hour");
                            int hour = scanner.nextInt();
                            System.out.println("Enter the minutes");
                            int minutes = scanner.nextInt();
                            LocalDateTime l = LocalDateTime.of(year,month,day,hour,minutes);
                            System.out.println("Please enter the weight of the product");
                            double weight = scanner.nextDouble();
                            System.out.println("Enter the id of the supplier that supplies this product");
                            if(!superMarket.addProduct(scanner.nextInt(), catid, price, pname, manufacturer, l, weight))
                                System.out.println("Error");
                            else System.out.println("Success!");
                            break;
                        case 8:
                            System.out.println("Enter the supplierID");
                            int supid = scanner.nextInt();
                            System.out.println("Enter the productID");
                            if(superMarket.removeSupplierProduct(supid, scanner.nextInt()))
                                System.out.println("Success!");
                            else System.out.println("Error!");
                            break;
                        case 9:
                            System.out.println("Enter the supplierID");
                            int sid1 = scanner.nextInt();
                            for(Product p : superMarket.getSupplier(sid1).getProducts())
                                System.out.println(p.toString() + "\n");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Choose 1 to view details about all pending orders of a certain supplier");
                    System.out.println("Choose 2 to add an order");
                    System.out.println("Choose 3 to edit an order");
                    System.out.println("Choose 4 to cancel an order");
                    System.out.println("Choose 5 to announce the arrival of an order");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int suppid = scanner.nextInt();
                            Supplier supp = superMarket.getSupplier(suppid);
                            if(supp == null) {
                                System.out.println("No such supplier");
                                break;
                            }
                            for(Order o : supp.getOrders())
                                System.out.println(o.toString() + "\n");
                            break;
                        case 2:
                            System.out.println("Please enter the supplier ID");
                            int s = scanner.nextInt();
                            HashMap<Pproduct, Pair<Integer, Integer>> hm = new HashMap<>();
                            System.out.println("Please enter the source address");
                            String src = scanner.nextLine();
                            if(!checkValidName(src))
                                break;
                            System.out.println("Please enter the destination address");
                            String dest = scanner.nextLine();
                            if(!checkValidName(dest))
                                break;
                            String ans;
                            do{
                                System.out.println("Please enter the id of the product");
                                int id = scanner.nextInt();
                                System.out.println("Please enter the name of the product");
                                String name = scanner.next();
                                if(!checkValidName(name))
                                    break;
                                System.out.println("Please enter the manufacturer of the product");
                                String manu = scanner.next();
                                if(!checkValidName(manu))
                                    break;
                                System.out.println("Please enter the price of the product");
                                double pri = scanner.nextDouble();
                                System.out.println("Please enter the quantity of the product");
                                int amo = scanner.nextInt();
                                System.out.println("Please enter the weight of the product");
                                double weight = scanner.nextDouble();
                                Pproduct p = new Pproduct(id, pri,name,manu, LocalDateTime.now().plusDays(7), weight);
                                Pair<Integer, Integer> pair = new Pair<>(amo,0);
                                hm.put(p,pair);
                                System.out.println("Would you like to enter another one? (y/n)");
                                ans = scanner.next();
                            } while(!ans.equals("n"));
                            if(!superMarket.addOrder(s,LocalDateTime.now(),hm, src, dest))
                                System.out.println("Error");
                            else System.out.println("Success!");
                        case 3:
                            System.out.println("Choose 1 to edit the ETA of an order");
                            System.out.println("Choose 2 to edit the amount of a certain product in an order");
                            System.out.println("Choose 3 to remove a product from an order");
                            System.out.println("Choose 4 to add a product to an order");
                            System.out.println("Choose 5 to change the source address");
                            System.out.println("Choose 6 to change the destination address");
                            System.out.println("Choose 0 to go back");
                            do {
                                try {
                                    option = Integer.parseInt(scanner.nextLine());
                                    error = false;
                                }
                                catch (Exception e){
                                    error = true;
                                    System.out.println("Illegal input try again.");
                                }
                            }while (error);
                            switch (option) {
                                case 0:
                                    break;
                                case 1:
                                    System.out.println("Enter the ID of the order");
                                    int ordid = scanner.nextInt();
                                    System.out.println("Enter the new year");
                                    int year = scanner.nextInt();
                                    System.out.println("Enter the new month");
                                    int month = scanner.nextInt();
                                    System.out.println("Enter the new day");
                                    int day = scanner.nextInt();
                                    System.out.println("Enter the new hour");
                                    int hour = scanner.nextInt();
                                    System.out.println("Enter the new minutes");
                                    int minutes = scanner.nextInt();
                                    LocalDateTime l = LocalDateTime.of(year,month,day,hour,minutes);
                                    if((superMarket.getOrder(ordid) != null) && (superMarket.setOrderETA(ordid, l)))
                                        System.out.println("Success!");
                                    else System.out.println("Error!");
                                    break;
                                case 2:
                                    System.out.println("Please enter the order's ID");
                                    int oid = scanner.nextInt();
                                    System.out.println("Please enter the catalog ID of the product");
                                    int cid = scanner.nextInt();
                                    System.out.println("Please enter the new amount");
                                    if(!superMarket.setAmountOfProductInOrder(oid, cid, scanner.nextInt()))
                                        System.out.println("Error");
                                    else System.out.println("Success!");
                                    break;
                                case 3:
                                    System.out.println("Enter supplierID of the order");
                                    int sid = scanner.nextInt();
                                    System.out.println("Enter orderID");
                                    int orid = scanner.nextInt();
                                    System.out.println("Enter productID");
                                    if(superMarket.removeProductFromOrder(sid, orid, scanner.nextInt()))
                                        System.out.println("Success!");
                                    else System.out.println("Error");
                                    break;
                                case 4:
                                    System.out.println("Enter the productID");
                                    int pid = scanner.nextInt();
                                    System.out.println("Enter the orderID");
                                    int orderid = scanner.nextInt();
                                    System.out.println("Enter the supplierID");
                                    int suid = scanner.nextInt();
                                    System.out.println("Enter the amount you would like to order");
                                    int amount = scanner.nextInt();
                                    if(!superMarket.addNewProductToOrder(pid, orderid, suid, amount))
                                        System.out.println("Error");
                                    else System.out.println("Success!");
                                    break;
                                case 5:
                                    System.out.println("Please enter the order's ID");
                                    int ordeid1 = scanner.nextInt();
                                    System.out.println("Please enter the new source address");
                                    String src1 = scanner.nextLine();
                                    if(!superMarket.setOrderSourceAddress(ordeid1, src1))
                                        System.out.println("Error");
                                    else System.out.println("Success!");
                                    break;
                                case 6:
                                    System.out.println("Please enter the order's ID");
                                    int ordeid2 = scanner.nextInt();
                                    System.out.println("Please enter the new destination address");
                                    String dest1 = scanner.nextLine();
                                    if(!superMarket.setOrderSourceAddress(ordeid2, dest1))
                                        System.out.println("Error");
                                    else System.out.println("Success!");
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the ID of the order");
                            int i = scanner.nextInt();
                            Order ooo = superMarket.getOrder(i);
                            if(ooo == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            if(!superMarket.reportCancellation(ooo))
                                System.out.println("Error");
                            System.out.println("Success!");
                            break;
                        case 5:
                            System.out.println("Enter the ID of the order");
                            int i1 = scanner.nextInt();
                            Order ooo1 = superMarket.getOrder(i1);
                            if(ooo1 == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            if(!superMarket.reportArrival(ooo1))
                                System.out.println("Error");
                            System.out.println("Success!");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Choose 1 to view all arrival reports");
                    System.out.println("Choose 2 to view all cancellation reports");
                    System.out.println("Choose 3 to view a certain report");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            for(Report r : superMarket.getReports()){
                                if(r instanceof ArrivalReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported().toString());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 2:
                            for(Report r : superMarket.getReports()){
                                if(r instanceof CancellationReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Enter the reportID");
                            int rid = scanner.nextInt();
                            Report r = superMarket.getReport(rid);
                            if(r == null){
                                System.out.println("No such report");
                                break;
                            }
                            System.out.println("Report ID: "+r.getID());
                            System.out.println("Date reported: "+r.getDateReported());
                            System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Choose 1 to view details about all agreements of a certain supplier");
                    System.out.println("Choose 2 to add a new agreement");
                    System.out.println("Choose 3 to edit an agreement");
                    System.out.println("Choose 4 to remove agreement");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int sid = scanner.nextInt();
                            Supplier s = superMarket.getSupplier(sid);
                            if(s == null){
                                System.out.println("No such supplier");
                                break;
                            }
                            for(Agreement a : s.getAgreements())
                                System.out.println(a.toString() + "\n");
                            break;
                        case 2:
                            System.out.println("Enter the supplier ID");
                            int su = scanner.nextInt();
                            Supplier to = superMarket.getSupplier(su);
                            if(to == null){
                                System.out.println("Error! no such supplier");
                                break;
                            }
                            System.out.println("Enter the catalogID of the product to be applied on");
                            int catalogID1 = scanner.nextInt();
                            System.out.println("Enter the name of the product");
                            String nn = scanner.next();
                            if(!checkValidName(nn))
                                break;
                            System.out.println("Enter the price of the product");
                            double pp = scanner.nextDouble();
                            System.out.println("Enter the manufacturer of the product");
                            String mm = scanner.next();
                            if(!checkValidName(mm))
                                break;
                            System.out.println("Please enter the weight of the product");
                            double weight = scanner.nextDouble();
                            System.out.println("Enter the amount for the sale to apply");
                            int aa = scanner.nextInt();
                            System.out.println("Enter the sale for it");
                            int hh = scanner.nextInt();
                            if(superMarket.addAgreement(to.getID(), new Pair<>(new Pproduct(catalogID1,pp,nn,mm, LocalDateTime.now().plusDays(7), weight),new Pair<>(aa,hh))))
                                System.out.println("Success!");
                            else System.out.println("Error, supplier does not supply this item");
                            break;
                        case 3:
                            System.out.println("Choose 1 to set the product amount");
                            System.out.println("Choose 2 to set the product sale");
                            System.out.println("Choose 0 to go back");
                            do {
                                try {
                                    option = Integer.parseInt(scanner.nextLine());
                                    error = false;
                                }
                                catch (Exception e){
                                    error = true;
                                    System.out.println("Illegal input try again.");
                                }
                            }while (error);
                            switch (option) {
                                case 0:
                                    break;
                                case 1:
                                    System.out.println("Enter the agreement ID");
                                    int u = scanner.nextInt();
                                    System.out.println("Enter new amount");
                                    int y = scanner.nextInt();
                                    if(superMarket.setAgreementProdAmount(u, y))
                                        System.out.println("Success!");
                                    else System.out.println("Error, no such agreement");
                                    break;
                                case 2:
                                    System.out.println("Enter the agreement ID");
                                    int u1 = scanner.nextInt();
                                    System.out.println("Enter new condition");
                                    int y1 = scanner.nextInt();
                                    if(superMarket.setAgreementProdSale(u1, y1))
                                        System.out.println("Success!");
                                    else System.out.println("Error, no such agreement");
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the supplier ID");
                            int ii = scanner.nextInt();
                            System.out.println("Enter agreement ID");
                            int jj = scanner.nextInt();
                            if(superMarket.removeSupplierAgreement(ii, jj))
                                System.out.println("Success!");
                            else System.out.println("Error!");
                            break;
                    }
                    break;
                default:
                    isTerminated = true;
                    break;
            }
        }
        System.out.println("\nThanks for using our system, looking forward to seeing you again!");
    }

    public void runManager() {
        boolean isTerminated = false;
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Would you like to load pre-made data? (y/n)");
        //if(scanner.next().equals("y")) {
        //    businessManager.loadSystem();
        //    isLoad = true;
        //}
        boolean error;
        while (!isTerminated) {
            System.out.println("Choose 1 for suppliers");
            System.out.println("Choose 2 for orders");
            System.out.println("Choose 3 for reports");
            System.out.println("Choose 4 for agreements");
            System.out.println("Choose 0 to exit");
            int option = 0;
            do {
                try {
                    option = Integer.parseInt(scanner.nextLine());
                    error = false;
                }
                catch (Exception e){
                    error = true;
                    System.out.println("Illegal input try again.");
                }
            }while (error);
            switch (option) {
                case 1:
                    System.out.println("Choose 1 to view details about a supplier");
                    System.out.println("Choose 2 to view all products a supplier has to offer");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int suppid = scanner.nextInt();
                            Supplier s = superMarket.getSupplier(suppid);
                            if(s == null) {
                                System.out.println("No such supplier");
                                break;
                            }
                            System.out.println("ID: " + s.getID());
                            System.out.println("Name: " + s.getName());
                            System.out.println("Company ID: " + s.getCompanyID());
                            System.out.println("Pay condition: " + s.getPayCond());
                            System.out.println("Phone number: " + s.getPhoneNum());
                            System.out.println("Bank account: " + s.getBankAccNum());
                            if(!s.getContacts().isEmpty()) {
                                System.out.print("Contacts: ");
                                for (Pair<String, String> contact : s.getContacts())
                                    System.out.print("<" + contact.getKey() + ", " + contact.getValue() + "> ");
                                System.out.println();
                            }
                            System.out.println();
                            break;
                        case 2:
                            System.out.println("Enter the supplierID");
                            int sid1 = scanner.nextInt();
                            for(Product p : superMarket.getSupplier(sid1).getProducts())
                                System.out.println(p.toString() + "\n");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Choose 1 to view details about all pending orders of a certain supplier");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int suppid = scanner.nextInt();
                            Supplier supp = superMarket.getSupplier(suppid);
                            if (supp == null) {
                                System.out.println("No such supplier");
                                break;
                            }
                            for (Order o : supp.getOrders())
                                System.out.println(o.toString() + "\n");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Choose 1 to view all arrival reports");
                    System.out.println("Choose 2 to view all cancellation reports");
                    System.out.println("Choose 3 to view a certain report");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    option = scanner.nextInt();
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            for(Report r : superMarket.getReports()){
                                if(r instanceof ArrivalReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported().toString());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 2:
                            for(Report r : superMarket.getReports()){
                                if(r instanceof CancellationReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Enter the reportID");
                            int rid = scanner.nextInt();
                            Report r = superMarket.getReport(rid);
                            if(r == null){
                                System.out.println("No such report");
                                break;
                            }
                            System.out.println("Report ID: "+r.getID());
                            System.out.println("Date reported: "+r.getDateReported());
                            System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Choose 1 to view details about all agreements of a certain supplier");
                    System.out.println("Choose 0 to go back");
                    do {
                        try {
                            option = Integer.parseInt(scanner.nextLine());
                            error = false;
                        }
                        catch (Exception e){
                            error = true;
                            System.out.println("Illegal input try again.");
                        }
                    }while (error);
                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter the supplierID");
                            int sid = scanner.nextInt();
                            Supplier s = superMarket.getSupplier(sid);
                            if (s == null) {
                                System.out.println("No such supplier");
                                break;
                            }
                            for (Agreement a : s.getAgreements())
                                System.out.println(a.toString() + "\n");
                            break;
                    }
                    break;
                default:
                    isTerminated = true;
                    break;
            }
        }
    }

    private boolean checkValidName(String name){
        if(name.isEmpty() || name.matches(".*\\d+.*")){
            System.out.println("Not a valid name");
            return false;
        }
        return true;
    }
}