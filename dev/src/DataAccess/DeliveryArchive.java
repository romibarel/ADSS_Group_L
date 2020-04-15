package DataAccess;

import java.util.List;

public class DeliveryArchive {
    private List<Delivery> deliveries;
    private List<Integer> documents;


    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Integer> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Integer> documents) {
        this.documents = documents;
    }


}
