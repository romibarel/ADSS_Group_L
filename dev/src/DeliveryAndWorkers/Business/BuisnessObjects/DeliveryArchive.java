package DeliveryAndWorkers.Business.BuisnessObjects;

import DeliveryAndWorkers.DataAccess.DALObjects.DALDeliveryDoc;
import DeliveryAndWorkers.DataAccess.DALObjects.DalArchive;
import DeliveryAndWorkers.DataAccess.DALObjects.DalDelivery;

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
            String approved = "approved";
            if (!delivery.isApproved())
                approved = "unapproved";
            String delivered = "delivered";
            if (!delivery.isDelivered())
                delivered = "in process";
            s += "Delivery with ID "+delivery.getID()+" from " + delivery.getSource().getAddress() +" at "+ delivery.getDate().toString()+" is "+approved+" and "+delivered+".\n";
            for (DeliverDoc doc : delivery.getDocsWOSource()){
                s += "Delivery List #" +doc.getNum()+ " Arrives to " + doc.getDestination().getAddress() +".\n";
                s += "Deliver/pickup: \n";
                for (Supply sup : doc.getDeliveryList()){
                    s += sup.getName() +"\t" + sup.getQuant()+"\n";
                }
            }
            s += "\n";
        }
        if (s.equals(""))
            return "No deliveries in the database.";
        return s;
    }

    public Delivery removeLastDel(){
        if (deliveries.size() == 0)
            return null;
        Delivery delivery = deliveries.get(0);
        for (Delivery del : deliveries){
            if (del.getID() > delivery.getID())
                delivery = del;
        }
        deliveries.remove(delivery);
        removeDocs(delivery.getDocs());
        return delivery;
    }

    public void removeDocs(List<DeliverDoc> docs){
        for (DeliverDoc doc : docs){
            if (documents.contains(doc.getNum())){
                Integer integer = doc.getNum();
                documents.remove(integer);
            }
        }
    }
}
