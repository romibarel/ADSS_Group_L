package StorageAndSupplier.Presentation;

import StorageAndSupplier.API_Buisness;
import StorageAndSupplier.*;
import Suppliers.BusinessLayer.*;
import javafx.util.Pair;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Presentation {
    private API_Buisness buisnessManager;
    private int option;
    private Scanner in;
    String pattern;
    SimpleDateFormat simpleDateFormat;
    Date today;
    Boolean shouldTerminate = false;

    public Presentation() {
        this.buisnessManager = Singltone_Supplier_Storage_Manager.getInstance();
        option = 0;
        in = new Scanner(System.in);
        pattern = "dd/MM/yyyy";
        simpleDateFormat = new SimpleDateFormat(pattern);
        //get today's date
        String date = simpleDateFormat.format(new Date());
        today = null;
        try {
            today = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (Exception e) {}
    }


    /*
     * Storage Section
     * */

    public void startProgramMenu() {
        System.out.println("\nWelcome, please choose an action:\n");
        List<String> initiateOptions = Arrays.asList("Inventory section", "Location section",
                "Transaction section", "Defect section", "Report section", "exit");
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
                buisnessManager.exit();
        }
        startProgramMenu();
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
                System.out.print("  Type expiration date of the " + i + " item (dd/MM/yyyy format): ");
                Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);

                System.out.print("  Type amount of the " + i + " item: ");
                int amount = Integer.parseInt(in.nextLine());

                boolean alert = buisnessManager.sellProduct(today, barcode, amount, expirationDate);
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

        /*int barCode, String productName, String supplier, int price,
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
        System.out.print("  Type manufactor name: ");
        String manufactor = in.nextLine();
        for (int i = 1; i <= numberOfItems; i++) {
            try {
                System.out.print("  Type barcode number of the " + i + " item: ");
                int barcode = Integer.parseInt(in.nextLine());
                System.out.print("  Type product name of the " + i + " item: ");
                String productName = in.nextLine();
                System.out.print("  Type price of the " + i + " item: ");
                double price = Double.parseDouble(in.nextLine());
                System.out.print("  Type discount of the " + i + " item (if doesn't exists type 0): ");
                double discount = Double.parseDouble(in.nextLine());
                System.out.print("  Type expiration date of the " + i + " item (dd/MM/yyyy format): ");
                Date expirationDate = null;

                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);

                System.out.print("  Type amount of the " + i + " item: ");
                int amount = Integer.parseInt(in.nextLine());
                System.out.print("  Type the number of location to allocate the " + i + " item: ");
                int location = Integer.parseInt(in.nextLine());
                buisnessManager.buyProduct(barcode, productName, manufactor,
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
                buisnessManager.setSaleInfoOfNewProduct(barcode, productName, price, discount);
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
                    buisnessManager.setPriceOfExistingProduct(barcode, newPrice);
                } else {
                    System.out.print("  Type the new discount of the " + i + " item: ");
                    double newDiscount = Double.parseDouble(in.nextLine());
                    buisnessManager.setDiscountOfExistingProduct(barcode, newDiscount);
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
                buisnessManager.setMinimumAmount(barcode, minimumAmount);
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
                System.out.print("  Type new manufactor's name of the " + i + " item: ");
                String newName = in.nextLine();
                buisnessManager.setManufactorforProduct(barcode, newName);
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
                nextSupply = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);

                buisnessManager.setNextSupply(barcode, nextSupply);
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
                    "   Product name :" + buisnessManager.getProducteName(barcode)+ "\n" +
                    "   Product manufactor :" + buisnessManager.getProducteManufactor(barcode)+ "\n"+
                    "   Product amount :" + buisnessManager.getProducteAmount(barcode)+ "\n"+
                    "   Product minimum amount :" + buisnessManager.getProducteMinAmount(barcode)+ "\n"+
                    "   Product next supply date :" + buisnessManager.getProducteDate(barcode) +
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
                 "  Product name: " + buisnessManager.getDataSaleName(barcode)+ "\n" +
                 "  Product price: " + buisnessManager.getDataSalePrice(barcode)+ "\n"+
                 "  Product discount: " + buisnessManager.getDataSaleDiscount(barcode) + "\n\n");
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
            List<Date> dateList = buisnessManager.getBarcodDates(barcode);
            if(dateList!=null) {
                System.out.print("  Barode: " + barcode + "\n");


                for (Date d : dateList) {
                    System.out.print("  \tDate: " + d.toString() + "\n");
                    List<Integer> locationList = buisnessManager.getLocationsByDate(barcode, d);

                    for (Integer i : locationList) {
                        Integer j = buisnessManager.getAmountByLocation(barcode, d, i);
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
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);

                System.out.print("\n");
                buisnessManager.addDefect(today, barcode, amount, reason, creator, location, expirationDate);
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
        System.out.print("  Type date to from which you wish to find defects (dd/MM/yyyy format): ");
        Date fromDate = null;
        try {
            String fromDateS = in.nextLine();
            fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDateS);
            List<Pdefect> defectsToShow = buisnessManager.creatDefectReport(today, fromDate); //THE END ISN'T RELEVANT
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
                buisnessManager.setMainCategory(categoryName);
            } else if (chosenOption == 2) {
                System.out.print("  " + i + ". Main category name (the category above this category): ");
                String mainCategoryName = in.nextLine();
                buisnessManager.setNewSubCategory(categoryName, mainCategoryName);
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
                buisnessManager.setCategoryName(categoryName, newName);
            } else if (chosenOption == 2) {
                buisnessManager.deleteCategory(categoryName);
            }
            System.out.print("\n");
        }
        System.out.print("\nEdit categories complete successfully.\n");
    }

    private void printAllExistingCategories() {
        {
            System.out.print("\nListing all categories in store:\n");
            List<String> names = buisnessManager.getListOfCategoriesNames();
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
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);

            System.out.print("  " + i + ". amount of items to move: ");
            int amount = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". from location (number): ");
            int fromLocation = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". To location (number): ");
            int toLocation = Integer.parseInt(in.nextLine());
            System.out.print("\n");
            buisnessManager.moveProduct(barcode, expirationDate, amount, fromLocation, toLocation);
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
                buisnessManager.connectProductToCategory(categoryName, barcode);
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
        List<String> filterMainCategories = buisnessManager.getListOfCategoriesNames();
        for (List<String> listOfCategories : buisnessManager.subcat(date)) {
            for (String name : listOfCategories) {
                if (filterMainCategories.contains(name)) {
                    filterMainCategories.remove(name);      //remove from list all sub categories
                }
            }
        }
        return filterMainCategories;
    }


    public void showInventoryReport(){
        buisnessManager.creatInventoryReport(today);
        System.out.println("Updated inventory report: \n");
        System.out.println("report for date: " + today.toString() + "\n\n");
        List<String> mainCategories = getMainCategoriesByDate(today);
        for(String category : mainCategories){
            showRecursiveFromMainCategoryDowns(category , "");
        }

    }

    public void showInventoryReportByCategories(){
        buisnessManager.creatInventoryReport(today);
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
        List<PdataInventoryReport> myCategoryProducts = buisnessManager.RepProdofInventoryReport(today , FromHereAndDown);
        for (PdataInventoryReport productRepData : myCategoryProducts) {
            System.out.print(offset + "- Barcode: " + productRepData.getBarcode() + ", Product name: " + productRepData.getProductName() + ", Product amount: " + productRepData.getAmount() + "\n");
        }
        for (String subCategories : buisnessManager.CategoriesOfInventoryReport(today ,FromHereAndDown)) {
            showRecursiveFromMainCategoryDowns(subCategories,offset + "        ");
        }
    }

    /*
    * Suppliers Section
    * */

    public void run(){
        while(!shouldTerminate){
            System.out.println("Choose 1 for suppliers");
            System.out.println("Choose 2 for orders");
            System.out.println("Choose 3 for reports");
            System.out.println("Choose 4 for agreements");
            System.out.println("Choose anything else to exit");
            int option = in.nextInt();
            switch (option){
                case 1:
                    System.out.println("Choose 1 to view all suppliers and their details");
                    System.out.println("Choose 2 to add a new supplier to the system");
                    System.out.println("Choose 3 to edit a supplier detail");
                    System.out.println("Choose 4 to delete a supplier from the system");
                    System.out.println("Choose anything else to go back");
                    option = in.nextInt();
                    switch (option){
                        case 1:
                            for(Supplier s : buisnessManager.getSuppliers()){
                                System.out.println("ID: "+s.getID());
                                System.out.println("Company ID: "+s.getCompanyID());
                                System.out.println("Pay condition: "+s.getPayCond());
                                System.out.println("Phone number: "+s.getPhoneNum());
                                System.out.println("Bank account: "+s.getBankAccNum()+"\n");
                            }
                            break;
                        case 2:
                            System.out.println("Please type the company ID:");
                            int cid = in.nextInt();
                            System.out.println("Please type the phone number:");
                            String pn = in.next();
                            System.out.println("Please type the bank account:");
                            String ba = in.next();
                            System.out.println("Please type the pay condition:");
                            String pc = in.next();
                            System.out.println("Please type the contact names, and 'done' when you're done");
                            String n = in.next();
                            LinkedList<String> names = new LinkedList<>();
                            while(!n.equals("done")) {
                                names.add(n);
                                n = in.next();
                            }
                            System.out.println("Press 1 to create fixed days supplier");
                            System.out.println("Press 2 to create invite only supplier");
                            System.out.println("Press 3 to create self pickup supplier");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    buisnessManager.addSupplier(new FixedDaysSupplier(cid,ba,pc,names,pn));
                                    System.out.println("Success!\n");
                                    break;
                                case 2:
                                    buisnessManager.addSupplier(new InviteOnlySupplier(cid,ba,pc,names,pn));
                                    System.out.println("Success!\n");
                                    break;
                                case 3:
                                    System.out.println("Please type the pickup location:");
                                    String location = in.next();
                                    buisnessManager.addSupplier(new SelfPickupSupplier(cid,ba,pc,names,pn,location));
                                    System.out.println("Success!\n");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to edit Company ID");
                            System.out.println("Press 2 to edit bank account");
                            System.out.println("Press 3 to edit pay condition");
                            System.out.println("Press 4 to edit phone number");
                            System.out.println("Press 5 to edit contact names");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            int id;
                            switch (option){
                                case 1:
                                    System.out.println("Please enter the supplier ID");
                                    id = in.nextInt();
                                    System.out.println("Please enter new Company ID");
                                    int cid1 = in.nextInt();
                                    if(buisnessManager.setSupplierCompanyID(id,cid1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 2:
                                    System.out.println("Please enter the supplier ID");
                                    id = in.nextInt();
                                    System.out.println("Please enter new bank account");
                                    String ba1 = in.next();
                                    if(buisnessManager.setSupplierBankAccNum(id,ba1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 3:
                                    System.out.println("Please enter the supplier ID");
                                    id = in.nextInt();
                                    System.out.println("Please enter new pay condition");
                                    String pc1 = in.next();
                                    if(buisnessManager.setSupplierPayCond(id,pc1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 4:
                                    System.out.println("Please enter the supplier ID");
                                    id = in.nextInt();
                                    System.out.println("Please enter new phone number");
                                    String pn1 = in.next();
                                    if(buisnessManager.setSupplierPhoneNum(id,pn1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 5:
                                    System.out.println("Please enter the supplier ID");
                                    id = in.nextInt();
                                    System.out.println("Please enter the new contact names, and 'done' when you're done");
                                    String n1 = in.next();
                                    LinkedList<String> names1= new LinkedList<>();
                                    while(!n1.equals("done")){
                                        names1.add(n1);
                                        n1 = in.next();
                                    }
                                    if(buisnessManager.setSupplierContactNames(id,names1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Please enter the supplier ID");
                            buisnessManager.removeSupplier(in.nextInt());
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Press 1 to watch all pending orders and their details");
                    System.out.println("Press 2 to add an order");
                    System.out.println("Press 3 to edit an order");
                    System.out.println("Press 4 to cancel an order");
                    System.out.println("Press 5 to announce the arrival of an order");
                    System.out.println("Press anything else to go back");
                    option = in.nextInt();
                    switch (option){
                        case 1:
                            for(Order o : buisnessManager.getOrders()){
                                /*System.out.println("Order ID: "+ o.getID());
                                System.out.println("Supplier ID: "+ o.getSupplierID());
                                System.out.println("ETA: "+ o.getETA().toString());
                                System.out.println("Date issued: "+ o.getDateIssued());
                                System.out.println("Total: "+ o.getTotal());*/
                                System.out.println(o.toString());
                            }
                            break;
                        case 2:
                            System.out.println("Press 1 to add a constant order(fixed days supplier only)");
                            System.out.println("Press 2 to add a regular order");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Please enter the supplier ID");
                                    int s = in.nextInt();
                                    Supplier supp = buisnessManager.getSupplierByID(s);
                                    if(!(supp instanceof FixedDaysSupplier)){
                                        System.out.println("Error! not a fixed days supplier");
                                    }
                                    System.out.println("Please enter the year, month and day of the ETA");
                                    LocalDate l = LocalDate.of(in.nextInt(),in.nextInt(),in.nextInt());
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na = in.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm = new HashMap<>();
                                    while(!na.equals("done")){
                                        int catalogID = in.nextInt();
                                        String manu = in.next();
                                        double pri = in.nextDouble();
                                        int amo = in.nextInt();
                                        Product p = new Product(catalogID, pri,na,manu);
                                        Pair<Integer, Integer> pair = new Pair<>(amo,0);
                                        hm.put(p,pair);
                                        na = in.next();
                                    }
                                    Order o = new Order(s,l,LocalDate.now(),hm);
                                    buisnessManager.addOrder(o);
                                    supp.addOrder(o);
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Please enter the supplier ID");
                                    int s1 = in.nextInt();
                                    Supplier supp1 = buisnessManager.getSupplierByID(s1);
                                    if(supp1 instanceof FixedDaysSupplier){
                                        System.out.println("Error! a fixed days only supplier");
                                    }
                                    System.out.println("Please enter the year, month and day of the ETA");
                                    LocalDate l1 = LocalDate.of(in.nextInt(),in.nextInt(),in.nextInt());
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na1 = in.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm1 = new HashMap<>();
                                    while(!na1.equals("done")){
                                        int catalogID = in.nextInt();
                                        String manu1 = in.next();
                                        double pri1 = in.nextDouble();
                                        int amo1 = in.nextInt();
                                        Product p1 = new Product(catalogID, pri1,na1,manu1);
                                        Pair<Integer, Integer> pair1 = new Pair<>(amo1,0);
                                        hm1.put(p1,pair1);
                                        na1 = in.next();
                                    }
                                    Order o1 = new Order(s1,l1,LocalDate.now(),hm1);
                                    buisnessManager.addOrder(o1);
                                    supp1.addOrder(o1);
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to edit ETA");
                            System.out.println("Press 2 to edit products");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the new year, month and day");
                                    LocalDate ld = LocalDate.of(in.nextInt(),in.nextInt(),in.nextInt());
                                    System.out.println("Enter the ID of the order");
                                    int ordid = in.nextInt();
                                    if((buisnessManager.getOrderByID(ordid) != null) && (buisnessManager.getOrderByID(ordid).setETA(ld)))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error!");
                                    break;
                                case 2:
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na2 = in.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm2 = new HashMap<>();
                                    while(!na2.equals("done")){
                                        int catalogID = in.nextInt();
                                        String manu2 = in.next();
                                        double pri2 = in.nextDouble();
                                        int amo2 = in.nextInt();
                                        Product p2 = new Product(catalogID, pri2,na2,manu2);
                                        Pair<Integer, Integer> pair2 = new Pair<>(amo2,0);
                                        hm2.put(p2,pair2);
                                        na2 = in.next();
                                    }
                                    System.out.println("Enter the ID of the order");
                                    int d = in.nextInt();
                                    if((buisnessManager.getOrderByID(d) != null)&&(buisnessManager.getOrderByID(d).setProducts(hm2)))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the ID of the order");
                            int i = in.nextInt();
                            Order ooo = buisnessManager.getOrderByID(i);
                            if(ooo == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            buisnessManager.reportCancellation(ooo);
                            System.out.println("Success!");
                            break;
                        case 5:
                            System.out.println("Enter the ID of the order");
                            int i1 = in.nextInt();
                            Order ooo1 = buisnessManager.getOrderByID(i1);
                            if(ooo1 == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            buisnessManager.reportArrival(ooo1);
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Press 1 to view all arrival reports");
                    System.out.println("Press 2 to view all cancellation reports");
                    System.out.println("press anything else to go back");
                    option = in.nextInt();
                    switch (option){
                        case 1:
                            for(Report r : buisnessManager.getReports()){
                                if(r instanceof ArrivalReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported().toString());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 2:
                            for(Report r : buisnessManager.getReports()){
                                if(r instanceof CancellationReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Press 1 to view agreement details");
                    System.out.println("Press 2 to add a new agreement");
                    System.out.println("Press 3 to set an agreement");
                    System.out.println("Press 4 to remove agreement");
                    System.out.println("Press anything else to go back");
                    option = in.nextInt();
                    switch (option){
                        case 1:
                            System.out.println("Press 1 to view all sale agreements");
                            System.out.println("Press 2 to view all gift agreements");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    for(Supplier s : buisnessManager.getSuppliers()){
                                        for(Agreement a : s.getAgreements()){
                                            if(a instanceof SaleAgreement)
                                                System.out.println(a.getDescription() + "Associated with supplier ID " + s.getID());
                                        }
                                    }
                                    break;
                                case 2:
                                    for(Supplier s : buisnessManager.getSuppliers()){
                                        for(Agreement a : s.getAgreements()){
                                            if(a instanceof GiftAgreement)
                                                System.out.println(a.getDescription() + " Associated with supplier ID " + s.getID());
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Press 1 to add a new sale agreement");
                            System.out.println("Press 2 to add a new gift agreement");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the supplier ID");
                                    int su = in.nextInt();
                                    Supplier to = buisnessManager.getSupplierByID(su);
                                    if(to == null){
                                        System.out.println("Error! no such a supplier");
                                        break;
                                    }
                                    System.out.println("Enter the catalogID, name, price and manufacture of the product");
                                    int catalogID1 = in.nextInt();
                                    String nn = in.next();
                                    double pp = in.nextDouble();
                                    String mm = in.next();
                                    System.out.println("Enter two numbers: how much of the product you need to order, and how much percent off of it");
                                    int aa = in.nextInt();
                                    int hh = in.nextInt();
                                    to.addAgreement(new SaleAgreement(new Pair<>(new Product(catalogID1, pp,nn,mm),new Pair<>(aa,hh))));
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Enter the supplier ID");
                                    int su1 = in.nextInt();
                                    Supplier to1 = buisnessManager.getSupplierByID(su1);
                                    if(to1 == null){
                                        System.out.println("Error! no such a supplier");
                                        break;
                                    }
                                    System.out.println("Enter the catalogID, name, price and manufacture of the product");
                                    int catalogID2 = in.nextInt();
                                    String nn1 = in.next();
                                    double pp1 = in.nextDouble();
                                    String mm1 = in.next();
                                    System.out.println("Enter two numbers: how much of the product you need to order, and how many of these products you get for free");
                                    int aa1 = in.nextInt();
                                    int hh1 = in.nextInt();
                                    to1.addAgreement(new GiftAgreement(new Pair<>(new Product(catalogID2, pp1,nn1,mm1),new Pair<>(aa1,hh1))));
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to set the product amount");
                            System.out.println("Press 2 to set the product condition");
                            System.out.println("Press anything else to go back");
                            option = in.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the supplier ID");
                                    int r = in.nextInt();
                                    Supplier j = buisnessManager.getSupplierByID(r);
                                    if(j == null){
                                        System.out.println("Error! no such supplier");
                                        break;
                                    }
                                    System.out.println("Enter the agreement ID");
                                    int u = in.nextInt();
                                    Agreement v = j.getAgreementByID(u);
                                    if(v == null){
                                        System.out.println("Error! no such agreement");
                                        break;
                                    }
                                    System.out.println("Enter new amount");
                                    int y = in.nextInt();
                                    v.setProdAmount(y);
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Enter the supplier ID");
                                    int r1 = in.nextInt();
                                    Supplier j1 = buisnessManager.getSupplierByID(r1);
                                    if(j1 == null){
                                        System.out.println("Error! no such supplier");
                                        break;
                                    }
                                    System.out.println("Enter the agreement ID");
                                    int u1 = in.nextInt();
                                    Agreement v1 = j1.getAgreementByID(u1);
                                    if(v1 == null){
                                        System.out.println("Error! no such agreement");
                                        break;
                                    }
                                    System.out.println("Enter new condition");
                                    int y1 = in.nextInt();
                                    v1.setProdCond(y1);
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the supplier ID");
                            int ii = in.nextInt();
                            Supplier su = buisnessManager.getSupplierByID(ii);
                            if(su == null){
                                System.out.println("Error! no such supplier");
                                break;
                            }
                            System.out.println("Enter agreement ID");
                            int jj = in.nextInt();
                            Agreement mn = su.getAgreementByID(jj);
                            if(mn == null){
                                System.out.println("Error! no such agreement");
                                break;
                            }
                            su.removeAgreementByID(jj);
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    shouldTerminate = true;
                    break;
            }
        }
        System.out.println("\nThanks for using our system, looking forward to seeing you again!");
    }
}