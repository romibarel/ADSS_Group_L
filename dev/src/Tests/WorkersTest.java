package Tests;

import CLI.PresentConstraint;
import CLI.PresentShift;
import CLI.PresentWorker;
import Logic.ConstrainsRepo;
import Logic.ShiftRepo;
import Logic.WorkersRepo;
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
		WorkersRepo.getWorkers().clear();
		ShiftRepo.get_shifts().clear();
		ConstrainsRepo.getConstraints().clear();
	}


	@Test
	public void test_get_by_role1() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w1=new PresentWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		PresentWorker w2=new PresentWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		PresentWorker w3=new PresentWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		PresentWorker w4=new PresentWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		PresentWorker w5=new PresentWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersRepo.add_worker(w1);
		WorkersRepo.add_worker(w2);
		WorkersRepo.add_worker(w3);
		WorkersRepo.add_worker(w4);
		WorkersRepo.add_worker(w5);

		//no constraint were added so all managers should be returned
		assertEquals(2,WorkersRepo.get_by_role("manager",date,true).size());
	}

	@Test
	public void test_get_by_role2() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w1=new PresentWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		PresentWorker w2=new PresentWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		PresentWorker w3=new PresentWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		PresentWorker w4=new PresentWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		PresentWorker w5=new PresentWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersRepo.add_worker(w1);
		WorkersRepo.add_worker(w2);
		WorkersRepo.add_worker(w3);
		WorkersRepo.add_worker(w4);
		WorkersRepo.add_worker(w5);

		//no constraint were added so all cashiers should be returned
		assertEquals(2,WorkersRepo.get_by_role("cashier",date,true).size());

		//no constraint were added so all drivers should be returned
		assertEquals(1,WorkersRepo.get_by_role("driver",date,true).size());

		PresentConstraint c1=new PresentConstraint(date,true,w3.getId(),"wedding",0);
		PresentConstraint c2=new PresentConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsRepo.addConstraint(c1);
		ConstrainsRepo.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0,WorkersRepo.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_by_role3() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w1=new PresentWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		PresentWorker w2=new PresentWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		PresentWorker w3=new PresentWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		PresentWorker w4=new PresentWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		PresentWorker w5=new PresentWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersRepo.add_worker(w1);
		WorkersRepo.add_worker(w2);
		WorkersRepo.add_worker(w3);
		WorkersRepo.add_worker(w4);
		WorkersRepo.add_worker(w5);

		//no constraint were added so all drivers should be returned
		assertEquals(1,WorkersRepo.get_by_role("driver",date,true).size());

		PresentConstraint c1=new PresentConstraint(date,true,w3.getId(),"wedding",0);
		PresentConstraint c2=new PresentConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsRepo.addConstraint(c1);
		ConstrainsRepo.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0,WorkersRepo.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_by_role4() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w1=new PresentWorker("avi cohen",1,12,1234,11,5,5,date,"manager");
		PresentWorker w2=new PresentWorker("avi levi",2,12,1234,11,5,5,date,"manager");
		PresentWorker w3=new PresentWorker("avi bitter",3,12,1234,11,5,5,date,"cashier");
		PresentWorker w4=new PresentWorker("shimon levi",4,12,1234,11,5,5,date,"cashier");
		PresentWorker w5=new PresentWorker("shimon cohen",5,12,1234,11,5,5,date,"driver");
		WorkersRepo.add_worker(w1);
		WorkersRepo.add_worker(w2);
		WorkersRepo.add_worker(w3);
		WorkersRepo.add_worker(w4);
		WorkersRepo.add_worker(w5);

		PresentConstraint c1=new PresentConstraint(date,true,w3.getId(),"wedding",0);
		PresentConstraint c2=new PresentConstraint(date,true,w4.getId(),"wedding",1);
		ConstrainsRepo.addConstraint(c1);
		ConstrainsRepo.addConstraint(c2);

		//the cashiers w3,w4 added constraints so 0 cashiers should be available
		assertEquals(0,WorkersRepo.get_by_role("cashier",date,true).size());
	}

	@Test
	public void test_get_worker_by_id() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersRepo.add_worker(w);
		assertEquals("avi cohen",WorkersRepo.get_by_id(0).getName());
	}

	@Test
	public void test_fail_edit_worker() throws ParseException
	{
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		PresentWorker w2=new PresentWorker("avi bitter",1234,12,13,14,15,16,date3,"cashier");
		assertFalse(WorkersRepo.edit_worker(w2).success); //no worker with id 1234
	}

	@Test
	public void test_good_edit_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		Date date4=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		WorkersRepo.add_worker(w);
		PresentWorker w2=new PresentWorker("avi bitter",0,12,13,14,15,16,date4,"cashier"); //w2 has same id as w
		WorkersRepo.edit_worker(w2);
		assertEquals("avi bitter",WorkersRepo.get_by_id(0).getName());
	}

	@Test
	public void test_fail_delete_worker2() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(date2,true,0,null);

		WorkersRepo.add_worker(w);
		s.setManager_id(0); //assign w to be the manager in s
		ShiftRepo.add_shift(s);
		WorkersRepo.delete_worker(0); // worker is scheduled for shift so no delete
		assertEquals(1,WorkersRepo.getWorkers().size());
	}
	@Test
	public void test_fail_delete_worker1() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(date2,true,0,null);

		assertFalse(WorkersRepo.delete_worker(123456).success); //worker doest exist
	}

	@Test public void test_good_delete_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersRepo.add_worker(w);
		WorkersRepo.delete_worker(0);
		assertTrue(WorkersRepo.getWorkers().isEmpty());
	}

	@Test
	public void test_good_add_worker() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		WorkersRepo.add_worker(w);
		assertEquals(1, WorkersRepo.getWorkers().size());
	}

	@Test
	public void add_worker_wrong_name() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setName("");
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setName(null);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_bank_account_number() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setBank_account_number(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_salary() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setSalary(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_pension() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setPension(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_vacation_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setVacation_days(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_sick_days() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setSick_days(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_date() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setStart_date(null);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	@Test
	public void add_worker_wrong_role() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");
		w.setRole("");
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setRole(null);
		assertFalse(WorkersRepo.add_worker(w).success);
	}


}