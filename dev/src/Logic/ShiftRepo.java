package Logic;

import CLI.PresentShift;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShiftRepo
{
	private static List<Logic.Shift> shifts=new LinkedList<>();

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

	public static Result add_shift(PresentShift shift)
	{
		Result result=Shift.check_parameters(shift);
		if (result.success)
		{
			Shift new_shift=new Shift(shift);
			shifts.add(new_shift);
		}
		return result;
	}

	public static Result edit_shift(PresentShift shift)
	{
		Result result;
		result=Shift.check_parameters(shift);
		if (result.success)
		{
			Shift shift_to_edit=get_shift(shift.getDate(),shift.isMorning());
			if (shift_to_edit==null) return new Result(false,"shift doesnt exist");
			shift_to_edit.setDate(shift.getDate());
			shift_to_edit.setManager_id(shift.getManager_id());
			shift_to_edit.setMorning(shift.isMorning());
			shift_to_edit.setWorkers(shift.getWorkers());
		}
		return result;
	}

	public static Result delete_shift(Date date, boolean morning)
	{
		Shift shift= get_shift(date,morning);
		if (shift==null)
			return new Result(false,"shift doesnt exist");
		shifts.remove(shift);
		return new Result(true,"success");
	}

	public static Shift get_shift(Date date, boolean morning)
	{
		for (Shift shift:shifts)
		{
			if (shift.getDate().equals(date) && shift.isMorning()==morning)
				return shift;
		}
		return null;
	}

	//for the tests
	public static List<Shift> get_shifts()
	{
		return shifts;
	}



}
