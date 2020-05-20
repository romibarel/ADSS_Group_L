package Business.BuisnessObjects;

import DataAccess.DALObjects.DALDeliveryDoc;
import DataAccess.DALObjects.DalArchive;
import DataAccess.DALObjects.DalDelivery;

import java.util.LinkedList;
import java.util.List;

public class DeliveryArchive {
    private List<Delivery> deliveries;
    private List<Integer> documents;

    public DeliveryArchive(){
        deliveries = new LinkedList<>();
        documents = new LinkedList<>();
    }

    public DeliveryArchive(DalArchive dalArchive, List<DALDeliveryDoc> docs) {
        deliveries = new LinkedList<>();
        documents = new LinkedList<>();

        for (DalDelivery daldel : dalArchive.getDeliveries()){
            Delivery newDel = new Delivery(daldel, docs);
            deliveries.add(newDel);
        }
        for (Integer docNum : dalArchive.getDocuments()){
            documents.add(docNum);
        }
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

    public String toString(){
        String s = "";
        for (Delivery delivery : deliveries){
            s = s + "Delivery from " + delivery.getSource().getAddress() +".\n";
            for (DeliverDoc doc : delivery.getDocsWOSource()){
                s = s + "arrives to " + doc.getDestination().getAddress() +".\n";
            }
            s = s + "\n";
        }
        if (s.equals(""))
            return "No deliveries in the database.";
        return s;
    }

}
