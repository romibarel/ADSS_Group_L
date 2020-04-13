package Logic;

import CLI.PresentConstraint;
import javafx.concurrent.Worker;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class ConstrainsRepo
{
	private static List<Constraint> constraints=new LinkedList<>();
	public static boolean is_available(int worker_id,String date, boolean morning)
	{
		for(Constraint c: constraints){
			if(c.getDate().equals(date)&& worker_id==c.getId()&& morning==c.isMorning())
				return false;
		}
		return true;
	}

	public static List<Constraint> getConstraints() {
		return constraints;
	}

	public static String addConstraint(Constraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return "Employee does not exist in the system";
		try {
			new SimpleDateFormat("dd/MM/yyyy").parse(c.getDate());
		}  catch (Exception e){
			return "Date is invalid";
		}
		return "Constraint was added "+c.toString();
	}

}
