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
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:Database.db";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void initialize() {
        if (check_if_database_empty())
        {
            createTables();
            initLocations();
            initSections();
            initTrucks();
            initWorkers();
            initShifts();
            initShiftsWorkers();
            initConstraints();
        }
    }

    private boolean check_if_database_empty()
    {
        boolean return_value=true;
        openConn();
        String sql;
        sql = "SELECT * FROM Locations";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            resultSet=statement.executeQuery();
            if (resultSet.next()) return_value=false;
        }
        catch (Exception ignored) {}
        finally
        {
            try {
                if (resultSet!=null)
                    resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return return_value;
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
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (1, 111, 5000, 4000, \"Mazda\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (2, 222, 10000, 7000, \"Toyota\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (3, 333, 8000, 5500, \"Mercedes\");");
        sqls.add("INSERT INTO Trucks (id, plate, maxWeight, netoWeight, type) VALUES (4, 123, 6500, 4000, \"Mazda\");");

        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();
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

    public void initWorkers(){
        List<String> sqls = new LinkedList<>();
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (13,\"avi\",1000,123,3,12,12,1577829600000,\"manager\", \"Super Lee\")");
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (14,\"inbar\",1500,105,9,30,12,1558990800000,\"manager\",\"Costco\")");
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (17,\"haim\",2000,189,6,25,30,1525294800000,\"cashier\",\"Super Lee\")");
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (15,\"romi\",2000,189,6,25,30,1525294800000,\"storekeeper\", \"Super Lee\")");
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (16,\"gil\",2000,189,6,25,30,1525294800000,\"driver\",\"Costco\");");
        sqls.add("Insert Into Workers (id,name,salary,bank_account_number,pension,vacation_days,sick_days,start_date,role,branchAddress) Values (18,\"lala\",2000,189,6,25,30,1525294800000,\"driver\",\"Mega\");");
        sqls.add("Insert Into DriverLicences (driver_id, license) VALUES(16,\"Mercedes\")");
        sqls.add("Insert Into DriverLicences (driver_id, license) VALUES(18,\"Toyota\")");
        sqls.add("Insert Into DriverLicences (driver_id, license) VALUES(18,\"Mazda\")");
        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){

            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initShifts(){
        List<String> sqls = new LinkedList<>();
        sqls.add("Insert Into Shifts (date,morning,branch,manager_id) Values (1579557600000,1,\"Super Lee\",13);");
        sqls.add("Insert Into Shifts (date,morning,branch,manager_id) Values (1579557600000,0,\"Costco\",14);");
        sqls.add("Insert Into Shifts (date,morning,branch,manager_id) Values (1579557600000,1,\"Costco\",14);");
        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){

            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initShiftsWorkers(){
        List<String> sqls = new LinkedList<>();
        sqls.add("Insert Into WorkersInShift (date,morning,worker_id,branch) Values (1579557600000,1,15,\"Super Lee\");");
        sqls.add("Insert Into WorkersInShift (date,morning,worker_id,branch) Values (1579557600000,1,17,\"Super Lee\");");
        sqls.add("Insert Into WorkersInShift (date,morning,worker_id,branch) Values (1579557600000,0,16,\"Costco\");");
        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){

            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initConstraints(){
        List<String> sqls = new LinkedList<>();
        sqls.add("INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(1,13,1590613200000,\"false\",\"wedding\");");
        sqls.add("INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(2,13,1590699600000,\"true\",\"wedding\");");
        sqls.add("INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(3,14,1593378000000,\"false\",\"doctor\");");
        sqls.add("INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(4,15,1599598800000,\"false\",\"vacation\");");
        sqls.add("INSERT INTO Constraints(cid, wid, date, morning, reason) VALUES(5,15,1599598800000,\"true\",\"vacation\");");
        openConn();
        for (String sqlCommand : sqls){
            try (PreparedStatement statement = conn.prepareStatement(sqlCommand)) {
                statement.execute();    //todo which one for create??
//                statement.executeQuery();

            }
            catch (Exception exception){

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
        sqls.add("CREATE TABLE IF NOT EXISTS \"Deliveries\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"departureDate\"\tDATE,\n" +
                "\t\"departureTime\"\tTIME,\n" +
                "\t\"truckNum\"\tINTEGER,\n" +
                "\t\"driver\"\tTEXT,\n" +
                "\t\"source\"\tTEXT,\n" +
                "\t\"truckWeight\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\"),\n" +
                "\tFOREIGN KEY(\"truckNum\") REFERENCES \"Trucks\"(\"id\")\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS \"DeliveryDocs\" (\n" +
                "\t\"deliveryID\"\tINTEGER NOT NULL,\n" +
                "\t\"docID\"\tINTEGER NOT NULL,\n" +
                "\t\"destination\"\tINTEGER NOT NULL,\n" +
                "\t\"estimatedTimeOfArrival\"\tTIME NOT NULL,\n" +
                "\t\"estimatedDayOfArrival\"\tDATE NOT NULL,\n" +
                "\tPRIMARY KEY(\"docID\"),\n" +
                "\tFOREIGN KEY(\"deliveryID\") REFERENCES \"Delivery \"(\"id\"),\n" +
                "\tFOREIGN KEY(\"destination\") REFERENCES \"Locations\"(\"address\")\n" +
                ");");   //DalDelivery Document
        sqls.add("CREATE TABLE IF NOT EXISTS \"Locations\" (\n" +
                "\t\"isBranch\"\tBOOLEAN NOT NULL,\n" +
                "\t\"address\"\tTEXT NOT NULL,\n" +
                "\t\"associate\"\tTEXT,\n" +
                "\t\"phone\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"address\")\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS\"Sections\" (\n" +
                "\t\"area\"\tINTEGER,\n" +
                "\t\"location\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"location\"),\n" +
                "\tFOREIGN KEY(\"location\") REFERENCES \"Locations\"(\"address\")\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS\"Supply\" (\n" +
                "\t\"docNum\"\tINTEGER,\n" +
                "\t\"destination\"\tTEXT,\n" +
                "\t\"supName\"\tTEXT,\n" +
                "\t\"quant\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"destination\") REFERENCES \"Locations\"(\"address\"),\n" +
                "\tPRIMARY KEY(\"docNum\",\"destination\")\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS\"Trucks\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"plate\"\tINTEGER,\n" +
                "\t\"maxWeight\"\tINTEGER,\n" +
                "\t\"netoWeight\"\tINTEGER,\n" +
                "\t\"type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ");");

        sqls.add("CREATE TABLE IF NOT EXISTS `Workers` (\n" +
                "\t`id`\tINTEGER,\n" +
                "\t`name`\tTEXT,\n" +
                "\t`salary`\tINTEGER,\n" +
                "\t`bank_account_number`\tINTEGER,\n" +
                "\t`pension`\tINTEGER,\n" +
                "\t`vacation_days`\tINTEGER,\n" +
                "\t`sick_days`\tINTEGER,\n" +
                "\t`start_date`\tDATE,\n" +
                "\t`role`\tTEXT,\n" +
                "\t`branchAddress`\tTEXT,\n" +
                "\tPRIMARY KEY(`id`)\n" +
                ");");

        sqls.add("CREATE TABLE IF NOT EXISTS `Shifts` (\n" +
                "\t`date`\tDATE,\n" +
                "\t`morning`\tINTEGER,\n" +
                "\t`branch`\tTEXT,\n" +
                "\t`manager_id`\tINTEGER,\n" +
                "\tFOREIGN KEY(`manager_id`) REFERENCES `Workers`(`id`),\n" +
                "\tPRIMARY KEY(`date`,`morning`,`branch`)\n" +
                ");");

        sqls.add("CREATE TABLE `WorkersInShift` (\n" +
                "\t`date`\tDATE,\n" +
                "\t`morning`\tINTEGER,\n" +
                "\t`worker_id`\tINTEGER,\n" +
                "\t`branch`\tTEXT,\n" +
                "\tFOREIGN KEY(`date`,`morning`,`branch`) REFERENCES `Shifts`(`date`,`morning`,`branch`),\n" +
                "\tPRIMARY KEY(`date`,`morning`,`worker_id`,`branch`)\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS `Constraints` (\n" +
                "\t`cid`\tINTEGER,\n" +
                "\t`wid`\tINTEGER,\n" +
                "\t`date`\tDATE,\n" +
                "\t`morning`\tTEXT,\n" +
                "\t`reason`\tTEXT,\n" +
                "\tFOREIGN KEY(`wid`) REFERENCES `Workers`(`id`),\n" +
                "\tPRIMARY KEY(`cid`)\n" +
                ");");
        sqls.add("CREATE TABLE IF NOT EXISTS \"DriverLicences\" (\n" +
                "\t\"driver_id\"\tINTEGER,\n" +
                "\t\"license\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"driver_id\") REFERENCES \"Workers\"(\"id\"),\n" +
                "\tPRIMARY KEY(\"driver_id\",\"license\")\n" +
                ");\n");

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
            pstmt.setDate(3,new java.sql.Date(constraint.getDate().getTime()));
            pstmt.setString(4, String.valueOf(constraint.isMorning()));
            pstmt.setString(5, constraint.getReason());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new Result(false, "Saving to data base has failed");
        }
        return new Result(true, "constraint saved to db");
    }

    public Result updateConstraint(DALConstraint constraint) {
        openConn();
        String sql = "UPDATE Constraints SET wid=?, date=?, morning=?, reason=? WHERE cid=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, constraint.getId());
            pstmt.setDate(2, new java.sql.Date(constraint.getDate().getTime()));
            pstmt.setString(3, String.valueOf(constraint.isMorning()));
            pstmt.setString(4, constraint.getReason());
            pstmt.setInt(5, constraint.getCid());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            pstmt.setDate(2, new java.sql.Date(date.getTime()));
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
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            pstmt.setDate(1,new java.sql.Date( dateend.getTime()));
            pstmt.setDate(1, new java.sql.Date(datestart.getTime()));
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
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        return constraint;
    }

    public int getMax()  {
        int ret=0;
        openConn();
        String sql = "SELECT MAX(cid) AS max_cid" +
                " FROM Constraints";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                ret=rs.getInt("max_cid");
            }
            conn.close();
        } catch (SQLException e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        }
        return ret;
    }

    //--------------------------------workers--------------------------------
    public Result insertDriver(DALWorker worker,List<String> licenses)
    {
        Result res=insertWorker(worker);
        if (res.success)
        {
            openConn();

            try
            {
                conn.setAutoCommit(false);
                for (String license : licenses)
                {
                    String sql = "Insert INTO DriverLicences (driver_id,license) VALUES (?,?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, worker.getId());
                    statement.setString(2, license);
                    statement.executeUpdate();
                }
                conn.commit();
            } catch (Exception ignored)
            {
                return new Result(false, "insert driver falied");
            } finally
            {
                try
                {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored)
                {
                }
            }
        }
        return res;

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

    public List<String> selectLicenses(int driver_id)
    {
        List<String> licenses=new LinkedList<>();
        openConn();
        String sql;
        sql = "SELECT license FROM DriverLicences WHERE driver_id=?";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,driver_id);
            resultSet=statement.executeQuery();
            while (resultSet.next())
                licenses.add(resultSet.getString("license"));
        }
        catch (Exception ignored) {}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return licenses;
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
        if (worker!=null && worker.getRole().equals("driver"))
        {
            worker=new DALDriver(worker,selectLicenses(worker.getId()));
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
                "(Select id From Workers join Constraints on Workers.id=Constraints.wid Where start_date=? and morning=? and branchAddress=?)"+
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
        } catch (Exception ignored)
        {
        }
        return worker;
    }

    public List<DALWorker> select_available_workers(java.util.Date date, boolean morning, String role, String branch)
    {
        List<DALWorker> workers=new LinkedList<>();
        openConn();
        String sql;
        sql = "SELECT * from Workers Where id not in" +
                "(Select id From Workers join Constraints on Workers.id=Constraints.wid Where start_date=? and morning=? and branchAddress=?)" +
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
        // if the worker is driver upload his licenses part
        for (int i=0;i<workers.size();i++)
        {
            if (workers.get(i).getRole().equals("driver"))
            {
                DALWorker temp=workers.get(i);
                workers.remove(i);
                temp=new DALDriver(temp,selectLicenses(temp.getId()));
                workers.add(0,temp);
            }
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
                resultSet = statement.executeQuery();
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

    public List<DALWorker> select_all_workers()
    {
        List<DALWorker> dalWorkers=new LinkedList<>();
        openConn();
        String sql="SELECT * From Workers";
        ResultSet resultSet=null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                dalWorkers.add(setDalWorkerFromResultSet(resultSet));
            }
        }
        catch (SQLException ignored){}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return dalWorkers;
    }
    //-------------------------------end workers-------------------------------

    //------------------------------shifts--------------------------------------
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
            statement.executeUpdate();
            for (Integer worker: shift.getWorkers())
            {
                sql="INSERT INTO WorkersInShift(date,morning,worker_id,branch) VALUES(?,?,?,?)";
                statement = conn.prepareStatement(sql);
                statement.setDate(1,new java.sql.Date(shift.getDate().getTime()));
                statement.setInt(2, shift.isMorning() ? 1 : 0);
                statement.setInt(3,worker);
                statement.setString(4,shift.getBranchAddress());
                statement.executeUpdate();
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
            String sql="SELECT worker_id FROM WorkersInShift WHERE date=? and morning=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(previous_date.getTime()));
            statement.setInt(2,shift.isMorning() ? 1 : 0);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next())
                workers_before_update.add(resultSet.getInt("worker_id"));
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
                    statement.executeUpdate();
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
            statement.executeUpdate();

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
                    statement.executeUpdate();
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
            conn.setAutoCommit(false);
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
        catch (SQLException e) {
            try
            {
                conn.rollback();
            } catch (SQLException ex) { }
            result= new Result(false, "Delete shift failed"); }
        finally
        {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) { }
        }
        return result;
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
                statement  = conn.prepareStatement(sql);
                statement.setDate(1, new java.sql.Date(date.getTime()));
                statement.setInt(2, morning ? 1 : 0);
                statement.setString(3, branch);
                resultSet = statement.executeQuery();
                while (resultSet.next())
                    workers_in_shift.add(resultSet.getInt("worker_id"));
                shift.setWorkers(workers_in_shift);
            }

        }
        catch (SQLException ignored) {System.out.println(ignored); }
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

    public List<DALShift> select_week_shifts(java.util.Date currentWeekStart, java.util.Date currentWeekEnd)
    {
        List<DALShift> week_shifts=new LinkedList<>();
        String sql="SELECT * from shifts WHERE date BETWEEN ? and ?";
        openConn();
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement  = conn.prepareStatement(sql);
            statement.setDate(1,new java.sql.Date(currentWeekStart.getTime()));
            statement.setDate(2,new java.sql.Date(currentWeekEnd.getTime()));
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                DALShift shift=setDalShiftFromResultSet(resultSet);
                week_shifts.add(shift);
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
        for (DALShift shift : week_shifts)
        {
            shift.setWorkers(selectWorkersInShift(shift.getDate(),shift.isMorning(),shift.getBranchAddress()));
        }
        return week_shifts;

    }

    private List<Integer> selectWorkersInShift(java.util.Date date,boolean morning, String branch)
    {
        List<Integer> workers_in_shift=new LinkedList<>();
        openConn();
        String sql="SELECT worker_id FROM WorkersInShift where date=? and morning=? and branch=?";
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            statement.setInt(2, morning ? 1 : 0);
            statement.setString(3, branch);
            resultSet = statement.executeQuery();
            while (resultSet.next())
                workers_in_shift.add(resultSet.getInt("worker_id"));
        }
        catch (Exception ignored) {}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return workers_in_shift;
    }

    public List<DALShift> select_all_shifts()
    {
        List<DALShift> dalShifts=new LinkedList<>();
        openConn();
        String sql="SELECT * From Shifts";
        ResultSet resultSet=null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                dalShifts.add(setDalShiftFromResultSet(resultSet));
            }
        }
        catch (SQLException ignored){}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        for (DALShift shift : dalShifts)
        {
            shift.setWorkers(selectWorkersInShift(shift.getDate(),shift.isMorning(),shift.getBranchAddress()));
        }
        return dalShifts;
    }
    //-----------------------------end shifts-------------------------------------


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
                dalTruck.setWeighNeto(rs.getInt("netoWeight"));
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
        System.out.println("sql fail us");
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
        String sql = "SELECT * FROM Sections";
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

    public DalArchive loadArchive(){
        DalArchive archive = new DalArchive();
        openConn();
        String sql = "SELECT * From Deliveries";
        List<DalDelivery> daldel = new LinkedList<>();
        List<Integer> docNums = new LinkedList<>();
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                DalDelivery delivery = new DalDelivery();
                setDeliveryPrams(rs, delivery);
                daldel.add(delivery);
                archive.setDeliveries(daldel);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("sql failed us - archive");    //todo remove
            return null;
        }

        openConn();
        sql = "SELECT docID From DeliveryDocs";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                int docNum = rs.getInt("docID");
                docNums.add(docNum);
            }
            archive.setDocuments(docNums);
            conn.close();
        } catch (SQLException e) {
            System.out.println("sql failed us - archive2");    //todo remove
            e.printStackTrace();
            return null;
        }
        System.out.println("returning real archive");
        return archive;
    }

    private void setDeliveryPrams(ResultSet rs, DalDelivery delivery) throws SQLException {
        delivery.setId(rs.getInt("id"));
        delivery.setDate(rs.getDate("departureDate"));
        delivery.setDepartureTime(rs.getTime("departureTime"));
        delivery.setTruckNum(rs.getInt("truckNum"));
        delivery.setDriver(rs.getString("driver"));
        DalLocation source = loadLocation(rs.getString("source"));
        delivery.setSource(source);
    }

    public boolean saveDelivery(DalDelivery delivery ) {
        openConn();
        String sql = "INSERT INTO Deliveries(id, departureDate, departureTime, truckNum, truckWeight, driver, source) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getId());
            pstmt.setDate(2, new java.sql.Date(delivery.getDate().getTime()));
//            pstmt.setDate(2, new Date(delivery.getDate().getYear(),delivery.getDate().getMonth(),delivery.getDate().getDay()));
            pstmt.setTime(3, new java.sql.Time(delivery.getDepartureTime().getTime()));
//            pstmt.setTime(3, new Time(delivery.getDate().getHours(),delivery.getDate().getMinutes(), 0));
            pstmt.setInt(4 , delivery.getTruckNum());
            pstmt.setInt(5 , delivery.getTruckWeight());
            pstmt.setString(6, delivery.getDriver());
            pstmt.setString(7, delivery.getSource().getAddress());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        for (DALDeliveryDoc dalDoc : delivery.getDocs()){
            saveDoc(delivery.getId(), dalDoc);
        }
        return true;
    }

