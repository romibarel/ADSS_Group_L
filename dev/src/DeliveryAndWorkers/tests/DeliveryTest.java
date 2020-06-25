package DeliveryAndWorkers.tests;

import DeliveryAndWorkers.Business.*;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class DeliveryTest
{

	@Test
	public void createDocSuccess() throws ParseException {
		BTIController bti = BTIController.getBTI();
		bti.set();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("10/10/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("11:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "Super Lee";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		supplies.add(new Pair<>("one", 2));
		String result = bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
		String expected = "Delivery list to Super Lee created successfully.";
		assertEquals(result ,expected);
		bti.deleteDoc(docNum);
	}


	@Test
	public void createDocFail() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		bti.set();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("11/10/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "we don't work with the open university destination";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		supplies.add(new Pair<>("one", 2));
		String result = bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
		String expected = "The destination doesn't exist.";
		assertEquals(result ,expected);
	}


	@Test
	public void createDelivery() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		bti.set();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfDeparture = dateFormat.parse("11/10/2023");
		Date estimatedDayOfArrival = dateFormat.parse("11/10/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfDeparture = timeFormat.parse("11:30");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "Super Lee";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		supplies.add(new Pair<>("one", 2));

		int driverID = 18;
		int truckID = 1;

		String sourceAddress = "Gucci";
		bti.createDoc(estimatedTimeOfDeparture, estimatedDayOfDeparture, docNum, sourceAddress, supplies);
		bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum+1, destination, supplies);

		List<Integer> docNums = new LinkedList<>();
		docNums.add(docNum);
		docNums.add(docNum+1);

		int truckWeight = 4500;
		String result = bti.createDelivery(estimatedDayOfDeparture , estimatedTimeOfDeparture, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "Delivery was created successfully! The delivery and all its documents were saved to the database.";
		assertEquals(result ,expected);

		bti.deleteDoc(docNum);
		bti.deleteDoc(docNum+1);
		bti.deleteDelivery();
		ShiftController.delete_shift(estimatedDayOfDeparture, true, "Mega");
		ShiftController.delete_shift(estimatedDayOfArrival, false, "Mega");
		ShiftController.delete_shift(estimatedDayOfArrival, false, "Super Lee");
	}

	@Test
	public void createDeliveryFailTruck() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		bti.set();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfDeparture = dateFormat.parse("12/10/2023");
		Date estimatedDayOfArrival = dateFormat.parse("122/10/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfDeparture = timeFormat.parse("11:30");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "Super Lee";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		supplies.add(new Pair<>("one", 2));

		int driverID = 18;
		int truckID = 60; //Truck doesn't exist

		String sourceAddress = "Gucci";
		bti.createDoc(estimatedTimeOfDeparture, estimatedDayOfDeparture, docNum, sourceAddress, supplies);
		bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum+1, destination, supplies);

		List<Integer> docNums = new LinkedList<>();
		docNums.add(docNum);
		docNums.add(docNum+1);

		int truckWeight = 4500;
		String result = bti.createDelivery(estimatedDayOfDeparture , estimatedTimeOfDeparture, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "The given truck doesn't exist.";
		assertEquals(result ,expected);

		bti.deleteDoc(docNum);
		bti.deleteDoc(docNum+1);
	}
	@Test
	public void createUnfitDriver() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		bti.set();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfDeparture = dateFormat.parse("13/10/2023");
		Date estimatedDayOfArrival = dateFormat.parse("13/10/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfDeparture = timeFormat.parse("11:30");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "Super Lee";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		supplies.add(new Pair<>("one", 2));

		int driverID = 16;
		int truckID = 1;

		String sourceAddress = "Gucci";
		bti.createDoc(estimatedTimeOfDeparture, estimatedDayOfDeparture, docNum, sourceAddress, supplies);
		bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum+1, destination, supplies);

		List<Integer> docNums = new LinkedList<>();
		docNums.add(docNum);
		docNums.add(docNum+1);

		int truckWeight = 4500;
		String result = bti.createDelivery(estimatedDayOfDeparture , estimatedTimeOfDeparture, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "The driver is unlicensed for the given truck.";
		assertEquals(result ,expected);

		bti.deleteDoc(docNum);
		bti.deleteDoc(docNum+1);
	}


}
