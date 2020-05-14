package Business;

import Interface.InterfaceShift;

import java.util.*;

public class ShiftController
{
	private static List<Shift> shifts=new LinkedList<>();

	// checks if a worker is scheduled for any shift
	public static boolean is_worker_scheduled(int worker_id)
	{
		for (Shift shift:shifts)
		{
			if (shift.getWorkers().contains(worker_id))
				return true;
			if (shift.getManager_id()==worker_id)
				return true;
		}
		return false;
	}

	//checks if there a shift scheduled in a certain date
	public static boolean is_shift_scheduled(Date date,boolean morning)
	{
		for (Shift shift: shifts)
		{
			if (shift.getDate().equals(date) && shift.isMorning()==morning)
				return true;
		}
		return false;
	}

	public static Result add_shift(InterfaceShift shift)
	{
		Result result=Shift.check_parameters(shift,true);
		if (result.success)
		{
			Shift new_shift=new Shift(shift);
			shifts.add(new_shift);
		}
		return result;
	}

	public static Result edit_shift(InterfaceShift shift, Date previous_date, boolean previous_morning, String previous_branch)
	{
		Result result;
		Shift shift_to_edit=get_shift(previous_date,previous_morning, previous_branch);
		if (shift_to_edit==null) return new Result(false,"shift doesnt exist");
		boolean check_date=true;
		if (previous_date.equals(shift.getDate()) & previous_morning==shift.isMorning()& previous_branch.equals((shift.getBranchAddress())))  //if the date hasn't changed no need to check its validity
			check_date=false;
		result=Shift.check_parameters(shift,check_date);
		if (result.success)
		{
			shift_to_edit.setDate(shift.getDate());
			shift_to_edit.setManager_id(shift.getManager_id());
			shift_to_edit.setMorning(shift.isMorning());
			shift_to_edit.setWorkers(shift.getWorkers());
			shift_to_edit.setBranchAddress(shift.getBranchAddress());
		}
		return result;
	}

	public static Result delete_shift(Date date, boolean morning, String branch)
	{
		Shift shift= get_shift(date,morning, branch);
		if (shift==null)
			return new Result(false,"shift doesnt exist");
		shifts.remove(shift);
		return new Result(true,"success");
	}

	public static Shift get_shift(Date date, boolean morning, String branch)
	{
		for (Shift shift:shifts)
		{
			if (shift.getDate().equals(date) && shift.isMorning()==morning&& branch.equals(shift.getBranchAddress()))
				return shift;
		}
		return null;
	}

	//returns shifts of the current week;
	public static List<Shift> get_week_shifts()
	{
		List<Shift> currentWeekShifts=new LinkedList<>();
		Calendar calendar=Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		Date currentWeekStart=calendar.getTime();
		calendar.add(Calendar.DATE,6);
		Date currentWeekEnd=calendar.getTime();
		for (Shift shift:shifts)
		{
			//inclusive first day of the week and last day of the week
			if (!(shift.getDate().before(currentWeekStart) || shift.getDate().after(currentWeekEnd)))
			{
				currentWeekShifts.add(shift);
			}
		}
		currentWeekShifts.sort(Comparator.comparing(Shift::getDate)); //sort by date
		return currentWeekShifts;
	}

	//for the tests
	public static List<Shift> get_shifts()
	{
		return shifts;
	}

	//check if worker is scheduled at specific date
	public static boolean is_worker_scheduled_at(int id,Date date,boolean morning, String branch)
	{
		Shift shift=get_shift(date,morning, branch);
		if (shift==null) return false;
		return shift.getWorkers().contains(id) || shift.getManager_id() == id;
	}

}
