import Storage.Tests.CategoryTest;
import Storage.Tests.LocationControllerTest;
import Storage.Tests.ProductControllerTest;
import Storage.Tests.PurchaseTransactionTest;
import StorageAndSupplier.API_Buisness;
import StorageAndSupplier.Presentation.Presentation;
import StorageAndSupplier.Singltone_Supplier_Storage_Manager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;


public class main {



    public static void main (String[] args){

        Presentation p = new Presentation();
        p.loginUser();


    }


}
