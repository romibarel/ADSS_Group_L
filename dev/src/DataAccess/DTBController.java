package DataAccess;

import Business.BTDController;
import Business.BTIController;
import Business.Result;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DTBController {
    private static BTDController btdController;
    private static DTBController thisOne;
    private List<Driver> drivers;
    private DeliveryArchive archive;
    private List<Truck> trucks;
    private List<Location> locations;
    private Sections sections;
    private Connection conn;

    private DTBController() {
    }

    public static DTBController getDTB() {
        if (thisOne == null) {
            thisOne = new DTBController();
            btdController = BTDController.getBTD();
        }
        return thisOne;
    }

    private void openConn(){
        try {
            String url = "jdbc:sqlite:"+new File("dev\\src\\DataAccess\\Database.db").getAbsolutePath();
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Result saveConstraint(DALConstraint constraint)  {
        openConn();
        String sql = "INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, constraint.getCid());
            pstmt.setInt(2, constraint.getId());
            pstmt.setDate(3, new Date(constraint.getDate().getYear(),constraint.getDate().getMonth(),constraint.getDate().getDay()));
            pstmt.setString(4, String.valueOf(constraint.isMorning()));
            pstmt.setString(5, constraint.getReason());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return new Result(false, "Saving to data base has failed");
        }
        return new Result(true, "constraint saved to db");
    }

    public Result updateConstraint(DALConstraint constraint) {
        openConn();
        String sql = "UPDATE Constraints SET wid=?, date=?, morning=?, reason=? WHERE cid=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, constraint.getId());
            pstmt.setDate(2, new Date(constraint.getDate().getYear(),constraint.getDate().getMonth(),constraint.getDate().getDay()));
            pstmt.setString(3, String.valueOf(constraint.isMorning()));
            pstmt.setString(4, constraint.getReason());
            pstmt.setInt(5, constraint.getCid());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return new Result(false, "updating to data base has failed");
        }
        return new Result(true, "constraint updated");
    }

    public List<DALConstraint> loadConstraint(int id, java.util.Date date, boolean morning)  {
        List<DALConstraint> constraints=new LinkedList<>();
        DALConstraint constraint;
        openConn();
        String sql = "SELECT* FROM Constraints WHERE wid=? AND date=? AND morning=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setDate(2, new Date(date.getYear(),date.getMonth(),date.getDay()));
            pstmt.setString(3, String.valueOf(morning));
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                constraint=new DALConstraint();
                constraint.setCid(rs.getInt("cid"));
                constraint.setId(rs.getInt("wid"));
                constraint.setDate(rs.getDate("date"));
                constraint.setMorning(Boolean.valueOf(rs.getString("morning")));
                constraint.setReason(rs.getString("reason"));
                constraints.add(constraint);
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return constraints;
    }

    public Result getMax()  {
        int ret=0;
        openConn();
        String sql = "SELECT MAX(cid) AS 'max_cid'" +
                "FROM Constraints";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                ret=rs.getInt("max_cid");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("whyyy");
            System.out.println(e.getMessage());
            return new Result(false, "failed");
        }
        return new Result(true, String.valueOf(ret+1));
    }

/*
    public void save(List<Business.Driver> drivers, Business.DeliveryArchive archive, List<Business.Truck> trucks, List<Business.Location> locations, Business.Sections sections) {
        this.drivers = save(drivers);
        this.archive = save(archive);
        this.trucks = save(trucks);
        this.locations = save(locations);
        this.sections = save(sections);
    }*/

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public DeliveryArchive getArchive() {
        return archive;
    }

    public void setArchive(DeliveryArchive archive) {
        this.archive = archive;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Sections getSections() {
        return sections;
    }

    public void setSections(Sections sections) {
        this.sections = sections;
    }

    /*public void save(List<Business.Location> bLocations) {
        locations = new LinkedList<>();
        for (Business.Location l: bLocations ){
            locations.add(new Location(l.getAddress(), l.getPhone(), l.getAssociate()));
        }
    }*/

}
