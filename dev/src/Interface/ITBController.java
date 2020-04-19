package Interface;

import Business.BTIController;

import java.util.Date;
import java.util.List;

public class ITBController {
    private static ITBController itb = null;
    private static BTIController bti;
    private static ITPController itp;

    private ITBController(){

    }

    public static ITBController getITB(){
        if (itb == null)
            itb = new ITBController();
        return itb;
    }

    public void set(BTIController bti, ITPController itp){
        ITBController.bti = bti;
        ITBController.itp = itp;
    }

    //destination, supplies&quants,
    //doc0=destination doc1=long string of format: supply1 quant1, supply2, quant2...
    public String createDoc(int docNum, String[] doc){
       return bti.createDoc(docNum, doc);
    }

    public String createDelivery(Date date, Date time, int truck, String driver, String source, List<Integer> docs){
        return bti.createDelivery(date, time, truck, driver, source, docs);
    }
}
