package DeliveryAndWorkers.Business;

import DeliveryAndWorkers.Business.BuisnessObjects.Constraint;
import DeliveryAndWorkers.Business.BuisnessObjects.Shift;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceConstraint;

import java.util.*;

public class ConstrainsController
{


	private static Map<Integer, Constraint> constraints=new HashMap<>();
	private static BTDController btd=BTDController.getBTD();
	private static int id=btd.getMax()+1;

	public static List<Constraint> getConstraints()
	{
		constraints=new HashMap<>();
		List<Constraint> cons=btd.loadAllConstraint();
		listToMap(cons, true);
		return cons;
	}

	private static List<Constraint> hasToList(Map<Integer, Constraint> constraints){
		List <Constraint> cons=new LinkedList<>();
		for(Constraint c: constraints.values()){
			cons.add(c);
		}
		return cons;
	}

	private static void listToMap(List<Constraint> cons, boolean isLoaded){
		for(Constraint c: cons){
			c.setLoaded(isLoaded);
			if(constraints.containsKey(c.getCid()))
				constraints.get(c.getCid()).setLoaded(isLoaded);
			else
				constraints.put(c.getCid(),c);
		}
	}

	private static boolean isDateLoaded(int id, Date date, boolean isMorning){
		for(Constraint con: hasToList(constraints)){
			if(con.getId()==id&&con.getDate().equals(date)&& isMorning==con.isMorning()&&con.isLoaded())
				return true;
		}
		return false;
	}

	public static boolean is_available(int worker_id, Date date, boolean morning)
	{
		if(!getConstraint(id,date,morning).isEmpty())
				return false;
		return true;
	}

	public static boolean isDriverAvailable(int id, Date departureDate, Date departureTime ,Date arrivalDate, Date arrivalTime){
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		if(!getConstraint(id, departureDate, Shift.is_morning_shift(departureTime)).isEmpty())
			return false;
		if(Shift.is_morning_shift(departureTime)){
			if(!departureDate.equals(arrivalDate)){
				if(!getConstraint(id, departureDate, false).isEmpty())
					return false;
			}
		}
		if(!getConstraint(id, arrivalDate, Shift.is_morning_shift(arrivalTime)).isEmpty())
			return false;
		if(!Shift.is_morning_shift(arrivalTime)){
			if(!departureDate.equals(arrivalDate)){
				if(!getConstraint(id, departureDate, true).isEmpty())
					return false;
			}
		}
		c.setTime(departureDate);
		c.add(Calendar.DATE, 1);
		c2.setTime(arrivalDate);
		while(c.get(Calendar.DAY_OF_YEAR)!=c2.get(Calendar.DAY_OF_YEAR)){
			if(!getConstraint(id, c.getTime(), false).isEmpty())
				return false;
			if(!getConstraint(id, c.getTime(), true).isEmpty())
				return false;
			c.add(Calendar.DATE, 1);
		}
		return true;
	}

	public static List<Constraint> getConstraint(int id,Date date,  boolean morning){
		List<Constraint> cons=new LinkedList<>();
		if(!isDateLoaded(id, date,morning)){
			cons=btd.loadConstraint(id, date, morning);
			if(cons!=null)
				listToMap(cons, true);
			else
				cons=new LinkedList<>();
		}
		else{
			for(Constraint con:hasToList(constraints)){
				if(con.getId()==id&&con.getDate().equals(date)&& morning==con.isMorning())
					cons.add(con);
			}
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
		cons= btd.loadConstraintByWeek(currentWeekStart, currentWeekEnd);
		if(cons!=null)
			listToMap(cons, true);
		return cons;
	}

	public static Result addConstraint(InterfaceConstraint c){
		Result checking=Constraint.check(c);
		if(!checking.success)
			return checking;
		Constraint con=new Constraint(c.getDate(),c.isMorning(),c.getId(),c.getReason(), id);
		id++;
		constraints.put(con.getCid(), con);
		if(!btd.saveConstraint(con).success)
			return new Result(false,"could not save constraint to data base");
		return new Result(true,"Constraint was added ");
	}

	public static Result editConstraint(InterfaceConstraint c){
		Result checking=Constraint.check(c);
		Constraint constraint=null;
		if(!checking.success)
			return checking;
		for(Constraint con: hasToList(constraints)){
			if(con.getCid()==c.getCid()){
				constraint=con;
			}
		}
		if(constraint==null){
			constraint=btd.loadConstraint(c.getCid());
			if(constraint!=null)
				constraints.putIfAbsent(constraint.getCid(),constraint);
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
		for(Constraint con: hasToList(constraints)){
			if(con.getCid()==c.getCid()){
				constraints.remove(con);
			}
		}
		if(btd.loadConstraint(c.getCid())==null)
			return new Result(false,"could not find matching constraint to delete");
		if(btd.deleteConstraint(new Constraint(c)).success)
			return new Result(true,"Constraint was deleted");
		return new Result(false,"error");
	}

}
