import Business.BTDController;
import Business.BTIController;
import Interface.ITBController;
import Interface.ITPController;
import Presentation.PTIcontroller;

import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main (String[] args){
        PTIcontroller pti = PTIcontroller.getPTI();
        ITPController itp = ITPController.getITP();
        ITBController itb = ITBController.getITB();
        BTIController bti = BTIController.getBTI();
        BTDController btd = BTDController.getBTD();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> driver = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        pti.set(itp);
        itp.set(pti, itb);
        itb.set(bti, itp);
        bti.set(itb, btd, supplies, driver, sections, locations, trucks);
    }
}
