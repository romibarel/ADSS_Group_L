package DAL.ReportsDAL;


import Buisness.Invenrory.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsDAL {
    private List <DefectReportDAL> defectReportDALList;
    private List<ProductReportDAL> productReportDALList;

    public ReportsDAL(){
        this.defectReportDALList = new ArrayList<DefectReportDAL>();
        this.productReportDALList = new ArrayList<ProductReportDAL>();
    }

    public void addNewProductReport (ProductReportDAL productReportDAL, Connection conn){
        //this.productReportDALList.add(productReportDAL);
        for (List<ProductRepDataDAL> productRepDataDALList :productReportDAL.getReportData().values()) {
            for (ProductRepDataDAL p : productRepDataDALList ) {
                try {
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO PRODUCT_REPORT VALUES(?,?,?,?)");
                    stmt2.setDate(1, new java.sql.Date(productReportDAL.getDate().getTime()));
                    stmt2.setInt(2, p.getBarCode());
                    stmt2.setString(3, p.getProductName());
                    stmt2.setInt(4, p.getAmount());
                    stmt2.executeUpdate();
                }
                catch (Exception e){
                    System.out.println("failed");
                }
            }
        }
    }

    public void addNewDefectReport (DefectReportDAL defectReportDAL){
        this.defectReportDALList.add(defectReportDAL);
    }

    public List <DefectReportDAL> restoreDefectsReportsList (){return this.defectReportDALList;}

    public List <ProductReportDAL> restoreProductsReportList(Connection conn){
        //return this.productReportDALList;
        List<ProductReportDAL> ret= new ArrayList<>();
        Map<String, List<String>> hir = new HashMap<>();
        Map<String, List<ProductRepDataDAL>> reportData = new HashMap<>();
        List <ProductRepDataDAL> l = new ArrayList<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * " +
                    "FROM PRODUCT_REPORT ");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                ProductRepDataDAL p = new ProductRepDataDAL(rs2.getInt(2),rs2.getString(3),rs2.getInt(4));
                l.add(p);
            }
            hir = restoreHierarchy(conn);
            reportData = createMapByCategories(l, conn);
            ProductReportDAL addedReport = new ProductReportDAL(rs2.getDate(1), hir, reportData);
            ret.add(addedReport);
        }
        catch (Exception e){
            System.out.println("failed");
        }
        return ret;
    }

    private Map<String, List<ProductRepDataDAL>> createMapByCategories(List<ProductRepDataDAL> l, Connection conn) {
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
                System.out.println("failed");
            }
        }
        return ret;
    }

    private Map<String, List<String>> restoreHierarchy(Connection conn) {
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
            System.out.println("failed");
        }
        return ret;
    }
}
