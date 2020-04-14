package Logic;

import CLI.PresentWorker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Worker
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

	public Worker(int id,PresentWorker worker)
	{
		this.id=id;
		this.name=worker.getName();
		this.bank_account_number=worker.getBank_account_number();
		this.salary=worker.getSalary();
		this.pension=worker.getPension();
		this.vacation_days=worker.getVacation_days();
		this.sick_days=worker.getSick_days();
		this.role=worker.getRole();
		try
		{
			this.start_date=new SimpleDateFormat("dd/MM/yyyy").parse(worker.getStart_date()); //date is validated so no catch
		} catch (Exception ignored){}
	}

	public static Result check_parameters(PresentWorker worker)
	{

		//check name
		if (worker.getName()==null || worker.getName().isEmpty()) return new Result(false,"name can't be empty");

		//check role
		if (worker.getRole()==null || worker.getRole().isEmpty()) return new Result(false,"role can't be empty");

		//check bank account number
		if (worker.getBank_account_number()<0) return new Result(false, "invalid bank account number");

		//check salary
		if (worker.getSalary()<0) return new Result(false, "salary is lower than 0");

		//check pension
		if (worker.getPension()<0)	return new Result(false, "pension is lower than 0");

		//check vacation days
		if (worker.getVacation_days()<0)	return new Result(false, "vacation days is lower than 0");

		//check sick days
		if (worker.getSick_days()<0)	return new Result(false, "sick days is lower than 0");

		//check start date
		try	{ new SimpleDateFormat("dd/MM/yyyy").parse(worker.getStart_date()); }
		catch (Exception e)	{ return new Result(false,"invalid start date"); }

		return new Result(true,"success");
	}

	public static boolean is_manager(int id)
	{
		//TODO - implement later
		return true;
	}


	/*-------------------- Getters and Setters ---------------------------------*/


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getBank_account_number()
	{
		return bank_account_number;
	}

	public void setBank_account_number(int bank_account_number)
	{
		this.bank_account_number = bank_account_number;
	}

	public int getSalary()
	{
		return salary;
	}

	public void setSalary(int salary)
	{
		this.salary = salary;
	}

	public int getPension()
	{
		return pension;
	}

	public void setPension(int pension)
	{
		this.pension = pension;
	}

	public int getVacation_days()
	{
		return vacation_days;
	}

	public void setVacation_days(int vacation_days)
	{
		this.vacation_days = vacation_days;
	}

	public int getSick_days()
	{
		return sick_days;
	}

	public void setSick_days(int sick_days)
	{
		this.sick_days = sick_days;
	}

	public Date getStart_date()
	{
		return start_date;
	}

	public void setStart_date(Date start_date)
	{
		this.start_date = start_date;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}



}
