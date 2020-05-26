package StorageAndSupplier.Suppliers.PersistenceLayer;

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
        if (instance == null)
            instance = new DataController();
        return instance;
    }

    private DataController(){
        //try {
        //    Class.forName("org.sqlite.JDBC");
        //    String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\dev\\storage.db";
        //    con = DriverManager.getConnection(url);
        //} catch ( Exception e) {
        //    e.printStackTrace();
        //}

        loanSuppliers = new LinkedList<>();
        loanOrders = new LinkedList<>();
        loanAgreements = new LinkedList<>();
        loanProducts = new LinkedList<>();
        loanReports = new LinkedList<>();
    }

//    public void loadSystem(){
//        LoanProduct Milk1 = new LoanProduct(1,1, 5.0, "Milk", "Tara", LocalDateTime.now().plusDays(7));
//        LoanProduct Milk2 = new LoanProduct(2,1, 5.0, "Milk", "Tnuva", LocalDateTime.now().plusDays(7));
//        LoanProduct Tuna = new LoanProduct(3,2, 18.5, "Tuna", "Starkist", LocalDateTime.now().plusDays(7));
//        LoanProduct Shampoo = new LoanProduct(1,3, 13.25, "Shampoo", "Dove", LocalDateTime.now().plusDays(7));
//        LoanProduct Cheese = new LoanProduct(1,4, 24.0, "Cheese", "Emek", LocalDateTime.now().plusDays(7));
//        LoanProduct Shocko = new LoanProduct(2,5, 4.0, "Shocko", "Tnuva", LocalDateTime.now().plusDays(7));
//        LinkedList<LoanProduct> loanProducts1 = new LinkedList<>();
//        LinkedList<LoanProduct> loanProducts2 = new LinkedList<>();
//        LinkedList<LoanProduct> loanProducts3 = new LinkedList<>();
//        loanProducts1.add(Milk1);
//        loanProducts1.add(Milk2);
//        loanProducts1.add(Tuna);
//        loanProducts2.add(Shampoo);
//        loanProducts2.add(Cheese);
//        loanProducts3.add(Shocko);
//        addSupplierProduct(Milk1, 1);
//        addSupplierProduct(Cheese, 1);
//        addSupplierProduct(Shampoo, 1);
//        addSupplierProduct(Milk2, 2);
//        addSupplierProduct(Shocko, 2);
//        addSupplierProduct(Tuna, 3);
//        loanProducts.add(Milk1);
//        loanProducts.add(Milk2);
//        loanProducts.add(Tuna);
//        loanProducts.add(Shampoo);
//        loanProducts.add(Cheese);
//        loanProducts.add(Shocko);
//
//        HashMap<LoanProduct, Pair<Integer, Integer>> o1Products = new HashMap<>();
//        HashMap<LoanProduct, Pair<Integer, Integer>> o2Products = new HashMap<>();
//        HashMap<LoanProduct, Pair<Integer, Integer>> o3Products = new HashMap<>();
//        o1Products.put(Milk1, new Pair<>(20, 8));
//        o1Products.put(Shampoo, new Pair<>(20, 5));
//        o1Products.put(Cheese, new Pair<>(5, 0));
//        o2Products.put(Shocko, new Pair<>(15, 0));
//        o2Products.put(Milk2, new Pair<>(30, 3));
//        o3Products.put(Tuna, new Pair<>(50, 7));
//        LoanOrder o1 = new LoanOrder(1, 1, 463.75, LocalDateTime.now().plusDays(1), LocalDateTime.now(), o1Products);
//        LoanOrder o2 = new LoanOrder(2, 2, 205.5, LocalDateTime.now().plusDays(2), LocalDateTime.now(), o2Products);
//        LoanOrder o3 = new LoanOrder(3, 3, 860.25, LocalDateTime.now().plusDays(3), LocalDateTime.now(), o3Products);
//        addSupplierOrder(o1);
//        addSupplierOrder(o2);
//        addSupplierOrder(o3);
//        LinkedList<LoanOrder> loanOrders1 = new LinkedList<>();
//        LinkedList<LoanOrder> loanOrders2 = new LinkedList<>();
//        LinkedList<LoanOrder> loanOrders3 = new LinkedList<>();
//        loanOrders1.add(o1);
//        loanOrders2.add(o2);
//        loanOrders3.add(o3);
//        loanOrders.add(o1);
//        loanOrders.add(o2);
//        loanOrders.add(o3);
//
//        LoanAgreement a1 = new LoanAgreement(1, 1, new Pair<>(Shampoo, new Pair<>(20, 5)));
//        LoanAgreement a12 = new LoanAgreement(1, 4, new Pair<>(Milk1, new Pair<>(15, 8)));
//        LoanAgreement a2 = new LoanAgreement(2, 2, new Pair<>(Milk2, new Pair<>(10, 3)));
//        LoanAgreement a3 = new LoanAgreement(3, 3, new Pair<>(Tuna, new Pair<>(30, 7)));
//        addSupplierAgreement(a1, 1);
//        addSupplierAgreement(a12, 1);
//        addSupplierAgreement(a2, 2);
//        addSupplierAgreement(a3,3);
//        LinkedList<LoanAgreement> loanAgreements1 = new LinkedList<>();
//        LinkedList<LoanAgreement> loanAgreements2 = new LinkedList<>();
//        LinkedList<LoanAgreement> loanAgreements3 = new LinkedList<>();
//        loanAgreements1.add(a1);
//        loanAgreements1.add(a2);
//        loanAgreements2.add(a12);
//        loanAgreements3.add(a3);
//        loanAgreements.add(a1);
//        loanAgreements.add(a2);
//        loanAgreements.add(a12);
//        loanAgreements.add(a3);
//
//        LinkedList<Pair<String, String>> contacts1 = new LinkedList<>();
//        LinkedList<Pair<String, String>> contacts2 = new LinkedList<>();
//        LinkedList<Pair<String, String>> contacts3 = new LinkedList<>();
//        Pair<String, String> contact1 = new Pair<>("Adir Ben Shahar", "010-1020304");
//        Pair<String, String> contact2 = new Pair<>("Rom Golovin", "020-1020304");
//        Pair<String, String> contact3 = new Pair<>("Mister Sister", "030-1020304");
//        contacts1.add(contact1);
//        contacts2.add(contact2);
//        contacts3.add(contact3);
//        addSupplierContact(contact1.getKey(), contact1.getValue(), 1);
//        addSupplierContact(contact2.getKey(), contact2.getValue(), 2);
//        addSupplierContact(contact3.getKey(), contact3.getValue(), 3);
//        LoanSupplier s1 = new LoanSupplier("FixedDays",1,"Rom", 1, "1-2-3", "Cash", "010", contacts1, loanAgreements1, loanOrders1, loanProducts1);
//        LoanSupplier s2 = new LoanSupplier("OrderOnly",2,"Adir", 2, "2-3-1", "Credit", "020", contacts2, loanAgreements2, loanOrders2, loanProducts2);
//        LoanSupplier s3 = new LoanSupplier("SelfPickup", 3,"Din", 3, "3-2-1", "Credit",  "030", contacts3, loanAgreements3, loanOrders3, loanProducts3);
//        addSupplier(s1);
//        addSupplier(s2);
//        addSupplier(s3);
//        loanSuppliers.add(s1);
//        loanSuppliers.add(s2);
//        loanSuppliers.add(s3);
//    }

