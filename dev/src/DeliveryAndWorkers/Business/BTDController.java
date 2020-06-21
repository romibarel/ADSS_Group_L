package DeliveryAndWorkers.Business;


import DeliveryAndWorkers.Business.BuisnessObjects.*;
import DeliveryAndWorkers.DataAccess.*;
import DeliveryAndWorkers.DataAccess.DALObjects.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BTDController {
    private static BTDController thisOne;
    private static DALController dataTb;
    private static BTIController bti;
    private List<Integer> currDeliveryIDs;
    private int deliveryIdCounter;      // todo change to be good

    private BTDController(){
        currDeliveryIDs = new LinkedList<>();
    }

    public static BTDController getBTD(){
        if (thisOne == null) {
            thisOne = new BTDController();
            BTDController.dataTb = DALController.getDTB();
            BTDController.bti = BTIController.getBTI();
        }
        return thisOne;
    }

    public List<Integer> getCurrDeliveryIDs(){
        return currDeliveryIDs;
    }

    public List<Constraint> loadConstraint(int id, java.util.Date date, boolean morning)  {
        List<Constraint> constraints=new LinkedList<>();
        for (DALConstraint c:  dataTb.loadConstraint(id, date,morning))
            constraints.add(new Constraint(c));
        return constraints;
    }

    public List<Constraint> loadAllConstraint()  {
        List<Constraint> constraints=new LinkedList<>();
        for (DALConstraint c:  dataTb.loadALLConstraint())
            constraints.add(new Constraint(c));
        return constraints;
    }

    public Constraint loadConstraint(int cid)  {
        return new Constraint(dataTb.loadConstraint(cid));
    }

    public List<Constraint> loadConstraintByWeek(java.util.Date datestart, java.util.Date dateend) {
        List<Constraint> constraints=new LinkedList<>();
        for (DALConstraint c:  dataTb.loadConstraintByWeek(datestart, dateend))
            constraints.add(new Constraint(c));
        return constraints;
    }

    public Result deleteConstraint(Constraint constraint) {
        return dataTb.deleteConstraint(new DALConstraint(constraint));
    }

    public int getMax()  {
        return dataTb.getMax();
    }

    public Result updateConstraint(Constraint constraint) {
        return dataTb.updateConstraint(new DALConstraint(constraint));
    }

    public Result saveConstraint(Constraint constraint) {
        return dataTb.saveConstraint(new DALConstraint(constraint));
    }

    //-------------------------------------workers-------------------------------------------------------
    public Result insertDriver(Worker worker, List<String> licenses)
    {
        return dataTb.insertDriver(new DALWorker(worker),licenses);
    }

    public Result insertWorker(Worker worker)
    {
        return dataTb.insertWorker(new DALWorker(worker));
    }

    public Result updateWorker(Worker worker)
    {
        return dataTb.updateWorker(new DALWorker(worker));
    }

    public Result deleteWorker(int worker_id)
    {
        return dataTb.deleteWorker(worker_id);
    }

    public Worker selectWorker(int id)
    {
        DALWorker worker=dataTb.selectWorker(id);
        if (worker==null) return null;
        if (worker.getRole().equals("driver")) return new Driver((DALDriver) worker);
        return new Worker(worker);
    }

    public int select_available_worker_id(Date date, boolean morning, String branch,String role)
    {
        return dataTb.select_available_worker_id(date,morning,branch,role);
    }

    public List<Worker> select_available_workers(Date date, boolean morning, String role, String branch)
    {
        List<Worker> workers=new LinkedList<>();
        List<DALWorker> dalWorkers=dataTb.select_available_workers(date,morning,role,branch);
        for (DALWorker dalWorker:dalWorkers)
        {
            if (dalWorker.getRole().equals("driver")) workers.add(new Driver((DALDriver) dalWorker));
            else workers.add(new Worker(dalWorker)); // in runtime it will create driver or worker depends if it is DAlWorker or DALDriver
        }
        return workers;
    }

    public boolean is_worker_scheduled(int worker_id)
    {
        return dataTb.is_worker_scheduled(worker_id);
    }

    //-------------------------------------end workers-------------------------------------------------

    //--------------------------------------shifts---------------------------------------------------
    public Result insertShift(Shift shift)
    {
        return dataTb.insertShift(new DALShift(shift));
    }

    public Result updateShift(Shift shift, java.util.Date previous_date, boolean previous_morning, String previous_branch)
    {
        return dataTb.updateShift(new DALShift(shift),previous_date,previous_morning,previous_branch);
    }

    public Result deleteShift(java.util.Date date, boolean morning, String branch)
    {
        return dataTb.deleteShift(date,morning,branch);
    }

    public Shift selectShift(java.util.Date date,boolean morning, String branch)
    {
        DALShift shift= dataTb.selectShift(date,morning,branch);
        if (shift==null) return null;
        return new Shift(shift);
    }

    //-------------------------------------end shifts---------------------------------------------

    //-------------------------------------messages-----------------------------------------------
    public void send_message(String recipient , String message)
    {
        dataTb.insertMessage(recipient,message);
    }

    public List<String> get_Messages(String recipient)
    {
        return dataTb.selectMessages(recipient);
    }
    //-------------------------------------end messages-------------------------------------------

    public void set()
    {
        dataTb.initialize();
    }

//    public void saveToDB(){
//        dataTb.save(dataTb.make(drivers), dataTb.make(archive), dataTb.make(trucks), dataTb.make(locations), dataTb.make(sections));
//    }


    public Truck loadTruck(int truckId) {
        DalTruck truck = dataTb.loadTruck(truckId);
        if (truck == null)
            return null;
        return new Truck(truck);
    }

//    public boolean saveTruck(Truck truck)
//    {
//        return dataTb.saveTruck(new DalTruck(truck));
//    }

    public Location loadLocation(String address) {
        DalLocation dalLocation = dataTb.loadLocation(address);
        if(dalLocation==null)
            return null;
        if (dalLocation.getIsBranch()){
            return new Branch(dalLocation);
        }
        return new Supplier(dalLocation);
    }

    public boolean saveLocation(boolean isBranch, Location location) {
        return dataTb.saveLocation(new DalLocation(isBranch, location));
    }


    public void saveDoc(int delId, DeliverDoc deliveryDoc)  //todo probbly remove
    {
        dataTb.saveDoc(delId , new DALDeliveryDoc(deliveryDoc));
    }

    public boolean saveDelivery(Delivery delivery) {    //todo finish
        int curId = deliveryIdCounter;
        currDeliveryIDs.add(curId);
        deliveryIdCounter++;
        return dataTb.saveDelivery(new DalDelivery(delivery , curId));
    }


    public Sections loadSections(){

        return new Sections(dataTb.loadSections());
    }

    public List<Shift> get_week_shifts(Date currentWeekStart, Date currentWeekEnd)
    {
        List<Shift> week_shifts=new LinkedList<>();
        List<DALShift> dal_shifts=dataTb.select_week_shifts(currentWeekStart,currentWeekEnd);
        for (DALShift dalShift : dal_shifts)
        {
            week_shifts.add(new Shift(dalShift));
        }
        return week_shifts;
    }

    public List<Shift> get_all_shifts()
    {
        List<Shift> shifts=new LinkedList<>();
        List<DALShift> dalShifts=dataTb.select_all_shifts();
        for (DALShift dalShift:dalShifts)
        {
            Shift shift=new Shift(dalShift);
            shifts.add(shift);
        }
        return shifts;
    }

    public List<Worker> get_all_workers()
    {
        List<Worker> workers=new LinkedList<>();
        List<DALWorker> dalWorkers=dataTb.select_all_workers();
        for (DALWorker dalWorker:dalWorkers)
        {
            Worker worker=new Worker(dalWorker);
            workers.add(worker);
        }
        return workers;
    }

    public DeliveryArchive loadArchive(){
        DalArchive arc = dataTb.loadArchive();
        if (arc == null)
            return new DeliveryArchive();
        deliveryIdCounter += arc.getDeliveries().size();
        List<DALDeliveryDoc> docs = new LinkedList<>();
        for (Integer docNum : arc.getDocuments()){
            DALDeliveryDoc daldoc = dataTb.loadDoc(docNum);
            docs.add(daldoc);
        }

        return new DeliveryArchive(arc, docs);
    }

    public int getMaxDocNum(){
        return dataTb.getMaxDocNum();
    }

    public List<String> load_all_branches()
    {
        return dataTb.load_Branches();
    }
}
