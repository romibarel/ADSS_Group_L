package Suppliers.PersistenceLayer;

import javafx.util.Pair;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class DataController {
    private LinkedList<LoanSupplier> loanSuppliers;
    private LinkedList<LoanOrder> loanOrders;
    private LinkedList<LoanAgreement> loanAgreements;

    public DataController(){
        LoanProduct oliveOil = new LoanProduct(1, 20, "Olive Oil", "Zeita");
        LoanProduct milk = new LoanProduct(2, 5, "Milk", "Tara");
        LoanProduct bread = new LoanProduct(3, 10, "Bread", "Berman");
        LoanProduct butter = new LoanProduct(4, 3, "Butter", "Tnuva");
        LoanProduct cheese = new LoanProduct(5, 25, "Cheese", "Emek");

        LinkedList<String> contacts = new LinkedList<>();
        contacts.add("Yossi");
        loanSuppliers = new LinkedList<>();
        LoanSupplier s1 = new LoanSupplier("FixedDays", 1, "1-2-3", "Cash", contacts, "010");
        LoanSupplier s2 = new LoanSupplier("InviteOnly", 2, "2-3-1", "Credit", contacts, "020");
        LoanSupplier s3 = new LoanSupplier("SelfPickup", 3, "3-2-1", "Credit", contacts, "030");
        loanSuppliers.add(s1);
        loanSuppliers.add(s2);
        loanSuppliers.add(s3);

        HashMap<LoanProduct, Pair<Integer, Integer>> o1Products = new HashMap<>();
        HashMap<LoanProduct, Pair<Integer, Integer>> o2Products = new HashMap<>();
        HashMap<LoanProduct, Pair<Integer, Integer>> o3Products = new HashMap<>();
        o1Products.put(cheese, new Pair<>(20, 0));
        o1Products.put(butter, new Pair<>(5, 0));
        o2Products.put(milk, new Pair<>(20, 0));
        o2Products.put(bread, new Pair<>(30, 0));
        o3Products.put(oliveOil, new Pair<>(50, 0));
        loanOrders = new LinkedList<>();
        LoanOrder o1 = new LoanOrder(s1.getID(), LocalDate.now().plusDays(1), LocalDate.now(), o1Products);
        LoanOrder o2 = new LoanOrder(s2.getID(), LocalDate.now().plusDays(2), LocalDate.now(), o2Products);
        LoanOrder o3 = new LoanOrder(s3.getID(), LocalDate.now().plusDays(3), LocalDate.now(), o3Products);
        loanOrders.add(o1);
        loanOrders.add(o2);
        loanOrders.add(o3);

        loanAgreements = new LinkedList<>();
        LoanAgreement a1 = new LoanAgreement("Sale", new Pair<>(cheese, new Pair<>(20, 5)));
        LoanAgreement a2 = new LoanAgreement("Gift", new Pair<>(milk, new Pair<>(10, 1)));
        LoanAgreement a3 = new LoanAgreement("Sale", new Pair<>(oliveOil, new Pair<>(40, 8)));
        loanAgreements.add(a1);
        loanAgreements.add(a2);
        loanAgreements.add(a3);
    }

    public LinkedList<LoanAgreement> getLoanAgreements() {
        return loanAgreements;
    }

    public LinkedList<LoanOrder> getLoanOrders() {
        return loanOrders;
    }

    public LinkedList<LoanSupplier> getLoanSuppliers() {
        return loanSuppliers;
    }
}
