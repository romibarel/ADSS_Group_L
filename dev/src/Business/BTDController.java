package Business;


import DataAccess.*;
import Interface.InterfaceWorker;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BTDController {
    private static BTDController thisOne;
    private static DALController dataTb;
    private static BTIController bti;
    private List<Driver> drivers;
    private DeliveryArchive archive ;
    private Sections sections;
    private List<Location> locations;
    private List<DalTruck> trucks;

    private BTDController(){
        drivers = new LinkedList<>();
        locations = new LinkedList<>();
        trucks = new LinkedList<>();
    }

    public static BTDController getBTD(){
        if (thisOne == null) {
            thisOne = new BTDController();
            BTDController.dataTb = DALController.getDTB();
            BTDController.bti = BTIController.getBTI();
        }
        return thisOne;
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

    public Result getMax()  {
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

    /**
     * DalSections and Archive must be given all other can be null for not changeing
     * @param drivers
     * @param archive
     * @param sections
     * @param locations
     * @param trucks
     */
    public void set(List<Driver> drivers, DeliveryArchive archive, Sections sections, List<Location> locations, List<DalTruck> trucks) {
        this.archive = archive;
        this.sections = sections;
        if (drivers != null) {
            this.drivers = drivers;
        }
        if (locations != null) {
            this.locations = locations;
        }
        if (trucks != null) {
            this.trucks = trucks;
        }
    }

//    public void saveToDB(){
//        dataTb.save(dataTb.make(drivers), dataTb.make(archive), dataTb.make(trucks), dataTb.make(locations), dataTb.make(sections));
//    }


    public void addDelivery(Delivery delivery)
    {
//        archive.add(delivery);
//        delivery.get
//todo        make it create a dall delivery with an int id
//todo          save all its delivery docs with the same id
        //todo change so will add all the
    }

    public Truck loadTruck(int truckId) {
        return new Truck(dataTb.loadTruck(truckId));
    }

    public boolean saveTruck(Truck truck)
    {
        return dataTb.saveTruck(new DalTruck(truck));
    }

    public Location getLocation(String address) {
        return new Location(dataTb.loadLocation(address));
    }

    public boolean saveLocation(boolean isBranch, Location location) {
        return dataTb.saveLocation(new DalLocation(isBranch, location));
    }


    public void saveDoc(int delId, DeliverDoc deliveryDoc)
    {
        dataTb.saveDoc(delId , new DALDeliveryDoc(deliveryDoc));
    }

    public boolean saveDelivery(Delivery delivery) {
        return dataTb.saveDelivery(new DalDelivery(delivery));
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


//    public boolean addTruck(DalTruck truck)
//    {
//        if (trucks == null)
//            trucks = new LinkedList<>();
//        return trucks.add(truck);
//    }
//
//    public boolean addLocation(Location location)
//    {
//        if (locations == null)
//            locations = new LinkedList<>();
//        return locations.add(location);
//    }
//
//    public boolean addDriver(Driver driver)
//    {
//        if (drivers == null)
//            drivers = new LinkedList<>();
//        return drivers.add(driver);
//    }
//
//
//    public List<Driver> getDrivers() {
//        return drivers;
//    }
//
//    public void setDrivers(List<Driver> drivers) {
//        this.drivers = drivers;
//    }
//
//    public DeliveryArchive getArchive() {
//        return archive;
//    }
//
//    public void setArchive(DeliveryArchive archive) {
//        this.archive = archive;
//    }
//
//    public List<DalTruck> getTrucks() {
//        return trucks;
//    }
//
//    public void setTrucks(List<DalTruck> dalTrucks) {
//        this.trucks = dalTrucks;
//    }
//
//    public List<Location> getLocations() {
//        return locations;
//    }
//
//    public void setLocations(List<Location> locations) {
//        this.locations = locations;
//    }
//
//    public Sections getSections() {
//        return sections;
//    }
//
//    public void setSections(Sections sections) {
//        this.sections = sections;
//    }

}
