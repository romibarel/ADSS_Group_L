package Interface;

import Business.BTIController;

import java.util.Date;
import java.util.List;

public class ITBController {
    private static ITBController itb = null;
    private static BTIController bti;
    private static ITPController itp;

    private ITBController(){
        this.bti = BTIController.getBTI(null, null, null, null, null);
        this.itp = ITPController.getITP();
    }

    public static ITBController getITB(){
        if (itb == null)
            itb = new ITBController();
        return itb;
    }

    public void createDoc(int docNum, String[] doc){
        bti.createDoc(docNum, doc);
    }

    public void createDelivery(Date date, Date time, int truck, String driver, String source, List<Integer> docs){
        bti.createDelivery(date, time, truck, driver, source, docs);
    }
}
