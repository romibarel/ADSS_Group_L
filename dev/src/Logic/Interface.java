package Logic;

import CLI.PresentConstraint;
import CLI.PresentShift;
import CLI.PresentWorker;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Interface
{

	public static void initializeSystem(){
		ConstrainsRepo.initConstrains();

	}


	public static String addEmployee(PresentWorker worker){
		Result r=WorkersRepo.add_worker(worker);
		if(r.success)
			return "employee was added:\n"+worker.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String addShift(PresentShift shift){
		Result r=ShiftRepo.add_shift(shift);
		if(r.success)
			return "shift was added:\n"+shift.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String addConstraint(PresentConstraint constraint){
		Result r=ConstrainsRepo.addConstraint(constraint);
		if(r.success)
			return "Constraint was added:\n"+constraint.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editEmployee(PresentWorker worker){
		Result r=WorkersRepo.edit_worker(worker);
		if(r.success)
			return "employee was edited:\n"+worker.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editShift(PresentShift shift, Date previous_date, boolean previous_morning){
		Result r=ShiftRepo.edit_shift(shift, previous_date, previous_morning);
		if(r.success)
			return "shift was edited:\n"+shift.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editConstraint(PresentConstraint constraint){
		Result r=ConstrainsRepo.editConstraint(constraint);
		if(r.success)
			return "Constraint was edited:\n"+constraint.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteEmployee(PresentWorker worker){
		Result r=WorkersRepo.delete_worker(worker.getId());
		if(r.success)
			return "employee was deleted\n"+worker.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteShift(PresentShift shift){
		Result r=ShiftRepo.delete_shift(shift.getDate(),shift.isMorning());
		if(r.success)
			return "shift was deleted\n"+shift.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteConstraint(PresentConstraint constraint){
		Result r=ConstrainsRepo.deleteConstraint(constraint);
		if(r.success)
			return "constraint was deleted\n"+constraint.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static PresentWorker searchEmployee(int id){
		Worker w=WorkersRepo.get_by_id(id);
		if(w==null)
			return null;
		return new PresentWorker(w);
	}

	public static PresentShift searchShift(Date date, boolean morning){
		Shift s=ShiftRepo.get_shift(date, morning);
		if(s==null)
			return null;
		return new PresentShift(s);
	}

	public static List<PresentConstraint> searchConstraint(int id,Date date,  boolean morning){
		List<Constraint> constraints=ConstrainsRepo.getConstraint(id,date,morning);
		List<PresentConstraint> presentConstraints=new LinkedList<>();
		PresentConstraint con;
		for (Constraint c: constraints) {
			con=new PresentConstraint(c);
			presentConstraints.add(con);
		}
		return presentConstraints;
	}



	public static String shiftReport(){
		PresentShift shift=new PresentShift();
		List<Shift> shifts=ShiftRepo.get_week_shifts();
		shifts.sort(new Comparator<Shift>() {
			@Override
			public int compare(Shift shift1, Shift shift2) {
				int i=shift1.getDate().compareTo(shift2.getDate());
				if(i!=0)
					return i;
				if(shift1.isMorning()==shift2.isMorning())
					return 0;
				if(shift1.isMorning())
					return -1;
				return 1;
			}
		});
		String ret="";
		if(shifts.isEmpty())
			return "No shifts to present for current week";
		int count=1;
		ret="The shift for this week are:";
		for (Shift s: shifts) {
			shift=new PresentShift(s);
			ret+="\n"+count+". "+shift.toString();
			count++;
		}
		return ret;
	}

	public static String constraintReport(){
		PresentConstraint con=new PresentConstraint();
		List<Constraint> constraints=ConstrainsRepo.getConstraintsByWeek();
		constraints.sort(new Comparator<Constraint>() {
			@Override
			public int compare(Constraint constraint, Constraint t1) {
				int i=constraint.getDate().compareTo(t1.getDate());
				if(i!=0)
					return i;
				if(constraint.isMorning()==constraint.isMorning())
					return 0;
				if(constraint.isMorning())
					return -1;
				return 1;
			}
		});
		String ret="";
		if(constraints.isEmpty())
			return "No constraint to present for current week";
		int count=1;
		ret="The constraints for this week are:";
		for (Constraint c: constraints) {
			con=new PresentConstraint(c);
			ret+="\n"+count+". "+con.toString();
			count++;
		}
		return ret;
	}

	public static String getWorkersByRole(String role,Date date,boolean morning){
		PresentWorker worker=new PresentWorker();
		List<Worker> workers=WorkersRepo.get_by_role(role, date, morning);
		String ret="";
		if(workers.isEmpty())
			return "No workers to present for selected role, date and hour";
		int count=1;
		for (Worker w: workers) {
			worker=new PresentWorker(w);
			if(count!=1)
				ret+= "\n";
			ret+=count+".\n"+worker.toString();
		}
		return ret;
	}
}
