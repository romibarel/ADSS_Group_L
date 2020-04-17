import java.util.Date;
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
            expiration1 = new SimpleDateFormat("dd/MM/yyyy").parse("17/05/2020");
            supply1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2020");
            defect1 = new SimpleDateFormat("dd/MM/yyyy").parse("13/05/2020");

        }
        catch (Exception e){}


        /*  story:
        Milk: barcode 1 tnuva price:10 discount:0 expiration:"17/05/2020" amount:200 supplyTime: 10/05/2020
               location: SHELF (2)
        move 50 Milk from location 2 to location 3
        set 2 main categories: 'main category1' 'main category2'
        set sub category to 'main category1': 'sub Category1'
        connect Milk to 'main category1'
        set sale product info of Milk to be: 6.5 -> no discount
        */

        Singletone_Storage_Management manager = Singletone_Storage_Management.getInstance();
        manager.buyProduct(1, "Milk","tnuva",
        10,0,expiration1,
        200, supply1, LocationController.SHELF);
        manager.moveProduct(1, expiration1, 50, 2, 3);
        manager.setMainCategory("main category1");
        manager.setMainCategory("main category2");
        manager.setNewSubCategory("subCategory1", "main category1");
        manager.connectProductToCategory("main category1",1 );
        manager.setSaleInfoOfNewProduct(1, "Milk", 6.5, 0);



        /*  story:
        Tuna: barcode 2 StarKist price:6.5 discount:2 expiration:"17/05/2020" amount:800 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 600 Milk from location 1 to location 2
        connect Tuna to 'main category2'
        set sale product info of Tuna to be: 12.5 -> 2.5
        */

        manager.buyProduct(2, "Tuna","StarKist",
                6.5,2,expiration1,
                800, supply1, LocationController.STORAGE);
        manager.moveProduct(1, expiration1, 600, 1, 2);
        manager.connectProductToCategory("main category2",2 );
        manager.setSaleInfoOfNewProduct(1, "Milk", 12.5, 2.5);


        Presentation p = new Presentation();
        p.startProgramMenu();
    }
}
