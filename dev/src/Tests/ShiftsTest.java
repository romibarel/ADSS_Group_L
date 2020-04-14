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

public class ShiftsTest
{
	PresentWorker w;
	PresentShift s;
	static int added_workers=0;

	@BeforeEach
	public void before_tests() throws ParseException
	{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		w=new PresentWorker("avi cohen",1,12,1234,11,5,5,date,"manager");

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
	public void test_fail_add_shift() throws ParseException
	{
		ShiftRepo.add_shift(s);
		assertFalse(ShiftRepo.add_shift(s).success); // there is already a shift in that time

		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/1990");
		s.setDate(date);
		assertFalse(ShiftRepo.add_shift(s).success); // cant add shift in the past
		s.setDate(null);
		assertFalse(ShiftRepo.add_shift(s).success); // null date
		s.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2023"));

		s.setManager_id(12345);
		assertFalse(ShiftRepo.add_shift(s).success); // manager with id 12345 doesnt exist

		//TODO add workers and manager with constarint and run more tests
	}

	@Test
	public void test_good_add_shift() throws ParseException
	{
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		PresentShift s2=new PresentShift(date2,true,0,null);

		WorkersRepo.add_worker(w);
		added_workers++;
		s.setManager_id(added_workers-1);
		ShiftRepo.add_shift(s);
		s2.setManager_id(added_workers-1);
		ShiftRepo.add_shift(s2);
		assertEquals(2,ShiftRepo.get_shifts().size()); //2 shifts were added
	}

	@Test
	public void test_fail_delete_shift() throws ParseException
	{
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		assertFalse(ShiftRepo.delete_shift(date2,true).success);//no shift was added to that time
	}

	@Test
	public void test_good_delete_shift() throws ParseException
	{
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		WorkersRepo.add_worker(w);
		added_workers++;
		s.setManager_id(added_workers-1);
		s.setDate(date2);
		ShiftRepo.add_shift(s);
		assertTrue(ShiftRepo.delete_shift(date2,s.isMorning()).success);
	}

	@Test
	public void test_get_shift() throws ParseException
	{
		//add two shifts, then get shift in date2 and check that it is indeed the date
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		PresentShift s2=new PresentShift(date2,true,0,null);

		WorkersRepo.add_worker(w);
		added_workers++;
		s.setManager_id(added_workers-1);
		ShiftRepo.add_shift(s);
		s2.setManager_id(added_workers-1);
		ShiftRepo.add_shift(s2);
		assertEquals(date2,ShiftRepo.get_shift(date2,true).getDate());

		//get shift in date that wasn't scheduled
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2030");
		assertNull(ShiftRepo.get_shift(date3,true));
	}

}
