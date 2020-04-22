package Interface;

import Business.DeliverDoc;
import Business.Driver;
import Business.Location;
import Business.Truck;
import Presentation.PTIcontroller;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ITPController {
    private static ITPController itp = null;
    private static PTIcontroller pti;
    private static ITBController itb;

    private ITPController(){

    }

    public static ITPController getITP(){
        if (itp == null)
            itp = new ITPController();
        return itp;
    }

    public void set(PTIcontroller pti, ITBController itb){
        ITPController.pti = pti;
        ITPController.itb = itb;
    }

    public String createDoc(int docNum, String[] doc){
       return itb.createDoc(docNum, doc);
    }

    public String createDelivery(Date date, Date time, int truck, String driver, String source, List<Integer> docs, int truckWeight){
        return itb.createDelivery(date, time, truck, driver, source, docs, truckWeight);
    }

    public String addSupply(String name, int num) {

        return itb.addSupply(name , num);
    }

    public void arriveAt(String dest){
        pti.arriveAt(dest);
    }

    public void execute(int docNum){
        itb.execute(docNum);
    }
}

