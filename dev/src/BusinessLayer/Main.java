package BusinessLayer;

import PersistenceLayer.DataController;
import PresentationLayer.InputController;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataController dc = new DataController();
        SystemController sc = new SystemController(dc);
        InputController ic = new InputController(sc);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to load pre-made data or start from scratch? (y/n)");
        if(scanner.next().equals("y"))
            sc.loadSystem();
        ic.run();
    }
}
