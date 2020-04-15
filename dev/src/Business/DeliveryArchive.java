package Business;

import java.util.List;

public class DeliveryArchive {
    private List<Delivery> deliveries;
    private List<Integer> documents;


    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Integer> getDocuments() {
        return documents;
    }

    public void add(Business.Delivery del){
        deliveries.add(del);
        List<DeliverDoc> docs = del.getDocs();
        for (DeliverDoc d : docs){
            documents.add(d.getNum());
        }
    }

}
