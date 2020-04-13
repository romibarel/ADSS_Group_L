

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkersRepo
{
	private static List<Worker> workers= new LinkedList<>();

	public static Result add_worker(String name, String role, String start_date,String bank_account_number,String salary, String pension, String vacation_days, String sick_days)
	{
		Result result= Worker.check_parameters(name, role, start_date,bank_account_number,salary, pension, vacation_days, sick_days);
		if (result.success)
		{
			Worker new_worker;
			try
			{
				Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
				int bankAccountNumber=Integer.parseInt(bank_account_number);
				int Salary=Integer.parseInt(salary);
				int Pension=Integer.parseInt(pension);
				int vacationDays=Integer.parseInt(vacation_days);
				int sickDays=Integer.parseInt(sick_days);
				new_worker=new Worker(workers.size(),name,role,startDate,bankAccountNumber,Salary,Pension,vacationDays,sickDays);
				workers.add(new_worker);
			}
			catch (Exception e) {}
		}
		return result;
	}

	public static Result delete_worker(int id)
	{
		Worker worker=get_by_id(id);
		if (worker==null) return new Result(false,"worker doesnt exist");
		if (ShiftRepo.is_scheduled(id))
			return new Result(false,"cant delete worker that is scheduled for a shift");
		return new Result(true,"success");
	}


	public static Result edit_worker(int id,String name, String role, String start_date,String bank_account_number,String salary, String pension, String vacation_days, String sick_days)
	{
		Result result;
		Worker worker=get_by_id(id);
		if (worker==null) return new Result(false,"worker doesnt exist");
		result=Worker.check_parameters(name, role, start_date,bank_account_number,salary,pension, vacation_days,sick_days);
		if (result.success)
		{
			try
			{
				worker.setStart_date(new SimpleDateFormat("dd/MM/yyyy").parse(start_date));
				worker.setBank_account_number(Integer.parseInt(bank_account_number));
				worker.setSalary(Integer.parseInt(salary));
				worker.setPension(Integer.parseInt(pension));
				worker.setVacation_days(Integer.parseInt(vacation_days));
				worker.setSick_days(Integer.parseInt(sick_days));
			}
			catch (Exception e) {}
		}
		return result;
	}

	// return the available workers in specific date and role
	public static List<Worker> get_by_role(String role,Date date,boolean morning)
	{
		List<Worker> return_list=new LinkedList<>();
		for (int i=0;i<workers.size();i++)
		{
			Worker worker=workers.get(i);
			if (!worker.getRole().equals(role)) continue;
			if (!ConstrainsRepo.is_available(worker.getId(),date,morning)) continue;
			return_list.add(worker);

		}
		return return_list;
	}

	public static Worker get_by_id(int id)
	{
		for (int i=0;i<workers.size();i++)
		{
			if (workers.get(i).getId()==id)
				return workers.get(i);
		}
		return null;
	}



}
