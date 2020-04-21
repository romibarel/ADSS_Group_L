package Tests;

import Business.*;
import DataAccess.DTBController;
import Interface.ITBController;
import Interface.ITPController;
import Presentation.PTIcontroller;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;

public class TestConstraints {


    @Test
    public void wrongDriver() throws ParseException {
        Supply f = new Supply("First", 1);
        List<Supply> supplyList = new LinkedList<>();
        supplyList.add(f);
        Location destination = new Location("Super Lee", 518, "Haim");
        List<Location> destinations = new LinkedList<>();
        destinations.add(destination);
        Location source = new Location("Lee Office", 516, "Romi");
        DeliverDoc doc = new DeliverDoc(1, supplyList, destination);
        List<DeliverDoc> docs = new LinkedList<>();
        docs.add(doc);
        Truck truck = new Truck(1, 111, 1000, 4000, "Mazda");
        List<String> lisences = new LinkedList<>();
        lisences.add("Toyota");
        Driver driver = new Driver(lisences, "Moshe");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");
        Delivery delivery = new Delivery(date, time, truck, driver, source, destinations, docs, 3000);
        assertFalse(delivery.isApproved());
    }

    @Test
    public void overWeight() throws ParseException {
        Supply f = new Supply("First", 1);
        List<Supply> supplyList = new LinkedList<>();
        supplyList.add(f);
        Location destination = new Location("Super Lee", 518, "Haim");
        List<Location> destinations = new LinkedList<>();
        destinations.add(destination);
        Location source = new Location("Lee Office", 516, "Romi");
        DeliverDoc doc = new DeliverDoc(1, supplyList, destination);
        List<DeliverDoc> docs = new LinkedList<>();
        docs.add(doc);
        Truck truck = new Truck(1, 111, 1000, 4000, "Mazda");
        List<String> lisences = new LinkedList<>();
        lisences.add("Mazda");
        Driver driver = new Driver(lisences, "Moshe");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");
        Delivery delivery = new Delivery(date, time, truck, driver, source, destinations, docs, 5000);
        assertFalse(delivery.isApproved());
    }

    @Test
    public void goodDelivery() throws ParseException {
        Supply f = new Supply("First", 1);
        List<Supply> supplyList = new LinkedList<>();
        supplyList.add(f);
        Location destination = new Location("Super Lee", 518, "Haim");
        List<Location> destinations = new LinkedList<>();
        destinations.add(destination);
        Location source = new Location("Lee Office", 516, "Romi");
        DeliverDoc doc = new DeliverDoc(1, supplyList, destination);
        List<DeliverDoc> docs = new LinkedList<>();
        docs.add(doc);
        Truck truck = new Truck(1, 111, 1000, 4000, "Mazda");
        List<String> lisences = new LinkedList<>();
        lisences.add("Mazda");
        Driver driver = new Driver(lisences, "Moshe");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");
        Delivery delivery = new Delivery(date, time, truck, driver, source, destinations, docs, 3000);
        assertTrue(delivery.isApproved());
    }

    @Test
    public void createBTI() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);
        Location superLee = new Location("Super Lee", 052, "Haim");

        assertFalse(bti.getSupplies().isEmpty());
        assertFalse(bti.getDrivers().isEmpty());
        assertFalse(bti.getTrucks().isEmpty());
        assertEquals(1, bti.getSections().getSection(superLee));
        assertTrue(bti.getDocuments().isEmpty());
    }

    @Test
    public void BTIcreateDoc() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        bti.createDoc(0, new String[] {"Super Lee", "First 1 Second 2"});

        assertFalse(bti.getDocuments().isEmpty());
        assertEquals(0, bti.getDocuments().get(0).getNum());
    }

    @Test
    public void BTIcreateDelivery() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        bti.createDoc(0, new String[] {"Super Lee", "First 1 Second 2"});
        List<Integer> docs = new LinkedList<>();
        docs.add(0);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");

        assertEquals("Delivery was created successfully!", bti.createDelivery(date, time, 1, "Moshe", "Lee Office", docs, 3000));
        assertFalse(bti.getArchive().getDeliveries().isEmpty());
        assertEquals(1, bti.getArchive().getDeliveries().get(0).getTruckNum());
    }

    @Test
    public void BTIcreateDeliveryWrongDriver() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        bti.createDoc(0, new String[] {"Super Lee", "First 1 Second 2"});
        List<Integer> docs = new LinkedList<>();
        docs.add(0);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");

        assertEquals("The driver is unlicensed for the given truck.", bti.createDelivery(date, time, 1, "Joseph", "Lee Office", docs, 3000));
        assertTrue(bti.getArchive().getDeliveries().isEmpty());
    }

    @Test
    public void BTIcreateDeliveryOverWeight() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        bti.createDoc(0, new String[] {"Super Lee", "First 1 Second 2"});
        List<Integer> docs = new LinkedList<>();
        docs.add(0);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020");
        Date time = new SimpleDateFormat("HH:mm").parse("09:00");

        assertEquals("The truck exceeds its max weight", bti.createDelivery(date, time, 1, "Moshe", "Lee Office", docs, 5000));
        assertTrue(bti.getArchive().getDeliveries().isEmpty());
    }

    @Test
    public void BTIaddSupply() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        bti.addSupply("First", 3);
        bti.addSupply("Fourth", 4);

        assertEquals(5, bti.getSupplies().get(0).getQuant());
        assertEquals("Fourth", bti.getSupplies().get(3).getName());
    }

    @Test
    public void BTIcreateBadDoc() throws ParseException {
        BTIController bti = BTIController.getBTI();
        List<String[]> supplies = new LinkedList<>();
        List<String[]> drivers = new LinkedList<>();
        List<String[]> sections = new LinkedList<>();
        List<String[]> locations = new LinkedList<>();
        List<String[]> trucks = new LinkedList<>();

        supplies.add(new String[] {"First", "2"});
        supplies.add(new String[] {"Second", "5"});
        supplies.add(new String[] {"Third", "10"});

        drivers.add(new String[] {"Moshe", "Mazda", "Toyota"});
        drivers.add(new String[] {"Joseph", "Mercedes"});

        sections.add(new String[] {"1", "Super Lee", "Lee Office"});
        sections.add(new String[] {"2", "Shufersal", "Max Stock", "Best Buy"});
        sections.add(new String[] {"3", "Mega", "Costco"});

        locations.add(new String[] {"Super Lee", "052", "Haim"});
        locations.add(new String[] {"Lee Office", "058", "Romi"});
        locations.add(new String[] {"Shufersal", "054", "Tony"});
        locations.add(new String[] {"Max Stock", "050", "Ziv"});
        locations.add(new String[] {"Best Buy", "055", "Tomer"});
        locations.add(new String[] {"Mega", "053", "Sivan"});
        locations.add(new String[] {"Costco", "057", "Gali"});

        //int truckNum, int plate, int weighNeto, int maxWeight, String type
        trucks.add(new String[] {"1", "111", "1000", "4000", "Mazda"});
        trucks.add(new String[] {"2", "222", "1200", "7000", "Toyota"});
        trucks.add(new String[] {"3", "333", "1100", "5500", "Mercedes"});
        trucks.add(new String[] {"4", "123", "2000", "4000", "Mazda"});

        bti.set(null, null, supplies, drivers, sections, locations, trucks);

        assertEquals("The destination doesn't exist.", bti.createDoc(0, new String[] {"Neverland", "First 1 Second 2"}));
        assertTrue(bti.getDocuments().isEmpty());
    }
}
