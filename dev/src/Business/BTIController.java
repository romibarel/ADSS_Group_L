package Business;

import Interface.ITBDelController;
import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static Business.ShiftController.assign_Driver;
import static Business.ShiftController.assign_storekeeper;

public class BTIController {
    private static BTIController bti = null;
    private static ITBDelController itb;
    private static BTDController btd;
    private DeliveryArchive archive;
    private List<Supply> supplies;
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

    public void set(List<String[]> sections, List<String[]> locations, List<String[]> trucks){
        itb = ITBDelController.getITB();
        btd = BTDController.getBTD();
        archive = new DeliveryArchive();

        this.supplies = new LinkedList<>();

        documents = new LinkedList<>();

        HashMap<Integer, List<String>> secs = new HashMap<>();
        for (String[] combo : sections){
            Integer area = Integer.parseInt(combo[0]);
            List <String> locs = new LinkedList<>();
            for (int i=1; i<combo.length; i++){
                locs.add(combo[i]);
            }
            secs.put(area, locs);
        }
        this.sections = new Sections(secs);

        this.locations = new LinkedList<>();
        //(boolean type, String address, int phone, String associate)
        //"1", "Super Lee", "052", "Haim"
        for (String[] combo : locations){
            Location loc;
            boolean isBranch = true;
            if (combo[0].equals("0"))
                isBranch = false;
            if (isBranch) {
                loc = new Branch(combo[1], Integer.parseInt(combo[2]), combo[3]);
                WorkersController.addBranch(loc.getAddress());
            }
            else
                loc = new Supplier(combo[1], Integer.parseInt(combo[2]), combo[3]);
            this.locations.add(loc);
        }

        this.trucks = new LinkedList<>();
        //(int truckNum, int plate, int weighNeto, int maxWeight, String type)
        //"1", "111", "1000", "4000", "Mazda"
        for (String[] combo : trucks){
            Truck truck = new Truck(Integer.parseInt(combo[0]), Integer.parseInt(combo[1]), Integer.parseInt(combo[2]), Integer.parseInt(combo[3]), combo[4]) ;
            this.trucks.add(truck);
        }
        updateBTD();
    }

    public void updateBTD() {
        btd = BTDController.getBTD();
   //     btd.set(this.archive, this.sections , this.locations , this.trucks);

    }

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String[] doc){
        String[] data = doc[1].split(" ", Integer.MAX_VALUE);
        List<Supply> DocSupplies = new LinkedList<>();
        for (int i=0; i<data.length-1; i+=2){
            try {
                Supply supp = null;
                for (Supply s : supplies){
                    if (s.getName().equals(data[i])){
                        supp = new Supply(s, Integer.parseInt(data[i+1]));
                        if (s.getQuant() < Integer.parseInt(data[i+1]))
                            return "Insufficient amount of supply " + supp.getName() + ".";
                        s.setQuant(s.getQuant()-Integer.parseInt(data[i+1]));
                        DocSupplies.add(supp);
                        break;
                    }
                }
            }catch (Exception e){
                return "Invalid supplies input";
            }
        }
        Location doc0 = null;
        for (Location l: locations) {
            if (doc[0].equals(l.getAddress()))
            {
                doc0 = l;
                break;
            }
        }
        if (doc0 == null)
            return "The destination doesn't exist.";
        DeliverDoc deliverDoc = new DeliverDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, DocSupplies, doc0);
        documents.add(deliverDoc);
        return "Document created successfully.";
    }

    public String createDelivery(Date date, Date time, int truckInt, int driverID, String sourceName, List<Integer> docNums, int truckWeight){
        Truck truck = null;
        for (Truck t : trucks){
            if (t.getTruckNum() == truckInt)
                truck = t;
        }
        if (truck == null)
            return "The given truck doesn't exist.";
        if (truck.getMaxWeight() < truckWeight)
            return "The given truck exceeds its max weight";

        String driver = WorkersController.get_driver_name(driverID);
        if (driver == null)
            return "The driver doesn't exist.";

        boolean goodLicenses = WorkersController.canDriveTruck(driverID, truck.getType()) ;


        Location source = null;
        for (Location l : locations){
            if (l.getAddress().equals(sourceName))
                source = l;
        }
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
            return "Some delivery docs weren't added.";


        List<Location> destinations = new LinkedList<>();
        int area = sections.getSection( docs.get(0).getDestination());
        for (DeliverDoc doc : docs){
            if (locations.contains(doc.getDestination())){
                if (area != sections.getSection(doc.getDestination()))
                    return "You tried to deliver to different areas.";
                destinations.add(doc.getDestination());
            }
        }
        if (destinations.size() != docs.size())
            return "Some of the destinations weren't added.";
        Delivery delivery = new Delivery(date, time, truck, driver, goodLicenses, source, destinations, docs, truckWeight);
        if(!(delivery.isApproved()))
            return "The driver is unlicensed for the given truck.";

        /*driverHours[0] = day of departure,
           driverHours[1] = time of departure,
           driverHours[2] = day of arrival,
           driverHours[3] = time of arrival
         */
        Date[] driverHours = delivery.getDuration();

        if(!assign_Driver(driverID, driverHours[0], driverHours[1], driverHours[2], driverHours[3]).success)
            return "The driver is unavailable for the delivery.";

        List<Pair<String, Date[]>> estimatedArrivals = delivery.getEstimatedArrivals();
        /*String = location name,
           Date[0] = day of arrival,
           Date[1] = time of arrival
         */
        //Result assign_storekeeper(Date date,Date hour,String branch)
        for (Pair<String, Date[]> p : estimatedArrivals){
            assign_storekeeper(p.getValue()[0], p.getValue()[1], p.getKey());
        }

        //if we got here all is a ok
        archive.add(delivery);
        return "Delivery was created successfully!";
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public List<DeliverDoc> getDocuments() {
        return documents;
    }

    public Sections getSections() {
        return sections;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public String addSupply(String name, int num) {
        boolean newSup = true;
        for (Supply sup : supplies) {
            if (name.equals(sup.getName()))
            {
                sup.setQuant(sup.getQuant() + num);
                newSup = false;
                break;
            }
        }
        if (newSup)
        {
            supplies.add(new Supply(name,num));
        }

        return "You added " +num+ " " + name +".";
    }

    public DeliveryArchive getArchive(){
        return archive;
    }
}
