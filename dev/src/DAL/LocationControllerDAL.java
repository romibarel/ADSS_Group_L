package DAL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LocationControllerDAL {
    private Map<Integer , Map<Date, Map<Integer, Integer>>> productsLocationDAL; // <barcode, <expirationDate, <location, quantity>
    private Map<Integer , String> locationsDAL; // <locationNumber , locationName>

    public LocationControllerDAL(){
        this.productsLocationDAL = new HashMap<Integer, Map<Date, Map<Integer, Integer>>>();
        this.locationsDAL = new HashMap();
    }

    public Map<Integer, Map<Date, Map<Integer, Integer>>> getProductsLocationDAL() {
        return productsLocationDAL;
    }

    public void setProductsLocationDAL(Map<Integer, Map<Date, Map<Integer, Integer>>> productsLocationDAL) {
        this.productsLocationDAL = productsLocationDAL;
    }

    public Map<Integer, String> getLocationsDAL() {
        return locationsDAL;
    }

    public void setLocationsDAL(Map<Integer, String> locationsDAL) {
        this.locationsDAL = locationsDAL;
    }

    public void updateProductsQuantityInLocation (int barCode, Date expirationDate, int location, int quantity){
        if (this.productsLocationDAL.get(barCode) == null){ //if product doesn't exists
            productsLocationDAL.put(barCode, new HashMap<>());
            productsLocationDAL.get(barCode).put(expirationDate, new HashMap<>());
            productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        }
        else if (this.productsLocationDAL.get(barCode).get(expirationDate) == null){ //product exists but expiration date doesn't exists
            productsLocationDAL.get(barCode).put(expirationDate, new HashMap<Integer, Integer>());
            productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        }
        else if (this.productsLocationDAL.get(barCode).get(expirationDate).get(location)==null){    //product exists, exp date exists, location doesn't exists
            productsLocationDAL.get(barCode).get(expirationDate).put(location, quantity);
        }
        else{
            this.productsLocationDAL.get(barCode).get(expirationDate).replace(location, quantity);
        }
    }
}