//    public DalDelivery loadDelivery(int id) {
//        DalDelivery delivery = null;
//        openConn();
//        String sql = "SELECT* FROM Deliveries WHERE id=?";
//        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, id);
//            ResultSet rs  = pstmt.executeQuery();
//            if (rs.next()) {
//                delivery = new DalDelivery();
//                setDeliveryPrams(rs, delivery);
//            }
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("sql failed us");    //todo remove
//            return null;
//        }
//        return delivery;
//    }

    public boolean saveDoc(int deliveryID, DALDeliveryDoc doc) {
        openConn();
        String sql = "INSERT INTO DeliveryDocs(deliveryID, docID, destination, estimatedTimeOfArrival, estimatedDayOfArrival) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deliveryID);
            pstmt.setInt(2, doc.getNum());
            pstmt.setString(3 , doc.getDestination().getAddress());
//            pstmt.setTime(4, new Time(doc.getEstimatedTimeOfArrival().getHours(), doc.getEstimatedTimeOfArrival().getMinutes(), 0));
            pstmt.setTime(4, new java.sql.Time(doc.getEstimatedTimeOfArrival().getTime()));
            pstmt.setDate(5, new java.sql.Date(doc.getEstimatedDayOfArrival().getTime()));
//            pstmt.setDate(5, new Date(doc.getEstimatedDayOfArrival().getDay(), doc.getEstimatedDayOfArrival().getMonth(), doc.getEstimatedDayOfArrival().getYear()));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return false;
        }
        for (DalSupply dalSupply : doc.getDeliveryList()){
            saveSupply(doc.getNum(), doc.getDestination().getAddress(), dalSupply);
        }
        return true;
    }

    public DALDeliveryDoc loadDoc(int docNum) {
        DALDeliveryDoc doc = null;
        openConn();
        String sql = "SELECT* FROM DeliveryDocs WHERE docID=?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setInt(1, docNum);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                doc = new DALDeliveryDoc();
                doc.setNum(rs.getInt("docID"));
                String address = rs.getString("destination");
                doc.setDestination(loadLocation(address));
                doc.setEstimatedTimeOfArrival(rs.getTime("estimatedTimeOfArrival"));
                System.out.println(rs.getDate("estimatedTimeOfArrival").toString());
                System.out.println(rs.getTime("estimatedDayOfArrival").toString());
                doc.setEstimatedDayOfArrival(rs.getDate("estimatedDayOfArrival"));
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

    public List<String> load_Branches()
    {
        List<String> branches=new LinkedList<>();
        openConn();
        String sql="SELECT address From Locations where isBranch=1";
        ResultSet resultSet=null;
        try
        {
            PreparedStatement statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                branches.add(resultSet.getString("address"));
            }
        }
        catch (SQLException ignored){}
        finally
        {
            try {
                resultSet.close();
                conn.close();
            } catch (SQLException ignored) {}
        }
        return branches;
    }


    /*public void save(List<Business.DalLocation> bLocations) {
        dalLocations = new LinkedList<>();
        for (Business.DalLocation l: bLocations ){
            dalLocations.add(new DalLocation(l.getAddress(), l.getPhone(), l.getAssociate()));
        }
    }*/

}
