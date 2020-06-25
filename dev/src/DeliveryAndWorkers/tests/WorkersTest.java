package DeliveryAndWorkers.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import DeliveryAndWorkers.Business.ConstrainsController;
import DeliveryAndWorkers.Business.Result;
import DeliveryAndWorkers.Business.ShiftController;
import DeliveryAndWorkers.Business.WorkersController;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceConstraint;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceShift;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

public class WorkersTest
{



	@After
	public void after_tests()
	{
		WorkersController.getWorkers().clear();
		ShiftController.get_shifts().clear();
		ConstrainsController.getConstraints().clear();
	}


	@Test
	public void test_get_by_role1() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w1=new InterfaceWorker("avi cohen",1,12,1234,11,5,5,date,"manager","Mega");
		InterfaceWorker w2=new InterfaceWorker("avi levi",2,12,1234,11,5,5,date,"manager","Mega");
		InterfaceWorker w3=new InterfaceWorker("avi bitter",3,12,1234,11,5,5,date,"cashier","Mega");
		InterfaceWorker w4=new InterfaceWorker("shimon levi",4,12,1234,11,5,5,date,"cashier","Mega");
		InterfaceWorker w5=new InterfaceWorker("shimon cohen",5,12,1234,11,5,5,date,"driver","Mega");
		WorkersController.add_worker(w1,null);
		WorkersController.add_worker(w2,null);
		WorkersController.add_worker(w3,null);
		WorkersController.add_worker(w4,null);
		WorkersController.add_worker(w5,null);

		//no constraint were added so all managers should be returned
		assertTrue(2<= WorkersController.get_available_workers("manager",date,true,"Mega").size());
	}


	@Test
	public void test_get_worker_by_id() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",1553,12,1234,11,5,5,date,"manager","Mega");
		WorkersController.add_worker(w,null);
		assertEquals("avi cohen", WorkersController.get_by_id(1553).getName());
	}

	@Test
	public void test_fail_edit_worker() throws ParseException
	{
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		InterfaceWorker w2=new InterfaceWorker("avi bitter",1234,12,13,14,15,16,date3,"cashier","Mega");
		assertFalse(WorkersController.edit_worker(w2).success); //no worker with id 1234
	}

	@Test
	public void test_good_edit_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		Date date4=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		WorkersController.add_worker(w,null);
		InterfaceWorker w2=new InterfaceWorker("avi bitter",0,12,13,14,15,16,date4,"cashier","Mega"); //w2 has same id as w
		WorkersController.edit_worker(w2);
		assertEquals("avi bitter", WorkersController.get_by_id(0).getName());
	}



	@Test
	public void test_fail_delete_worker1() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(date2,true,0,null,"Mega");

		assertFalse(WorkersController.delete_worker(123456).success); //worker doest exist
	}

	@Test public void test_good_delete_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",17989,12,1234,11,5,5,date,"manager","Mega");
		WorkersController.add_worker(w,null);
		assertTrue(WorkersController.delete_worker(17989).success);
	}

	@Test
	public void add_worker_wrong_name() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setName("");
		assertFalse(WorkersController.add_worker(w,null).success);
		w.setName(null);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_bank_account_number() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setBank_account_number(-5);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_salary() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setSalary(-5);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_pension() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setPension(-5);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_vacation_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setVacation_days(-5);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_sick_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setSick_days(-5);
		assertFalse(WorkersController.add_worker(w,null).success);
	}

	@Test
	public void add_worker_wrong_date() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setStart_date(null);
		Result res =WorkersController.add_worker(w,new LinkedList<>());
		assertFalse(res.success);
	}

	@Test
	public void add_worker_wrong_role() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager","Mega");
		w.setRole("");
		assertFalse(WorkersController.add_worker(w,null).success);
		w.setRole(null);
		assertFalse(WorkersController.add_worker(w,null).success);
	}


}
