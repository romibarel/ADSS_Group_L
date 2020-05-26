package Permissions.DAL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Permissions_DAL {

    Connection conn;

    public Permissions_DAL() { }

    public void connectToDB (Connection conn){
        this.conn = conn;
    }

    public int checkPermission(String username, String password) {
        int ret = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("Select Permission_Code FROM PERMISSIONS WHERE Username = '"+username+"' AND Password = '"+password+"'");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return ret;
    }
}
