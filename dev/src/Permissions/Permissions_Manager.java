package Permissions;

import Permissions.DAL.Permissions_DAL;

import java.sql.Connection;

public class Permissions_Manager implements Permissions_API {

    Permissions_DAL permissions_dal;

    public Permissions_Manager() {
        this.permissions_dal = new Permissions_DAL();
    }

    public Permissions_DAL getPermissions_dal() {
        return permissions_dal;
    }

    public void setPermissions_dal(Permissions_DAL permissions_dal) {
        this.permissions_dal = permissions_dal;
    }

    @Override
    public int checkPermission(String username, String password) {
        return this.permissions_dal.checkPermission(username, password);
    }

    @Override
    public void connectToDB(Connection conn) {
        this.permissions_dal.connectToDB(conn);
    }
}
