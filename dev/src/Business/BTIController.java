package Business;

import Business.BuisnessObjects.*;
import Interface.ITBDelController;
import javafx.util.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static Business.ShiftController.assign_Driver;
import static Business.ShiftController.assign_storekeeper;

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
        return "Document created successfully.";
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
            return "Some delivery docs weren't added.";     //todo check how this was printed


        List<Location> destinations = new LinkedList<>();
        int area = sections.getSection(docs.get(0).getDestination());      //todo check this
        for (DeliverDoc doc : docs){
            Location dest = btd.loadLocation(doc.getDestination().getAddress());
            if (dest == null)
                return "The destination "+doc.getDestination().getAddress() +" doesn't exist.";
            if (area != sections.getSection(doc.getDestination()))
                return "Can't deliver to more than one area.";
            destinations.add(dest);
        }
        if (destinations.size() != docs.size())
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

    public String printArchive(){
        return archive.toString();
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
}
