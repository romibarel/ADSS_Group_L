import java.util.Date;
import java.util.List;

class Singletone_Storage_Management implements API_Buisness{

    private static Singletone_Storage_Management instance = null;
    private ReportController report;
    private DefectController defects;
    private TransactionController transactions;
    private LocationController locations;
    private ProductController inventory;

    private Singletone_Storage_Management() {
        this.report = new ReportController();
        this.defects = new DefectController();
        this.transactions = new TransactionController();
        this.locations = new LocationController();
        this.inventory = new ProductController();
    }

    public static Singletone_Storage_Management getInstance(){

        if (instance == null)
            instance = new Singletone_Storage_Management();

        return instance;
    }

    public ReportController getReport(){
        return this.report;
    }

    public DefectController getDefects(){
        return this.defects;
    }

    public TransactionController getTransactions(){
        return transactions;
    }

    public LocationController getLocations(){
        return locations;
    }

    public ProductController getInventory(){
        return inventory;
    }

    @Override
    public void buyProduct(int barCode, String productName, String supplier, double price,
                           double discount,Date expirationDate, int amount, Date date,int location) { //barCode, productName, price, amount, date, manufactor, location, expiratiomDate
        this.transactions.purchase(barCode, productName, supplier, price,discount,expirationDate,amount,date,location);
    }

    @Override
    public void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation) {
        this.locations.moveProduct(barCode, expiration, amount, fromLocation, toLocation);
    }

    @Override
    public void setMainCategory(String mainCategoryName) {
        this.inventory.setMainCategory(mainCategoryName);
    }

    @Override
    public void setNewSubCategory(String subcategoryName, String MainCategoryName) {
        this.inventory.setNewSubCategory(subcategoryName, MainCategoryName);
    }

    @Override
    public void appendProductToCategory(int barCode, String categoryName) {
        this.inventory.appendProductToCategory(barCode, categoryName);
    }

    @Override
    public List<String> getListOfCategoriesNames() {
        return this.inventory.getListOfCategoriesNames();
    }

    @Override
    public List<String> getListOfProductsNames() {
        return this.inventory.getListOfProductsNames();
    }

    @Override
    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        this.defects.addDefect(date, barCode, amount, reason, creator, location, expiration);
    }

    @Override
    public boolean sellProduct(Date date, int barCode, int amount, Date expirationDate) {
        return this.transactions.sellProduct(date, barCode, amount, expirationDate);
    }

    @Override
    public void setSaleInfoOfNewProduct(int barcode, String productName, double price, double discount) {
        this.inventory.setSaleInfoOfNewProduct(barcode, productName, price, discount);
    }

    @Override
    public void setPriceOfExistingProduct(int barcode, double newPrice) {
        this.inventory.setPriceOfExistingProduct(barcode, newPrice);
    }

    @Override
    public void setDiscountOfExistingProduct(int barcode, double newDiscount) {
        this.inventory.setDiscountOfExistingProduct(barcode, newDiscount);
    }

    @Override
    public DefectReport getDefectReport(Date today, Date fromDate) {
        return this.report.getDefectReports(today, fromDate);
    }

    @Override
    public ProductReport getTimeReport(Date today) {
        return this.report.getTimeReports(today);
    }

    @Override
    public void connectProductToCategory(String categoryName, int barcode) {
        this.inventory.connectProductToCategory(categoryName, barcode);
    }

    @Override
    public void setMinimumAmount(int barcode, int minimumAmount) {
        this.getInventory().setMinimumAmount(barcode, minimumAmount);
    }

    public void setManufactorforProduct(int barcode , String newName){
        this.getInventory().setManufactorForProduct(barcode, newName);
    }

    @Override
    public void setNextSupply(int barcode , Date nextSupply){
        this.getInventory().setNextSupply(barcode, nextSupply);
    }

    @Override
    public String getProducteName(Integer barcode) {
        return this.getInventory().getProductName(barcode);
    }

    @Override
    public String getProducteManufactor(Integer barcode) {
        return this.getInventory().getProductManufactor(barcode);
    }

    @Override
    public String getProducteAmount(Integer barcode) {
        return this.getInventory().getProductAmount(barcode);
    }

    @Override
    public String getProducteMinAmount(Integer barcode) {
        return this.getInventory().getProductMinAmount(barcode);
    }

    @Override
    public String getProducteDate(Integer barcode) {
        return this.getInventory().getProductNextSupplyTime(barcode);
    }

    @Override
    public String getDataSaleName(Integer barcode) {
        return this.getInventory().getSaleDataName(barcode);
    }

    @Override
    public String getDataSalePrice(Integer barcode) {
        double d = this.getInventory().getSaleDataPrice(barcode);
        return String.valueOf(d);
    }

    @Override
    public String getDataSaleDiscount(Integer barcode) {
        double d = this.getInventory().getSaleDataDiscount(barcode);
        return String.valueOf(d);
    }


}

