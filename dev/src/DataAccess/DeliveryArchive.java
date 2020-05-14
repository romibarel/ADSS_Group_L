package DataAccess;

import java.util.LinkedList;
import java.util.List;

public class DeliveryArchive {
    private List<Delivery> deliveries;
    private List<Integer> documents;


    public boolean addDelivery (Delivery delivery)
    {
        return deliveries.add(delivery);
    }

    public boolean removeDelivery(Delivery delivery)
    {
        return deliveries.remove(delivery);
    }

    public boolean addDocument (int document)
    {
        return documents.add(document);
    }

    public boolean removeDocument(Integer document)
    {
        return documents.remove(document);
    }



    /**
     * @param deliveries can be null for empty list
     * @param documents can be null for empty list
     */
    public DeliveryArchive(List<Delivery> deliveries, List<Integer> documents) {
        this.deliveries = deliveries != null ? deliveries : new LinkedList<>();
        this.documents = documents != null ? documents : new LinkedList<>();
    }


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
