package Business;

import Interface.InterfaceWorker;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkersController
{
	private static List<Worker> workers= new LinkedList<>();
	private static List<String> branches=new LinkedList<>();
	private static BTDController data=BTDController.getBTD();

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
				new_worker=new Driver(worker,licenses);
			else new_worker=new Worker(worker);
			workers.add(new_worker);
			data.insertWorker(new_worker);
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
		List<Worker> return_list=new LinkedList<>();
		for (Worker worker : workers)
		{
			if (!worker.getRole().equals(role)) continue;
			if (!ConstrainsController.is_available(worker.getId(),date,morning)) continue;
			return_list.add(worker);
		}
		return return_list;
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

	//returns all workers in the given role that works in the given branch
	public static List<Worker> get_by_role_and_branch(String role,String branch)
	{
		workers=BTDController.upload_by_role_and_branch(role,branch);
		return workers;
	}

	//checks if there is an available manager in the given branch and the given date
	//returns -1 if no manager is available
	public static int find_available_manager(Date date, boolean morning,String branch)
	{
		List<Worker> managers=get_by_role_and_branch("manager",branch);
		for (Worker manager: managers)
		{
			if (ConstrainsController.is_available(manager.getId(),date,morning)) return manager.getId();
		}
		return -1;
	}

	public static int find_available_storekeeper(Date date, boolean morning,String branch)
	{
		List<Worker> storekeepres=get_by_role_and_branch("storekeeper",branch);
		for (Worker storekeeper: storekeepres)
		{
			if (ConstrainsController.is_available(storekeeper.getId(),date,morning)) return storekeeper.getId();
		}
		return -1;
	}

	public static boolean canDriveTruck(int driver_id,String truckType)
	{
		Worker w=get_by_id(driver_id);
		if (w!=null && w.getRole().equals("driver"))
			return ((Driver)w).canDriveTruck(truckType);
		return false;
	}

	//for the tests
	public static List<Worker> getWorkers()
	{
		return workers;
	}

	public static List<String> getBranches() {
		return branches;
	}

	public static void addBranch(String branch) {
		WorkersController.branches.add(branch);
	}

}
