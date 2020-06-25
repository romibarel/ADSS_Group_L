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
		String result = bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
		String expected = "Delivery list to "+ destination +" created successfully.";
		assertEquals(result ,expected);
	}


	@Test
	public void createDocFail() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("10/11/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int docNum = bti.getMaxDocNum() + 1;
		String destination = "we don't work with the open university destination";
		List<Pair<String , Integer>> supplies = new LinkedList<>();
		String result = bti.createDoc(estimatedTimeOfArrival, estimatedDayOfArrival, docNum, destination, supplies);
		String expected = "The destination doesn't exist.";
		assertEquals(result ,expected);
	}


	@Test
	public void createDelivery() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("10/11/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int driverID = 18;
		int truckID = 18;

		String sourceAddress = "Super Lee";

		List<Integer> docNums = new LinkedList<>();
		int truckWeight = 100;
		String result = bti.createDelivery(estimatedDayOfArrival , estimatedTimeOfArrival, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "The destination doesn't exist.";
		assertEquals(result ,expected);
	}

	@Test
	public void createDeliveryFailTruck() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("10/11/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int driverID = 18;
		int truckID = 967678197;	// truck doesn't exists

		String sourceAddress = "Super Lee";

		List<Integer> docNums = new LinkedList<>();
		int truckWeight = 100;
		String result = bti.createDelivery(estimatedDayOfArrival , estimatedTimeOfArrival, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "The given truck doesn't exist.";
		assertEquals(result ,expected);
	}
	@Test
	public void createDeliveryFail() throws ParseException
	{
		BTIController bti = BTIController.getBTI();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date estimatedDayOfArrival = dateFormat.parse("10/11/2023");

		DateFormat timeFormat = new SimpleDateFormat("hh:mm");
		Date estimatedTimeOfArrival = timeFormat.parse("15:30");

		int driverID = 18;
		int truckID = 18;	// truck doesn't exists

		String sourceAddress = "Super Lee";

		List<Integer> docNums = new LinkedList<>();
		int truckWeight = 817056978;	// too heavy
		String result = bti.createDelivery(estimatedDayOfArrival , estimatedTimeOfArrival, truckID, driverID, sourceAddress, docNums, truckWeight);
		String expected = "The given truck exceeds its max weight";
		assertEquals(result ,expected);
	}


}
