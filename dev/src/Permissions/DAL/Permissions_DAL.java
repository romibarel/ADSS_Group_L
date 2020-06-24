package Permissions.DAL;
import java.sql.*;

public class Permissions_DAL {

    Connection conn;

    public Permissions_DAL() { }

    public void connectToDB (Connection conn){
        this.conn = conn;
    }

    public int checkPermission(String username, String password) {
        int ret = 0;
        try {
            if (conn.isClosed()){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:Database.db";
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("Select Permission_Code FROM PERMISSIONS WHERE Username = '"+username+"' AND Password = '"+password+"'");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            }
            conn.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return ret;
    }
}
