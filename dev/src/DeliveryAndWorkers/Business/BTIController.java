package DeliveryAndWorkers.Business;

import DeliveryAndWorkers.Business.BuisnessObjects.*;
import DeliveryAndWorkers.Interface.ITBDelController;
import StorageAndSupplier.Singltone_Supplier_Storage_Manager;
import StorageAndSupplier.Suppliers.BusinessLayer.Order;
import StorageAndSupplier.Suppliers.BusinessLayer.Product;
import StorageAndSupplier.Suppliers.BusinessLayer.Supplier;
import javafx.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static DeliveryAndWorkers.Business.ShiftController.assign_Driver;
import static DeliveryAndWorkers.Business.ShiftController.assign_storekeeper;

public class BTIController {
    private static BTIController bti = null;
    private static ITBDelController itb;
    private static BTDController btd;
    private DeliveryArchive archive;
    private List<DeliverDoc> documents;
    private Sections sections;
    private List<Location> locations;
    private List<Truck> trucks;

    private BTIController(){

    }

    public static BTIController getBTI(){
        if (bti == null)
            bti = new BTIController();
        return bti;
    }

    public void set(){
        itb = ITBDelController.getITB();
        btd = BTDController.getBTD();
        btd.set();
        archive = btd.loadArchive();
        documents = new LinkedList<>();
        this.sections = btd.loadSections();
        this.locations = new LinkedList<>();
        this.trucks = new LinkedList<>();
    }

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String destination, List < Pair<String , Integer> > supplies){

        List<Supply> docSupplies = new LinkedList<>();
        for (Pair<String, Integer> suppPair : supplies){
            Supply newSup = new Supply(suppPair.getKey(), suppPair.getValue());
            docSupplies.add(newSup);
        }

        Location myDestination = btd.loadLocation(destination);
        if (myDestination == null)
            return "The destination doesn't exist.";
        DeliverDoc deliverDoc = new DeliverDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, docSupplies, myDestination);
        documents.add(deliverDoc);
        return "Delivery list to "+ deliverDoc.getDestination().getAddress() +" created successfully.";
    }

    public String createDelivery(Order order, int weight){
        for (Delivery del: archive.getDeliveries()){
            if (del.getSource().equals(order))
                return "Delivery already in place.";
        }

        Truck truck = null;
        for (Truck check : trucks){
            if (check.getWeighNeto() + weight <= check.getMaxWeight()) {
                truck = check;
                break;
            }
        }
        if (truck == null)
            truck = btd.loadTruckByWeight(weight);
        if (truck == null)
            return "There is no fitting truck for the delivery.";
        trucks.add(truck);

        int driver = WorkersController.get_available_driver_id(truck.getType(), order.getETA());
        if (driver == -1)
            return "There is no fitting driver.";

        String source = order.getSrcAddress();
        if (source == null)
            return "The source doesn't exist.";

        String destS = order.getDestAddress();
        Location dest = btd.loadLocation(destS);
        if(dest == null)
            return "error there is no such location : "+ destS;

        List<Location> destinations = new LinkedList<>();
        destinations.add(dest);
//        we didnt use arrival ?? haim todo
        Date date = convertToDateViaSqlTimestamp(order.getETA());
        DateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeF = new SimpleDateFormat("hh:mm");
        Date arrival = null;
        Date depart = null;

        try{
            String arrivalS = timeF.format(date);
            arrival = timeF.parse(arrivalS);
            String[] split = arrivalS.split(":");
            Integer departInt = Integer.parseInt(split[0]) - 3;
            String departS = departInt.toString() + ":" + split[1];
            depart = timeF.parse(departS);
            date = dateF.parse(dateF.format(date));
        }catch (Exception e){}

        List<Pair<String, Integer>> supplies = milkSuppliesFromOrder(order);
        int docNum = getMaxDocNum() + 1;

        List<Integer> numToDelivery = new LinkedList<>();
        numToDelivery.add(docNum);

        return createDelivery(date, depart, truck.getTruckNum(), driver, source, numToDelivery, weight);

    }

    public String createDelivery(Date date, Date time, int truckID, int driverID, String sourceAddress, List<Integer> docNums, int truckWeight){
        Truck truck = null;
        for (Truck check : trucks){
            if (check.getTruckNum() == truckID)
                truck = check;
        }
        if (truck == null)
            truck = btd.loadTruck(truckID);
        if (truck == null)
            return "The given truck doesn't exist.";
        trucks.add(truck);
        if (truck.getMaxWeight() < truckWeight)
            return "The given truck exceeds its max weight";

        String driver = WorkersController.get_driver_name(driverID);
        if (driver == null)
            return "The driver doesn't exist.";

        boolean goodLicenses = WorkersController.canDriveTruck(driverID, truck.getType()) ;
        if(!goodLicenses)
            return "The driver is unlicensed for the given truck.";

        Location source = btd.loadLocation(sourceAddress);
        if (source instanceof Branch)
            return "The source must be a supplier.";
        if (source == null)
            return "The source doesn't exist.";

        List<DeliverDoc> docs = new LinkedList<>();
        for (DeliverDoc doc : documents){
            if (docNums.contains(doc.getNum()))
                docs.add(doc);
        }
        if (docs.isEmpty())
            return "No delivery documents were added.";
        if (docs.size() != docNums.size())
            return "Some delivery docs weren't added to the delivery, creation failed.";


        List<Location> destinations = new LinkedList<>();
        int area = sections.getSection(docs.get(1).getDestination());
        if (area == 0)
            return "This location doesn't exist. Delivery creation failed.";
        List<DeliverDoc> woSource = new LinkedList<>();
        for (int i=1; i<docs.size(); i++){
            woSource.add(docs.get(i));
        }
        for (DeliverDoc doc : woSource){
            Location dest = btd.loadLocation(doc.getDestination().getAddress());
            if (dest == null)
                return "The destination "+doc.getDestination().getAddress() +" doesn't exist.";
            if (area != sections.getSection(doc.getDestination()))
                return "Can't deliver to more than one area.";
            destinations.add(dest);
        }
        if (destinations.size() != woSource.size())
            return "Some of the destinations weren't added...";
        Delivery delivery = new Delivery(date, time, truck, driver, goodLicenses, source, docs, truckWeight);


        /*driverHours[0] = day of departure,
           driverHours[1] = time of departure,
           driverHours[2] = day of arrival,
           driverHours[3] = time of arrival
         */
        Date[] driverHours = delivery.getDuration();
        Result result = assign_Driver(driverID, driverHours[0], driverHours[1], driverHours[2], driverHours[3]);
        if(!result.success)
            return result.msg;

        List<Pair<String, Date[]>> estimatedArrivals = delivery.getEstimatedArrivals();
        /*String = location name,
           Date[0] = day of arrival,
           Date[1] = time of arrival
         */
        //Result assign_storekeeper(Date date,Date hour,String branch)
        for (Pair<String, Date[]> p : estimatedArrivals){
            Result r = assign_storekeeper(p.getValue()[0], p.getValue()[1], p.getKey());
            if (!r.success)
                return r.msg+".";
        }

        //if we got here all is a ok
        //addDelivery saves all the deliveryDocs of this delivery as well
        btd.saveDelivery(delivery);
        archive.add(delivery);
        return "Delivery was created successfully! The delivery and all its documents were saved to the database.";
    }

    public String cancelDelivery(int delID){
        Delivery delivery = null;
        for (Delivery del : archive.getDeliveries()){
            if (del.getID == delID){
                delivery = del;
                break;
            }
        }
        if (delivery == null)
            return "There was no delivery with this ID.";

    }

    public String printArchive(){
        return archive.toString();
    }

    public int getMaxDocNum(){
        return btd.getMaxDocNum();
    }

    public List<DeliverDoc> getDocuments() {
        return documents;
    }

    public Sections getSections() {
        return sections;
    }

    public DeliveryArchive getArchive(){
        return archive;
    }

    /**
     * check if there are deliveries that arrived
     * if the given date and time passed call send DeliveryList & the delimitter is in one of the supplies
     * it is or all of them or none
     * @param date
     * @param time
     */
    public void checkCurrentTime(Date date, Date time) {
        List<Delivery> deliveries = archive.getDeliveries();
        for (Delivery deli : deliveries ) {
            sendDeliveryList(deli);
        }
    }

    private void sendDeliveryList(Delivery deli) {
//        todo haim
        //todo: avi needs buyProduct(int supplierID, int catalogID, String productName, double price, double discount, Date expiration, int amount, Date date)


    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        if (dateToConvert == null) return null;
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    private List<Pair<String, Integer>> milkSuppliesFromOrder(Order order){
        //supply's name: int supplierID, int catalogID, String productName, double price, double discount, Date expiration
        List<Pair<String, Integer>> supplies = new LinkedList<>();
        HashMap<Product, Pair<Integer, Integer>> a = order.getProducts();
        for(Map.Entry<Product, Pair<Integer, Integer>> e : order.getProducts().entrySet()){
            String suppName = e.getKey().getName();
            int suppCatalog = e.getKey().getCatalogID();
            LocalDateTime suppExperationDate = e.getKey().getExpirationDate();
            double price = order.getPriceOf(e.getKey().getCatalogID());
            double discount = order.getDiscountOf(e.getKey().getCatalogID());
            String delemiter = "^^";
            String newName = order.getSupplierID() + delemiter + suppCatalog + delemiter + suppName + delemiter
                       + price + delemiter + discount + delemiter + suppExperationDate.toString();
            Integer quant = e.getValue().getKey();
            supplies.add(new Pair<>(newName, quant));
        }
        return supplies;
    }
}
