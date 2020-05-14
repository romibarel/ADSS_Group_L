package Tests;

import Interface.InterfaceConstraint;
import Interface.InterfaceShift;
import Interface.InterfaceWorker;
import Business.ConstrainsController;
import Business.ShiftController;
import Business.WorkersController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		InterfaceWorker w1=new InterfaceWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		InterfaceWorker w2=new InterfaceWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		InterfaceWorker w3=new InterfaceWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w4=new InterfaceWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w5=new InterfaceWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersController.add_worker(w1);
		WorkersController.add_worker(w2);
		WorkersController.add_worker(w3);
		WorkersController.add_worker(w4);
		WorkersController.add_worker(w5);

		//no constraint were added so all managers should be returned
		assertEquals(2, WorkersController.get_by_role("manager",date,true).size());
	}

	@Test
	public void test_get_by_role2() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w1=new InterfaceWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		InterfaceWorker w2=new InterfaceWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		InterfaceWorker w3=new InterfaceWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w4=new InterfaceWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w5=new InterfaceWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersController.add_worker(w1);
		WorkersController.add_worker(w2);
		WorkersController.add_worker(w3);
		WorkersController.add_worker(w4);
		WorkersController.add_worker(w5);

		//no constraint were added so all cashiers should be returned
		assertEquals(2, WorkersController.get_by_role("cashier",date,true).size());

		//no constraint were added so all drivers should be returned
		assertEquals(1, WorkersController.get_by_role("driver",date,true).size());

		InterfaceConstraint c1=new InterfaceConstraint(date,true,w3.getId(),"wedding",0);
		InterfaceConstraint c2=new InterfaceConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsController.addConstraint(c1);
		ConstrainsController.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0, WorkersController.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_by_role3() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w1=new InterfaceWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		InterfaceWorker w2=new InterfaceWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		InterfaceWorker w3=new InterfaceWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w4=new InterfaceWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w5=new InterfaceWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersController.add_worker(w1);
		WorkersController.add_worker(w2);
		WorkersController.add_worker(w3);
		WorkersController.add_worker(w4);
		WorkersController.add_worker(w5);

		//no constraint were added so all drivers should be returned
		assertEquals(1, WorkersController.get_by_role("driver",date,true).size());

		InterfaceConstraint c1=new InterfaceConstraint(date,true,w3.getId(),"wedding",0);
		InterfaceConstraint c2=new InterfaceConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsController.addConstraint(c1);
		ConstrainsController.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0, WorkersController.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_by_role4() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w1=new InterfaceWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		InterfaceWorker w2=new InterfaceWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		InterfaceWorker w3=new InterfaceWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w4=new InterfaceWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		InterfaceWorker w5=new InterfaceWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersController.add_worker(w1);
		WorkersController.add_worker(w2);
		WorkersController.add_worker(w3);
		WorkersController.add_worker(w4);
		WorkersController.add_worker(w5);

		InterfaceConstraint c1=new InterfaceConstraint(date,true,w3.getId(),"wedding",0);
		InterfaceConstraint c2=new InterfaceConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsController.addConstraint(c1);
		ConstrainsController.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0, WorkersController.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_worker_by_id() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersController.add_worker(w);
		assertEquals("avi cohen", WorkersController.get_by_id(0).getName());
	}

	@Test
	public void test_fail_edit_worker() throws ParseException
	{
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		InterfaceWorker w2=new InterfaceWorker("avi bitter",1234,12,13,14,15,16,date3,"cashier");
		assertFalse(WorkersController.edit_worker(w2).success); //no worker with id 1234
	}

	@Test
	public void test_good_edit_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		Date date4=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		WorkersController.add_worker(w);
		InterfaceWorker w2=new InterfaceWorker("avi bitter",0,12,13,14,15,16,date4,"cashier"); //w2 has same id as w
		WorkersController.edit_worker(w2);
		assertEquals("avi bitter", WorkersController.get_by_id(0).getName());
	}

	@Test
	public void test_fail_delete_worker2() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(date2,true,0,null);

		WorkersController.add_worker(w);
		s.setManager_id(0); //assign w to be the manager in s
		ShiftController.add_shift(s);
		WorkersController.delete_worker(0); // worker is scheduled for shift so no delete
		assertEquals(1, WorkersController.getWorkers().size());
	}
	@Test
	public void test_fail_delete_worker1() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(date2,true,0,null);

		assertFalse(WorkersController.delete_worker(123456).success); //worker doest exist
	}

	@Test public void test_good_delete_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersController.add_worker(w);
		WorkersController.delete_worker(0);
		assertTrue(WorkersController.getWorkers().isEmpty());
	}

	@Test
	public void test_good_add_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersController.add_worker(w);
		assertEquals(1, WorkersController.getWorkers().size());
	}

	@Test
	public void add_worker_wrong_name() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setName("");
		assertFalse(WorkersController.add_worker(w).success);
		w.setName(null);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_bank_account_number() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setBank_account_number(-5);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_salary() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setSalary(-5);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_pension() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setPension(-5);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_vacation_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setVacation_days(-5);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_sick_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setSick_days(-5);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_date() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setStart_date(null);
		assertFalse(WorkersController.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_role() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setRole("");
		assertFalse(WorkersController.add_worker(w).success);
		w.setRole(null);
		assertFalse(WorkersController.add_worker(w).success);
	}


}
