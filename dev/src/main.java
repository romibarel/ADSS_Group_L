import Business.BTDController;
import Business.BTIController;
import Business.DeliveryArchive;
import Business.Sections;
import DataAccess.DTBController;
import Interface.ITBController;
import Interface.ITPController;
import Presentation.PTIcontroller;

import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main (String[] args){
        PTIcontroller pti = PTIcontroller.getPTI();
        pti.setup();
        pti.start();
    }
}
