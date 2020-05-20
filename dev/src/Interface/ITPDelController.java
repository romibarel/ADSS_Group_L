package Interface;

import Presentation.PTIDelController;
import javafx.util.Pair;

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

    public String printArchive(){
        return itb.printArchive();
    }

    public String createDoc(Date estimatedTimeOfArrival, Date estimatedDayOfArrival, int docNum, String destination, List <Pair<String , Integer>> supplies){
        return itb.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
    }

    public String createDelivery(Date date, Date time, int truck, int driverID, String source, List<Integer> docs, int truckWeight){
        return itb.createDelivery(date, time, truck, driverID, source, docs, truckWeight);
    }

}

