package Logic;

import CLI.PresentConstraint;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ConstrainsRepo
{
	private static List<Constraint> constraints=new LinkedList<>();
	private static int id=0;

	public static boolean is_available(int worker_id, Date date, boolean morning)
	{
		for(Constraint c: constraints){
			if(c.getDate().equals(date)&& worker_id==c.getId()&& morning==c.isMorning())
				return false;
		}
		return true;
	}

	public static List<Constraint> getConstraintsByWeek() {
		return null;
	}

	public static String addConstraint(PresentConstraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return "Employee does not exist in the system";
		Constraint con=new Constraint(c.getDate(),c.isMorning(),c.getId(),c.getReason(), id);
		constraints.add(con);
		return "Constraint was added "+c.toString();
	}

	public static String editConstraint(PresentConstraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return "Employee does not exist in the system";
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				con.setDate(c.getDate());
				con.setId(c.getId());
				con.setMorning(c.isMorning());
				con.setReason(c.getReason());
				return "Constraint was edited "+c.toString();
			}
		}
		return "could not find matching constraint to edit";
	}

	public static String deleteConstraint(PresentConstraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return "Employee does not exist in the system";
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				constraints.remove(con);
				return "Constraint was constraint was deleted";
			}
		}
		return "could not find matching constraint to delete";
	}

}
