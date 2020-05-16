package DataAccess;

import Business.Worker;

import java.util.Date;

public class DALWorker
{
	private String name;
	private int id;
	private int bank_account_number;
	private int salary;
	private int pension;
	private int vacation_days;
	private int sick_days;
	private Date start_date;
	private String role;
	private String branchAddress;

	public DALWorker(Worker w)
	{
		this.name=w.getName();
		this.id=w.getId();
		this.bank_account_number=w.getBank_account_number();
		this.salary=w.getSalary();
		this.pension=w.getPension();
		this.vacation_days=w.getVacation_days();
		this.sick_days=w.getSick_days();
		this.start_date=w.getStart_date();
		this.role=w.getRole();
		this.branchAddress=w.getBranchAddress();
	}

	public DALWorker(String name, int id, int bank_account_number,int salary,int pension,int vacation_days,int sick_days, Date start_date,String role,String branchAddress)
	{
		this.name=name;
		this.id=id;
		this.bank_account_number=bank_account_number;
		this.salary=salary;
		this.pension=pension;
		this.vacation_days=vacation_days;
		this.sick_days=sick_days;
		this.start_date=start_date;
		this.role=role;
		this.branchAddress=branchAddress;
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public int getBank_account_number()
	{
		return bank_account_number;
	}

	public int getSalary()
	{
		return salary;
	}

	public int getPension()
	{
		return pension;
	}

	public int getVacation_days()
	{
		return vacation_days;
	}

	public int getSick_days()
	{
		return sick_days;
	}

	public Date getStart_date()
	{
		return start_date;
	}

	public String getRole()
	{
		return role;
	}

	public String getBranchAddress()
	{
		return branchAddress;
	}
}
