package Tests;

import InterfaceLayer.InterfaceConstraint;
import InterfaceLayer.InterfaceShift;
import InterfaceLayer.InterfaceWorker;
import Logic.ConstrainsController;
import Logic.ShiftController;
import Logic.WorkersController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

public class ShiftsTest
{

	@After
	public void after_tests()
	{
		WorkersController.getWorkers().clear();
		ShiftController.get_shifts().clear();
		ConstrainsController.getConstraints().clear();
	}

	@Test
	public void test_fail_add_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(shift_date,true,0,null);

		ShiftController.add_shift(s);
		assertFalse(ShiftController.add_shift(s).success); // there is already a shift in that time

		Date date=new SimpleDateFormat("dd/MM/yyyy").parse("14/02/1990");
		s.setDate(date);

		assertFalse(ShiftController.add_shift(s).success); // cant add shift in the past

		s.setDate(null);
		assertFalse(ShiftController.add_shift(s).success); // null date

		s.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("14/02/2023"));
		s.setManager_id(12345);
		assertFalse(ShiftController.add_shift(s).success); // manager with id 12345 doesnt exist

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceConstraint c1=new InterfaceConstraint(date2,true,0,"wedding",0);
		WorkersController.add_worker(w);
		ConstrainsController.addConstraint(c1);
		s.setManager_id(0);
		s.setDate(date2);
		assertFalse(ShiftController.add_shift(s).success); //the manager has a constraint
	}

	@Test
	public void test_good_add_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(shift_date,true,0,null);

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		InterfaceShift s2=new InterfaceShift(date2,true,0,null);

		WorkersController.add_worker(w);
		s.setManager_id(0);
		ShiftController.add_shift(s);
		s2.setManager_id(0);
		ShiftController.add_shift(s2);
		assertEquals(2, ShiftController.get_shifts().size()); //2 shifts were added
	}

	@Test
	public void test_fail_delete_shift() throws ParseException
	{
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		assertFalse(ShiftController.delete_shift(date2,true).success);//no shift was added to that time
	}

	@Test
	public void test_good_delete_shift() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(shift_date,true,0,null);

		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		WorkersController.add_worker(w);
		s.setManager_id(0);
		s.setDate(date2);
		ShiftController.add_shift(s);
		assertTrue(ShiftController.delete_shift(date2,s.isMorning()).success);
	}

	@Test
	public void test_get_shift1() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(shift_date,true,0,null);

		//add two shifts, then get shift in date2 and check that it is indeed the date
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		InterfaceShift s2=new InterfaceShift(date2,true,0,null);

		WorkersController.add_worker(w);
		s.setManager_id(0);
		ShiftController.add_shift(s);
		s2.setManager_id(0);
		ShiftController.add_shift(s2);
		assertEquals(date2, ShiftController.get_shift(date2,true).getDate());

	}

	@Test
	public void test_get_shift2() throws ParseException
	{
		Date start_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2016");
		InterfaceWorker w=new InterfaceWorker("avi cohen",0,12,1234,11,5,5,start_date,"manager");
		Date shift_date=new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2020");
		InterfaceShift s=new InterfaceShift(shift_date,true,0,null);
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2020");
		InterfaceShift s2=new InterfaceShift(date2,true,0,null);

		WorkersController.add_worker(w);
		ShiftController.add_shift(s);
		ShiftController.add_shift(s2);

		//get shift in date that wasn't scheduled
		Date date3=new SimpleDateFormat("dd/MM/yyyy").parse("16/05/2030");
		assertNull(ShiftController.get_shift(date3,true));
	}

}
