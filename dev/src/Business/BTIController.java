package Business;

import Interface.ITBController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BTIController {
    private static BTIController bti = null;
    private static ITBController itb;
    private static BTDController btd;
    private DeliveryArchive archive;
    private List<Supply> supplies;
    private List<DeliverDoc> documents;
    private List<Driver> drivers;
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

    public void set(ITBController itb, BTDController btd, List<String[]> supplies, List<String[]> drivers, List<String[]> sections, List<String[]> locations, List<String[]> trucks){
        BTIController.itb = itb;
        BTIController.btd = btd;
        archive = new DeliveryArchive();

        this.supplies = new LinkedList<>();
        //(String name, int quant)
        for (String[] combo : supplies){
            Supply sup = new Supply(combo[0], Integer.parseInt(combo[1]));
            this.supplies.add(sup);
        }

        documents = new LinkedList<>();

        this.drivers = new LinkedList<>();
        //drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        //(List<String> licenses, String name)
        for (String[] combo : drivers){
            String name = combo[0];
            List<String> licenses= new LinkedList<>();
            for (int i=1; i<combo.length; i++){
                licenses.add(combo[i]);
            }
            Driver driver = new Driver(licenses, name);
            this.drivers.add(driver);
        }

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
        //(String address, int phone, String associate)
        //"Super Lee", "052", "Haim"
        for (String[] combo : locations){
            Location loc = new Location(combo[0], Integer.parseInt(combo[1]), combo[2]);
            this.locations.add(loc);
        }

        this.trucks = new LinkedList<>();
        //(int truckNum, int plate, int weighNeto, int maxWeight, String type)
        //"1", "111", "1000", "4000", "Mazda"
        for (String[] combo : trucks){
            Truck truck = new Truck(Integer.parseInt(combo[0]), Integer.parseInt(combo[1]), Integer.parseInt(combo[2]), Integer.parseInt(combo[3]), combo[4]) ;
            this.trucks.add(truck);
        }
        //updateBTD();
    }

    public void updateBTD() {
        btd.set(this.drivers, this.archive, this.sections , this.locations , this.trucks );

    }

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    //todo: check that creating deliver doc is right now
    public String createDoc(int docNum, String[] doc){
        String[] data = doc[1].split(" ", Integer.MAX_VALUE);
        List<Supply> supplies = new LinkedList<>();
        for (int i=0; i<data.length-1; i+=2){
            try {
                Supply supp = new Supply(data[i], Integer.parseInt(data[i + 1]));
                supplies.add(supp);
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
        DeliverDoc deliverDoc = new DeliverDoc(docNum, supplies, doc0);
        documents.add(deliverDoc);
        return "Document created successfully.";
    }

    public String createDelivery(Date date, Date time, int truckInt, String driverName, String sourceName, List<Integer> docNums, int truckWeight){
        Truck truck = null;
        for (Truck t : trucks){
            if (t.getTruckNum() == truckInt)
                truck = t;
        }
        if (truck == null)
            return "This truck doesn't exist.";
        if (truck.getMaxWeight() < truckWeight)
            return "The truck exceeds its max weight";
        Driver driver = null;
        for(Driver d : drivers){
            if (driverName.equals(d.getName()))
                driver = d;
        }
        if (driver == null)
            return "This driver doesn't exist.";

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
        if (locations.size() != docs.size())
            return "Some of the destinations weren't added.";
        Delivery delivery = new Delivery(date, time, truck, driver, source, destinations, docs, truckWeight);
        if(!(delivery.isApproved()))
            return "The driver is unlicensed for the given truck.";

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

    public List<Driver> getDrivers() {
        return drivers;
    }

    public Sections getSections() {
        return sections;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void addSupply(String name, int num) {
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
    }

    public DeliveryArchive getArchive(){
        return archive;
    }
}
