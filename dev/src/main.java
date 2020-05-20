import DataAccess.DALController;
import Presentation.PTIDelController;
import Presentation.WorkerMenu;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class main {
    public static void main (String[] args){
        try{
            long date= new SimpleDateFormat("dd/MM/yyyy").parse("21/5/2020").getTime();
            date= new SimpleDateFormat("dd/MM/yyyy").parse("22/5/2020").getTime();
            date=new SimpleDateFormat("dd/MM/yyyy").parse("23/5/2020").getTime();
            date=new SimpleDateFormat("dd/MM/yyyy").parse("25/5/2020").getTime();
            date=new SimpleDateFormat("dd/MM/yyyy").parse("26/5/2020").getTime();
            date=new SimpleDateFormat("dd/MM/yyyy").parse("28/5/2020").getTime();
            date=new SimpleDateFormat("dd/MM/yyyy").parse("31/5/2020").getTime();
            Scanner input = new Scanner(System.in);
            String choice;
            boolean quit=false;
            PTIDelController pti = PTIDelController.getPTI();
            pti.setup();
            while(!quit){
                System.out.println("Hello, please choose the system you want to use:");
                System.out.println("1. Delivery System");
                System.out.println("2. HR System");
                System.out.println("3. Exit");
                choice = input.nextLine();
                switch (choice){
                    case "1":
                        pti.start();
                        break;
                    case "2":
                        WorkerMenu.mainFunc();
                        break;
                    case "3":
                        quit=true;
                        break;
                    default:
                        System.out.println("no such option");
                        break;
                }
            }
        }
        catch (Exception e){
            System.out.println("some thing went wrong reloading system");
        }

    }
}
