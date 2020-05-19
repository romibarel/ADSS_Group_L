package DataAccess;

import Business.Delivery;
import Business.DeliveryArchive;

import java.util.LinkedList;
import java.util.List;

public class DalArchive {
    private List<DalDelivery> deliveries;
    private List<Integer> documents;

    public DalArchive(){
        deliveries = new LinkedList<>();
        documents = new LinkedList<>();
    }

    public DalArchive(List<DalDelivery> deliveries, List<Integer> documents) {
        this.deliveries = deliveries;
        this.documents = documents;
    }

    public List<DalDelivery> getDeliveries() {
        return deliveries;
    }

    public List<Integer> getDocuments() {
        return documents;
    }

    public void add(DalDelivery delivery){
        deliveries.add(delivery);
        for (DALDeliveryDoc doc : delivery.getDocs()){
            documents.add(doc.getNum());
        }
    }
}
