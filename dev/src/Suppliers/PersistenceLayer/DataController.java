package Suppliers.PersistenceLayer;

import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataController {
    private static DataController instance = null;
    private LinkedList<LoanSupplier> loanSuppliers;
    private LinkedList<LoanOrder> loanOrders;
    private LinkedList<LoanAgreement> loanAgreements;
    private LinkedList<LoanProduct> loanProducts;
    private LinkedList<LoanReport> loanReports;
    private Connection con;
    private PreparedStatement p;

    public static DataController getInstance(){
        if(instance == null)
            return new DataController();
        return instance;
    }

    private DataController(){

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\dev\\storage.db";
            con = DriverManager.getConnection(url);
        } catch ( Exception e) {
            e.printStackTrace();
        }

        loanSuppliers = new LinkedList<>();
        loanOrders = new LinkedList<>();
        loanAgreements = new LinkedList<>();
        loanProducts = new LinkedList<>();
        loanReports = new LinkedList<>();

//        LoanProduct oliveOil = new LoanProduct(1, 20, "Olive Oil", "Zeita", LocalDateTime.now().plusDays(7));
//        LoanProduct milk = new LoanProduct(2, 5, "Milk", "Tara", LocalDateTime.now().plusDays(7));
//        LoanProduct bread = new LoanProduct(3, 10, "Bread", "Berman", LocalDateTime.now().plusDays(7));
//        LoanProduct butter = new LoanProduct(4, 3, "Butter", "Tnuva", LocalDateTime.now().plusDays(7));
//        LoanProduct cheese = new LoanProduct(5, 25, "Cheese", "Emek", LocalDateTime.now().plusDays(7));
//        loanProducts = new LinkedList<>();
//        loanProducts.add(cheese);
//        loanProducts.add(butter);
//        loanProducts.add(milk);
//        loanProducts.add(bread);
//        loanProducts.add(oliveOil);
//
//        loanSuppliers = new LinkedList<>();
//        LoanSupplier s1 = new LoanSupplier("FixedDays","Rom", 1, "1-2-3", "Cash", "010");
//        LoanSupplier s2 = new LoanSupplier("InviteOnly","Adir", 2, "2-3-1", "Credit", "020");
//        LoanSupplier s3 = new LoanSupplier("SelfPickup", "Din", 3, "3-2-1", "Credit",  "030");
//        loanSuppliers.add(s1);
//        loanSuppliers.add(s2);
//        loanSuppliers.add(s3);
//
//        HashMap<LoanProduct, Pair<Integer, Integer>> o1Products = new HashMap<>();
//        HashMap<LoanProduct, Pair<Integer, Integer>> o2Products = new HashMap<>();
//        HashMap<LoanProduct, Pair<Integer, Integer>> o3Products = new HashMap<>();
//        o1Products.put(cheese, new Pair<>(20, 0));
//        o1Products.put(butter, new Pair<>(5, 0));
//        o2Products.put(milk, new Pair<>(20, 0));
//        o2Products.put(bread, new Pair<>(30, 0));
//        o3Products.put(oliveOil, new Pair<>(50, 0));
//        loanOrders = new LinkedList<>();
//        LoanOrder o1 = new LoanOrder(s1.getID(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), o1Products);
//        LoanOrder o2 = new LoanOrder(s2.getID(), LocalDateTime.now().plusDays(2), LocalDateTime.now(), o2Products);
//        LoanOrder o3 = new LoanOrder(s3.getID(), LocalDateTime.now().plusDays(3), LocalDateTime.now(), o3Products);
//        loanOrders.add(o1);
//        loanOrders.add(o2);
//        loanOrders.add(o3);
//
//        loanAgreements = new LinkedList<>();
//        LoanAgreement a1 = new LoanAgreement(new Pair<>(cheese, new Pair<>(20, 5)));
//        LoanAgreement a2 = new LoanAgreement(new Pair<>(milk, new Pair<>(10, 3)));
//        LoanAgreement a3 = new LoanAgreement(new Pair<>(oliveOil, new Pair<>(40, 8)));
//        loanAgreements.add(a1);
//        loanAgreements.add(a2);
//        loanAgreements.add(a3);
    }

    public void loadSystem(){
        LoanProduct oliveOil = new LoanProduct(3,1, 20, "Olive Oil", "Zeita", LocalDateTime.now().plusDays(7));
        LoanProduct milk1 = new LoanProduct(1,2, 5, "Milk", "Tara", LocalDateTime.now().plusDays(7));
        LoanProduct milk2 = new LoanProduct(2,2, 5, "Milk", "Tnuva", LocalDateTime.now().plusDays(7));
        LoanProduct bread = new LoanProduct(2,3, 10, "Bread", "Berman", LocalDateTime.now().plusDays(7));
        LoanProduct butter = new LoanProduct(2,4, 3, "Butter", "Tnuva", LocalDateTime.now().plusDays(7));
        LoanProduct cheese = new LoanProduct(1,5, 25, "Cheese", "Emek", LocalDateTime.now().plusDays(7));
        LinkedList<LoanProduct> loanProducts1 = new LinkedList<>();
        LinkedList<LoanProduct> loanProducts2 = new LinkedList<>();
        LinkedList<LoanProduct> loanProducts3 = new LinkedList<>();
        loanProducts1.add(cheese);
        loanProducts1.add(butter);
        loanProducts1.add(milk1);
        loanProducts2.add(milk2);
        loanProducts2.add(bread);
        loanProducts3.add(oliveOil);
        addSupplierProduct(oliveOil, 3);
        addSupplierProduct(milk1, 1);
        addSupplierProduct(cheese, 1);
        addSupplierProduct(butter, 1);
        addSupplierProduct(milk2, 2);
        addSupplierProduct(bread, 2);

        HashMap<LoanProduct, Pair<Integer, Integer>> o1Products = new HashMap<>();
        HashMap<LoanProduct, Pair<Integer, Integer>> o2Products = new HashMap<>();
        HashMap<LoanProduct, Pair<Integer, Integer>> o3Products = new HashMap<>();
        o1Products.put(cheese, new Pair<>(20, 5));
        o1Products.put(milk1, new Pair<>(20, 8));
        o1Products.put(butter, new Pair<>(5, 0));
        o2Products.put(milk2, new Pair<>(15, 3));
        o2Products.put(bread, new Pair<>(30, 0));
        o3Products.put(oliveOil, new Pair<>(50, 8));
        LoanOrder o1 = new LoanOrder(1, 1, 582, LocalDateTime.now().plusDays(1), LocalDateTime.now(), o1Products);
        LoanOrder o2 = new LoanOrder(2, 2, 372.75, LocalDateTime.now().plusDays(2), LocalDateTime.now(), o2Products);
        LoanOrder o3 = new LoanOrder(3, 3, 800, LocalDateTime.now().plusDays(3), LocalDateTime.now(), o3Products);
        addSupplierOrder(o1);
        addSupplierOrder(o2);
        addSupplierOrder(o3);
        LinkedList<LoanOrder> loanOrders1 = new LinkedList<>();
        LinkedList<LoanOrder> loanOrders2 = new LinkedList<>();
        LinkedList<LoanOrder> loanOrders3 = new LinkedList<>();
        loanOrders1.add(o1);
        loanOrders2.add(o2);
        loanOrders3.add(o3);

        LoanAgreement a1 = new LoanAgreement(1, 1, new Pair<>(cheese, new Pair<>(20, 5)));
        LoanAgreement a12 = new LoanAgreement(1, 4, new Pair<>(milk1, new Pair<>(15, 8)));
        LoanAgreement a2 = new LoanAgreement(2, 2, new Pair<>(milk2, new Pair<>(10, 3)));
        LoanAgreement a3 = new LoanAgreement(3, 3, new Pair<>(oliveOil, new Pair<>(40, 8)));
        addSupplierAgreement(a1, 1);
        addSupplierAgreement(a12, 1);
        addSupplierAgreement(a2, 2);
        addSupplierAgreement(a3,3);
        LinkedList<LoanAgreement> loanAgreements1 = new LinkedList<>();
        LinkedList<LoanAgreement> loanAgreements2 = new LinkedList<>();
        LinkedList<LoanAgreement> loanAgreements3 = new LinkedList<>();
        loanAgreements1.add(a1);
        loanAgreements1.add(a2);
        loanAgreements2.add(a12);
        loanAgreements3.add(a3);

        LinkedList<Pair<String, String>> contacts = new LinkedList<>();
        addSupplierContact("Adir Ben Shahar", "080-1020304", 1);
        addSupplier(new LoanSupplier("FixedDays",1,"Rom", 1, "1-2-3", "Cash", "010", contacts, loanAgreements1, loanOrders1, loanProducts1));
        addSupplier(new LoanSupplier("InviteOnly",2,"Adir", 2, "2-3-1", "Credit", "020", contacts, loanAgreements2, loanOrders2, loanProducts2));
        addSupplier(new LoanSupplier("SelfPickup", 3,"Din", 3, "3-2-1", "Credit",  "030", contacts, loanAgreements3, loanOrders3, loanProducts3));
    }

    public void close(){
        try {
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addSupplier(LoanSupplier ls){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIERS VALUES(?,?,?,?,?,?,?)");
            System.out.println("ID: " + ls.getID());
            p.setInt(1, ls.getID());
            p.setInt(2, ls.getCompanyID());
            p.setString(3, ls.getBankAccNum());
            p.setString(4, ls.getPayCond());
            p.setString(5, ls.getPhoneNum());
            p.setString(6, ls.getTag());
            p.setString(7, ls.getName());
            p.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addSupplierContact(String fullName, String phoneNum, int supplierID){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIER_CONTACTS VALUES(?,?,?)");
            p.setString(1, fullName);
            p.setString(2, phoneNum);
            p.setInt(3, supplierID);
            p.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addSupplierOrder(LoanOrder lo){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIER_ORDERS VALUES(?,?,?,?,?)");
            p.setInt(1, lo.getSupplierID());
            p.setInt(2, lo.getOrderID());
            p.setDate(3, Date.valueOf(lo.getETA().toLocalDate()));
            p.setDate(4, Date.valueOf(lo.getDateIssued().toLocalDate()));
            p.setDouble(5, lo.getTotal());
            p.executeUpdate();
            for(Map.Entry<LoanProduct, Pair<Integer, Integer>> e : lo.getProducts().entrySet()) {
                p = con.prepareStatement("INSERT INTO ORDER_PRODUCTS VALUES(?,?,?,?)");
                p.setInt(1, lo.getOrderID());
                p.setInt(2, e.getKey().getCatalogID());
                p.setInt(3, e.getValue().getKey());
                p.setInt(4, e.getValue().getValue());
                p.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addSupplierAgreement(LoanAgreement la, int supplierID){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIER_AGREEMENTS VALUES(?,?,?,?,?)");
            p.setInt(1, supplierID);
            p.setInt(2, la.getAgreementID());
            p.setInt(3, la.getAgreementDetails().getKey().getCatalogID());
            p.setInt(4, la.getAgreementDetails().getValue().getKey());
            p.setInt(5, la.getAgreementDetails().getValue().getValue());
            p.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addSupplierProduct(LoanProduct lp, int supplierID){
        try {
            PreparedStatement p = con.prepareStatement("INSERT INTO SUPPLIER_PRODUCTS VALUES(?,?,?,?,?,?,?)");
            p.setInt(1, lp.getCatalogID());
            p.setDouble(2, lp.getPrice());
            p.setDouble(3, lp.getPrice());
            p.setString(4, lp.getName());
            p.setString(5, lp.getManufacturer());
            p.setDate(6, Date.valueOf(lp.getExpirationDate().toLocalDate()));
            p.setInt(7, supplierID);
            p.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addReport(LoanReport lr){
        try {
            p = con.prepareStatement("INSERT INTO REPORTS VALUES(?,?,?)");
            p.setInt(1, lr.getReportID());
            p.setInt(2, lr.getReportedOrder().getOrderID());
            p.setDate(3, Date.valueOf(lr.getDateReported().toLocalDate()));
            p.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSupplier(int supplierID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIERS WHERE supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSupplierContact(int supplierID, String phoneNum){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_CONTACTS WHERE supplierID = " + supplierID + " AND phoneNum = " + phoneNum);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSupplierOrder(int orderID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_ORDERS WHERE orderID = " + orderID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSupplierProduct(int productID, int supplierID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_PRODUCTS WHERE catalogID = " + productID + " AND supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeSupplierAgreement(int agreementID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_AGREEMENTS WHERE agreementID = " + agreementID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeProductFromOrder(int orderID, int productID){
        try {
            p = con.prepareStatement("DELETE FROM ORDER_PRODUCTS WHERE catalogID = " + productID + " AND orderID = " + orderID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSupplierCompanyID(int companyID, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET companyID = " + companyID + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSupplierBankAccNum(String bankAccNum, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET bankAccNum = " + bankAccNum + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSupplierPayCond(String payCond, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET payCond = " + payCond + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSupplierPhoneNum(String phoneNum, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET phoneNum = " + phoneNum + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAgreementProdAmount(int amount, int agreementID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_AGREEMENTS SET Amount = " + amount + " WHERE agreementID = " + agreementID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAgreementProdSale(int sale, int agreementID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_AGREEMENTS SET Sale = " + sale + " WHERE agreementID = " + agreementID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateOrderETA(LocalDateTime ETA, int orderID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET ETA = (?)  WHERE orderID = " + orderID);
            p.setDate(1, Date.valueOf(ETA.toLocalDate()));
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateAmountOfProductInOrder(int orderID, int productID, int amount, double total){
        try {
            p = con.prepareStatement("UPDATE ORDER_PRODUCTS SET amountOrdered = " + amount + "  WHERE orderID = " + orderID + " AND productID = " + productID);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET total = " + total + "  WHERE orderID = " + orderID);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public LinkedList<LoanSupplier> pullSupplierData(){
        LinkedList<Pair<Integer, Pair<String, String>>> contacts = new LinkedList<>();
        try {
            p = con.prepareStatement("SELECT * FROM SUPPLIER_CONTACTS");
            ResultSet rs = p.executeQuery();
            while(rs.next())
                contacts.add(new Pair<>(rs.getInt("supplierID"), new Pair<>(rs.getString("FullName"), rs.getString("PhoneNum"))));

            p = con.prepareStatement("SELECT * FROM SUPPLIER_PRODUCTS");
            rs = p.executeQuery();
            while(rs.next())
                loanProducts.add(new LoanProduct(rs.getInt("supplierID"), rs.getInt("catalogID"), rs.getDouble("Price"),rs.getString("Name"), rs.getString("Manufacturer"),
                        rs.getDate("expirationDate").toLocalDate().atTime(LocalTime.now())));

            p = con.prepareStatement("SELECT * FROM SUPPLIER_AGREEMENTS");
            rs = p.executeQuery();
            while(rs.next()) {
                for(LoanProduct lp : loanProducts){
                    if(lp.getCatalogID() == rs.getInt("catalogID")) {
                        loanAgreements.add(new LoanAgreement(rs.getInt("supplierID"), rs.getInt("agreementID"), new Pair<>(lp, new Pair<>(rs.getInt("Amount"), rs.getInt("Sale")))));
                        break;
                    }
                }
            }

            p = con.prepareStatement("SELECT * FROM SUPPLIER_ORDERS");
            rs = p.executeQuery();
            while(rs.next()) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM ORDER_PRODUCTS WHERE orderID = " + rs.getInt("orderID"));
                ResultSet r = ps.executeQuery();
                HashMap<LoanProduct, Pair<Integer, Integer>> prod = new HashMap<>();
                while(r.next()) {
                    for (LoanProduct lp : loanProducts) {
                        if (lp.getCatalogID() == r.getInt("catalogID")) {
                            prod.put(lp, new Pair<>(r.getInt("amountOrdered"), r.getInt("sale")));
                            break;
                        }
                    }
                }
                r.close();
                loanOrders.add(new LoanOrder(rs.getInt("supplierID"), rs.getInt("orderID"), rs.getDouble("total"),
                        rs.getDate("ETA").toLocalDate().atTime(LocalTime.now()), rs.getDate("dateIssued").toLocalDate().atTime(LocalTime.now()),
                        prod));
            }

            p = con.prepareStatement("SELECT * FROM SUPPLIERS");
            rs = p.executeQuery();
            int supplierID;
            while(rs.next()) {
                supplierID = rs.getInt("supplierID");
                loanSuppliers.add(new LoanSupplier(rs.getString("tag"), supplierID, rs.getString("Name"),
                        rs.getInt("companyID"), rs.getString("bankAccNum"), rs.getString("payCond"), rs.getString("phoneNum"),
                        getContactsOfSupplier(supplierID, contacts), getLoanAgreementsOfSupplier(supplierID), getLoanOrdersOfSupplier(supplierID), getLoanProductsOfSupplier(supplierID)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return loanSuppliers;
    }

    public LinkedList<LoanReport> pullReports(){
        LinkedList<LoanReport> reports = new LinkedList<>();
        try{
            p = con.prepareStatement("SELECT * FROM REPORTS");
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                for(LoanOrder lo : loanOrders){
                    if(lo.getOrderID() == rs.getInt("reportedOrderID"))
                        loanReports.add(new LoanReport(rs.getString("tag"), rs.getInt("reportID"), rs.getDate("dateReported").toLocalDate().atTime(LocalTime.now()), lo));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    private LinkedList<LoanAgreement> getLoanAgreementsOfSupplier(int supplierID) {
        LinkedList<LoanAgreement> la = new LinkedList<>();
        for(LoanAgreement l : loanAgreements) {
            if (l.getSupplierID() == supplierID)
                la.add(l);
        }
        return la;
    }

    private LinkedList<LoanOrder> getLoanOrdersOfSupplier(int supplierID) {
        LinkedList<LoanOrder> lo = new LinkedList<>();
        for(LoanOrder l : loanOrders) {
            if (l.getSupplierID() == supplierID)
                lo.add(l);
        }
        return lo;
    }

    private LinkedList<LoanProduct> getLoanProductsOfSupplier(int supplierID) {
        LinkedList<LoanProduct> lp = new LinkedList<>();
        for(LoanProduct l : loanProducts) {
            if (l.getSupplierID() == supplierID)
                lp.add(l);
        }
        return lp;
    }

    private LinkedList<Pair<String, String>> getContactsOfSupplier(int supplierID, LinkedList<Pair<Integer, Pair<String, String>>> contacts) {
        LinkedList<Pair<String, String>> cons = new LinkedList<>();
        for(Pair<Integer, Pair<String, String>> c : contacts) {
            if(c.getKey() == supplierID)
                cons.add(new Pair<>(c.getValue().getKey(), c.getValue().getValue()));
        }
        return cons;
    }
}
