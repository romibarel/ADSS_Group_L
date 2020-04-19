
import CLI.PresentConstraint;
import CLI.PresentShift;
import CLI.PresentWorker;
import Logic.ConstrainsRepo;
import Logic.ShiftRepo;
import Logic.WorkersRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ShiftsTest
{

	@AfterEach
	public void after_tests()
	{
		WorkersRepo.getWorkers().clear();
		ShiftRepo.get_shifts().clear();
		ConstrainsRepo.getConstraints().clear();
	}

	@Test
	public void test_fail_add_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(shift_date,true,0,null);

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

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentConstraint c1=new PresentConstraint(date2,true,0,"wedding",0);
		WorkersRepo.add_worker(w);
		ConstrainsRepo.addConstraint(c1);
		s.setManager_id(0);
		s.setDate(date2);
		assertFalse(ShiftRepo.add_shift(s).success); //the manager has a constraint
	}

	@Test
	public void test_good_add_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(shift_date,true,0,null);

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		PresentShift s2=new PresentShift(date2,true,0,null);

		WorkersRepo.add_worker(w);
		s.setManager_id(0);
		ShiftRepo.add_shift(s);
		s2.setManager_id(0);
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
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(shift_date,true,0,null);

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		WorkersRepo.add_worker(w);
		s.setManager_id(0);
		s.setDate(date2);
		ShiftRepo.add_shift(s);
		assertTrue(ShiftRepo.delete_shift(date2,s.isMorning()).success);
	}

	@Test
	public void test_get_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		PresentWorker w=new PresentWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		PresentShift s=new PresentShift(shift_date,true,0,null);

		//add two shifts, then get shift in date2 and check that it is indeed the date
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		PresentShift s2=new PresentShift(date2,true,0,null);

		WorkersRepo.add_worker(w);
		s.setManager_id(0);
		ShiftRepo.add_shift(s);
		s2.setManager_id(0);
		ShiftRepo.add_shift(s2);
		assertEquals(date2,ShiftRepo.get_shift(date2,true).getDate());

		//get shift in date that wasn't scheduled
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2030");
		assertNull(ShiftRepo.get_shift(date3,true));
	}

}
