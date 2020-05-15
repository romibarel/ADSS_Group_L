import Business.BTDController;
import Business.BTIController;
import Business.DeliveryArchive;
import Business.Sections;
import DataAccess.DTBController;
import Interface.ITBDelController;
import Interface.ITPDelController;
import Presentation.PTIDelController;
import Presentation.WorkerMenu;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        int choice=0;
        boolean quit=false;
        while(!quit){
            System.out.println("Hello, please choose the system you want to use:");
            System.out.println("1. Delivery System");
            System.out.println("2. HR System");
            System.out.println("3. Exit");
            try {
                choice = Integer.parseInt(input.nextLine());
            }
            catch (Exception e){
                System.out.println("illegal input");
                continue;
            }
            if(choice>3| choice<1){
                System.out.println("no such menu");
                continue;
            }
            switch (choice){
                case 1:
                    PTIDelController pti = PTIDelController.getPTI();
                    pti.setup();
                    pti.start();
                    break;
                case 2:
                    WorkerMenu.mainFunc();
                    break;
                case 3:
                    quit=true;
                    break;
                default:
                    System.out.println("Error");
                    break;

            }

        }

    }
}
