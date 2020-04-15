package Presentation;

import Interface.ITPController;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTIcontroller {
    private static PTIcontroller ptIcontroller = null;
    private static ITPController itp;
    private Scanner scanner = new Scanner(System.in);

    private PTIcontroller(){
        this.itp = ITPController.getITP();
    }

    public static PTIcontroller getPTI(){
        if (ptIcontroller == null)
            ptIcontroller = new PTIcontroller();
        return ptIcontroller;
    }

    public void start(){
        //docNum is the id of the delivery document
        int docNum = 0;
        while(true){
            docNum = createDelivery(docNum);
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
        docs = createDocuments(docNum);
        System.out.println("Please weigh the truck and enter its weight in kilos.");
        input = scanner.nextLine();
        try{
            truckWeight = Integer.parseInt(input);
        }catch (Exception e){
            System.out.println("Invalid weight.");
            return docNum;
        }

        //create the delivery trough interface layer
        itp.createDelivery(date, time, truckNum, driver, source, docs);
        return docNum + docs.size();
    }

    private List<Integer> createDocuments(int docNum){
        boolean finish = false;
        List<Integer> docNums = new LinkedList<>();

        while (!finish) {
            //destination, supplies&quants,
            String[] doc = new String[2];
            System.out.println("Let's create a delivery document. Where would you like to deliver?");
            doc[0] = scanner.nextLine();
            System.out.println("Please enter all the supplies and quantities to deliver in this format: supply1 quant1, supply2, quant2...");
            doc[1] = scanner.nextLine();
            System.out.println("Do you wish to add another destination? [y/n]");
            String yn = scanner.nextLine();
            if (yn.equals("n"))
                finish = true;
            else if (!yn.equals("y")){
                System.out.println("Sorry, I didn't catch that... No more delivery documents.");
                finish = true;
            }
            itp.createDoc(docNum, doc);
            docNums.add(docNum);
            docNum++;
        }

        return docNums;
    }
}
