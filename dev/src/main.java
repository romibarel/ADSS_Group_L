import Business.BTDController;
import Business.BTIController;
import Business.Sections;
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
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        pti.set(itp);
        itp.set(pti, itb);
        itb.set(bti, itp);
        bti.set(itb, btd, supplies, drivers, sections, locations, trucks);

        pti.start();
    }
}
