package Logic;

import CLI.PresentWorker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkersRepo
{
	private static List<Worker> workers= new LinkedList<>();
	private static int next_id=0;

	public static Result add_worker(PresentWorker worker)
	{
		Result result= Worker.check_parameters(worker);
		if (result.success)
		{
			Worker new_worker=new Worker(next_id,worker);
			next_id++;
			workers.add(new_worker);
		}
		return result;
	}

	public static Result delete_worker(int id)
	{
		Worker worker=get_by_id(id);
		if (worker==null)
			return new Result(false,"worker doesnt exist");
		if (ShiftRepo.is_worker_scheduled(id))
			return new Result(false,"cant delete worker that is scheduled for a shift");
		workers.remove(worker);
		return new Result(true,"success");
	}


	public static Result edit_worker(PresentWorker worker)
	{
		Result result;
		Worker worker_to_edit=get_by_id(worker.getId());
		if (worker_to_edit==null) return new Result(false,"worker doesnt exist");
		result=Worker.check_parameters(worker);
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
		}
		return result;
	}

	// return the available workers in specific date and role
	public static List<Worker> get_by_role(String role,Date date,boolean morning)
	{
		List<Worker> return_list=new LinkedList<>();
		for (Worker worker : workers)
		{
			if (!worker.getRole().equals(role)) continue;
			if (!ConstrainsRepo.is_available(worker.getId(),date,morning)) continue;
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
		return null;
	}

	//for the tests
	public static List<Worker> getWorkers()
	{
		return workers;
	}

}
