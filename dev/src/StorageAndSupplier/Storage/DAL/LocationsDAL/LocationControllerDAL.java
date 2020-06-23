package StorageAndSupplier.Storage.DAL.LocationsDAL;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LocationControllerDAL {
    private Map<Integer , Map<Date, Map<Integer, Integer>>> productsLocationDAL; // <barcode, <expirationDate, <location, quantity>
    private Map<Integer , String> locationsDAL; // <locationNumber , locationName>

    public LocationControllerDAL(){
        this.productsLocationDAL = new HashMap<Integer, Map<Date, Map<Integer, Integer>>>();
        this.locationsDAL = new HashMap();
    }

    public Map<Integer, Map<Date, Map<Integer, Integer>>> getProductsLocationDAL(Connection conn) {

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        //return productsLocationDAL;
        Map <Integer, Map<Date, Map<Integer, Integer>>> ret = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * " +
                    "FROM PRODUCT_LOCATIONS " +
                    "group by Barcode,Expiration,Location_id " +
                    "order by Barcode,Expiration,Location_id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int barcode = rs.getInt(1);
               // Date expiration = rs.getDate(2);
                Date expiration = new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString(2));
                int location_id = rs.getInt(3);
                int quantity = rs.getInt(4);
                if (ret.containsKey(barcode)){  //has already this barcode
                    if (ret.get(barcode).containsKey(expiration)){
                        if (ret.get(barcode).get(expiration).containsKey(location_id)){
                            //never reach here because key is unique
                        }
                        else{
                            ret.get(barcode).get(expiration).put(location_id, quantity);
                        }
                    }
                    else{
                        Map <Integer, Integer> thirdMap = new HashMap<>();
                        thirdMap.put(location_id,quantity);
                        ret.get(barcode).put(expiration,thirdMap);
                    }
                }
                else{
                    Map <Date, Map<Integer, Integer>> secondMap = new HashMap<>();
                    Map <Integer, Integer> thirdMap = new HashMap<>();
                    thirdMap.put(location_id,quantity);
                    secondMap.put(expiration,thirdMap);
                    ret.put(barcode, secondMap);
                }
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

    public void setProductsLocationDAL(Map<Integer, Map<Date, Map<Integer, Integer>>> productsLocationDAL) {
        this.productsLocationDAL = productsLocationDAL;
    }

    public Map<Integer, String> getLocationsDAL(Connection conn) {

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        //return locationsDAL;
        Map <Integer, String> ret= new HashMap<>();
        try {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * " +
                    "FROM Locations ");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                ret.put(rs2.getInt(1), rs2.getString(2));
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

    public void setLocationsDAL(Map<Integer, String> locationsDAL) {
        this.locationsDAL = locationsDAL;
    }

    public void updateProductsQuantityInLocation(int barCode, Date expirationDate, int location, int quantity, Connection conn){
        //if (this.productsLocationDAL.get(barCode) == null){ //if product doesn't exists
        //    productsLocationDAL.put(barCode, new HashMap<>());
        //    productsLocationDAL.get(barCode).put(expirationDate, new HashMap<>());
        //    productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        //}
        //else if (this.productsLocationDAL.get(barCode).get(expirationDate) == null){ //product exists but expiration date doesn't exists
        //    productsLocationDAL.get(barCode).put(expirationDate, new HashMap<Integer, Integer>());
        //    productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        //}
        //else if (this.productsLocationDAL.get(barCode).get(expirationDate).get(location)==null){    //product exists, exp date exists, location doesn't exists
        //    productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        //}
        //else{
        //    this.productsLocationDAL.get(barCode).get(expirationDate).replace(location, quantity);
        //}

        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e){

        }

        boolean updateOrInsert = false; //update = true, insert = false
        //TODO: The date isn't good.
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String ts = sdf.format(new java.sql.Timestamp(expirationDate.getTime()));

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCT_LOCATIONS " +
                    " WHERE Barcode = " + barCode +
                    " AND Expiration = '" + ts +"'" +
                    " AND Location_id = " + location);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){updateOrInsert = true;}
            if(updateOrInsert) {
                PreparedStatement stmt2 = conn.prepareStatement("UPDATE PRODUCT_LOCATIONS SET " +
                        " Location_id = " + location + " AND Quantity = " + quantity + " WHERE Barcode = " + barCode +
                        " AND Expiration = '" + ts + "'"
                );


                stmt2.executeUpdate();
            }
            else{
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO PRODUCT_LOCATIONS VALUES(?,?,?,?)");
                stmt2.setInt(1, barCode);
                stmt2.setString(2, ts);
                stmt2.setInt(3, location);
                stmt2.setInt(4, quantity);
                stmt2.executeUpdate();
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
    }
}
