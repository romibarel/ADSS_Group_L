

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

	public Shift(Date date,boolean morning, int manager_id,List<Integer> workers)
	{
		this.date=date;
		this.morning=morning;
		this.manager_id=manager_id;
		this.workers=workers;
	}

	public static Result check_parameters(String manager_id, String date,String morning, String workers)
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
