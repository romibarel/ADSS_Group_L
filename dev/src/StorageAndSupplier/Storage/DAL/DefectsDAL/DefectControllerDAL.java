package StorageAndSupplier.Storage.DAL.DefectsDAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectControllerDAL {

    List<DefectDAL> defects;
    Date today;
    public DefectControllerDAL(){
        this.defects = new ArrayList<>();
        String pattern = "yyyy-mm-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        today = null;
        try {
            today = new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (Exception e) {}
    }

    public List<DefectDAL> getDefects() {
        return defects;
    }

    public void setDefects(List<DefectDAL> defects) {
        this.defects = defects;
    }

    public void addDefect (DefectDAL defectDAL, Connection conn){

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String expiration = sdf.format(new java.sql.Timestamp(defectDAL.getExpiration().getTime()));
            String insertToday = sdf.format(new java.sql.Timestamp(today.getTime()));
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DEFECT_REPORTS VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, expiration);
            stmt.setInt(2, defectDAL.getBarCode());
            stmt.setString(3, insertToday);
            stmt.setInt(4, defectDAL.getAmount());
            stmt.setString(5, defectDAL.getReason());
            stmt.setString(6, defectDAL.getCreator());
            stmt.setInt(7, defectDAL.getLocation());
            stmt.setDate(8, null);

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
