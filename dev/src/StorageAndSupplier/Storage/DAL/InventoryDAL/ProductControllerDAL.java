package StorageAndSupplier.Storage.DAL.InventoryDAL;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ProductControllerDAL {
    private List<CategoryDAL> categoryDALS;
    private Map<Integer , DataSaleProductDAL> saleData; //<barCode, DataSaleProduct>
    private Connection conn1;

    public ProductControllerDAL(){
        this.categoryDALS = new ArrayList<>();
        this.saleData = new HashMap<>();
    }

    public List<CategoryDAL> getCategoryDALS(Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //return categoryDALS;
        List<CategoryDAL> ret = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CATEGORIES");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret.add(createCategoryDALByName(rs.getString(1), conn));
            }
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }

    private CategoryDAL createCategoryDALByName(String categoryName, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<ProductDAL> p = new ArrayList<>();
        List<CategoryDAL> c = new ArrayList<>();
        CategoryDAL ret = new CategoryDAL(categoryName, c, p);
        List<String> cNames = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT PRODUCTS.* " +
                    "FROM CATEGORIES_OF_PRODUCTS INNER JOIN PRODUCTS ON CATEGORIES_OF_PRODUCTS.Barcode = PRODUCTS.Barcode " +
                    "WHERE CATEGORIES_OF_PRODUCTS.CName = '"+categoryName+"'");
            ResultSet rs = stmt.executeQuery();
            //TODO: convert the date
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString(6));
            while (rs.next()) {
                ProductDAL product = new ProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getInt(3), rs.getInt(4),
                        rs.getInt(5), date);
                p.add(product);
            }
            PreparedStatement stmt2 = conn.prepareStatement("SELECT SUB_CATEGORIES.Sub " +
                    "FROM SUB_CATEGORIES " +
                    "WHERE main = '"+categoryName+"'");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                cNames.add(rs2.getString(1));
            }
        }
        catch (Exception e){

        }
        for (String name : cNames){
            CategoryDAL cat = createCategoryDALByName(name, conn);
            c.add(cat);
        }
        ret.setSubCategoriesDAL(c);
        ret.setProductListDAL(p);

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }

    public void setCategoryDALS(List<CategoryDAL> categoryDALS) {
        this.categoryDALS = categoryDALS;
    }

    public Map<Integer, DataSaleProductDAL> getSaleData(Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //return saleData;
        Map <Integer, DataSaleProductDAL> ret = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DATA_SALE_PRODUCTS");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DataSaleProductDAL d = new DataSaleProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getDouble(3), rs.getDouble(4));
                ret.put(d.getBarCode(), d);
            }
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void setSaleData(Map<Integer, DataSaleProductDAL> saleData) {
        this.saleData = saleData;
    }

    public void addCategory(CategoryDAL dal, Connection conn) {

        if(conn == null){
            try {
                    Class.forName("org.sqlite.JDBC");
                    String url = "jdbc:sqlite:Database.db";
                 conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //this.categoryDALS.add(dal);
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO CATEGORIES VALUES (?)");
            stmt.setString(1, dal.getName());
            stmt.executeUpdate();

        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/
            //System.out.println("failed");
        }

        try{
            conn.close();
        }
         catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public CategoryDAL getCategoryDALByName(String name){
        for (CategoryDAL category:this.categoryDALS) {
            if (category.getName().equals(name)){
                return category;
            }
        }
        return null; /*the category doesn't exists ->this case never happens because we reach DAL only if
                    exists but it won't harm*/
    }

    public void appendSubCategory(String main, String sub, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SUB_CATEGORIES VALUES (?,?)");
            stmt.setString(1, main);
            stmt.setString(2, sub);
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void appendProductToCategoryDAL(String name, ProductDAL productDAL, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO CATEGORIES_OF_PRODUCTS VALUES (?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, productDAL.getBarCode());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addNewDataSaleProduct(int barcode, DataSaleProductDAL dal, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DATA_SALE_PRODUCTS VALUES (?,?,?,?)");
            stmt.setInt(1, dal.getBarCode());
            stmt.setString(2, dal.getProductName());
            stmt.setDouble(3, dal.getPrice());
            stmt.setDouble(4, dal.getDiscount());
            stmt.executeUpdate();
        }
        catch (Exception e){    /*try to insert, if its exists reach also here*/

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDataSaleProduct(int barcode, DataSaleProductDAL dataSaleProductDAL, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //this.saleData.replace(barcode, dataSaleProductDAL);
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE DATA_SALE_PRODUCTS SET" +
                    " Name = " + dataSaleProductDAL.getProductName() + " Price = " + dataSaleProductDAL.getPrice() +"Discount = "+dataSaleProductDAL.getDiscount()
                    +"WHERE Barcode = " + dataSaleProductDAL.getBarCode());
            stmt.executeUpdate();
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteProductFromCategory (ProductDAL productDAL, CategoryDAL categoryDAL, Connection conn){
        categoryDAL.deleteProduct(productDAL, conn);
    }

    public void addNewProduct (ProductDAL productDAL, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO PRODUCTS VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, productDAL.getBarCode());
            stmt.setString(2, productDAL.getProductName());
            stmt.setInt(3, productDAL.getManufactor());
            stmt.setInt(4, productDAL.getAmount());
            stmt.setInt(5, productDAL.getMinAmount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String ts = sdf.format(new java.sql.Timestamp(productDAL.getNextSupplyTime().getTime()));
            stmt.setString(6, ts);

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

    }//in this case we don't ave to do something, but whenever we'll add database so we use this function

    public void updateProduct(ProductDAL productDAL, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE PRODUCTS " +
                    "SET Name = ? "+
                    ", Manufactor = ? " +
                    ", Amount = ? " +
                    ", MinAmount = ? " +
                    ", NextSupplyTime = ?" +
                    "WHERE Barcode = ?"
                    );
            stmt.setString(1, productDAL.getProductName());
            stmt.setInt(2 ,productDAL.getManufactor());
            stmt.setInt(3,productDAL.getAmount());
            stmt.setInt(4,productDAL.getMinAmount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String ts = sdf.format(new java.sql.Timestamp(productDAL.getNextSupplyTime().getTime()));

            stmt.setString(5, ts);
            //stmt.setDate(5, new java.sql.Date(productDAL.getNextSupplyTime().getTime()) , Calendar.getInstance());
            stmt.setInt(6,productDAL.getBarCode());

            stmt.executeUpdate();
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ProductDAL searchProduct (int barCode, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ProductDAL ret = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE " +
                    " Barcode = " + barCode);
            ResultSet rs = stmt.executeQuery();
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString(6));
            ret = new ProductDAL(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getInt(4),
                    rs.getInt(5), date);
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void updateCategory (String categoryName, String newName, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE CATEGORIES SET" +
                    " Name = " + newName +" WHERE Name = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE CATEGORIES_OF_PRODUCTS SET" +
                    " CName = " + newName +" WHERE CName = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE SUB_CATEGORIES SET" +
                    " Main = " + newName +" WHERE Main = " + categoryName
            );
            stmt.executeUpdate();
            stmt = conn.prepareStatement("UPDATE SUB_CATEGORIES SET" +
                    " Sub = " + newName +" WHERE Sub = " + categoryName
            );
            stmt.executeUpdate();
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCategory(String cName, Connection conn){

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM CATEGORIES WHERE " +
                    " Name = " + cName);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DELETE FROM SUB_CATEGORIES WHERE " +
                    " Sub = " + cName);
            stmt.executeUpdate();
            stmt = conn.prepareStatement("DELETE FROM CATEGORIES_OF_PRODUCTS WHERE " +
                    " CName = " + cName);
            stmt.executeUpdate();
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<CategoryDAL> getMySubCategoriesDAL(String name, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List <CategoryDAL> ret = new ArrayList<>();
        List <String> cNames = new ArrayList<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT SUB_CATEGORIES.Sub " +
                    "FROM SUB_CATEGORIES " +
                    "WHERE main = '"+name+"'");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                cNames.add(rs2.getString(1));
            }
        }
        catch (Exception e){

        }
        for (String n : cNames){
            CategoryDAL cat = createCategoryDALByName(n, conn);
            ret.add(cat);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public List<ProductDAL> getProductListDAL(String name, Connection conn) {

        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn.isClosed() ){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<ProductDAL> ret = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT PRODUCTS.* " +
                    "FROM CATEGORIES_OF_PRODUCTS INNER JOIN PRODUCTS ON CATEGORIES_OF_PRODUCTS.Barcode = PRODUCTS.Barcode " +
                    "WHERE CATEGORIES_OF_PRODUCTS.CName = '"+name+"'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Date date = new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString(6));
                ProductDAL product = new ProductDAL(rs.getInt(1),
                        rs.getString(2), rs.getInt(3), rs.getInt(4),
                        rs.getInt(5), date);
                ret.add(product);
            }
        }
        catch (Exception e){

        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;

    }
}
