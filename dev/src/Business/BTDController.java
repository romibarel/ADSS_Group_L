package Business;


import DataAccess.DALConstraint;
import DataAccess.DALController;
import DataAccess.DALDeliveryDoc;
import DataAccess.DalTruck;

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



    //return all workers in specific role and branch
    public static List<Worker> upload_by_role_and_branch(String role, String branch)
    {
        //TODO
        return null;
    }

    /**
     * Sections and Archive must be given all other can be null for not changeing
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

    public void saveDoc(int delId, DeliverDoc deliveryDoc)
    {
        dataTb.saveDoc(delId , new DALDeliveryDoc(deliveryDoc));
    }

    public boolean addTruck(DalTruck truck)
    {
        if (trucks == null)
            trucks = new LinkedList<>();
        return trucks.add(truck);
    }

    public boolean addLocation(Location location)
    {
        if (locations == null)
            locations = new LinkedList<>();
        return locations.add(location);
    }

    public boolean addDriver(Driver driver)
    {
        if (drivers == null)
            drivers = new LinkedList<>();
        return drivers.add(driver);
    }


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

    public List<DalTruck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<DalTruck> dalTrucks) {
        this.trucks = dalTrucks;
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

}
