import java.util.*;
import java.text.SimpleDateFormat;

public class Presentation {
    private API_Buisness buisnessManager;
    private int option;
    private Scanner in;
    String pattern;
    SimpleDateFormat simpleDateFormat;
    Date today;

    public Presentation() {
        this.buisnessManager = Singletone_Storage_Management.getInstance();
        option = 0;
        in = new Scanner(System.in);
        pattern = "dd/MM/yyyy";
        simpleDateFormat = new SimpleDateFormat(pattern);
        //get today's date
        String date = simpleDateFormat.format(new Date());
        today = null;
        try {
            today = new SimpleDateFormat("dd/MM/yyyy").parse(date);

        } catch (Exception e) {
        }
    }

    public void startProgramMenu() {
        System.out.println("\nWelcome, please choose an action:\n");
        List<String> initiateOptions = Arrays.asList("Inventory section", "Location section",
                "Transaction section", "Defect section", "Report section", "exit");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
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
                System.exit(0);
        }
        startProgramMenu();
    }

    public void InventoryMenu() {
        System.out.println("\nInventory Section:\n");
        List<String> initiateOptions = Arrays.asList("Add new 'sale product'", "Edit existing 'sale product' information",
                "Alter product information", "Add category", "Connect product to category", "Product information",
                "print all categories name in store", "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
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
                connectProductToCategory();
                break;
            case 6:
                ShowProducInfoMenu();
                break;
            case 7:
                printAllExistingCategories();
                break;
            default:
                break;
        }
    }

    public void LocationMenu() {
        System.out.println("\nLocation Section:\n");
        List<String> initiateOptions = Arrays.asList("Move Product", "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
        switch (option) {
            case 1:
                moveProduct();
                break;
            default:
                break;
        }
    }

    public void TransactionMenu() {
        System.out.println("\nTransaction Section:\n");
        List<String> initiateOptions = Arrays.asList("Sell products", "Purchase products", "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
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

    public void DefectMenu() {
        System.out.println("\nDefect Section:\n");
        List<String> initiateOptions = Arrays.asList("Add defects", "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
        switch (option) {
            case 1:
                addDefect();
                break;
            default:
                break;
        }
    }

    public void ReportMenu() {
        System.out.println("\nReports section:\n");
        List<String> initiateOptions = Arrays.asList("Defects report", "Time report", "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
        switch (option) {
            case 1:
                getDefectsReport();
                break;
            case 2:
                getTimeReport();
                break;
            default:
                break;
        }
    }

    public void printMenu(List<String> options) {
        int i = 1;
        for (String option : options) {
            System.out.println(i++ + ". " + option);
        }
    }

    public void AlterProductMenu(){
        List<String> initiateOptions = Arrays.asList(
                "Set Minimum amount of product",
                "Set manufactor of product",
                "Set next supply date of product",
                "Back to main menu");
        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
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

    public void ShowProducInfoMenu(){
        List<String> initiateOptions = Arrays.asList(
                "General information",
                "Sale information",
                "Back to main menu");

        printMenu(initiateOptions);
        option = Integer.parseInt(in.nextLine());
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

    public void sellProducts() {
        boolean error = false;
        System.out.print("  Sell products:\n");
        System.out.print("  Type how many items (different bar codes) in this sale: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  Type expiration date of the " + i + " item (dd/MM/yyyy format): ");
            Date expirationDate = null;
            try {
                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);
            } catch (Exception e) {
                System.out.print("Illegal date input");
                error = true;
                break;
            }
            System.out.print("  Type amount of the " + i + " item: ");
            int amount = Integer.parseInt(in.nextLine());
            boolean alert = buisnessManager.sellProduct(today, barcode, amount, expirationDate);
            if (alert){ //alert to costumer that the product reached it's minimum amount limit
                System.out.print("  Product "+barcode+" has reached under it's minimum amount!!!!!");
            }
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nSale complete successfully.\n");
        }
    }

    public void purchaseProducts() {

        /*int barCode, String productName, String supplier, int price,
        int discount,Date expirationDate, int amount, Date date,int location -> parameters needed for each product*/
        boolean error = false;
        System.out.print("  Purchase products:\n");
        System.out.print("  Type how many items (different bar codes) in this purchase: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        System.out.print("  Type manufactor name: ");
        String manufactor = in.nextLine();
        for (int i = 1; i <= numberOfItems; i++) {
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
            try {
                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);
            } catch (Exception e) {
                System.out.print("Illegal date input");
                error = true;
                break;
            }
            System.out.print("  Type amount of the " + i + " item: ");
            int amount = Integer.parseInt(in.nextLine());
            System.out.print("  Type the number of location to allocate the " + i + " item: ");
            int location = Integer.parseInt(in.nextLine());
            buisnessManager.buyProduct(barcode, productName, manufactor,
                    price, discount, expirationDate,
                    amount, today, location);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nPurchase complete successfully.\n");
        }
    }

    public void addNewProductSale() {
        /*int barcode, String productName, double price, double discount -> the parameters should give*/
        boolean error = false;
        System.out.print("  Add products sale information:\n");
        System.out.print("  Type how many items (different bar codes) in this sale: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
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
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nAdd new product sale complete successfully.\n");
        }
    }

    public void editExistingSaleProductInformation() {
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit existing products sale information:\n");
        printMenu(Arrays.asList("Edit price", "Edit discount", "back to menu"));
        int chosenOption = Integer.parseInt(in.nextLine());
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        System.out.print("  Type how many items (different bar codes) you want to edit: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
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
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nEdit sale details complete successfully.\n");
        }
    }

    public void editMinumumAmountOfProduct(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit minimum amount of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  Type the minimum amount of the " + i + " item: ");
            int minimumAmount = Integer.parseInt(in.nextLine());
            buisnessManager.setMinimumAmount(barcode, minimumAmount);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nEdit minimum amount of product complete successfully.\n");
        }
    }

    public void editManufactorOfProduct(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit manufactor of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  Type new manufactor's name of the " + i + " item: ");
            String newName = in.nextLine();
            buisnessManager.setManufactorforProduct(barcode, newName);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nEdit Manufactor of product complete successfully.\n");
        }
    }

    public void editNextSupply(){
        // int barcode, double newPrice/double newDiscount int chosenOption
        boolean error = false;
        System.out.print("  Edit next Supply of product:\n");
        System.out.print("  Type how many items (different bar codes) you want to edit their minimum amount: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  Type next supply date of the " + i + " item: ");
            Date nextSupply = null;
            try {
                String expiDate = in.nextLine();
                nextSupply = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);
            } catch (Exception e) {
                System.out.print("Illegal date input");
                error = true;
                break;
            }
            buisnessManager.setNextSupply(barcode, nextSupply);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nEdit Manufactor of product complete successfully.\n");
        }
    }

    public void ShowGeneralInfo() {
        System.out.print("  Type how many items (different bar codes) you want to watch general information: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        List<Integer> productsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            productsToShow.add(new Integer(barcode));
        }
        for(Integer barcode : productsToShow){
            System.out.print(
                    "Barode: " + barcode + "\n" +
                    "Product name :" + buisnessManager.getProducteName(barcode)+ "\n" +
                    "Product manufactor :" + buisnessManager.getProducteManufactor(barcode)+ "\n"+
                    "Product amount :" + buisnessManager.getProducteAmount(barcode)+ "\n"+
                    "Product minimum amount :" + buisnessManager.getProducteMinAmount(barcode)+ "\n"+
                    "Product next supply date :" + buisnessManager.getProducteDate(barcode) +
                     "\n\n");
        }
    }
//
    public void ShowSaleInfo() {
        System.out.print("  Type how many items (different bar codes) you want to watch sale information: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        List<Integer> productsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            productsToShow.add(new Integer(barcode));
        }
        for(Integer barcode : productsToShow){
         System.out.print(
                 "Barode: " + barcode + "\n" +
                 "Product name: " + buisnessManager.getDataSaleName(barcode)+ "\n" +
                 "Product price: " + buisnessManager.getDataSalePrice(barcode)+ "\n"+
                 "Product discount: " + buisnessManager.getDataSaleDiscount(barcode) + "\n\n");
        }
    }

    public void ShowProductLocation(){
        System.out.print("  Type how many items (different bar codes) you want to watch location information: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        List<Integer> locationsToShow = new LinkedList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  Type barcode number of the " + i + " item: ");
            int barcode = Integer.parseInt(in.nextLine());
            locationsToShow.add(new Integer(barcode));
        }

        for(Integer barcode : locationsToShow){
            System.out.print("Barode: " + barcode + "\n");
            //List<String> dateList =
        }

    }

    public void addDefect() {
        //Date date, int barCode, int amount, String reason, String creator, int location, Date expiration -> the parameters
        boolean error = false;
        System.out.print("  Add defects:\n");
        System.out.print("  Type how many items (different bar codes) you want to declare as defects: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
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
            try {
                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);
            } catch (Exception e) {
                System.out.print("Illegal date input");
                error = true;
                break;
            }
            System.out.print("\n");
            buisnessManager.addDefect(today, barcode, amount, reason, creator, location, expirationDate);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nSet defects complete successfully.\n");
        }
    }

    public void getDefectsReport() {
        boolean error = false;
        System.out.print("  Defect report:\n");
        System.out.print("  Type date to from which you wish to find defects (dd/MM/yyyy format): ");
        Date fromDate = null;
        try {
            String fromDateS = in.nextLine();
            fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDateS);
        } catch (Exception e) {
            System.out.print("Illegal date input");
            error = true;
        }
        DefectReport defectReport = buisnessManager.getDefectReport(today, fromDate); //THE END ISN'T RELEVANT
        showDefects(defectReport);
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nDefect report complete successfully.\n");
        }
    }

    private void showDefects(DefectReport defectReport) {
        System.out.print("\nThe defect report from date " + defectReport.getDateStart().toString() + " is:\n");
        System.out.print("sorted by dates:\n\n");
        List<Defect> defects = defectReport.getDefects();
        defects.sort(Comparator.comparing(Defect::getDate));
        if (defects.size() == 0) {
            System.out.print("No defects to show.\n");
            return;
        }
        for (Defect defect : defects) {
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

    public void addCatagoryToInventoryMenu() {
        /*
        *   void setMainCategory(String mainCategoryName);
            void setNewSubCategory(String subcategoryName, String MainCategoryName);
        * */
        boolean error = false;
        System.out.print("  Add categories:\n");
        printMenu(Arrays.asList("add main category", "Add sub category", "back to main menu"));
        int chosenOption = Integer.parseInt(in.nextLine());
        if (chosenOption != 1 && chosenOption != 2) {
            return;
        }
        System.out.print("  Type how many categories you want to add: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
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
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nAdd categories complete successfully.\n");
        }
    }

    public void printAllExistingProducts() {
        System.out.print("\nListing all products in store:\n");
        List<String> names = buisnessManager.getListOfProductsNames();
        if (names != null) {
            printMenu(names);
        } else {
            System.out.print("no products to show.\n");
        }
        System.out.print("\nprinting all product's names complete successfully.\n");
    }

    public void printAllExistingCategories() {
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

    public void moveProduct() {
        //int barCode, Date expiration, int amount, int fromLocation, int toLocation -> parameters
        boolean error = false;
        System.out.print("  Move products:\n");
        System.out.print("  Type how many items (different bar codes) you want to move place: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  " + i + ". barcode of item: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". expiration date of item: ");
            Date expirationDate = null;
            try {
                String expiDate = in.nextLine();
                expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiDate);
            } catch (Exception e) {
                System.out.print("Illegal date input");
                error = true;
                break;
            }
            System.out.print("  " + i + ". amount of items to move: ");
            int amount = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". from location (number): ");
            int fromLocation = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". To location (number): ");
            int toLocation = Integer.parseInt(in.nextLine());
            System.out.print("\n");
            buisnessManager.moveProduct(barcode, expirationDate, amount, fromLocation, toLocation);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nMove products complete successfully.\n");
        }

    }

    public void getTimeReport() {
        boolean error = false;
        System.out.print("  Time report:\n\n");
        ProductReport timeReport = buisnessManager.getTimeReport(today);
        showTimeReport(timeReport);
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nTime report complete successfully.\n");
        }
    }

    public void connectProductToCategory() {
        boolean error = false;
        System.out.print("  Connect product to category:\n");
        System.out.print("  Type how many items (different bar codes) you want to connect to categories: ");
        int numberOfItems = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= numberOfItems; i++) {
            System.out.print("  " + i + ". barcode of product: ");
            int barcode = Integer.parseInt(in.nextLine());
            System.out.print("  " + i + ". category name to connect product:");
            String categoryName = in.nextLine();
            System.out.print("\n");
            buisnessManager.connectProductToCategory(categoryName, barcode);
        }
        if (error) {
            //TODO: if need to do something if date is not in the format
        } else {
            System.out.print("\nConnect product to category successfully.\n");
        }
    }

    //Time report functions -> recursively to show the hierarchy
    private void showTimeReport(ProductReport timeReport) {
        List<String> mainCategories = getMainCategories(timeReport);
        for (String categoryName : mainCategories) {
            showRecursiveFromMainCategoryDown(categoryName, timeReport, "");
            System.out.println();
        }
    }

    private List<String> getMainCategories(ProductReport timeReport) {
        List<String> filterMainCategories = buisnessManager.getListOfCategoriesNames();
        for (List<String> listOfCategories : timeReport.getHierarchy().values()) {
            for (String name : listOfCategories) {
                if (filterMainCategories.contains(name)) {
                    filterMainCategories.remove(name);      //remove from list all sub categories
                }
            }
        }
        return filterMainCategories;
    }

    private void showRecursiveFromMainCategoryDown(String FromHereAndDown, ProductReport productReport, String offset) {
        System.out.print(offset + "- Products under category " + FromHereAndDown + ":\n");
        List<ProductRepData> myCategoryProducts = productReport.getReportData().get(FromHereAndDown);
        for (ProductRepData productRepData : myCategoryProducts) {
            System.out.print(offset + "- barcode: " + productRepData.getBarCode() + ", Product name: " + productRepData.getProductName() + ", Product amount: " + productRepData.getAmount() + "\n");
        }
        for (String subCategories : productReport.getHierarchy().get(FromHereAndDown)) {
            showRecursiveFromMainCategoryDown(subCategories, productReport, offset + "        ");
        }
    }
}