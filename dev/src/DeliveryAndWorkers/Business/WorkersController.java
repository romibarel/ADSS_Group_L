package DeliveryAndWorkers.Business;

import DeliveryAndWorkers.Business.BuisnessObjects.Driver;
import DeliveryAndWorkers.Business.BuisnessObjects.Shift;
import DeliveryAndWorkers.Business.BuisnessObjects.Worker;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkersController
{
	private static List<Worker> workers= new LinkedList<>();
	private static BTDController data=BTDController.getBTD();
	private static List<String> branches=data.load_all_branches();

	public static String get_driver_name(int id)
	{
		Worker worker=get_by_id(id);
		if (worker!=null && worker.getRole().equals("driver"))
			return worker.getName();
		return null;
	}

	public static Result add_worker(InterfaceWorker worker,List<String> licenses)
	{
		if (get_by_id(worker.getId())!=null)
			return new Result(false,"there is already a worker with that id");
		Result result= Worker.check_parameters(worker,true);
		if (result.success)
		{
			Worker new_worker;
			if (worker.getRole().equals("driver"))
			{
				new_worker = new Driver(worker, licenses);
				result=data.insertDriver(new_worker,licenses);
			}
			else
			{
				new_worker = new Worker(worker);
				result=data.insertWorker(new_worker);
			}
			if (result.success)
				workers.add(new_worker);
		}
		return result;
	}

	public static Result delete_worker(int id)
	{
		Worker worker=get_by_id(id);
		if (worker==null)
			return new Result(false,"worker doesnt exist");
		if (ShiftController.is_worker_scheduled(id))
			return new Result(false,"cant delete worker that is scheduled for a shift");
		workers.remove(worker);
		data.deleteWorker(id);
		return new Result(true,"success");
	}

	public static Result edit_worker(InterfaceWorker worker)
	{
		Result result;
		Worker worker_to_edit=get_by_id(worker.getId());
		if (worker_to_edit==null) return new Result(false,"worker doesnt exist");
			result=Worker.check_parameters(worker,false);
		if (result.success)
		{
			worker_to_edit.setStart_date(worker.getStart_date());
			worker_to_edit.setBank_account_number(worker.getBank_account_number());
			worker_to_edit.setName(worker.getName());
			worker_to_edit.setPension(worker.getPension());
			worker_to_edit.setRole(worker.getRole());
			worker_to_edit.setSalary(worker.getSalary());
			worker_to_edit.setSick_days(worker.getSick_days());
			worker_to_edit.setVacation_days(worker.getVacation_days());
			worker_to_edit.setBranchAddress(worker.getBranchAddress());
			data.updateWorker(worker_to_edit);
		}
		return result;
	}

	// return the available workers in specific date, role and branch
	public static List<Worker> get_available_workers(String role, Date date, boolean morning,String branch)
	{
		workers=data.select_available_workers(date,morning,role.toLowerCase(),branch);
		return workers;
	}

	public static Worker get_by_id(int id)
	{
		for (Worker worker:workers)
		{
			if (worker.getId()==id)
				return worker;
		}
		// if the worker is not in my list look it up in the DB
		Worker worker=data.selectWorker(id);
		if (worker!=null)
		{
			workers.add(worker);
			return worker;
		}
		return null;
	}

	//checks if there is an available manager in the given branch and the given date
	//returns -1 if no manager is available
	public static int find_available_manager(Date date, boolean morning,String branch)
	{
		for (Worker worker: workers)
		{
			if (worker.getRole().equals("manager") & worker.getBranchAddress().equals(branch))
				if (ConstrainsController.is_available(worker.getId(),date,morning)) return worker.getId();
		}
		return data.select_available_worker_id(date,morning,branch,"manager");
	}

	public static int find_available_storekeeper(Date date, boolean morning,String branch)
	{
		for (Worker worker: workers)
		{
			if (worker.getRole().equals("storekeeper") & worker.getBranchAddress().equals(branch))
				if (ConstrainsController.is_available(worker.getId(),date,morning)) return worker.getId();
		}
		return data.select_available_worker_id(date,morning,branch,"storekeeper");
	}

	public static boolean canDriveTruck(int driver_id,String truckType)
	{
		Worker w=get_by_id(driver_id);
		if (w!=null && w.getRole().equals("driver"))
			return ((Driver)w).canDriveTruck(truckType);
		return false;
	}

	public static List<Worker> getWorkers()
	{
		workers=data.get_all_workers();
		return workers;
	}

	public static List<String> getBranches()
	{
		return branches;
	}

	public static List<String> get_HRManager_Messages()
	{
		return data.get_Messages("HRManager");
	}

	// @PRE departure time is 3 hours before arrival time - (Deliveries module decision)
	public static int get_available_driver_id(String truck_type, LocalDateTime eta )
	{
		LocalDateTime dep_date = eta.plusHours(-3);
		Date departure_date = Date.from(dep_date.atZone(ZoneId.systemDefault()).toInstant()); // convert LocalDateTime to Date
		boolean departure_morning = Shift.is_morning_shift(departure_date);					  // true if departure_date is in morning shift and false otherwise
		Date arrival_date = Date.from(eta.atZone(ZoneId.systemDefault()).toInstant());        // convert LocalDateTime to Date
		boolean arrival_morning = Shift.is_morning_shift(arrival_date);						 // true if arrival_date is in morning shift and false otherwise
		for (String branch : getBranches())
		{
			List<Worker> available_drivers = get_available_workers("driver", departure_date, departure_morning, branch); //return drivers with no constraints at departure_date
			for (Worker driver : available_drivers)
			{
				Driver d = (Driver) driver;
				if (ConstrainsController.is_available(driver.getId(),arrival_date,arrival_morning)) 	//check the driver has no constraints in arrival_date
				{
					Shift departure_shift = ShiftController.get_shift(departure_date , departure_morning ,branch);
					if ( (departure_shift==null) ||   (!departure_shift.getWorkers().contains(driver.getId()))) // check driver isnt already assigned to delivery in dept shift
					{
						Shift arrival_shift =ShiftController.get_shift(arrival_date,arrival_morning,branch);
						if ( (arrival_shift==null) ||  (!arrival_shift.getWorkers().contains(driver.getId())))  // check driver isnt already assigned to delivery in arrival shift
							if (d.getLicenses().contains(truck_type))
								return driver.getId();
					}
				}
			}
		}
		return -1;
	}
}
