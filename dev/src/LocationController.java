import java.util.*;

public class LocationController {
    public static final int DEFECTS = 0;
    public static final int STORAGE = 1;
    public static final int SHELF = 2;

    private Map<Integer , Map<Date , Map<Integer, Integer>>> productsLocation; // <barcode, <expirationDate, <location, quantity>
    private Map<Integer , String> locations; // <locationNumber , locationName>

    public LocationController(){
        this.productsLocation = new HashMap<Integer, Map<Date, Map<Integer, Integer>>>();
        this.locations = new HashMap();
    }

    public void addProduct(int barCode, Date expirationDate, int location, int amount) {
        if (productsLocation.containsKey(barCode)){ //barcode exists in locations
            if (productsLocation.get(barCode).containsKey(expirationDate)){ //barcode with expiration date exists
                if (productsLocation.get(barCode).get(expirationDate).containsKey(location)){ //barcode with expiration date exists and the location exists
                    int oldAmount = productsLocation.get(barCode).get(expirationDate).get(location);
                    productsLocation.get(barCode).get(expirationDate).remove(location);
                    int newValue = oldAmount + amount;
                    productsLocation.get(barCode).get(expirationDate).put(location, newValue);
                }
                else {  //barcode with expiration date exists but the location doesn't exists
                    productsLocation.get(barCode).get(expirationDate).put(location, amount);
                }
            }
            else{   //barcode exists but expiration date doesn't exists yet
                productsLocation.get(barCode).put(expirationDate, new HashMap<Integer, Integer>());
                productsLocation.get(barCode).get(expirationDate).put(location, amount);
            }
        }
        else{//barcode  doesn't exists
            productsLocation.put(barCode, new HashMap<>());
            productsLocation.get(barCode).put(expirationDate, new HashMap<Integer, Integer>());
            productsLocation.get(barCode).get(expirationDate).put(location, amount);
        }
    }

    public void moveProduct(int barCode, Date expiration, int amount, int fromLocation, int toLocation) {
        if (this.productsLocation.containsKey(barCode)&&
                this.productsLocation.get(barCode).containsKey(expiration)&&
                this.productsLocation.get(barCode).get(expiration).containsKey(fromLocation)&&
                this.productsLocation.get(barCode).get(expiration).get(fromLocation)>=amount){//checks if this is legal
            int oldValue = this.productsLocation.get(barCode).get(expiration).get(fromLocation);
            int newValue = oldValue - amount;
            this.productsLocation.get(barCode).get(expiration).replace(fromLocation, newValue);
            addProduct(barCode, expiration, toLocation, amount);
        }
    }

    boolean reduceFromShelf(int barCode, int amount, Date expiration){
        if(this.productsLocation.containsKey(barCode)&&
                this.productsLocation.get(barCode).containsKey(expiration)&&
                this.productsLocation.get(barCode).get(expiration).containsKey(SHELF)&&
                this.productsLocation.get(barCode).get(expiration).get(SHELF)>=amount){//Check if this is legal, otherwise doesn't do anything
            int oldValue = this.productsLocation.get(barCode).get(expiration).get(SHELF);
            int newValue = oldValue - amount;
            this.productsLocation.get(barCode).get(expiration).replace(SHELF, newValue);
            return true;
        }
        return false;
    }
}
