

import java.util.Date;
import java.util.HashMap;

public class Shift
{
	private Date date;
	private boolean morning;
	private int manager_id;
	private HashMap<String,Integer> workers; // key= name , value= worker id
	private String branch;

	/*-------------------- Getters and Setters ---------------------------------*/
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public boolean isHours()
	{
		return morning;
	}

	public void setHours(boolean morning)
	{
		this.morning = morning;
	}

	public int getManager_id()
	{
		return manager_id;
	}

	public void setManager_id(int manager_id)
	{
		this.manager_id = manager_id;
	}

	public HashMap<String, Integer> getWorkers()
	{
		return workers;
	}

	public void setWorkers(HashMap<String, Integer> workers)
	{
		this.workers = workers;
	}
}
