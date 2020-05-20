package DataAccess.DALObjects;

import Business.BuisnessObjects.Shift;

import java.util.Date;
import java.util.List;

public class DALShift
{
	private Date date;
	private boolean morning;
	private int manager_id;
	private List<Integer> workers;
	private String branchAddress;

	public DALShift(Shift s)
	{
		this.date=s.getDate();
		this.morning=s.isMorning();
		this.manager_id=s.getManager_id();
		this.workers=s.getWorkers();
		this.branchAddress=s.getBranchAddress();
	}

	public DALShift (Date date,boolean morning,int manager_id,List<Integer> workers,String branchAddress)
	{
		this.date=date;
		this.morning=morning;
		this.manager_id=manager_id;
		this.workers=workers;
		this.branchAddress=branchAddress;
	}

	public DALShift(){}

	public Date getDate()
	{
		return date;
	}

	public boolean isMorning()
	{
		return morning;
	}

	public int getManager_id()
	{
		return manager_id;
	}

	public List<Integer> getWorkers()
	{
		return workers;
	}

	public String getBranchAddress()
	{
		return branchAddress;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setMorning(boolean morning)
	{
		this.morning = morning;
	}

	public void setManager_id(int manager_id)
	{
		this.manager_id = manager_id;
	}

	public void setWorkers(List<Integer> workers)
	{
		this.workers = workers;
	}

	public void setBranchAddress(String branchAddress)
	{
		this.branchAddress = branchAddress;
	}
}
