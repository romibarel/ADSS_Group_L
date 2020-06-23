package StorageAndSupplier.Storage.DAL.ReportsDAL;


import StorageAndSupplier.Storage.DAL.DefectsDAL.DefectDAL;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ReportsDAL {
    private List <DefectReportDAL> defectReportDALList;
    private List<ProductReportDAL> productReportDALList;

    public ReportsDAL(){
        this.defectReportDALList = new ArrayList<DefectReportDAL>();
        this.productReportDALList = new ArrayList<ProductReportDAL>();
    }

    public void addNewProductReport (ProductReportDAL productReportDAL, Connection conn){
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }
        for (List<ProductRepDataDAL> productRepDataDALList :productReportDAL.getReportData().values()) {
            for (ProductRepDataDAL p : productRepDataDALList ) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String ts = sdf.format(new java.sql.Timestamp(productReportDAL.getDate().getTime()));
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO PRODUCT_REPORT VALUES(?,?,?,?)");
                    stmt2.setString(1, ts);
                    stmt2.setInt(2, p.getBarCode());
                    stmt2.setString(3, p.getProductName());
                    stmt2.setInt(4, p.getAmount());
                    stmt2.executeUpdate();
                }
                catch (Exception e){
                    //System.out.println("failed");
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewDefectReport (DefectReportDAL defectReportDAL){
        this.defectReportDALList.add(defectReportDAL);
    }

    public List <DefectReportDAL> restoreDefectsReportsList (Connection conn){
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }
        //return this.defectReportDALList;

        List <DefectReportDAL> ret = new ArrayList<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Date_Of_Report " +
                    "FROM DEFECT_REPORTS");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                String checkDate = rs2.getString(1);
                if (checkDate != null) {
                    //checkDate = checkDate.replace('-','/');
                    Date date =  new SimpleDateFormat("yyyy-MM-dd").parse(checkDate);

                    DefectReportDAL defectReportDAL = new DefectReportDAL(date, new ArrayList<>());
                    ret.add(defectReportDAL);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            for (DefectReportDAL d: ret) {
                List <DefectDAL> defects = new ArrayList<>();
                String myStartDate = sdf.format(new java.sql.Timestamp(d.getDateStart().getTime()));
                PreparedStatement stmt3 = conn.prepareStatement("SELECT * From DEFECT_REPORTS WHERE Date_Of_Feed > " + myStartDate);
                ResultSet rs3 = stmt3.executeQuery();
                while (rs3.next()) {
                    Date dateOfFeed = new SimpleDateFormat("yyyy-MM-dd").parse(rs3.getString(3));
                    Date expiration = new SimpleDateFormat("yyyy-MM-dd").parse(rs3.getString(1));

                    DefectDAL defectDAL= new DefectDAL(dateOfFeed,rs3.getInt(2),
                            rs3.getInt(4), rs3.getString(5), rs3.getString(6), rs3.getInt(7),
                            expiration);
                    defects.add(defectDAL);
                }
                d.setDefects(defects);
            }
        }
        catch (Exception e){
            //System.out.println("failed");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public List <ProductReportDAL> restoreProductsReportList(Connection conn){

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        List<ProductReportDAL> ret= new ArrayList<>();
        Map<String, List<String>> hir = new HashMap<>();
        Map<String, List<ProductRepDataDAL>> reportData = new HashMap<>();
        List <ProductRepDataDAL> l = new ArrayList<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * " +
                    "FROM PRODUCT_REPORT ");
            ResultSet rs2 = stmt2.executeQuery();
            Date date = null;
            while (rs2.next()) {
                String dateS = rs2.getString(1);
                DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                date = formatter.parse(dateS);

                ProductRepDataDAL p = new ProductRepDataDAL(rs2.getInt(2),rs2.getString(3),rs2.getInt(4));
                l.add(p);
            }
            hir = restoreHierarchy(conn);
            reportData = createMapByCategories(l, conn);
            //TODO: convert the date
            ProductReportDAL addedReport = new ProductReportDAL(date, hir, reportData);
            ret.add(addedReport);
        }
        catch (Exception e){
            //System.out.println("failed");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private Map<String, List<ProductRepDataDAL>> createMapByCategories(List<ProductRepDataDAL> l, Connection conn) {

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        Map<String, List<ProductRepDataDAL>> ret = new HashMap<>();
        for (ProductRepDataDAL p : l) {
            int barcode = p.getBarCode();
            try {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT CName " +
                        " FROM CATEGORIES_OF_PRODUCTS WHERE Barcode =  " + barcode);
                ResultSet rs2 = stmt2.executeQuery();
                String catName = rs2.getString(1);
                if (ret.containsKey(catName)){
                    ret.get(catName).add(p);
                }
                else{
                    List<ProductRepDataDAL> toAdd = new ArrayList();
                    toAdd.add(p);
                    ret.put(catName, toAdd);
                }
            } catch (Exception e) {
                //System.out.println("failed");
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private Map<String, List<String>> restoreHierarchy(Connection conn) {

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        List<String> allCategoryNames = new ArrayList<>();
        Map<String, List<String>> ret = new HashMap<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * " +
                    "FROM CATEGORIES ");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                String name = rs2.getString(1);
                allCategoryNames.add(name);
            }
            for (String name: allCategoryNames) {
                PreparedStatement stmt3 = conn.prepareStatement("SELECT * " +
                        "FROM SUB_CATEGORIES WHERE Main = '"+name+"'");
                ResultSet rs3 = stmt3.executeQuery();
                List<String> subList= new ArrayList();
                while (rs3.next()){
                    subList.add(rs3.getString(1));
                }
                ret.put(name, subList);
            }
        }
        catch (Exception e){
            //System.out.println("failed");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void addDefectReport(DefectReportDAL defectReportDAL, Connection conn) {

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String startDateOfReport = sdf.format(new java.sql.Timestamp(defectReportDAL.getDateStart().getTime()));
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DEFECT_REPORTS VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, null);
            stmt.setInt(2, 0);
            stmt.setString(3, null);
            stmt.setInt(4, 0);
            stmt.setString(5, null);
            stmt.setString(6, null);
            stmt.setInt(7, 0);
            stmt.setString(8, startDateOfReport);
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            //System.out.println("failed");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
