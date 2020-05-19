package Business;

import java.util.LinkedList;
import java.util.List;

public class DeliveryArchive {
    private List<Delivery> deliveries;
    private List<Integer> documents;

    public DeliveryArchive(){
        deliveries = new LinkedList<>();
        documents = new LinkedList<>();
    }

    public DeliveryArchive(DALDeliveryArchive archive) {

    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Integer> getDocuments() {
        return documents;
    }

    public void add(Delivery delivery){
        deliveries.add(delivery);
        for (DeliverDoc doc : delivery.getDocs()){
            documents.add(doc.getNum());
        }
    }

}
