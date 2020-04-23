package PresentationLayer;

import BusinessLayer.*;
import javafx.util.Pair;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class InputController {
    SystemController sc;
    Boolean shouldTerminate = false;

    public InputController(SystemController sc){
        this.sc = sc;
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(!shouldTerminate){
            System.out.println("Choose 1 for suppliers");
            System.out.println("Choose 2 for orders");
            System.out.println("Choose 3 for reports");
            System.out.println("Choose 4 for agreements");
            System.out.println("Choose anything else to exit");
            int option = scanner.nextInt();
            switch (option){
                case 1:
                    System.out.println("Choose 1 to view all suppliers and their details");
                    System.out.println("Choose 2 to add a new supplier to the system");
                    System.out.println("Choose 3 to edit a supplier detail");
                    System.out.println("Choose 4 to delete a supplier from the system");
                    System.out.println("Choose anything else to go back");
                    option = scanner.nextInt();
                    switch (option){
                        case 1:
                            for(Supplier s : sc.getSuppliers()){
                                System.out.println("ID: "+s.getID());
                                System.out.println("Company ID: "+s.getCompanyID());
                                System.out.println("Pay condition: "+s.getPayCond());
                                System.out.println("Phone number: "+s.getPhoneNum());
                                System.out.println("Bank account: "+s.getBankAccNum()+"\n");
                            }
                            break;
                        case 2:
                            System.out.println("Please type the company ID:");
                            int cid = scanner.nextInt();
                            System.out.println("Please type the phone number:");
                            String pn = scanner.next();
                            System.out.println("Please type the bank account:");
                            String ba = scanner.next();
                            System.out.println("Please type the pay condition:");
                            String pc = scanner.next();
                            System.out.println("Please type the contact names, and 'done' when you're done");
                            String n = scanner.next();
                            LinkedList<String> names = new LinkedList<>();
                            while(!n.equals("done")) {
                                names.add(n);
                                n = scanner.next();
                            }
                            System.out.println("Press 1 to create fixed days supplier");
                            System.out.println("Press 2 to create invite only supplier");
                            System.out.println("Press 3 to create self pickup supplier");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    sc.addSupplier(new FixedDaysSupplier(cid,ba,pc,names,pn));
                                    System.out.println("Success!\n");
                                    break;
                                case 2:
                                    sc.addSupplier(new InviteOnlySupplier(cid,ba,pc,names,pn));
                                    System.out.println("Success!\n");
                                    break;
                                case 3:
                                    System.out.println("Please type the pickup location:");
                                    String location = scanner.next();
                                    sc.addSupplier(new SelfPickupSupplier(cid,ba,pc,names,pn,location));
                                    System.out.println("Success!\n");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to edit Company ID");
                            System.out.println("Press 2 to edit bank account");
                            System.out.println("Press 3 to edit pay condition");
                            System.out.println("Press 4 to edit phone number");
                            System.out.println("Press 5 to edit contact names");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            int id;
                            switch (option){
                                case 1:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new Company ID");
                                    int cid1 = scanner.nextInt();
                                    if(sc.setSupplierCompanyID(id,cid1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 2:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new bank account");
                                    String ba1 = scanner.next();
                                    if(sc.setSupplierBankAccNum(id,ba1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 3:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new pay condition");
                                    String pc1 = scanner.next();
                                    if(sc.setSupplierPayCond(id,pc1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 4:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter new phone number");
                                    String pn1 = scanner.next();
                                    if(sc.setSupplierPhoneNum(id,pn1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                case 5:
                                    System.out.println("Please enter the supplier ID");
                                    id = scanner.nextInt();
                                    System.out.println("Please enter the new contact names, and 'done' when you're done");
                                    String n1 = scanner.next();
                                    LinkedList<String> names1= new LinkedList<>();
                                    while(!n1.equals("done")){
                                        names1.add(n1);
                                        n1 = scanner.next();
                                    }
                                    if(sc.setSupplierContactNames(id,names1))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error, can not find ID");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Please enter the supplier ID");
                            sc.removeSupplier(scanner.nextInt());
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Press 1 to watch all pending orders and their details");
                    System.out.println("Press 2 to add an order");
                    System.out.println("Press 3 to edit an order");
                    System.out.println("Press 4 to cancel an order");
                    System.out.println("Press 5 to announce the arrival of an order");
                    System.out.println("Press anything else to go back");
                    option = scanner.nextInt();
                    switch (option){
                        case 1:
                            for(Order o : sc.getOrders()){
                                /*System.out.println("Order ID: "+ o.getID());
                                System.out.println("Supplier ID: "+ o.getSupplierID());
                                System.out.println("ETA: "+ o.getETA().toString());
                                System.out.println("Date issued: "+ o.getDateIssued());
                                System.out.println("Total: "+ o.getTotal());*/
                                System.out.println(o.toString());
                            }
                            break;
                        case 2:
                            System.out.println("Press 1 to add a constant order(fixed days supplier only)");
                            System.out.println("Press 2 to add a regular order");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Please enter the supplier ID");
                                    int s = scanner.nextInt();
                                    Supplier supp = sc.getSupplierByID(s);
                                    if(!(supp instanceof FixedDaysSupplier)){
                                        System.out.println("Error! not a fixed days supplier");
                                    }
                                    System.out.println("Please enter the year, month and day of the ETA");
                                    LocalDate l = LocalDate.of(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na = scanner.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm = new HashMap<>();
                                    while(!na.equals("done")){
                                        int catalogID = scanner.nextInt();
                                        String manu = scanner.next();
                                        double pri = scanner.nextDouble();
                                        int amo = scanner.nextInt();
                                        Product p = new Product(catalogID, pri,na,manu);
                                        Pair<Integer, Integer> pair = new Pair<>(amo,0);
                                        hm.put(p,pair);
                                        na = scanner.next();
                                    }
                                    Order o = new Order(s,l,LocalDate.now(),hm);
                                    sc.addOrder(o);
                                    supp.addOrder(o);
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Please enter the supplier ID");
                                    int s1 = scanner.nextInt();
                                    Supplier supp1 = sc.getSupplierByID(s1);
                                    if(supp1 instanceof FixedDaysSupplier){
                                        System.out.println("Error! a fixed days only supplier");
                                    }
                                    System.out.println("Please enter the year, month and day of the ETA");
                                    LocalDate l1 = LocalDate.of(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na1 = scanner.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm1 = new HashMap<>();
                                    while(!na1.equals("done")){
                                        int catalogID = scanner.nextInt();
                                        String manu1 = scanner.next();
                                        double pri1 = scanner.nextDouble();
                                        int amo1 = scanner.nextInt();
                                        Product p1 = new Product(catalogID, pri1,na1,manu1);
                                        Pair<Integer, Integer> pair1 = new Pair<>(amo1,0);
                                        hm1.put(p1,pair1);
                                        na1 = scanner.next();
                                    }
                                    Order o1 = new Order(s1,l1,LocalDate.now(),hm1);
                                    sc.addOrder(o1);
                                    supp1.addOrder(o1);
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to edit ETA");
                            System.out.println("Press 2 to edit products");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the new year, month and day");
                                    LocalDate ld = LocalDate.of(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                                    System.out.println("Enter the ID of the order");
                                    int ordid = scanner.nextInt();
                                    if((sc.getOrderByID(ordid) != null) && (sc.getOrderByID(ordid).setETA(ld)))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error!");
                                    break;
                                case 2:
                                    System.out.println("Please enter the name of the product, its manufacturer and its price, than enter the quantity");
                                    System.out.println("Repeat until you're done, than enter 'done'");
                                    String na2 = scanner.next();
                                    HashMap<Product, Pair<Integer, Integer>> hm2 = new HashMap<>();
                                    while(!na2.equals("done")){
                                        int catalogID = scanner.nextInt();
                                        String manu2 = scanner.next();
                                        double pri2 = scanner.nextDouble();
                                        int amo2 = scanner.nextInt();
                                        Product p2 = new Product(catalogID, pri2,na2,manu2);
                                        Pair<Integer, Integer> pair2 = new Pair<>(amo2,0);
                                        hm2.put(p2,pair2);
                                        na2 = scanner.next();
                                    }
                                    System.out.println("Enter the ID of the order");
                                    int d = scanner.nextInt();
                                    if((sc.getOrderByID(d) != null)&&(sc.getOrderByID(d).setProducts(hm2)))
                                        System.out.println("Success!");
                                    else
                                        System.out.println("Error!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the ID of the order");
                            int i = scanner.nextInt();
                            Order ooo = sc.getOrderByID(i);
                            if(ooo == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            sc.reportCancellation(ooo);
                            System.out.println("Success!");
                            break;
                        case 5:
                            System.out.println("Enter the ID of the order");
                            int i1 = scanner.nextInt();
                            Order ooo1 = sc.getOrderByID(i1);
                            if(ooo1 == null){
                                System.out.println("Error! no such order");
                                break;
                            }
                            sc.reportArrival(ooo1);
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Press 1 to view all arrival reports");
                    System.out.println("Press 2 to view all cancellation reports");
                    System.out.println("press anything else to go back");
                    option = scanner.nextInt();
                    switch (option){
                        case 1:
                            for(Report r : sc.getReports()){
                                if(r instanceof ArrivalReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported().toString());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        case 2:
                            for(Report r : sc.getReports()){
                                if(r instanceof CancellationReport){
                                    System.out.println("Report ID: "+r.getID());
                                    System.out.println("Date reported: "+r.getDateReported());
                                    System.out.println("Order ID: "+ r.getReportedOrder().getID()+"\n");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Press 1 to view agreement details");
                    System.out.println("Press 2 to add a new agreement");
                    System.out.println("Press 3 to set an agreement");
                    System.out.println("Press 4 to remove agreement");
                    System.out.println("Press anything else to go back");
                    option = scanner.nextInt();
                    switch (option){
                        case 1:
                            System.out.println("Press 1 to view all sale agreements");
                            System.out.println("Press 2 to view all gift agreements");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    for(Supplier s : sc.getSuppliers()){
                                        for(Agreement a : s.getAgreements()){
                                            if(a instanceof SaleAgreement)
                                                System.out.println(a.getDescription() + "Associated with supplier ID " + s.getID());
                                        }
                                    }
                                    break;
                                case 2:
                                    for(Supplier s : sc.getSuppliers()){
                                        for(Agreement a : s.getAgreements()){
                                            if(a instanceof GiftAgreement)
                                                System.out.println(a.getDescription() + " Associated with supplier ID " + s.getID());
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Press 1 to add a new sale agreement");
                            System.out.println("Press 2 to add a new gift agreement");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the supplier ID");
                                    int su = scanner.nextInt();
                                    Supplier to = sc.getSupplierByID(su);
                                    if(to == null){
                                        System.out.println("Error! no such a supplier");
                                        break;
                                    }
                                    System.out.println("Enter the catalogID, name, price and manufacture of the product");
                                    int catalogID1 = scanner.nextInt();
                                    String nn = scanner.next();
                                    double pp = scanner.nextDouble();
                                    String mm = scanner.next();
                                    System.out.println("Enter two numbers: how much of the product you need to order, and how much percent off of it");
                                    int aa = scanner.nextInt();
                                    int hh = scanner.nextInt();
                                    to.addAgreement(new SaleAgreement(new Pair<>(new Product(catalogID1, pp,nn,mm),new Pair<>(aa,hh))));
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Enter the supplier ID");
                                    int su1 = scanner.nextInt();
                                    Supplier to1 = sc.getSupplierByID(su1);
                                    if(to1 == null){
                                        System.out.println("Error! no such a supplier");
                                        break;
                                    }
                                    System.out.println("Enter the catalogID, name, price and manufacture of the product");
                                    int catalogID2 = scanner.nextInt();
                                    String nn1 = scanner.next();
                                    double pp1 = scanner.nextDouble();
                                    String mm1 = scanner.next();
                                    System.out.println("Enter two numbers: how much of the product you need to order, and how many of these products you get for free");
                                    int aa1 = scanner.nextInt();
                                    int hh1 = scanner.nextInt();
                                    to1.addAgreement(new GiftAgreement(new Pair<>(new Product(catalogID2, pp1,nn1,mm1),new Pair<>(aa1,hh1))));
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("Press 1 to set the product amount");
                            System.out.println("Press 2 to set the product condition");
                            System.out.println("Press anything else to go back");
                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("Enter the supplier ID");
                                    int r = scanner.nextInt();
                                    Supplier j = sc.getSupplierByID(r);
                                    if(j == null){
                                        System.out.println("Error! no such supplier");
                                        break;
                                    }
                                    System.out.println("Enter the agreement ID");
                                    int u = scanner.nextInt();
                                    Agreement v = j.getAgreementByID(u);
                                    if(v == null){
                                        System.out.println("Error! no such agreement");
                                        break;
                                    }
                                    System.out.println("Enter new amount");
                                    int y = scanner.nextInt();
                                    v.setProdAmount(y);
                                    System.out.println("Success!");
                                    break;
                                case 2:
                                    System.out.println("Enter the supplier ID");
                                    int r1 = scanner.nextInt();
                                    Supplier j1 = sc.getSupplierByID(r1);
                                    if(j1 == null){
                                        System.out.println("Error! no such supplier");
                                        break;
                                    }
                                    System.out.println("Enter the agreement ID");
                                    int u1 = scanner.nextInt();
                                    Agreement v1 = j1.getAgreementByID(u1);
                                    if(v1 == null){
                                        System.out.println("Error! no such agreement");
                                        break;
                                    }
                                    System.out.println("Enter new condition");
                                    int y1 = scanner.nextInt();
                                    v1.setProdCond(y1);
                                    System.out.println("Success!");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Enter the supplier ID");
                            int ii = scanner.nextInt();
                            Supplier su = sc.getSupplierByID(ii);
                            if(su == null){
                                System.out.println("Error! no such supplier");
                                break;
                            }
                            System.out.println("Enter agreement ID");
                            int jj = scanner.nextInt();
                            Agreement mn = su.getAgreementByID(jj);
                            if(mn == null){
                                System.out.println("Error! no such agreement");
                                break;
                            }
                            su.removeAgreementByID(jj);
                            System.out.println("Success!");
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    shouldTerminate = true;
                    break;
            }
        }
        System.out.println("\nThanks for using our system, looking forward to seeing you again!");
    }

}
