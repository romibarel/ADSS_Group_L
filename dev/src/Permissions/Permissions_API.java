package Permissions;
import java.sql.Connection;

public interface Permissions_API {

    int checkPermission(String username, String password);  //return the permission code if exists, otherwise return 0

    void connectToDB (Connection conn);

}
