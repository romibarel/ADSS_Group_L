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
        this.pti = PTIcontroller.getPTI();
        this.itb = ITBController.getITB();
    }

    public static ITPController getITP(){
        if (itp == null)
            itp = new ITPController();
        return itp;
    }

    public void createDoc(int docNum, String[] doc){
        itb.createDoc(docNum, doc);
    }

    public void createDelivery(Date date, Date time, int truck, String driver, String source, List<Integer> docs){
        itb.createDelivery(date, time, truck, driver, source, docs);
    }

}

