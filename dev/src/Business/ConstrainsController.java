package Business;

import Interface.InterfaceConstraint;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ConstrainsController
{
	public static List<Constraint> getConstraints()
	{
		return constraints;
	}

	private static List<Constraint> constraintsNew=new LinkedList<>();
	private static BTDController btd=BTDController.getBTD();
	private static int id=Integer.parseInt(btd.getMax().msg);

	public static boolean is_available(int worker_id, Date date, boolean morning)
	{
		if(!getConstraint(id,date,morning).isEmpty())
				return false;
		return true;
	}

	public static boolean isDriverAvailable(int id, Date departureDate, Date departureTime ,Date arrivalDate, Date arrivalTime){
		Calendar c = Calendar.getInstance();
		if(!getConstraint(id, departureDate, Shift.is_morning_shift(departureTime)).isEmpty())
			return false;
		if(Shift.is_morning_shift(departureTime)){
			if(!departureDate.equals(arrivalDate)){
				if(!getConstraint(id, departureDate, false).isEmpty())
					return false;
			}
		}
		if(!getConstraint(id, arrivalDate, Shift.is_morning_shift(arrivalDate)).isEmpty())
			return false;
		if(!Shift.is_morning_shift(arrivalTime)){
			if(!departureDate.equals(arrivalDate)){
				if(!getConstraint(id, departureDate, true).isEmpty())
					return false;
			}
		}
		c.setTime(departureDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		while(!c.getTime().equals(arrivalDate)){
			if(!getConstraint(id, c.getTime(), false).isEmpty())
				return false;
			if(!getConstraint(id, c.getTime(), true).isEmpty())
				return false;
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return true;
	}

	public static List<Constraint> getConstraint(int id,Date date,  boolean morning){
		List<Constraint> cons=new LinkedList<>();
		cons=btd.loadConstraint(id, date, morning);
		return cons;
	}

	public static List<Constraint> getConstraintsByWeek() {
		List<Constraint> cons=new LinkedList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		Date currentWeekStart = calendar.getTime();
		calendar.add(Calendar.DATE, 6);
		Date currentWeekEnd = calendar.getTime();
		return btd.loadConstraintByWeek(currentWeekStart, currentWeekEnd);
	}

	public static Result addConstraint(InterfaceConstraint c){
		Result checking=Constraint.check(c);
		if(!checking.success)
			return checking;
		Constraint con=new Constraint(c.getDate(),c.isMorning(),c.getId(),c.getReason(), id);
		id++;
		constraints.add(con);
		if(!btd.saveConstraint(con).success)
			return new Result(false,"could not save constraint to data base");
		return new Result(true,"Constraint was added "+c.toString());
	}

	public static Result editConstraint(InterfaceConstraint c){
		Result checking=Constraint.check(c);
		Constraint constraint=null;
		if(!checking.success)
			return checking;
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				constraint=con;
			}
		}
		if(constraint==null){
			constraint=btd.loadConstraint(c.getCid());
			if(constraint!=null)
				constraints.add(constraint);
		}
		if(constraint==null)
			return new Result(false,"could not find matching constraint to edit");
		constraint.setDate(c.getDate());
		constraint.setId(c.getId());
		constraint.setMorning(c.isMorning());
		constraint.setReason(c.getReason());
		if(!btd.updateConstraint(constraint).success)
			return new Result(false,"could not update data base");
		return new Result(true,"Constraint was edited "+c.toString());

	}

	public static Result deleteConstraint(InterfaceConstraint c){
		for(Constraint con: constraints){
			if(con.getCid()==c.getCid()){
				constraints.remove(con);
			}
		}
		if(btd.deleteConstraint(new Constraint(c)).success)
			return new Result(true,"Constraint was deleted");
		return new Result(false,"could not find matching constraint to delete");
	}

}
