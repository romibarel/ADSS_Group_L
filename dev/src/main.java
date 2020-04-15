import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class main {

    private Scanner in;
    String pattern;
    SimpleDateFormat simpleDateFormat;

    public static void main (String[] args){
        Date expiration1 = null;
        Date supply1 = null;
        Date defect1 = null;
        try {
            expiration1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2020");
            supply1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2020");
            defect1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2020");

        }
        catch (Exception e){}



        Singletone_Storage_Management manager = Singletone_Storage_Management.getInstance();
        manager.buyProduct(1, "Milk","avi hovalot",
        10,0,expiration1,
        200, supply1, LocationController.SHELF);
        manager.moveProduct(1, expiration1, 50, 2, 3);
        manager.setMainCategory("main category1");
        manager.setMainCategory("main category2");
        manager.setNewSubCategory("subCategory1", "main category1");
        manager.appendProductToCategory(1, "main category1");
        manager.setSaleInfoOfNewProduct(1, "Tuna", 6.5, 0);


        Presentation p = new Presentation();
        p.startProgramMenu();
    }
}