//    public void unloadSystem(){
//        removeSupplier(1);
//        removeSupplier(2);
//        removeSupplier(3);
//    }

    public void close(){
        try {
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean addSupplier(LoanSupplier ls){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIERS VALUES(?,?,?,?,?,?,?)");
            p.setInt(1, ls.getID());
            p.setInt(2, ls.getCompanyID());
            p.setString(3, ls.getBankAccNum());
            p.setString(4, ls.getPayCond());
            p.setString(5, ls.getPhoneNum());
            p.setString(6, ls.getTag());
            p.setString(7, ls.getName());
            p.executeUpdate();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public boolean addSupplierContact(String fullName, String phoneNum, int supplierID){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIER_CONTACTS VALUES(?,?,?)");
            p.setString(1, fullName);
            p.setString(2, phoneNum);
            p.setInt(3, supplierID);
            p.executeUpdate();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public boolean addSupplierOrder(LoanOrder lo){
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
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public boolean addSupplierAgreement(LoanAgreement la, int supplierID){
        try {
            p = con.prepareStatement("INSERT INTO SUPPLIER_AGREEMENTS VALUES(?,?,?,?,?)");
            p.setInt(1, supplierID);
            p.setInt(2, la.getAgreementID());
            p.setInt(3, la.getAgreementDetails().getKey().getCatalogID());
            p.setInt(4, la.getAgreementDetails().getValue().getKey());
            p.setInt(5, la.getAgreementDetails().getValue().getValue());
            p.executeUpdate();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public boolean addSupplierProduct(LoanProduct lp, int supplierID){
        try {
            PreparedStatement p = con.prepareStatement("INSERT INTO SUPPLIER_PRODUCTS VALUES(?,?,?,?,?,?)");
            p.setInt(1, lp.getCatalogID());
            p.setDouble(2, lp.getPrice());
            p.setString(3, lp.getName());
            p.setString(4, lp.getManufacturer());
            p.setDate(5, Date.valueOf(lp.getExpirationDate().toLocalDate()));
            p.setInt(6, supplierID);
            p.executeUpdate();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean addProductToOrder(int orderID, int productID, int amount, double total, int sale){
        try {
            p = con.prepareStatement("INSERT INTO ORDER_PRODUCTS VALUES(?,?,?,?)");
            p.setInt(1, orderID);
            p.setInt(2, productID);
            p.setInt(3, amount);
            p.setInt(4, sale);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET total = " + total + "  WHERE orderID = " + orderID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean addReport(LoanReport lr){
        try {
            p = con.prepareStatement("INSERT INTO REPORTS VALUES(?,?,?,?)");
            p.setInt(1, lr.getReportID());
            p.setInt(2, lr.getReportedOrder().getOrderID());
            p.setDate(3, Date.valueOf(lr.getDateReported().toLocalDate()));
            p.setString(4, lr.getTag());
            p.executeUpdate();
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public boolean removeSupplier(int supplierID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIERS WHERE supplierID = " + supplierID);
            p.executeUpdate();
            p = con.prepareStatement("DELETE FROM SUPPLIER_AGREEMENTS WHERE supplierID = " + supplierID);
            p.executeUpdate();
            p = con.prepareStatement("SELECT orderID FROM SUPPLIER_ORDERS WHERE supplierID = " + supplierID);
            ResultSet r = p.executeQuery();
            while(r.next()){
                p = con.prepareStatement("DELETE FROM ORDER_PRODUCTS WHERE orderID = " + r.getString("orderID"));
                p.executeUpdate();
            }
            p = con.prepareStatement("DELETE FROM SUPPLIER_ORDERS WHERE supplierID = " + supplierID);
            p.executeUpdate();
            p = con.prepareStatement("DELETE FROM SUPPLIER_PRODUCTS WHERE supplierID = " + supplierID);
            p.executeUpdate();
            p = con.prepareStatement("DELETE FROM SUPPLIER_CONTACTS WHERE supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean removeSupplierContact(int supplierID, String phoneNum){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_CONTACTS WHERE supplierID = " + supplierID + " AND phoneNum = " + phoneNum);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean removeSupplierOrder(int orderID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_ORDERS WHERE orderID = " + orderID);
            p.executeUpdate();
            p = con.prepareStatement("DELETE FROM ORDER_PRODUCTS WHERE orderID = " + orderID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean removeSupplierProduct(int productID, int supplierID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_PRODUCTS WHERE catalogID = " + productID + " AND supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean removeSupplierAgreement(int agreementID){
        try {
            p = con.prepareStatement("DELETE FROM SUPPLIER_AGREEMENTS WHERE agreementID = " + agreementID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean removeProductFromOrder(int orderID, int productID, double total){
        try {
            p = con.prepareStatement("DELETE FROM ORDER_PRODUCTS WHERE catalogID = " + productID + " AND orderID = " + orderID);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET total = " + total + "  WHERE orderID = " + orderID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateSupplierCompanyID(int companyID, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET companyID = " + companyID + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateSupplierBankAccNum(String bankAccNum, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET bankAccNum = " + bankAccNum + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateSupplierPayCond(String payCond, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET payCond = " + payCond + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateSupplierPhoneNum(String phoneNum, int supplierID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIERS SET phoneNum = " + phoneNum + " WHERE supplierID = " + supplierID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateAgreementProdAmount(int amount, int agreementID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_AGREEMENTS SET Amount = " + amount + " WHERE agreementID = " + agreementID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateAgreementProdSale(int sale, int agreementID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_AGREEMENTS SET Sale = " + sale + " WHERE agreementID = " + agreementID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateOrderETA(LocalDateTime ETA, int orderID){
        try {
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET ETA = (?)  WHERE orderID = " + orderID);
            p.setDate(1, Date.valueOf(ETA.toLocalDate()));
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean updateAmountOfProductInOrder(int orderID, int productID, int amount, double total){
        try {
            p = con.prepareStatement("UPDATE ORDER_PRODUCTS SET amountOrdered = " + amount + "  WHERE orderID = " + orderID + " AND catalogID = " + productID);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE SUPPLIER_ORDERS SET total = " + total + "  WHERE orderID = " + orderID);
            p.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
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

    public LinkedList<LoanSupplier> getLoanSuppliers(){
        return loanSuppliers;
    }

    public LoanSupplier getSupplierByOrder(int orderID){
        try {
            p = con.prepareStatement("SELECT S.supplierID FROM SUPPLIERS AS S JOIN SUPPLIER_ORDERS AS SO ON S.supplierID = SO.supplierID AND orderID = " + orderID);
            ResultSet rs = p.executeQuery();
            return getLoanSupplier(rs.getInt("supplierID"));
        }catch (SQLException e){
            return null;
        }
    }

    public LoanSupplier getLoanSupplier(int supplierID){
        LoanSupplier ls = null;
        LinkedList<Pair<Integer, Pair<String, String>>> contacts = new LinkedList<>();
        try {
            p = con.prepareStatement("SELECT * FROM SUPPLIER_CONTACTS WHERE supplierID = " + supplierID);
            ResultSet rs = p.executeQuery();
            while(rs.next())
                contacts.add(new Pair<>(rs.getInt("supplierID"), new Pair<>(rs.getString("FullName"), rs.getString("PhoneNum"))));

            p = con.prepareStatement("SELECT * FROM SUPPLIER_PRODUCTS WHERE supplierID = " + supplierID);
            rs = p.executeQuery();
            while(rs.next())
                loanProducts.add(new LoanProduct(rs.getInt("supplierID"), rs.getInt("catalogID"), rs.getDouble("Price"),rs.getString("Name"), rs.getString("Manufacturer"),
                        rs.getDate("expirationDate").toLocalDate().atTime(LocalTime.now())));

            p = con.prepareStatement("SELECT * FROM SUPPLIER_AGREEMENTS WHERE supplierID = " + supplierID);
            rs = p.executeQuery();
            while(rs.next()) {
                for(LoanProduct lp : loanProducts){
                    if(lp.getCatalogID() == rs.getInt("catalogID")) {
                        loanAgreements.add(new LoanAgreement(rs.getInt("supplierID"), rs.getInt("agreementID"), new Pair<>(lp, new Pair<>(rs.getInt("Amount"), rs.getInt("Sale")))));
                        break;
                    }
                }
            }

            p = con.prepareStatement("SELECT * FROM SUPPLIER_ORDERS WHERE supplierID = " + supplierID);
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

            p = con.prepareStatement("SELECT * FROM SUPPLIERS WHERE supplierID = " + supplierID);
            rs = p.executeQuery();
            if(!rs.next()) return null;
            ls = new LoanSupplier(rs.getString("tag"), supplierID, rs.getString("Name"),
                    rs.getInt("companyID"), rs.getString("bankAccNum"), rs.getString("payCond"), rs.getString("phoneNum"),
                    getContactsOfSupplier(supplierID, contacts), getLoanAgreementsOfSupplier(supplierID), getLoanOrdersOfSupplier(supplierID), getLoanProductsOfSupplier(supplierID));
            loanSuppliers.add(ls);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ls;
    }

    public LoanReport getReport(int reportID){
        LoanReport lr = null;
        try{
            p = con.prepareStatement("SELECT * FROM REPORTS WHERE reportID = " + reportID);
            ResultSet rs = p.executeQuery();
            if(!rs.next()) return null;
            for(LoanOrder lo : loanOrders){
                if(lo.getOrderID() == rs.getInt("reportedOrderID")) {
                    lr = new LoanReport(rs.getString("tag"), rs.getInt("reportID"), rs.getDate("dateReported").toLocalDate().atTime(LocalTime.now()), lo);
                    loanReports.add(lr);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lr;
    }

    public HashMap<String, Integer> updateStaticIDs(){
        HashMap<String, Integer> statIDs = new HashMap<>();
        try {
            p = con.prepareStatement("SELECT COUNT(*) AS num FROM SUPPLIERS");
            ResultSet r = p.executeQuery();
            statIDs.put("StorageAndSupplier/Suppliers", r.getInt("num"));
            p = con.prepareStatement("SELECT COUNT(*) AS num FROM SUPPLIER_AGREEMENTS");
            r = p.executeQuery();
            statIDs.put("Agreements", r.getInt("num"));
            p = con.prepareStatement("SELECT COUNT(*) AS num FROM SUPPLIER_ORDERS");
            r = p.executeQuery();
            statIDs.put("Orders", r.getInt("num"));
            p = con.prepareStatement("SELECT COUNT(*) AS num FROM REPORTS");
            r = p.executeQuery();
            statIDs.put("Reports", r.getInt("num"));
        }catch (SQLException e){
            return null;
        }
        return statIDs;
    }

    public void setConnection(Connection conn) {
        this.con = conn;
    }
}
