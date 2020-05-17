package Interface;

import Business.DeliverDoc;
import Business.Driver;
import Business.Location;
import Business.Truck;
import Presentation.PTIDelController;
import Presentation.PTIDelController;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ITPDelController {
    private static ITPDelController itp = null;
    private static PTIDelController pti;
    private static ITBDelController itb;

    private ITPDelController(){

    }

    public static ITPDelController getITP(){
        if (itp == null)
            itp = new ITPDelController();
        return itp;
    }

    public void set(){
        pti = PTIDelController.getPTI();
        itb = ITBDelController.getITB();
    }

    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String[] doc){
        return itb.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, doc);
    }

    public String createDelivery(Date date, Date time, int truck, int driverID, String source, List<Integer> docs, int truckWeight){
        return itb.createDelivery(date, time, truck, driverID, source, docs, truckWeight);
    }

}
