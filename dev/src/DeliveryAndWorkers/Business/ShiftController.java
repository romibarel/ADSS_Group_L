package DeliveryAndWorkers.Business;

import DeliveryAndWorkers.Business.BuisnessObjects.Shift;
import DeliveryAndWorkers.Business.BuisnessObjects.Worker;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceShift;
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
		if (!WorkersController.getBranches().contains(branch)) return new Result(true,""); //not good idea change it next work
		if (date.before(new Date())) return new Result(false,"cant assign storekeeper in the past");
		boolean morning=Shift.is_morning_shift(hour);
		Shift shift=get_shift(date,morning,branch);
		int storekeeper_id=WorkersController.find_available_storekeeper(date,morning,branch);
		if (storekeeper_id==-1)
		{
			data.send_message("HRManager" , "attempt to set delivery failed - no available storekeeper at recipient branch(" + branch + ") at " + date.toString());
			return new Result(false,"no available storekeepers in " + date.toString() + " at " + branch);
		}
		if (shift==null) //if shift doesnt exist create new shift with a random available manager and a random available storekeeper
		{
			int manager_id=WorkersController.find_available_manager(date,morning,branch);
			if (manager_id==-1)
			{
				data.send_message("HRManager" , "attempt to set delivery failed- no available manager at recipient branch(" + branch + ") at " + date.toString());
				return new Result(false,"no available manager in " + date.toString() + " at " + branch);
			}
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
	//if there is a missing shift in this interval the function creates shift automatically and assigns the driver and an available manager
	public static Result assign_Driver(int driver_id,Date departure_date,Date departure_hours,Date arrival_day,Date arrival_hour)
	{
		List<Shift> assigned_shifts=new LinkedList<>();
		if (departure_date.before(new Date())) return new Result(false,"you can only set deliveries starting from tomorrow");
		boolean departure_morning=Shift.is_morning_shift(departure_hours); // indicates if the time of departure is morning shift
		boolean arrival_morning=Shift.is_morning_shift(arrival_hour); // indicates if the time of arrival is in morning shift
		Worker driver=WorkersController.get_by_id(driver_id);
		if (driver==null || !driver.getRole().equals("driver")) return new Result(false,"Driver doesnt exist");
		List <Pair<Date,Boolean>> shifts_in_range=get_shifts_in_range(departure_date,departure_morning,arrival_day,arrival_morning);
		if (!ConstrainsController.isDriverAvailable(driver_id,departure_date,departure_hours,arrival_day,arrival_hour)) return new Result(false,"Driver has constraint in this dates");
		for (Pair<Date,Boolean> p :  shifts_in_range)
		{
			Shift shift=get_shift(p.getKey(),p.getValue(),driver.getBranchAddress());
			if (shift==null) //if shift doesnt exist create new shift with a random available manager and the driver
			{
				int manager_id=WorkersController.find_available_manager(p.getKey(),p.getValue(),driver.getBranchAddress());
				if (manager_id==-1)
				{
					data.send_message("HRManager" , "attempt to set delivery at " + departure_date.toString() + "-" + arrival_day.toString() +
							" failed - no available manager at " + p.getKey().toString() + " at " + driver.getBranchAddress());
					return new Result(false,"no manager is available in " + p.getKey().toString() + " at " + driver.getBranchAddress());
				}
				List<Integer> workers_in_shift=new LinkedList<>();
				workers_in_shift.add(driver_id);
				assigned_shifts.add(new Shift(p.getKey(),p.getValue(),manager_id,workers_in_shift,driver.getBranchAddress()));
			}
			else //if shift exist edit it by adding the driver to its workers list
			{
				if (shift.getWorkers().contains(driver_id))
				{
					data.send_message("HRManager" , "attempt to set delivery at " + departure_date.toString() + "-" + arrival_day.toString() + " failed - requested driver " + driver_id + " already assigned");
					return new Result(false,"driver is already assigned to delivery");
				}
				shift.getWorkers().add(driver_id);
				assigned_shifts.add(shift);
			}
		}
		Result result=saveAll(assigned_shifts);
		if (result.success)
			shifts.addAll(assigned_shifts);
		return result;
	}

	public static List<Pair<Date,Boolean>> get_shifts_in_range(Date departure_date,boolean departure_morning,Date arrival_day,boolean arrival_morning)
	{
		List<Pair<Date,Boolean>> shifts = new LinkedList<>();
		boolean current_morning=departure_morning; // indicates if the current shift we are adding is morning shift, we start from departure_hours
		Calendar current_date= Calendar.getInstance(); // date of the current shift we are adding
		Calendar arrive_date=Calendar.getInstance();
		current_date.setTime(departure_date);
		arrive_date.setTime(arrival_day);
		while (current_date.get(Calendar.DAY_OF_YEAR)<arrive_date.get(Calendar.DAY_OF_YEAR))
		{
			shifts.add(new Pair(current_date.getTime(),current_morning));
			current_morning=!current_morning;
			if (current_morning==true) current_date.add(Calendar.DATE,1);
		}


		shifts.add(new Pair(arrival_day,true));

		//now current date and arrival date is equal
		if (arrival_morning==false)
		{
			shifts.add(new Pair(arrival_day,false));
		}
		return shifts;
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
