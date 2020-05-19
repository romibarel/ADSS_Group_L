package Business;

import DataAccess.DALController;
import Interface.InterfaceShift;
import javafx.util.Pair;

import java.util.*;

public class ShiftController
{
	private static List<Shift> shifts=new LinkedList<>();
	private static BTDController data=BTDController.getBTD();

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
		return data.is_worker_scheduled(worker_id);
	}

	//checks if there a shift scheduled in a certain date
	public static boolean is_shift_scheduled(Date date,boolean morning,String branch)
	{
		return get_shift(date, morning, branch) != null;
	}

	public static Result add_shift(InterfaceShift shift)
	{
		Result result=Shift.check_parameters(shift,true);
		if (result.success)
		{
			Shift new_shift=new Shift(shift);
			shifts.add(new_shift);
			return data.insertShift(new_shift);
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
			return data.updateShift(shift_to_edit,previous_date,previous_morning,previous_branch);
		}
		return result;
	}

	public static Result delete_shift(Date date, boolean morning, String branch)
	{
		Shift shift= get_shift(date,morning, branch);
		if (shift==null)
			return new Result(false,"shift doesnt exist");
		shifts.remove(shift);
		return data.deleteShift(date,morning,branch);
	}

	public static Shift get_shift(Date date, boolean morning, String branch)
	{
		for (Shift shift:shifts)
		{
			if (shift.getDate().equals(date) && shift.isMorning()==morning&& branch.equals(shift.getBranchAddress()))
				return shift;
		}
		return data.selectShift(date,morning,branch);
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
		shifts=data.get_week_shifts(currentWeekStart,currentWeekEnd);
		return shifts;
	}

	public static List<Shift> get_shifts()
	{
		shifts=data.get_all_shifts();
		return shifts;
	}

	//check if worker is scheduled at specific date
	public static boolean is_worker_scheduled_at(int id,Date date,boolean morning, String branch)
	{
		Shift shift=get_shift(date,morning, branch);
		if (shift==null) return false;
		return shift.getWorkers().contains(id) || shift.getManager_id() == id;
	}

	public static Result assign_storekeeper(Date date,Date hour,String branch)
	{
		if (date.before(new Date())) return new Result(false,"cant assign storekeeper in the past");
		boolean morning=Shift.is_morning_shift(hour);
		Shift shift=get_shift(date,morning,branch);
		int storekeeper_id=WorkersController.find_available_storekeeper(date,morning,branch);
		if (storekeeper_id==-1) return new Result(false,"no available storekeepers in this date");
		if (shift==null) //if shift doesnt exist create new shift with a random available manager and a random available storekeeper
		{
			int manager_id=WorkersController.find_available_manager(date,morning,branch);
			if (manager_id==-1) return new Result(false,"no available manager in this date");
			List<Integer> workers_in_shift=new LinkedList<>();
			workers_in_shift.add(storekeeper_id);
			shift=new Shift(date,morning,manager_id,workers_in_shift,branch);
			ShiftController.add_shift(new InterfaceShift(shift));
		}
		else
		{ //if shift exist ensure there is a storekeeper in it
			for (Integer worker: shift.getWorkers())
			{
				if (WorkersController.get_by_id(worker).getRole().equals("storekeeper")) return new Result(true,"storekeeper assigned");
			}
			shift.getWorkers().add(storekeeper_id);
			edit_shift(new InterfaceShift(shift),date,morning,branch);
		}
		return new Result(true,"storekeeper assigned successfully");
	}

	// assigns driver to all shifts between shift departure time to shift arrival time
	//if there is a missing shift in this interval the functions creates shift automatically and assigns the driver and an available manager
	public static Result assign_Driver(int driver_id,Date departure_date,Date departure_hours,Date arrival_day,Date arrival_hour)
	{
		List<Shift> assigned_shifts=new LinkedList<>();
		if (departure_date.before(new Date())) return new Result(false,"cant set delivery to the past");
		boolean current_morning=Shift.is_morning_shift(departure_hours); // indicates if the current shift we are adding is morning shift, we start from departure_hours
		boolean arrival_morning=Shift.is_morning_shift(arrival_hour); // indicates if the time of arrival is in morning shift
		Calendar current_date= Calendar.getInstance(); // date of the current shift we are adding
		Calendar arrive_date=Calendar.getInstance();
		current_date.setTime(departure_date);
		arrive_date.setTime(arrival_day);
		Worker driver=WorkersController.get_by_id(driver_id);
		if (driver==null || !driver.getRole().equals("driver")) return new Result(false,"Driver doesnt exist");
		do
		{
			if (!ConstrainsController.isDriverAvailable(driver_id,departure_date,departure_hours,arrival_day,arrival_hour)) return new Result(false,"Driver has constraint in this dates");
			Shift shift=get_shift(current_date.getTime(),current_morning,driver.getBranchAddress());
			if (shift==null) //if shift doesnt exist create new shift with a random available manager and the driver
			{
				int manager_id=WorkersController.find_available_manager(current_date.getTime(),current_morning,driver.getBranchAddress());
				if (manager_id==-1) return new Result(false,"no manager is available in this dates");
				List<Integer> workers_in_shift=new LinkedList<>();
				workers_in_shift.add(driver_id);
				assigned_shifts.add(new Shift(current_date.getTime(),current_morning,manager_id,workers_in_shift,driver.getBranchAddress()));
			}
			else //if shift exist edit it by adding the driver to its workers list
			{
				if (shift.getWorkers().contains(driver_id)) return new Result(false,"driver is already assigned to delivery");
				shift.getWorkers().add(driver_id);
				assigned_shifts.add(shift);
			}
			current_morning=!current_morning;
			if (current_morning==true) current_date.add(Calendar.DATE,1);
		} while (current_date.get(Calendar.DAY_OF_YEAR)<=arrive_date.get(Calendar.DAY_OF_YEAR) || (current_morning==false & arrival_morning==true)) ;
		Result result=saveAll(assigned_shifts);
		if (result.success)
			shifts.addAll(assigned_shifts);
		return result;
	}

	private static Result saveAll(List<Shift> shifts)
	{
		Result result=new Result(true,"inserted all shifts");
		for (Shift shift : shifts)
		{
			if (ShiftController.get_shift(shift.getDate(),shift.isMorning(),shift.getBranchAddress())!=null)
				result=data.updateShift(shift,shift.getDate(),shift.isMorning(),shift.getBranchAddress());
			else
				result=data.insertShift(shift);
			if (!result.success) return result;
		}
		return result;
	}
}
