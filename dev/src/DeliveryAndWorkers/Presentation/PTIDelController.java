package DeliveryAndWorkers.Presentation;

import DeliveryAndWorkers.Business.BTDController;
import DeliveryAndWorkers.Business.BTIController;
import DeliveryAndWorkers.DataAccess.DALController;
import DeliveryAndWorkers.Interface.ITBDelController;
import DeliveryAndWorkers.Interface.ITPDelController;
import SuperMarket.SuperMarketController;
import javafx.util.Pair;

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
        PTIDelController pti = PTIDelController.getPTI();
        ITPDelController itp = ITPDelController.getITP();
        ITBDelController itb = ITBDelController.getITB();
        BTIController bti = BTIController.getBTI();

        pti.set();
        itp.set();
        itb.set();
        bti.set();
    }

    public void oldSetup(){
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
           //     bti.set(sections, locations, trucks);
            }
            else if (input.equals("n")){
                List<String[]> sections = new LinkedList<>();
                List<String[]> locations = new LinkedList<>();
                List<String[]> trucks = new LinkedList<>();

                while(!finish){
                    System.out.println("What would you like to add?\n" +
                            "1) Section\n" +
                            "2) DalTruck\n" +
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
                                if (!validString(input)) {
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
                         //   bti.set(sections, locations, trucks);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void start(boolean isManager){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        formatter.format(date);

        formatter = new SimpleDateFormat("HH:mm");
        Date time = new Date();
//        todo haim romi is it todays ???
        System.out.println(formatter.format(date));
        itp.checkCurrentTime(date, time);
//        check current time bti

        if (!isManager){
            //docNum is the first available id of a delivery document.
            boolean finish = false;
            int docNum = itp.getMaxDocNum() + 1;
            System.out.println("Welcome to the delivery system!");
            while(!finish){
                System.out.println("Please choose your action.\n" +
                        "1) Create delivery\n" +
                        "2) Cancel delivery\n" +
                        "3) Print archive\n" +
                        "4) Exit delivery system");
                String input = scanner.nextLine();
                switch (input){
                    case "1":
                        docNum = createDelivery(docNum);
                        break;
                    case  "2":
                        cancelDelivery();
                        break;
                    case "3":
                        printArchive();
                        break;
                    case "4":
                        finish = true;
                        break;
                    default:
                        System.out.println("no such option");
                        break;
                }
            }
        }
        else {
            System.out.println("Please choose your action.\n" +
                    "1) Print archive\n" +
                    "2) Exit delivery system");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    printArchive();
                    break;
                case "2":
                    break;
                default:
                    System.out.println("no such option");
                    break;
            }
        }
    }

    private void printArchive(){
        System.out.println(itp.printArchive());
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
        System.out.println("Please enter the address for the source of delivery.");
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
        docs = createDocuments(docNum, source, date, time);
        if (docs.isEmpty()){
            System.out.println("No document was successfully added, document cannot be created. Try again.");
            return docNum;
        }
        //create the delivery trough interface layer
        System.out.println(itp.createDelivery(date, time, truckNum, driverID, source, docs, truckWeight));
        return docNum + docs.size();
    }

    private List<Integer> createDocuments(int docNum, String source, Date date, Date time){
        boolean finish = false;
        List<Integer> docNums = new LinkedList<>();
        Date estimatedDayOfArrival;
        Date estimatedTimeOfArrival;

        Date currDay = date;
        Date currTime = time;

        List < Pair<String , Integer> > sourceSupplies = userEnterSupplies();

        String out = itp.createDoc(time, date, docNum, source, sourceSupplies);
        System.out.println(out);
        if (!out.equals("Delivery list to "+ source +" created successfully."))
            return docNums;
        else {
            docNums.add(docNum);
            docNum++;
        }

        while (!finish) {
            //destination, supplies&quants,
            //0=destination 1=long string of format: supply1 quant1, supply2, quant2...
            String myDestination ;
            System.out.println("Let's create the delivery document for the next Destination. Where would you like to deliver?");
            String destination = scanner.nextLine();
            if (destination == null || destination.trim().equals("")){
                System.out.println("Destination address can't be empty.");
                return new LinkedList<>();
            }
            myDestination  = destination;
//            System.out.println("Please enter all the supplies and quantities to deliver in this format: supply1 quant1, supply2 quant2...");
//            doc[1] = scanner.nextLine();
            List < Pair<String , Integer> > supplies = userEnterSupplies();


            System.out.println("What is the estimated date of arrival to " +destination+ " in dd/mm/yyyy format?");
            String input = scanner.nextLine();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                estimatedDayOfArrival = format.parse(input);
                if (estimatedDayOfArrival.before(currDay)) {
                    System.out.println("You need a time machine for that");
                    return new LinkedList<>();
                }
            } catch (Exception e) {
                System.out.println("Invalid date, try again.");
                return new LinkedList<>();
            }

            System.out.println("What is the estimated time of arrival to " +destination+ " in HH:mm format?");
            input = scanner.nextLine();
            format = new SimpleDateFormat("HH:mm");
            try {
                estimatedTimeOfArrival = format.parse(input);
                if (estimatedDayOfArrival.compareTo(currDay)==0 && estimatedTimeOfArrival.before(currTime)) {
                    System.out.println("You need a time machine for that");
                    return new LinkedList<>();
                }
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
            out = itp.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, myDestination, supplies);
            System.out.println(out);
            if (!out.equals("Delivery list to "+ myDestination +" created successfully.")){
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

    private void cancelDelivery(){
        String input;
        System.out.println("Please enter Storage Manager username and password.");
        input = scanner.nextLine();
        //todo: check the actual id of store keeper, logistic manager and HR manager
        if (!validUser(input, 2)){
            System.out.println("Storage Manager username and ID incorrect, cancelation denied.");
            return;
        }

        System.out.println("Please enter Logistic Manager username and password.");
        input = scanner.nextLine();
        if (!validUser(input, 4)){
            System.out.println("Logistic Manager username and ID incorrect, cancelation denied.");
            return;
        }

        System.out.println("Please enter HR Manager user username password.");
        input = scanner.nextLine();
        if (!validUser(input, 1)){
            System.out.println("HR Manager username and ID incorrect, cancelation denied.");
            return;
        }

        System.out.println("Please enter the id of the delivery you'd like to cancel.");
        input = scanner.nextLine();
        int delID = -1;
        try {
            delID = Integer.parseInt(input);
        } catch (Exception e){
            System.out.println("Invalid number.");
            return;
        }

        System.out.println(itp.cancelDelivery(delID));
    }

    private List < Pair<String , Integer> > userEnterSupplies() {
        List<Pair<String, Integer>> supplies = new LinkedList<>();
        boolean continueSuppling = true;
        while (continueSuppling) {
            System.out.println("Please enter supply name to pick up or deliver");
            String supName = scanner.nextLine();
            if (!validString(supName)) {
                System.out.println("invalid name");
                continue;
            }
            System.out.println("now Enter the quantity of supply " + supName + ".");
            String supQun = scanner.nextLine();
            if (!validInt(supQun)) {
                System.out.println("invalid supply quantity");
                continue;
            }
            supplies.add(new Pair<>(supName, Integer.parseInt(supQun)));
            System.out.println("Any more supplies for us? enter y for yes, any other key to stop");
            String ans = scanner.nextLine();
            continueSuppling = "Y".equals(ans) || "y".equals(ans);
        }
        return supplies;
    }

    private boolean validString(String s)
    {
        return ! (s == null || s.trim().equals(""));
    }

    private boolean validInt(String s)
    {
        boolean ret = false;
        try {
            int check = Integer.parseInt(s);
            ret = true;
        } catch (Exception e){
            ret = false;
        }
        return ret;
    }

    private boolean validUser(String s, int id){
        //0=username 1=password
        if(s == null)
            return false;
        String[] parse = s.split(" ");
        if (parse.length != 2)
            return false;
        return SuperMarketController.getInstance().checkPermission(parse[0], parse[1]) == id;
    }
}
