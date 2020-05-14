package Interface;

import Business.BTIController;
import Business.Delivery;
import Business.Location;

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

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String[] doc){
        return bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, doc);
    }

    public String createDelivery(Date date, Date time, int truck ,int driverID, String source, List<Integer> docs, int truckWeight){
        return bti.createDelivery(date, time, truck, driverID, source, docs, truckWeight);
    }

}
