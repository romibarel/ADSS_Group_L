package DeliveryAndWorkers.Interface;

import DeliveryAndWorkers.Business.BTIController;
import javafx.util.Pair;

import java.util.Date;
import java.util.List;

public class ITBDelController {
    private static ITBDelController itb = null;
    private static BTIController bti;
    private static ITPDelController itp;

    private ITBDelController(){

    }

    public static ITBDelController getITB(){
        if (itb == null)
            itb = new ITBDelController();
        return itb;
    }

    public void set(){
        bti = BTIController.getBTI();
        itp = ITPDelController.getITP();
    }

    public String printArchive(){
        return  bti.printArchive();
    }

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String destination, List <Pair<String , Integer>> supplies){
        return bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
    }

    public String createDelivery(Date date, Date time, int truck ,int driverID, String source, List<Integer> docs, int truckWeight){
        return bti.createDelivery(date, time, truck, driverID, source, docs, truckWeight);
    }

    public int getMaxDocNum(){
        return bti.getMaxDocNum();
    }

}
