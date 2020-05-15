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

    public Result insertWorker(DALWorker worker)
    {
        Result result;
        openConn();
        String sql = "INSERT INTO Workers(name,id,bank_account_number,salary,pension,vacation_days,sick_days,start_date,role,branchAddress) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1,worker.getName());
            statement.setInt(2,worker.getId());
            statement.setInt(3,worker.getBank_account_number());
            statement.setInt(4,worker.getSalary());
            statement.setInt(5,worker.getPension());
            statement.setInt(6,worker.getVacation_days());
            statement.setInt(7,worker.getSick_days());
            statement.setDate(8, new java.sql.Date(worker.getStart_date().getTime()));
            statement.setString(9,worker.getRole());
            statement.setString(10,worker.getBranchAddress());
            statement.executeUpdate();
            result=new Result(true, "worker inserted");
        }
        catch (SQLException e) { result= new Result(false, "worker insert failed"); }
        finally {
            try {
                conn.close();
            } catch (SQLException ignored) { }
        }

        return result;
    }

    public Result updateWorker(DALWorker worker)
    {
        Result result;
        openConn();
        String sql = "UPDATE Workers SET name=?,bank_account_number=?,salary=?,pension=?,vacation_days=?,sick_days=?,start_date=?,role=?,branchAddress=? Where id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1,worker.getName());
            statement.setInt(2,worker.getBank_account_number());
            statement.setInt(3,worker.getSalary());
            statement.setInt(4,worker.getPension());
            statement.setInt(5,worker.getVacation_days());
            statement.setInt(6,worker.getSick_days());
            statement.setDate(7, new java.sql.Date(worker.getStart_date().getTime()));
            statement.setString(8,worker.getRole());
            statement.setString(9,worker.getBranchAddress());
            statement.setInt(10,worker.getId());
            statement.executeUpdate();
            result=new Result(true, "worker updated");
        }
        catch (SQLException e) { result= new Result(false, "update worker failed"); }
        finally {
            try {
                conn.close();
            } catch (SQLException ignored) { }
        }
        return result;
    }

    public Result deleteWorker(int id)
    {
        Result result;
        openConn();
        String sql = "Delete FROM Workers where id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1,id);
            statement.executeUpdate();
            result=new Result(true, "worker deleted");
        } catch (SQLException e) { result= new Result(false, "Delete worker failed"); }
        finally
        {
            try {
                conn.close();
            } catch (SQLException ignored) { }
        }
        return result;
    }

    public Result insertShift(DALShift shift)
    {
        Result result;
        openConn();
        try
        {
            conn.setAutoCommit(false);
            String sql="INSERT INTO Shifts(date,morning,manager_id,branch) VALUES(?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
            statement.setInt(2,shift.isMorning() ? 1 : 0);
            statement.setInt(3,shift.getManager_id());
            statement.setString(4,shift.getBranchAddress());
            statement.executeUpdate(sql);
            for (Integer worker: shift.getWorkers())
            {
                sql="INSERT INTO WorkersInShift(date,morning,worker_id) VALUES(?,?,?)";
                statement = conn.prepareStatement(sql);
                statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
                statement.setInt(2, shift.isMorning() ? 1 : 0);
                statement.setInt(3,worker);
                statement.executeUpdate(sql);
            }
            conn.commit();
            result=new Result(true,"shift inserted");

        }
        catch (Exception e)
        {
            try {
                conn.rollback();
            } catch (SQLException ex) { ex.printStackTrace(); }
            result= new Result(false,"insert shift failed");
        }
        finally
        {
            try
            {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {}
        }
        return result;
    }

    public Result updateShift(DALShift shift,Date previous_date,boolean previous_morning,String previous_branch)
    {
        Result result;
        openConn();
        try
        {
            LinkedList<Integer> workers_before_update=new LinkedList<>();

            //-----------------------get all workers in this shift before update---------------------//
            String sql="SELECT id FROM WorkersInShift WHERE date=? and morning=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
            statement.setInt(2,shift.isMorning() ? 1 : 0);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next())
                workers_before_update.add(resultSet.getInt("id"));
            resultSet.close();

            conn.setAutoCommit(false);
            //---------------------delete all workers that was removed from the shift---------------//
            for (Integer worker : workers_before_update)
            {
                if (!shift.getWorkers().contains(worker))
                {
                    sql="DELETE FROM WorkersInShift WHERE worker_id=?";
                    statement = conn.prepareStatement(sql);
                    statement.setInt(1,worker);
                    statement.executeUpdate(sql);
                }
            }

            //------------------update the shift -------------------------------------------------//

            sql="UPDATE Shifts SET date=?,morning=?,manager_id=?,branch=? Where date=? and morning=? and branch=? ";
            statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
            statement.setInt(2,shift.isMorning()? 1 : 0);
            statement.setInt(3 , shift.getManager_id());
            statement.setString(4,shift.getBranchAddress());
            statement.setDate(5,new java.sql.Date(previous_date.getTime()));
            statement.setInt(6,previous_morning? 1 : 0);
            statement.setString(7,previous_branch);
            statement.executeUpdate(sql);

            //---------------------insert the new workers in shift-----------------------------------//
            for (Integer worker_id : shift.getWorkers())
            {
                if (!workers_before_update.contains(worker_id))
                {
                    sql="INSERT INTO WorkersInShift(date,morning,worker_id,branch) VALUES(?,?,?,?)";
                    statement = conn.prepareStatement(sql);
                    statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
                    statement.setInt(2,shift.isMorning() ? 1 : 0);
                    statement.setInt(3,worker_id);
                    statement.setString(4,shift.getBranchAddress());
                    statement.executeUpdate(sql);
                }
            }
            conn.commit();
            result = new Result(true,"shift updated");
        }
        catch (Exception e)
        {
            try {
                conn.rollback();
            } catch (SQLException ignored) {  }
            result= new Result(false,"update shift failed");
        }
        finally
        {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) { }
        }
        return result;
    }

    public Result deleteShift(Date date, boolean morning, String branch)
    {
        Result result;
        openConn();
        String sql = "Delete FROM Shifts WHERE morning=? and branch=? and date=?";
        try
        {
            //delete shift
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,morning? 1 : 0);
            statement.setString(2,branch);
            statement.setDate(3, new java.sql.Date(date.getTime()));
            statement.executeUpdate();

            //delete workers in shift
            sql = "Delete FROM WorkersInShift WHERE morning=? and branch=? and date=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,morning? 1 : 0);
            statement.setString(2,branch);
            statement.setDate(3, new java.sql.Date(date.getTime()));
            statement.executeUpdate();

            conn.commit();
            result=new Result(true, "shift deleted");
        }
        catch (SQLException e) { result= new Result(false, "Delete shift failed"); }
        finally
        {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) { }
        }
        return result;
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
