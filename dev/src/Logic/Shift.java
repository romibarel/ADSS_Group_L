

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Shift
{
	private Date date;
	private boolean morning;
	private int manager_id;
	private List<Integer> workers;
	private String branch;

	public Shift(Date date,boolean morning, int manager_id,List<Integer> workers,String branch)
	{
		this.date=date;
		this.morning=morning;
		this.manager_id=manager_id;
		this.workers=workers;
		this.branch=branch;
	}

	public static Result check_parameters(String branch, String manager_id, String date,String morning, String workers)
	{
		//TODO implement later
		return null;
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

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
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
		return workers;
	}

	public void setWorkers(List<Integer> workers)
	{
		this.workers = workers;
	}

}
