package DataAccess;

import Business.BTDController;
import Business.Result;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DALController
{
    private static BTDController btdController;
    private static DALController thisOne;
    private List<Driver> drivers;
    private DeliveryArchive archive;
    private List<Truck> trucks;
    private List<Location> locations;
    private Sections sections;
    private Connection conn;

    private DALController() {
    }

    public static DALController getDTB() {
        if (thisOne == null) {
            thisOne = new DALController();
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

    public List<DALConstraint> loadALLConstraint()  {
        List<DALConstraint> constraints=new LinkedList<>();
        DALConstraint constraint;
        openConn();
        String sql = "SELECT* FROM Constraints";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
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

    public List<DALConstraint> loadConstraintByWeek(java.util.Date datestart, java.util.Date dateend)  {
        List<DALConstraint> constraints=new LinkedList<>();
        DALConstraint constraint;
        openConn();
        String sql = "SELECT* FROM Constraints WHERE AND date<? AND date>?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new Date(dateend.getYear(),dateend.getMonth(),dateend.getDay()));
            pstmt.setDate(1, new Date(datestart.getYear(),datestart.getMonth(),datestart.getDay()));
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

    public Result deleteConstraint(DALConstraint constraint) {
        openConn();
        String sql = "DELETE FROM Constraints WHERE cid=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, constraint.getCid());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return new Result(false, "deleting from data base has failed");
        }
        return new Result(true, "constraint deleted");
    }

    public DALConstraint loadConstraint(int cid)  {
        DALConstraint constraint=null;
        openConn();
        String sql = "SELECT* FROM Constraints WHERE cid=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cid);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                constraint=new DALConstraint();
                constraint.setCid(rs.getInt("cid"));
                constraint.setId(rs.getInt("wid"));
                constraint.setDate(rs.getDate("date"));
                constraint.setMorning(Boolean.valueOf(rs.getString("morning")));
                constraint.setReason(rs.getString("reason"));
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return constraint;
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

    public Result updateShift(DALShift shift, java.util.Date previous_date, boolean previous_morning, String previous_branch)
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
                    sql="DELETE FROM WorkersInShift WHERE worker_id=? and date=? and morning=? and branch=?";
                    statement = conn.prepareStatement(sql);
                    statement.setInt(1,worker);
                    statement.setDate(2,new java.sql.Date(previous_date.getTime()));
                    statement.setInt(3,previous_morning? 1 : 0);
                    statement.setString(4,previous_branch);
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

    public DALWorker selectWorker(int worker_id)
    {
        DALWorker worker=null;
        openConn();
        String sql = "SELECT* FROM Workers WHERE id=?";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement  = conn.prepareStatement(sql);
            statement.setInt(1, worker_id);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                worker=setDalWorkerFromResultSet(resultSet);
        } 
        catch (SQLException ignored) { }
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return worker;
    }

    public DALShift selectShift(java.util.Date date,boolean morning, String branch)
    {
        DALShift shift=null;
        openConn();
        String sql = "SELECT* FROM Shifts WHERE date=? and morning=? and branch=?";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement  = conn.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, morning ? 1 : 0);
            statement.setString(3, branch);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {

                shift = setDalShiftFromResultSet(resultSet);
                resultSet.close();
                List<Integer> workers_in_shift=new LinkedList<>();
                sql="SELECT worker_id FROM WorkersInShift where date=? and morning=? and branch=?";
                statement.setDate(1, new java.sql.Date(date.getTime()));
                statement.setInt(2, morning ? 1 : 0);
                statement.setString(3, branch);
                resultSet = statement.executeQuery();
                while (resultSet.next())
                    workers_in_shift.add(resultSet.getInt("worker_id"));
            }

        }
        catch (SQLException ignored) { }
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return shift;
    }

    private DALShift setDalShiftFromResultSet(ResultSet resultSet)
    {
        DALShift shift=new DALShift();
        try
        {
            shift.setDate(new java.util.Date(resultSet.getDate("date").getTime()));
            shift.setMorning(resultSet.getBoolean("morning"));
            shift.setBranchAddress(resultSet.getString("branch"));
            shift.setManager_id(resultSet.getInt("manager_id"));
        }
        catch (SQLException ignored){}
        return shift;
    }

    private DALWorker setDalWorkerFromResultSet(ResultSet resultSet)
    {
        DALWorker worker=new DALWorker();
        try
        {
            worker.setId(resultSet.getInt("id"));
            worker.setName(resultSet.getString("name"));
            worker.setSalary(resultSet.getInt("salary"));
            worker.setBank_account_number(resultSet.getInt("bank_account_number"));
            worker.setPension(resultSet.getInt("pension"));
            worker.setVacation_days(resultSet.getInt("vacation_days"));
            worker.setSick_days(resultSet.getInt("sick_days"));
            worker.setStart_date(new java.util.Date(resultSet.getDate("start_date").getTime()));
            worker.setRole(resultSet.getString("role"));
            worker.setBranchAddress(resultSet.getString("branchAddress"));
        } catch (SQLException ignored) { }
        return worker;
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

    public Truck loadTruck(int id) {
        Truck truck = null;
        openConn();
        String sql = "SELECT* FROM Trucks WHERE id=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                truck = new Truck();
                truck.setTruckNum(rs.getInt("id"));
                truck.setPlate(rs.getInt("plate"));
                truck.setMaxWeight(rs.getInt("maxWeight"));
                truck.setWeighNeto(rs.getInt("weightNeto"));
                truck.setType(rs.getString("type"));
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return truck;
    }

    public boolean saveTruck(Truck truck) {
        openConn();
        String sql = "INSERT INTO Trucks(id, plate, maxWeight, netoWeight, type) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, truck.getTruckNum());
            pstmt.setInt(2, truck.getPlate());
            pstmt.setInt(3 , truck.getMaxWeight());
            pstmt.setInt(4, truck.getWeighNeto());
            pstmt.setString(5, truck.getType());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Location loadLocation(String address) {
    Location location = null;
    openConn();
    String sql = "SELECT* FROM Locations WHERE address=?";
    try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        pstmt.setString(1, address);
        ResultSet rs  = pstmt.executeQuery();
        if (rs.next()) {
            location = new Location();
            location.setIsBranch(rs.getBoolean("isBranch"));
            location.setAddress(rs.getString("address"));
            location.setAssociate(rs.getString("associate"));
            location.setPhone(rs.getInt("phone"));
        }
        conn.close();
    } catch (SQLException e) {
        return null;
    }
    return location;
}

    public boolean saveLocation(Location location) {
        openConn();
        String sql = "INSERT INTO Locations(isBranch, address, phone, associate) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, location.getIsBranch());
            pstmt.setString(2, location.getAddress());
            pstmt.setInt(3 , location.getPhone());
            pstmt.setString(4, location.getAssociate());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Sections loadSections() {
        Sections sections = null;
        openConn();
        String sql = "SELECT* FROM Sections";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {\
            ResultSet rs  = pstmt.executeQuery();
            sections = new Sections();
            while (rs.next()) {
                int area = rs.getInt("area");
                String location = rs.getString("location");
                sections.addLocationToSection(area, location);
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return sections;
    }

    //todo: deliveries - not sure how to save multiple destinations
    public boolean saveDelivery(Delivery delivery) {
        openConn();
        String sql = "INSERT INTO Deliveries(id, departureDate, departureTime, truckNum, driver, source) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getId());
            pstmt.setDate(2, new Date(delivery.getDate().getYear(),delivery.getDate().getMonth(),delivery.getDate().getDay()));
            pstmt.setTime(3, new Time(delivery.getDate().getHours(),delivery.getDate().getMinutes(), 0));
            pstmt.setInt(4 , delivery.getTruckNum());
            pstmt.setString(5, delivery.getDriver());
            pstmt.setString(6, delivery.getSource());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Delivery loadDelivery(int id) {
        Delivery delivery = null;
        openConn();
        String sql = "SELECT* FROM Deliveries WHERE id=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                delivery = new Delivery();
                delivery.setId(rs.getInt("id"));
                delivery.setDate(rs.getDate("departureDate"));
                delivery.setDepartureTime(rs.getTime("departureTime"));
                delivery.setTruckNum(rs.getInt("truckNum"));
                delivery.setDriver(rs.getString("driver"));
                delivery.setSource(rs.getString("source"));
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return delivery;
    }

    //todo: deliveries - not sure how to save multiple supplies
    public boolean saveDoc(int deliveryID, DALDeliveryDoc doc) {
        openConn();
        String sql = "INSERT INTO DeliveryDocs(deliveryID, docID, destination, estimatedTimeOfArrival, estimatedDayOfArrival) VALUES(?,?,?.?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doc.getNum());
            pstmt.(2, );
            pstmt.setString(3 , doc.getDestination());
            pstmt.setTime(4, new Time(doc.getEstimatedTimeOfArrival().getHours(), doc.getEstimatedTimeOfArrival().getMinutes(), 0));
            pstmt.setDate(5, new Date((doc.getEstimatedDayOfArrival().getDay(), doc.getEstimatedDayOfArrival().getMonth(), doc.getEstimatedDayOfArrival().getYear()));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //todo: deliveries - IDKKKKKKK
    public DALDeliveryDoc loadDoc(int id) {
        DALDeliveryDoc doc = null;
        openConn();
        String sql = "SELECT* FROM DeliveryDocs WHERE id=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                doc = new DALDeliveryDoc();
                doc.setNum(rs.getInt("id"));
                doc.setEstimatedDayOfArrival(rs.getDate("estimatedDayOfArrival"));
                doc.setEstimatedTimeOfArrival(rs.getTime("estimatedTimeOfArrival"));
                doc.setDeliveryList(rs.getInt("truckNum"));
                doc.setDestination(rs.getString("destination"));
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return doc;
    }

    /*public void save(List<Business.Location> bLocations) {
        locations = new LinkedList<>();
        for (Business.Location l: bLocations ){
            locations.add(new Location(l.getAddress(), l.getPhone(), l.getAssociate()));
        }
    }*/

}