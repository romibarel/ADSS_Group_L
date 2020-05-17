package Presentation;

import Business.BTDController;
import Business.BTIController;
import DataAccess.DALController;
import Interface.ITBDelController;
import Interface.ITPDelController;

import java.text.SimpleDateFormat;
import java.util.*;

public class PTIDelController {
    private static PTIDelController pti = null;
    private static ITPDelController itp;
    private Scanner scanner = new Scanner(System.in);

    private PTIDelController(){
    }

    public static PTIDelController getPTI(){
        if (pti == null)
            pti = new PTIDelController();
        return pti;
    }

    public void set(){
        itp = ITPDelController.getITP();
    }

    public void setup(){
        boolean finish = false;
        PTIDelController pti = PTIDelController.getPTI();
        ITPDelController itp = ITPDelController.getITP();
        ITBDelController itb = ITBDelController.getITB();
        BTIController bti = BTIController.getBTI();
        BTDController btd = BTDController.getBTD();
        DALController dtb = DALController.getDTB();
        while(!finish) {
            System.out.println("Would you like to automatically setup? [y/n]");
            String input = scanner.nextLine();
            if (input.equals("y")){
                finish = true;
                List<String[]> sections = new LinkedList<>();
                List<String[]> locations = new LinkedList<>();
                List<String[]> trucks = new LinkedList<>();

                sections.add(new String[] {"1", "Super Lee", "Lee Office"});
                sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
                sections.add(new String[] {"3", "Mega", "Costco"});

                locations.add(new String[] {"1", "Super Lee", "052", "Haim"});
                locations.add(new String[] {"1", "Lee Office", "058", "Romi"});
                locations.add(new String[] {"1", "Shufersal", "054", "Tony"});
                locations.add(new String[] {"1", "Max Stock", "050", "Ziv"});
                locations.add(new String[] {"0", "Best Buy", "055", "Tomer"});
                locations.add(new String[] {"0", "Mega", "053", "Sivan"});
                locations.add(new String[] {"0", "Costco", "057", "Gali"});

                //int truckNum, int plate, int weighNeto, int maxWeight, String type
                trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
                trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
                trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
                trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

                pti.set();
                itp.set();
                itb.set();
                bti.set(sections, locations, trucks);
            }
            else if (input.equals("n")){
                List<String[]> sections = new LinkedList<>();
                List<String[]> locations = new LinkedList<>();
                List<String[]> trucks = new LinkedList<>();

                while(!finish){
                    System.out.println("What would you like to add?\n" +
                            "1) Section\n" +
                            "2) Truck\n" +
                            "3) Finish");
                    input = scanner.nextLine();
                    switch (input) {
                        case ("1"):
                            boolean moreLocations = true;
                            List<String> locsPerArea = new LinkedList<>();
                            System.out.println("Enter the number of section to create or add to.\n");
                            input = scanner.nextLine();
                            int area;
                            try {
                                area = Integer.parseInt(input);
                            } catch (Exception e) {
                                System.out.println("Invalid area number.");
                                break;
                            }
                            while (moreLocations) {
                                System.out.println("Enter the address of the location to add to area " + area + " or 'stop' if you're done.\n");
                                input = scanner.nextLine();
                                if (input == null || input.trim().equals("")) {
                                    System.out.println("Address must not be empty, Please try again.\n");
                                }
                                else if (input.equals("stop")){
                                    moreLocations = false;
                                }
                                else locsPerArea.add(input);
                            }
                            for (String location : locsPerArea){
                                String[] newLocation = new String[4];
                                newLocation[1] = location;
                                boolean validInput = false;
                                while(!validInput){
                                    System.out.println("Enter 1 if the location with address '" + location + "' is one of the company's branches, or 0 if it's a supplier.\n");
                                    String type = scanner.nextLine();
                                    if ((type.equals("0") || type.equals("1"))){
                                        newLocation[0] = type;
                                        validInput = true;
                                    }
                                     else System.out.println("Invalid location type.\n");
                                }
                                validInput = false;
                                while (!validInput){
                                    System.out.println("Enter the phone number of the location with address '" + location + "'.\n");
                                    String phone = scanner.nextLine();
                                    try {
                                        int check = Integer.parseInt(phone);
                                        newLocation[2] = phone;
                                        validInput = true;
                                    } catch (Exception e){
                                        System.out.println("Invalid phone number.");
                                    }
                                }
                                validInput = false;
                                while(!validInput){
                                    System.out.println("Enter the associate's name of the location with address '" + location + "'.\n");
                                    String associate = scanner.nextLine();
                                    if (associate == null || associate.trim().equals(""))
                                        System.out.println("Associate's name can't be empty.\n");
                                    else {
                                        newLocation[3] = associate;
                                        locations.add(newLocation);
                                        validInput = true;
                                    }
                                }
                            }
                            break;

                        case("2"):
                            System.out.println("Please enter the truck's number, plate, neto weight, maximum weight and type (type must be one word).\n");
                            input = scanner.nextLine();
                            String [] truck = input.split(" ", Integer.MAX_VALUE);
                            if (truck.length == 5) {
                                try {
                                    int i = Integer.parseInt(truck[0]);
                                    i = Integer.parseInt(truck[1]);
                                    i = Integer.parseInt(truck[2]);
                                    i = Integer.parseInt(truck[3]);
                                } catch (Exception e) {
                                    System.out.println("Invalid truck details.\n");
                                    break;
                                }
                                if (!(truck[4] == null || truck[4].equals("")))
                                    trucks.add(truck);
                                else System.out.println("Invalid truck type.\n");
                            }
                            else System.out.println("Insufficient information.\n");
                            break;
                        case("3"):
                            finish = true;
                            pti.set();
                            itp.set();
                            itb.set();
                            bti.set(sections, locations, trucks);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void start(){
        //docNum is the first available id of a delivery document.
        boolean finish = false;
        int docNum = 0;
        System.out.println("Welcome to the delivery system!");
        while(!finish){
            System.out.println("If you don't want to create any more deliveries please enter 'quit', otherwise press enter.\n");
            String input = scanner.nextLine();
            if (input.equals("quit"))
                finish = true;
            else{
                //create delivery returns the next available delivery document id.
                docNum = createDelivery(docNum);
            }
        }
    }

    private int createDelivery(int docNum) {
        Date date;
        Date time;
        int truckNum;
        int driverID;
        String source;
        int truckWeight;
        List<Integer> docs;

        System.out.println("Hi! Please enter the date of delivery in dd/mm/yyyy format.");
        String input = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(input);
        } catch (Exception e) {
            System.out.println("Invalid date, try again.");
            return docNum;
        }
        System.out.println("Please enter departure time in HH:mm format.");
        input = scanner.nextLine();
        format = new SimpleDateFormat("HH:mm");
        try {
            time = format.parse(input);
        } catch (Exception e) {
            System.out.println("Invalid departure time, try again.");
            return docNum;
        }
        System.out.println("Please enter the number of the truck you'd like to use.");
        input = scanner.nextLine();
        try {
            truckNum = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid truck number.");
            return docNum;
        }
        System.out.println("Please enter the driver's ID.");
        input = scanner.nextLine();
        try {
            driverID = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid ID number.");
            return docNum;
        }
        System.out.println("Please enter the address for thr source of delivery.");
        source = scanner.nextLine();
        if (source == null || source.trim().equals("")){
            System.out.println("Source address can't be empty.");
            return docNum;
        }
        System.out.println("Please weigh the truck and enter its weight in kilos.");
        input = scanner.nextLine();
        try{
            truckWeight = Integer.parseInt(input);
        }catch (Exception e){
            System.out.println("Invalid weight.");
            return docNum;
        }
        if (truckWeight < 0){
            System.out.println("A negative weight is literally impossible XD.");
            return docNum;
        }
        docs = createDocuments(docNum, source);
        if (docs.isEmpty()){
            System.out.println("No document was successfully added, document cannot be created. Try again.");
            return docNum;
        }
        //create the delivery trough interface layer
        System.out.println(itp.createDelivery(date, time, truckNum, driverID, source, docs, truckWeight));
        return docNum + docs.size();
    }

    private List<Integer> createDocuments(int docNum, String source){
        boolean finish = false;
        List<Integer> docNums = new LinkedList<>();
        Date estimatedDayOfArrival;
        Date estimatedTimeOfArrival;

        String[] sourceDoc = new String[2];
        sourceDoc[0] = source;
        System.out.println("What would you like to pick up from the source (address '" + source + "')?\n" +
                "Enter in format: supply1 quant1 supply2 quant2...");
        String sourceSupplies = scanner.next();
        sourceDoc[1] = sourceSupplies;
        String out = itp.createDoc(null, null, docNum, sourceDoc);
        System.out.println(out);
        if (!out.equals("Document created successfully."))
            return docNums;
        else {
            docNums.add(docNum);
            docNum++;
        }

        while (!finish) {
            //destination, supplies&quants,
            //0=destination 1=long string of format: supply1 quant1, supply2, quant2...
            String[] doc = new String[2];
            System.out.println("Let's create the delivery document for the next Destination. Where would you like to deliver?");
            String destination = scanner.nextLine();
            if (destination == null || destination.trim().equals("")){
                System.out.println("Destination address can't be empty.");
                return new LinkedList<>();
            }
            doc[0] = destination;
            System.out.println("Please enter all the supplies and quantities to deliver in this format: supply1 quant1 supply2 quant2...");
            doc[1] = scanner.nextLine();

            System.out.println("What is the estimated date of arrival to " +destination+ " in dd/mm/yyyy format?");
            String input = scanner.nextLine();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                estimatedDayOfArrival = format.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date, try again.");
                return new LinkedList<>();
            }

            System.out.println("What is the estimated time of arrival to " +destination+ " in HH:mm format?");
            input = scanner.nextLine();
            format = new SimpleDateFormat("HH:mm");
            try {
                estimatedTimeOfArrival = format.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid time, try again.");
                return new LinkedList<>();
            }

            System.out.println("Do you wish to add another destination? [y/n]");
            String yn = scanner.nextLine();
            if (yn.equals("n"))
                finish = true;
            else if (!yn.equals("y")){
                System.out.println("Invalid answer, the system will create the delivery with the current information now.");
                finish = true;
            }
            out = itp.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, doc);
            System.out.println(out);
            if (!out.equals("Document created successfully.")){
                System.out.println("The creation of the delivery document failed. The Delivery will not be created.");
                return new LinkedList<>();
            }
            else {
                docNums.add(docNum);
                docNum++;
            }
        }

        return docNums;
    }

}