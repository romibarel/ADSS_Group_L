package Buisness.Locations;

import java.util.*;

public class LocationController {
    public static final int DEFECTS = 0;
    public static final int STORAGE = 1;
    public static final int SHELF = 2;
    public static final int MINOR_STORAGE = 3;

    private Map<Integer , Map<Date , Map<Integer, Integer>>> productsLocation; // <barcode, <expirationDate, <location, quantity>
    private Map<Integer , String> locations; // <locationNumber , locationName>

    public LocationController(){
        this.productsLocation = new HashMap<Integer, Map<Date, Map<Integer, Integer>>>();
        this.locations = new HashMap();
        locations.put(DEFECTS, "Buisness/Defects");
        locations.put(STORAGE, "Storage");
        locations.put(SHELF, "Shelf");
        locations.put(MINOR_STORAGE, "Minor Storage");
    }

    public Map<Integer, Map<Date, Map<Integer, Integer>>> getProductsLocation() {
        return productsLocation;
    }

    public void setProductsLocation(Map<Integer, Map<Date, Map<Integer, Integer>>> productsLocation) {
        this.productsLocation = productsLocation;
    }

    public Map<Integer, String> getLocations() {
        return locations;
    }

    public void setLocations(Map<Integer, String> locations) {
        this.locations = locations;
    }

    public void addProduct(int barCode, Date expirationDate, int location, int amount) {
        if (0>location || location>=locations.size()){return;}  //location isn't exists -> doesn't do nothing
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
        if (0>fromLocation || fromLocation>=locations.size()){return;}  //location isn't exists -> doesn't do nothing
        if (0>toLocation || toLocation>=locations.size()){return;}  //location isn't exists -> doesn't do nothing
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

    public boolean reduceFromShelf(int barCode, int amount, Date expiration){
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

    public List<Date> getBarcodDates (int barcode){
        List<Date> dateList = new ArrayList(productsLocation.get(barcode).keySet());
        return dateList;
    }

    public List<Integer> getLocationsByDate(int barcode , Date date){
        List<Integer> locationSet = new ArrayList(productsLocation.get(barcode).get(date).keySet());
        return locationSet;
    }

    public Integer getAmountByLocation(int barcode , Date date , Integer location){
       Integer amount = productsLocation.get(barcode).get(date).get(location);
       return amount;
    }
}
