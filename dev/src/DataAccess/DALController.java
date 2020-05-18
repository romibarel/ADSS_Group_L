package DataAccess;

import Business.BTDController;
import Business.Result;
import sun.awt.image.ImageWatched;

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
    private List<DalTruck> dalTrucks;
    private List<DalLocation> dalLocations;
    private DalSections sections;
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

    public void initialize() {
        createTables();
        initLocations();
        initSections();
        initTrucks();
    }

    public void initLocations(){
        List<String> sqls = new LinkedList<>();
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Super Lee\", \"Haim\", 516);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Lee Office\", \"Romi\", 622);");   //DalDelivery Document
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Mega\", \"Michael\", 636);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Shufersal\", \"Inbar\", 163);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Costco\", \"Avi\", 123);\n");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Best Buy\", \"Gil\", 456);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"American Eagle\", \"Rom\", 789);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Max Stock\", \"Adir\", 147);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (true, \"Linux\", \"Adler\", 852);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (false, \"Asos\", \"Tony\", 963);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (false, \"Steve Madden\", \"Steve\", 9654);");
        sqls.add("INSERT INTO Locations (isBranch, address, associate, phone) VALUES (false, \"Gucci\", \"Gustavo\", 1654);");

        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){
//                return new Result(false, "Saving to data base has failed");
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initSections(){
        List<String> sqls = new LinkedList<>();
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Super Lee\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Lee Office\");");   //DalDelivery Document
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Mega\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Shufersal\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"Costco\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"Best Buy\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"American Eagle\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"Max Stock\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"Linux\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Asos\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (1, \"Steve Madden\");");
        sqls.add("INSERT INTO Sections (area, location) VALUES (2, \"Gucci\");");

        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){
//                return new Result(false, "Saving to data base has failed");
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTrucks(){
        List<String> sqls = new LinkedList<>();
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (1, 111, 1000, 4000, \"Mazda\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (2, 222, 1200, 7000, \"Toyota\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (3, 333, 1100, 5500, \"Mercedes\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (4, 123, 2000, 4000, \"Mazda\");");

        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){
//                return new Result(false, "Saving to data base has failed");
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void createTables(){
        List<String> sqls = new LinkedList<>();
        sqls.add("CREATE TABLE \"Deliveries\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"departureDate\"\tDATE,\n" +
                "\t\"departureTime\"\tTIME,\n" +
                "\t\"truckNum\"\tINTEGER,\n" +
                "\t\"driver\"\tTEXT,\n" +
                "\t\"source\"\tTEXT,\n" +
                "\t\"truckWeight\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\"),\n" +
                "\tFOREIGN KEY(\"driver\") REFERENCES \"Workers\"(\"name\"),\n" +
                "\tFOREIGN KEY(\"truckNum\") REFERENCES \"Trucks\"(\"id\")\n" +
                ");");
        sqls.add("CREATE TABLE \"DeliveryDocs\" (\n" +
                "\t\"deliveryID\"\tINTEGER NOT NULL,\n" +
                "\t\"docID\"\tINTEGER NOT NULL,\n" +
                "\t\"destination\"\tINTEGER NOT NULL,\n" +
                "\t\"estimatedTimeOfArrival\"\tTIME NOT NULL,\n" +
                "\t\"estimatedDayOfArrival\"\tDATE NOT NULL,\n" +
                "\tPRIMARY KEY(\"docID\"),\n" +
                "\tFOREIGN KEY(\"deliveryID\") REFERENCES \"Delivery \"(\"id\"),\n" +
                "\tFOREIGN KEY(\"destination\") REFERENCES \"Locations\"(\"address\")\n" +
                ");");   //DalDelivery Document
        sqls.add("CREATE TABLE \"Locations\" (\n" +
                "\t\"isBranch\"\tBOOLEAN NOT NULL,\n" +
                "\t\"address\"\tTEXT NOT NULL,\n" +
                "\t\"associate\"\tTEXT,\n" +
                "\t\"phone\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"address\")\n" +
                ");");
        sqls.add("CREATE TABLE \"Sections\" (\n" +
                "\t\"area\"\tINTEGER,\n" +
                "\t\"location\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"location\"),\n" +
                "\tFOREIGN KEY(\"location\") REFERENCES \"Locations\"(\"address\")\n" +
                ");");
        sqls.add("CREATE TABLE \"Supply\" (\n" +
                "\t\"docNum\"\tINTEGER,\n" +
                "\t\"destination\"\tTEXT,\n" +
                "\t\"supName\"\tTEXT,\n" +
                "\t\"quant\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"destination\") REFERENCES \"Locations\"(\"address\"),\n" +
                "\tPRIMARY KEY(\"docNum\",\"destination\")\n" +
                ");");
        sqls.add("CREATE TABLE \"Trucks\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"plate\"\tINTEGER,\n" +
                "\t\"maxWeight\"\tINTEGER,\n" +
                "\t\"netoWeight\"\tINTEGER,\n" +
                "\t\"type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");");



        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){
//                return new Result(false, "Saving to data base has failed");
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Result deleteShift(java.util.Date date, boolean morning, String branch)
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

    // returns the id of a worker in a selected role and branch that is available to work in a selected date
    public int select_available_worker_id(java.util.Date date, boolean morning, String branch,String role)
    {
        int id=-1;
        openConn();
        String sql;
        sql = "SELECT id from Workers Where id not in" +
              "(Select id From Workers join Constraints on Workers.id=Constraints.wid Where date=? and morning=? and branchAddress=?)"+
               "and branchAddress=? and role=? Limit 1";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(date.getTime()));
            statement.setInt(2, morning ? 1 : 0);
            statement.setString(3, branch);
            statement.setString(4,branch);
            statement.setString(5,role);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                id=resultSet.getInt("id");
        }
        catch (SQLException ignored) { }
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return id;
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
    public void save(List<Business.Driver> drivers, Business.DeliveryArchive archive, List<Business.DalTruck> dalTrucks, List<Business.DalLocation> dalLocations, Business.DalSections sections) {
        this.drivers = save(drivers);
        this.archive = save(archive);
        this.dalTrucks = save(dalTrucks);
        this.dalLocations = save(dalLocations);
        this.sections = save(sections);
    }*/

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public DalTruck loadTruck(int id) {
        DalTruck dalTruck = null;
        openConn();
        String sql = "SELECT* FROM Trucks WHERE id=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                dalTruck = new DalTruck();
                dalTruck.setTruckNum(rs.getInt("id"));
                dalTruck.setPlate(rs.getInt("plate"));
                dalTruck.setMaxWeight(rs.getInt("maxWeight"));
                dalTruck.setWeighNeto(rs.getInt("weightNeto"));
                dalTruck.setType(rs.getString("type"));
            }
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return dalTruck;
    }

    public boolean saveTruck(DalTruck dalTruck) {
        openConn();
        String sql = "INSERT INTO Trucks(id, plate, maxWeight, netoWeight, type) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dalTruck.getTruckNum());
            pstmt.setInt(2, dalTruck.getPlate());
            pstmt.setInt(3 , dalTruck.getMaxWeight());
            pstmt.setInt(4, dalTruck.getWeighNeto());
            pstmt.setString(5, dalTruck.getType());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public DalLocation loadLocation(String address) {
    DalLocation location = null;
    openConn();
    String sql = "SELECT* FROM Locations WHERE address=?";
    try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        pstmt.setString(1, address);
        ResultSet rs  = pstmt.executeQuery();
        if (rs.next()) {
            location = new DalLocation();
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

    public boolean saveLocation(DalLocation location) {
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

    public DalSections loadSections() {
        DalSections sections = null;
        openConn();
        String sql = "SELECT* FROM Sections";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();
            sections = new DalSections();
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

//
//    id
//            date
//    departureTime
//            truckNum
//    driver
//            source
//    docToLocation

    public boolean saveDelivery(DalDelivery delivery) {
        openConn();
        String sql = "INSERT INTO Deliveries(id, departureDate, departureTime, truckNum, driver, source, truckWeight) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getId());
            pstmt.setDate(2, new Date(delivery.getDate().getYear(),delivery.getDate().getMonth(),delivery.getDate().getDay()));
            pstmt.setTime(3, new Time(delivery.getDate().getHours(),delivery.getDate().getMinutes(), 0));
            pstmt.setInt(4 , delivery.getTruckNum());
            pstmt.setString(5, delivery.getDriver());
            pstmt.setString(6, delivery.getSource());
//            pstmt.setString(6, delivery.getTruckWeight());
//            delivery.getDriver()
//            delivery.getDocs()
//            delivery.getDestinations()
//                    todo haim

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public DalDelivery loadDelivery(int id) {
        DalDelivery delivery = null;
        openConn();
        String sql = "SELECT* FROM Deliveries WHERE id=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                delivery = new DalDelivery();
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

    public boolean saveDoc(int deliveryID, DALDeliveryDoc doc) {
        openConn();
        String sql = "INSERT INTO DeliveryDocs(deliveryID, docID, destination, estimatedTimeOfArrival, estimatedDayOfArrival) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, doc.getNum());
            pstmt.setString(3 , doc.getDestination());
            pstmt.setTime(4, new Time(doc.getEstimatedTimeOfArrival().getHours(), doc.getEstimatedTimeOfArrival().getMinutes(), 0));
            pstmt.setDate(5, new Date(doc.getEstimatedDayOfArrival().getDay(), doc.getEstimatedDayOfArrival().getMonth(), doc.getEstimatedDayOfArrival().getYear()));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        for (DalSupply dalSupply : doc.getDeliveryList()){
            saveSupply(doc.getNum(), doc.getDestination(), dalSupply);
        }
        return true;
    }

    public DALDeliveryDoc loadDoc(int docNum) {
        DALDeliveryDoc doc = null;
        openConn();
        String sql = "SELECT* FROM DeliveryDocs WHERE docNum=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docNum);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                doc = new DALDeliveryDoc();
                doc.setNum(rs.getInt("docNum"));
                doc.setDestination(rs.getString("destination"));
                doc.setEstimatedTimeOfArrival(rs.getTime("estimatedTimeOfArrival"));
                doc.setEstimatedDayOfArrival(rs.getTime("setEstimatedDayOfArrival"));
            }
            conn.close();
            List<DalSupply> supplies = loadSupplies(doc.getNum());
            doc.setDeliveryList(supplies);

        } catch (SQLException e) {
            return null;
        }
        return doc;
    }

    public boolean saveSupply(int docNum, String destination, DalSupply dalSupply) {
        openConn();
        String sql = "INSERT INTO DeliveryDocs(docID, destination, supName, quant) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docNum);
            pstmt.setString(2, destination);
            pstmt.setString(3, dalSupply.getName());
            pstmt.setInt(4 , dalSupply.getQuant());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<DalSupply> loadSupplies(int docNum) {
        List<DalSupply> supplies = new LinkedList<>();
        openConn();
        String sql = "SELECT* FROM Supplies WHERE docNum=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docNum);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                DalSupply dalSupply = new DalSupply();
                dalSupply.setName(rs.getString("supName"));
                dalSupply.setQuant(rs.getInt("quant"));
                supplies.add(dalSupply);
            }
            conn.close();

        } catch (SQLException e) {
            return null;
        }
        return supplies;
    }

    public List<DALWorker> select_available_workers(java.util.Date date, boolean morning, String role, String branch)
    {
        List<DALWorker> workers=new LinkedList<>();
        openConn();
        String sql;
        sql = "SELECT * from Workers Where id not in" +
              "(Select id From Workers join Constraints on Workers.id=Constraints.wid Where date=? and morning=? and branchAddress=?)" +
              "and branchAddress=? and role=?";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(date.getTime()));
            statement.setInt(2,morning ? 1 : 0);
            statement.setString(3,branch);
            statement.setString(4,branch);
            statement.setString(5,role);
            resultSet = statement.executeQuery();
            while (resultSet.next())
                workers.add(setDalWorkerFromResultSet(resultSet));
        }
        catch (SQLException ignored) { }
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return workers;
    }

    public boolean is_worker_scheduled(int worker_id)
    {
        boolean scheduled=true;
        openConn();
        String sql="Select count(worker_id) From WorkersInShift Where worker_id=? GROUP By worker_id";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,worker_id);
            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                sql="Select count(manager_id) From Shifts Where manager_id=? GROUP By manager_id";
                statement = conn.prepareStatement(sql);
                statement.setInt(1,worker_id);
                if (!resultSet.next())
                    scheduled=false;
            }

        }
        catch (SQLException ignored) {}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return scheduled;
    }


    /*public void save(List<Business.DalLocation> bLocations) {
        dalLocations = new LinkedList<>();
        for (Business.DalLocation l: bLocations ){
            dalLocations.add(new DalLocation(l.getAddress(), l.getPhone(), l.getAssociate()));
        }
    }*/

}
