package Logic;

import CLI.PresentConstraint;

import java.util.Calendar;
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

	public static List<Constraint> getConstraint(int id,Date date,  boolean morning){
		List<Constraint> cons=new LinkedList<>();
		for(Constraint c: constraints){
			if(c.getDate().equals(date)&& c.getId()==id&& morning==c.isMorning())
				cons.add(c);
		}
		return cons;
	}

	public static List<Constraint> getConstraintsByWeek() {
		List<Constraint> cons=new LinkedList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		Date currentWeekStart = calendar.getTime();
		calendar.add(Calendar.DATE, 6);
		Date currentWeekEnd = calendar.getTime();
		for(Constraint c: constraints){
			if(c.getDate().after(currentWeekStart)&& c.getDate().before(currentWeekEnd))
				cons.add(c);
		}
		return cons;
	}

	public static Result addConstraint(PresentConstraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return new Result(false,"Employee does not exist in the system");
		Constraint con=new Constraint(c.getDate(),c.isMorning(),c.getId(),c.getReason(), id);
		constraints.add(con);
		return new Result(true,"Constraint was added "+c.toString());
	}

	public static Result editConstraint(PresentConstraint c){
		if(WorkersRepo.get_by_id(c.getId())==null)
			return new Result(false,"Employee does not exist in the system");
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				con.setDate(c.getDate());
				con.setId(c.getId());
				con.setMorning(c.isMorning());
				con.setReason(c.getReason());
				return new Result(true,"Constraint was edited "+c.toString());
			}
		}
		return new Result(false,"could not find matching constraint to edit");
	}

	public static Result deleteConstraint(PresentConstraint c){
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				constraints.remove(con);
				return new Result(true,"Constraint was deleted");
			}
		}
		return new Result(false,"could not find matching constraint to delete");
	}

}
