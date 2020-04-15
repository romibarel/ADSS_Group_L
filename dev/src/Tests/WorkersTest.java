import CLI.PresentShift;
import CLI.PresentWorker;
import Logic.ConstrainsRepo;
import Logic.ShiftRepo;
import Logic.Worker;
import Logic.WorkersRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class WorkersTest
{
	PresentWorker w;
	PresentShift s;

	@BeforeEach
	public void before_tests() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		w=new PresentWorker("avi cohen",0,12,1234,11,5,5,date,"manager");

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		s=new PresentShift(date2,true,0,null);
	}

	@AfterEach
	public void after_tests()
	{
		WorkersRepo.getWorkers().clear();
		ShiftRepo.get_shifts().clear();
	}

	@Test
	public void test_get_by_role() throws ParseException
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

		//no constraint were added so all cashiers should be returned
		assertEquals(2,WorkersRepo.get_by_role("cashier",date,true).size());

		//no constraint were added so all drivers should be returned
		assertEquals(1,WorkersRepo.get_by_role("driver",date,true).size());

		//TODO add constarint and run one more test
	}

	@Test
	public void test_get_worker_by_id()
	{
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
		Date date4=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2026");
		WorkersRepo.add_worker(w);
		PresentWorker w2=new PresentWorker("avi bitter",0,12,13,14,15,16,date4,"cashier"); //w2 has same id as w
		WorkersRepo.edit_worker(w2);
		assertEquals("avi bitter",WorkersRepo.get_by_id(0).getName());
	}

	@Test
	public void test_fail_delete_worker()
	{
		assertFalse(WorkersRepo.delete_worker(123456).success); //worker doest exist
		WorkersRepo.add_worker(w);
		s.setManager_id(0); //assign w to be the manager in s
		ShiftRepo.add_shift(s);
		assertFalse(WorkersRepo.delete_worker(0).success); // worker is scheduled for shift
	}

	@Test void test_good_delete_worker()
	{
		WorkersRepo.add_worker(w);
		WorkersRepo.delete_worker(0);
		assertTrue(WorkersRepo.getWorkers().isEmpty());
	}

	@Test
	public void test_good_add_worker()
	{
		WorkersRepo.add_worker(w);
		assertEquals(1, WorkersRepo.getWorkers().size());
	}

	@Test
	public void test_fail_add_worker()
	{
		test_wrong_name();
		test_wrong_bank_account_number();
		test_wrong_salary();
		test_wrong_pension();
		test_wrong_vacation_days();
		test_wrong_sick_days();
		test_wrong_date();
		test_wrong_role();
		assertTrue(WorkersRepo.getWorkers().isEmpty());
	}

	public void test_wrong_name()
	{
		w.setName("");
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setName(null);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setName("avi cohen");
	}

	public void test_wrong_bank_account_number()
	{
		w.setBank_account_number(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setBank_account_number(5);
	}

	public void test_wrong_salary()
	{
		w.setSalary(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setSalary(5);
	}

	public void test_wrong_pension()
	{
		w.setPension(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setPension(5);
	}

	public void test_wrong_vacation_days()
	{
		w.setVacation_days(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setVacation_days(5);
	}

	public void test_wrong_sick_days()
	{
		w.setSick_days(-5);
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setSick_days(5);
	}

	public void test_wrong_date()
	{

		w.setStart_date(null);
		assertFalse(WorkersRepo.add_worker(w).success);
	}

	public void test_wrong_role()
	{
		w.setRole("");
		assertFalse(WorkersRepo.add_worker(w).success);
		w.setRole(null);
		assertFalse(WorkersRepo.add_worker(w).success);
	}


}
