package Logic;

import java.util.Date;
import java.util.HashMap;

public class Shift
{
	private Date date;
	private boolean hours; // 1 for morning 0 for evening
	private int manager_id;
	private HashMap<String,Integer> workers; // key= name , value= worker id

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
		return hours;
	}

	public void setHours(boolean hours)
	{
		this.hours = hours;
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
