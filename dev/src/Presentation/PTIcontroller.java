package Presentation;

import Business.BTDController;
import Business.BTIController;
import DataAccess.DTBController;
import Interface.ITBController;
import Interface.ITPController;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTIcontroller {
    private static PTIcontroller ptIcontroller = null;
    private static ITPController itp;
    private Scanner scanner = new Scanner(System.in);

    private PTIcontroller(){
    }

    public static PTIcontroller getPTI(){
        if (ptIcontroller == null)
            ptIcontroller = new PTIcontroller();
        return ptIcontroller;
    }

    public void set(ITPController itp){
        PTIcontroller.itp = itp;
    }

    public void setup(){
        boolean finish = false;
        PTIcontroller pti = PTIcontroller.getPTI();
        ITPController itp = ITPController.getITP();
        ITBController itb = ITBController.getITB();
        BTIController bti = BTIController.getBTI();
        BTDController btd = BTDController.getBTD();
        DTBController dtb = DTBController.getDTB();
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

                pti.set(itp);
                itp.set(pti, itb);
                itb.set(bti, itp);
                bti.set(itb, btd, sections, locations, trucks);
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
                            pti.set(itp);
                            itp.set(pti, itb);
                            itb.set(bti, itp);
                            bti.set(itb, btd, sections, locations, trucks);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void start(){
        //docNum is the id of the delivery document
        boolean finish = false;
        int docNum = 0;
        System.out.println("Welcome to the delivery system!");
        while(!finish){
            System.out.println("If you don't want to create any more deliveries please enter 'quit', otherwise press enter.\n");
            String input = scanner.nextLine();
            if (input.equals("quit"))
                finish = true;
            else{
                createDelivery(docNum);
                docNum++;
            }
        }
    }



    private int createDelivery(int docNum) {
        Date date;
        Date time;
        int truckNum;
        String driver;
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
        System.out.println("Please enter the name of the driver.");
        driver = scanner.nextLine();
        System.out.println("Please enter the source of delivery.");
        source = scanner.nextLine();
        System.out.println("Please weight the truck and enter its weight in kilos.");
        input = scanner.nextLine();
        try{
            truckWeight = Integer.parseInt(input);
        }catch (Exception e){
            System.out.println("Invalid weight.");
            return docNum;
        }
        docs = createDocuments(docNum);
        if (docs.isEmpty()){
            System.out.println("No document was successfully added, document cannot be created. Try again.");
            return docNum;
        }
        //create the delivery trough interface layer
        System.out.println(itp.createDelivery(date, time, truckNum, driver, source, docs, truckWeight));
        return docNum + docs.size();
    }

    private List<Integer> createDocuments(int docNum){
        boolean finish = false;
        List<Integer> docNums = new LinkedList<>();

        while (!finish) {
            //destination, supplies&quants,
            //0=destination 1=long string of format: supply1 quant1, supply2, quant2...
            String[] doc = new String[2];
            System.out.println("Let's create a delivery document. Where would you like to deliver?");
            doc[0] = scanner.nextLine();
            System.out.println("Please enter all the supplies and quantities to deliver in this format: supply1 quant1 supply2 quant2...");
            doc[1] = scanner.nextLine();
            System.out.println("Do you wish to add another destination? [y/n]");
            String yn = scanner.nextLine();
            if (yn.equals("n"))
                finish = true;
            else if (!yn.equals("y")){
                System.out.println("Invalid answer, the system will create the delivery now.");
                finish = true;
            }
            String out = itp.createDoc(docNum, doc);
            System.out.println(out);
            if (!out.equals("Document created successfully.")){
                finish = true;
            }
            else {
                docNums.add(docNum);
                docNum++;
            }
        }

        return docNums;
    }

}
