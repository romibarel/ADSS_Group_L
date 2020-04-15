package Presentation;

import Interface.ITPController;

import java.util.Scanner;

public class PTIcontroller {
    private static PTIcontroller ptIcontroller = null;
    private ITPController itp;
    private Scanner scanner = new Scanner(System.in);

    private PTIcontroller(ITPController itp){
        this.itp = itp;
    }

    public static PTIcontroller getPTI(ITPController itp){
        if (ptIcontroller == null)
            ptIcontroller = new PTIcontroller(itp);
        return ptIcontroller;
    }

    public void start(){
        while(true){
            createDelivery();
        }
    }

    private void createDelivery(){
        System.out.println("Hi! please enter the date of delivery in dd/mm/yyyy format");
        String date =
    }
}
