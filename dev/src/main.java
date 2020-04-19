import Buisness.Locations.LocationController;
import Buisness.Singletone_Storage_Management;
import Presentation.Presentation;

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
        Date sell1 = null;
        try {
            expiration1 = new SimpleDateFormat("dd/MM/yyyy").parse("17/01/2020");
            supply1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            defect1 = new SimpleDateFormat("dd/MM/yyyy").parse("13/01/2020");
            sell1 = new SimpleDateFormat("dd/MM/yyyy").parse("15/01/2020");

        }
        catch (Exception e){}

        Singletone_Storage_Management manager = Singletone_Storage_Management.getInstance();
        /*
        set 2 main categories: 'main category1' 'main category2'
        set sub category to 'main category1': 'sub Category1'
        */
        manager.setMainCategory("Dairy");
        manager.setMainCategory("Canning");
        manager.setMainCategory("Personal care");
        manager.setNewSubCategory("Milk", "Dairy");
        manager.setNewSubCategory("Large", "Milk");
        manager.setNewSubCategory("Shower", "Personal care");

        /*  story:
        Milk: barcode 1 tnuva price:10 discount:0 expiration:"17/05/2020" amount:200 supplyTime: 10/05/2020
               location: SHELF (2)
        move 50 Milk from location 2 to location 3
        connect Milk to 'Large'
        set sale product info of Milk to be: 6.5 -> no discount
        */

        manager.buyProduct(1, "Milk","tnuva",
        10,0,expiration1,
        200, supply1, LocationController.SHELF);
        manager.moveProduct(1, expiration1, 50, 2, 3);
        manager.connectProductToCategory("Large",1 );
        manager.setSaleInfoOfNewProduct(1, "Milk", 6.5, 0);

        /*  story:
        Tuna: barcode 2 StarKist price:6.5 discount:2 expiration:"17/05/2020" amount:800 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 600 Tuna from location 1 to location 2
        connect Tuna to 'Canning'
        set sale product info of Tuna to be: 12.5 -> 2.5
        */

        manager.buyProduct(2, "Tuna","StarKist",
                6.5,2,expiration1,
                800, supply1, LocationController.STORAGE);
        manager.moveProduct(2, expiration1, 600, 1, 2);
        manager.connectProductToCategory("Canning",2 );
        manager.setSaleInfoOfNewProduct(2, "Tuna", 12.5, 2.5);


        /*  story:
        Tuna: barcode 3 Dove price:20 discount:5.5 expiration:"17/05/2020" amount:900 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 600 Shampoo from location 1 to location 2
        connect Shampoo to 'Shower'
        set sale product info of Shampoo to be: 25 -> 3
        */

        manager.buyProduct(3, "Shampoo","Dove",
                20,5.5,expiration1,
                900, supply1, LocationController.STORAGE);
        manager.moveProduct(3, expiration1, 600, 1, 2);
        manager.connectProductToCategory("Shower",3 );
        manager.setSaleInfoOfNewProduct(3, "Shampoo", 25, 3);

        /*  story:
        Tuna: barcode 4 tnuva price:8 discount:3 expiration:"17/05/2020" amount:1000 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 450 Cheese from location 1 to location 2
        connect cheese to 'Dairy'
        set sale product info of Cheese to be: 6 -> 0
        */

        manager.buyProduct(4, "Cheese","tnuva",
                8,3,expiration1,
                1000, supply1, LocationController.STORAGE);
        manager.moveProduct(4, expiration1, 450, 1, 2);
        manager.connectProductToCategory("Dairy",4 );
        manager.setSaleInfoOfNewProduct(4, "Dairy", 6, 0);

        /*  story:
        Tuna: barcode 5 tnuva price:10 discount:1.5 expiration:"17/05/2020" amount:1000 supplyTime: 10/05/2020
               location: STORAGE (1)
        move 555 Shocko from location 1 to location 2
        connect Shocko to 'Milk'
        set sale product info of Shocko to be: 14 -> 0
        */

        manager.buyProduct(5, "Shocko","tnuva",
                10,1.5,expiration1,
                1000, supply1, LocationController.STORAGE);
        manager.moveProduct(5, expiration1, 555, 1, 2);
        manager.connectProductToCategory("Milk",5 );
        manager.setSaleInfoOfNewProduct(5, "Shocko", 14, 0);

        /*
        * Set defects: a lot of products dropped from track because the driver was drunk :\
        * */

        manager.addDefect(supply1, 5, 200, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 4, 50, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 3, 155, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(supply1, 2, 40, "dropped from track", "Avi Ferdman", 1, expiration1);
        manager.addDefect(defect1, 2, 5, "costumer dropped from shelf", "Avi Ferdman", 2, expiration1);

        /*
         * Sell products: Milk sold 12, Tuna Sold 21, Shocko sold 22
         * */

        manager.sellProduct(sell1, 1, 3, expiration1);
        manager.sellProduct(sell1, 1, 2, expiration1);
        manager.sellProduct(sell1, 1, 7, expiration1);
        manager.sellProduct(sell1, 2, 9, expiration1);
        manager.sellProduct(sell1, 2, 12, expiration1);
        manager.sellProduct(sell1, 5, 14, expiration1);
        manager.sellProduct(sell1, 5, 3, expiration1);
        manager.sellProduct(sell1, 5, 3, expiration1);
        manager.sellProduct(sell1, 5, 2, expiration1);


        Presentation p = new Presentation();
        p.startProgramMenu();
    }
}
