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
        while(!finish) {
            System.out.println("Would you like to automatically setup? [y/n]");
            String input = scanner.nextLine();
            if (input.equals("y")){
                finish = true;
                PTIcontroller pti = PTIcontroller.getPTI();
                ITPController itp = ITPController.getITP();
                ITBController itb = ITBController.getITB();
                BTIController bti = BTIController.getBTI();
                BTDController btd = BTDController.getBTD();
                DTBController dtb = DTBController.getDTB();
                List<String[]> supplies = new LinkedList<>();
                List<String[]> drivers = new LinkedList<>();
                List<String[]> sections = new LinkedList<>();
                List<String[]> locations = new LinkedList<>();
                List<String[]> trucks = new LinkedList<>();

                supplies.add(new String[] {"First", "2"});
                supplies.add(new String[] {"Second", "5"});
                supplies.add(new String[] {"Third", "10"});

                drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
                drivers.add(new String[] {"Joseph", "Mercedes"});

                sections.add(new String[] {"1", "Super Lee", "Lee Office"});
                sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
                sections.add(new String[] {"3", "Mega", "Costco"});

                locations.add(new String[] {"Super Lee", "052", "Haim"});
                locations.add(new String[] {"Lee Office", "058", "Romi"});
                locations.add(new String[] {"Shufersal", "054", "Tony"});
                locations.add(new String[] {"Max Stock", "050", "Ziv"});
                locations.add(new String[] {"Best Buy", "055", "Tomer"});
                locations.add(new String[] {"Mega", "053", "Sivan"});
                locations.add(new String[] {"Costco", "057", "Gali"});

                //int truckNum, int plate, int weighNeto, int maxWeight, String type
                trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
                trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
                trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
                trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

                pti.set(itp);
                itp.set(pti, itb);
                itb.set(bti, itp);
                bti.set(itb, btd, supplies, drivers, sections, locations, trucks);
            }
            else if (input.equals("n")){
                PTIcontroller pti = PTIcontroller.getPTI();
                ITPController itp = ITPController.getITP();
                ITBController itb = ITBController.getITB();
                BTIController bti = BTIController.getBTI();
                BTDController btd = BTDController.getBTD();
                DTBController dtb = DTBController.getDTB();
                List<String[]> supplies = new LinkedList<>();
                List<String[]> drivers = new LinkedList<>();
                List<String[]> sections = new LinkedList<>();
                List<String[]> locations = new LinkedList<>();
                List<String[]> trucks = new LinkedList<>();

                while(!finish){
                    System.out.println("What would you like to add?\n" +
                            "1) Supply\n" +
                            "2) Driver\n" +
                            "3) Section\n" +
                            "4) Truck\n" +
                            "5) Finish");
                    input = scanner.nextLine();
                    switch (input){
                        case("1"):
                            System.out.println("Enter the name and quantity of the supply.\n");
                            input = scanner.nextLine();
                            String [] sup = input.split(" ", Integer.MAX_VALUE);
                            if (sup.length != 2){
                                System.out.println("Invalid supply.");
                                break;
                            }
                            try {
                                int i = Integer.parseInt(sup[1]);
                            } catch (Exception e){
                                System.out.println("Invalid supply.");
                                break;
                            }
                            supplies.add(sup);
                            break;
                        case("2"):
                            System.out.println("Enter the name of driver and all his/her types of licenses.\n");
                            input = scanner.nextLine();
                            String [] driver = input.split(" ", Integer.MAX_VALUE);
                            drivers.add(driver);
                            break;
                        case("3"):
                            System.out.println("Enter number of the section to add and all the locations that belong to it.\n");
                            input = scanner.nextLine();
                            String [] secs = input.split(" ", Integer.MAX_VALUE);
                            try {
                                int j = Integer.parseInt(secs[0]);
                            } catch (Exception e){
                                System.out.println("Invalid area number.");
                                break;
                            }
                            sections.add(secs);
                            for (int i=1; i<secs.length; i++){
                                boolean exists = false;
                                for (String[] s : locations) {
                                    if (s[0].equals(secs[i]))
                                        exists = true;
                                }
                                 if(!exists) {
                                     System.out.println("Enter the phone number and associate's name of " + secs[i] + ".\n");
                                     input = scanner.nextLine();
                                     String[] temp = input.split(" ", Integer.MAX_VALUE);
                                     try {
                                         int j = Integer.parseInt(temp[0]);
                                     } catch (Exception e){
                                         System.out.println("Invalid phone number.");
                                         break;
                                     }
                                     String[] loc = new String[3];
                                     loc[0] = secs[i];
                                     try {
                                         loc[1] = temp[0];
                                         loc[2] = temp[1];
                                     } catch (Exception e) {
                                         System.out.println("invalid phone number and associate's name.\n");
                                     }
                                     locations.add(loc);
                                 }
                            }
                            break;
                        case("4"):
                            System.out.println("Please enter the truck's number, plate, neto weight, maximum weight and type.\n");
                            input = scanner.nextLine();
                            String [] truck = input.split(" ", Integer.MAX_VALUE);
                            try {
                                int i = Integer.parseInt(truck[0]);
                                i = Integer.parseInt(truck[1]);
                                i = Integer.parseInt(truck[2]);
                                i = Integer.parseInt(truck[3]);
                            } catch (Exception e){
                                System.out.println("Invalid truck details.");
                                break;
                            }
                            trucks.add(truck);
                            break;
                        case("5"):
                            finish = true;
                            pti.set(itp);
                            itp.set(pti, itb);
                            itb.set(bti, itp);
                            bti.set(itb, btd, supplies, drivers, sections, locations, trucks);
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
        System.out.println("Welcome !");
        while(!finish){
            System.out.println("Please enter 1 for adding supplies, 2 for creating delivery and 3 for shut down.\n");
            String input = scanner.nextLine();
            switch (input){
                case ("1"):
                    System.out.println(addSupplies());
                    break;
                case ("2"):
                    docNum = createDelivery(docNum);
                    executeDelivery(docNum);
                    break;
                case ("3"):
                    finish = true;
                    break;
            }
        }
    }

    private void executeDelivery(int docNum){
        itp.execute(docNum);
    }

    private String addSupplies() {
        System.out.println("Enter Supply name");
        String name = scanner.nextLine();
        while (name == null)
        {
            System.out.println("Enter Supply name");
            name = scanner.nextLine();
        }
        System.out.println("Enter Supply quntity");
        String number = null;
        int num = 0 ;
        boolean goodNumber = false;
        while (!goodNumber) {
            number = scanner.nextLine();
            try {
                num = Integer.parseInt(number);
                goodNumber = true;
            } catch (Exception e) {
                System.out.println("Invalid number, please enter a number.");
                goodNumber = false;
            }
            if (num < 0) // todo <=?
            {
                System.out.println("Invalid number, please enter a positive number.");
                goodNumber = false;
            }
        }
        return itp.addSupply(name, num);
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

    public void arriveAt(String dest){
        System.out.println("The delivery arrived at "+ dest +", would you like to get supplies? [y/n]");
        String ans = scanner.nextLine();
        boolean goodAns = false;
        while (!goodAns){
            ans = scanner.nextLine();
            if (ans.equals("y") || ans.equals("n"))
                goodAns = true;
        }
        if (ans.equals("y")){
            System.out.println(addSupplies());
        }
    }

}
