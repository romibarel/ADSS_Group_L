package Logic;

import InterfaceLayer.InterfaceShift;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Shift
{
	private Date date;
	private boolean morning;
	private int manager_id;
	private List<Integer> workers;
	private String branchAddress;

	public Shift(InterfaceShift shift)
	{
		this.morning=shift.isMorning();
		this.manager_id=shift.getManager_id();
		this.date=new Date(shift.getDate().getTime()); //deep copy the date
		this.workers= new LinkedList<>();
		//deep copy the list
		if (shift.getWorkers()!=null)
			for (int id:shift.getWorkers())
				workers.add(id);
		this.branchAddress=shift.getBranchAddress();

	}

	public static Result check_parameters(InterfaceShift shift, boolean check_date)
	{
		//check date
		if (shift.getDate()==null)
			return new Result(false,"invalid date");
		Date current_date=new Date(); //get current Date
		if (shift.getDate().before(current_date))
			return new Result(false,"shift date is in the past");
		if (check_date)
		{
			if (ShiftController.is_shift_scheduled(shift.getDate(),shift.isMorning()))
				return new Result(false,"a shift is already scheduled for this date");
		}

		if(!WorkersController.getBranches().contains(shift.getBranchAddress()))
			return new Result (false,"branch does not exit");

		//check manager_id
		if (WorkersController.get_by_id(shift.getManager_id())==null)
			return new Result(false,"manager doest exist");
		if (!WorkersController.get_by_id(shift.getManager_id()).is_manager())//no null pointer exception because manager is verified to exist
			return new Result(false,"wrong manager id");
		if (!WorkersController.get_by_id(shift.getManager_id()).getBranchAddress().equals(shift.getBranchAddress()))
			return new Result(false,  "manager doesnt work in the right branch");
		if (!ConstrainsController.is_available(shift.getManager_id(),shift.getDate(),shift.isMorning()))
			return new Result(false, "manager has constraint for that shift");

		//check workers
		if (shift.getWorkers()!=null)
		{
			for (Integer worker_id : shift.getWorkers())
			{
				if (WorkersController.get_by_id(worker_id) == null)
					return new Result(false, "worker number " + worker_id + " doesnt exist");
				if (!WorkersController.get_by_id(worker_id).getBranchAddress().equals(shift.getBranchAddress()))
					return new Result(false, "worker number " + worker_id + " doesnt work in the right branch");
				if (!ConstrainsController.is_available(worker_id, shift.getDate(), shift.isMorning()))
					return new Result(false, "worker number " + worker_id + " has constraint for that shift");
			}
		}

		return new Result(true,"success");
	}


	/*-------------------- Getters and Setters ---------------------------------*/

	public boolean isMorning()
	{
		return morning;
	}

	public void setMorning(boolean morning)
	{
		this.morning = morning;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = new Date(date.getTime()); //deep copy the date
	}

	public int getManager_id()
	{
		return manager_id;
	}

	public void setManager_id(int manager_id)
	{
		this.manager_id = manager_id;
	}

	public List<Integer> getWorkers()
	{
		if (workers==null) return new LinkedList<>();
		return workers;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public void setWorkers(List<Integer> workers)
	{
		this.workers = new LinkedList<>();
		if (workers!=null)
			for( int id:workers) //deep copy the list
				this.workers.add(id);
	}

}
