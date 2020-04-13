package Logic;

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
	private String branch;

	public Worker(int id,String name, String role, String branch, Date start_date,int bank_account_number,int salary, int pension, int vacation_days, int sick_days)
	{
		this.id=id;
		this.name=name;
		this.bank_account_number=bank_account_number;
		this.salary=salary;
		this.pension=pension;
		this.vacation_days=vacation_days;
		this.sick_days=sick_days;
		this.start_date=start_date;
		this.role=role;
		this.branch=branch;
	}

	public static Result check_parameters(String name, String role, String branch, String start_date,String bank_account_number,String salary, String pension, String vacation_days, String sick_days)
	{
		int temp;

		//check name
		if (name==null || name.isEmpty()) return new Result(false,"name can't be empty");

		//check role
		if (role==null || role.isEmpty()) return new Result(false,"role can't be empty");

		//check branch
		if (branch==null || branch.isEmpty()) return new Result(false,"branch can't be empty");

		//check bank account number
		try { temp=Integer.parseInt(bank_account_number); }
		catch (Exception e) { return new Result(false, "invalid bank account number"); }
		if (temp<0) return new Result(false, "invalid bank account number");

		//check salary
		try { temp=Integer.parseInt(salary);	}
		catch (Exception e) { return new Result(false, "invalid salary");	}
		if (temp<0) return new Result(false, "salary is lower than 0");

		//check pension
		try { temp=Integer.parseInt(pension); }
		catch (Exception e) { return new Result(false, "invalid pension"); }
		if (temp<0)	return new Result(false, "pension is lower than 0");

		//check vacation days
		try { temp=Integer.parseInt(vacation_days); }
		catch (Exception e) { return new Result(false, "invalid vacation days"); }
		if (temp<0)	return new Result(false, "vacation days is lower than 0");

		//check sick days
		try { temp=Integer.parseInt(sick_days); }
		catch (Exception e) { return new Result(false, "invalid sick days"); }
		if (temp<0)	return new Result(false, "sick days is lower than 0");

		//check start date
		try	{ new SimpleDateFormat("dd/MM/yyyy").parse(start_date); }
		catch (Exception e)	{ return new Result(false,"invalid start date"); }

		return new Result(true,"success");
	}

	public static boolean is_manager(int id)
	{
		//TODO - implement later
		return true;
	}


	/*-------------------- Getters and Setters ---------------------------------*/

	public String getBranch()
	{
		return branch;
	}

	public void setBranch(String branch)
	{
		this.branch = branch;
	}

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
