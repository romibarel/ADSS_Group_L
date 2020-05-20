package Interface;

import Business.*;
import Business.BuisnessObjects.Constraint;
import Business.BuisnessObjects.Shift;
import Business.BuisnessObjects.Worker;
import Interface.InterfaceObjects.InterfaceConstraint;
import Interface.InterfaceObjects.InterfaceShift;
import Interface.InterfaceObjects.InterfaceWorker;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Interface
{


	public static String addEmployee(InterfaceWorker worker, List<String> licenses){
		Result r= WorkersController.add_worker(worker, licenses);
		if(r.success)
			return "employee was added\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String addShift(InterfaceShift shift){
		Result r= ShiftController.add_shift(shift);
		if(r.success)
			return "shift was added\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String addConstraint(InterfaceConstraint constraint){
		Result r= ConstrainsController.addConstraint(constraint);
		if(r.success)
			return "constraint was added";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editEmployee(InterfaceWorker worker){
		Result r= WorkersController.edit_worker(worker);
		if(r.success)
			return "employee was edited\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editShift(InterfaceShift shift, Date previous_date, boolean previous_morning, String previous_branch){
		Result r= ShiftController.edit_shift(shift, previous_date, previous_morning, previous_branch);
		if(r.success)
			return "shift was edited\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String editConstraint(InterfaceConstraint constraint){
		Result r= ConstrainsController.editConstraint(constraint);
		if(r.success)
			return "Constraint was edited\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteEmployee(InterfaceWorker worker){
		Result r= WorkersController.delete_worker(worker.getId());
		if(r.success)
			return "employee was deleted\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteShift(InterfaceShift shift){
		Result r= ShiftController.delete_shift(shift.getDate(),shift.isMorning(), shift.getBranchAddress());
		if(r.success)
			return "shift was deleted\n";
		return "The action have failed due to:\n "+r.msg;
	}

	public static String deleteConstraint(InterfaceConstraint constraint){
		Result r= ConstrainsController.deleteConstraint(constraint);
		if(r.success)
			return "constraint was deleted\n"+constraint.toString();
		return "The action have failed due to:\n "+r.msg;
	}

	public static InterfaceWorker searchEmployee(int id){
		Worker w= WorkersController.get_by_id(id);
		if(w==null)
			return null;
		return new InterfaceWorker(w);
	}

	public static InterfaceShift searchShift(Date date, boolean morning,  String branch){
		Shift s= ShiftController.get_shift(date, morning, branch);
		if(s==null)
			return null;
		return new InterfaceShift(s);
	}

	public static List<InterfaceConstraint> searchConstraint(int id, Date date, boolean morning){
		List<Constraint> constraints= ConstrainsController.getConstraint(id,date,morning);
		List<InterfaceConstraint> presentConstraints=new LinkedList<>();
		InterfaceConstraint con;
		for (Constraint c: constraints) {
			con=new InterfaceConstraint(c);
			presentConstraints.add(con);
		}
		return presentConstraints;
	}



	public static String shiftReport(){
		InterfaceShift shift=new InterfaceShift();
		List<Shift> shifts= ShiftController.get_week_shifts();
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
			shift=new InterfaceShift(s);
			ret+="\n"+count+". "+shift.toString();
			count++;
		}
		return ret;
	}

	public static String constraintReport(){
		InterfaceConstraint con=new InterfaceConstraint();
		List<Constraint> constraints= ConstrainsController.getConstraintsByWeek();
		if(constraints==null)
			return "error";
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
			con=new InterfaceConstraint(c);
			ret+="\n"+count+". "+con.toString();
			count++;
		}
		return ret;
	}

	public static String getWorkersByRole(String role, String branch,Date date,boolean morning){
		InterfaceWorker worker=new InterfaceWorker();
		List<Worker> workers= WorkersController.get_available_workers(role, date, morning, branch);
		String ret="";
		if(workers.isEmpty())
			return "No workers to present for selected role, date and hour";
		int count=1;
		for (Worker w: workers) {
			worker=new InterfaceWorker(w);
			if(count!=1)
				ret+= "\n";
			ret+=count+".\n"+worker.toString();
			count++;
		}
		return ret;
	}

	public static String printShifts(){
		InterfaceShift shift=new InterfaceShift();
		List<Shift> shifts= ShiftController.get_shifts();
		shifts.sort((shift1, shift2) -> {
			int i=shift1.getDate().compareTo(shift2.getDate());
			if(i!=0)
				return i;
			if(shift1.isMorning()==shift2.isMorning())
				return 0;
			if(shift1.isMorning())
				return -1;
			return 1;
		});
		String ret="";
		if(shifts.isEmpty())
			return "No shifts to present";
		int count=1;
		ret="The shift are:";
		for (Shift s: shifts) {
			shift=new InterfaceShift(s);
			ret+="\n"+count+".\n"+shift.toString();
			count++;
		}
		return ret;
	}

	public static String printConstraints(){
		InterfaceConstraint con=new InterfaceConstraint();
		List<Constraint> constraints= ConstrainsController.getConstraints();
		constraints.sort((constraint, t1) -> {
			int i=constraint.getDate().compareTo(t1.getDate());
			if(i!=0)
				return i;
			if(constraint.isMorning()==constraint.isMorning())
				return 0;
			if(constraint.isMorning())
				return -1;
			return 1;
		});
		String ret="";
		if(constraints.isEmpty())
			return "No constraint to present";
		int count=1;
		ret="The constraints are:";
		for (Constraint c: constraints) {
			con=new InterfaceConstraint(c);
			ret+="\n"+count+".\n"+con.toString();
			count++;
		}
		return ret;
	}

	public static String printEmployees(){
		InterfaceWorker con=new InterfaceWorker();
		List<Worker> workers= WorkersController.getWorkers();
		workers.sort((worker1, worker2) -> {
			return worker1.getId()-worker2.getId();
		});
		String ret="";
		if(workers.isEmpty())
			return "No employees to present";
		int count=1;
		ret="The employees are:";
		for (Worker c: workers) {
			con=new InterfaceWorker(c);
			ret+="\n"+count+".\n"+con.toString();
			count++;
		}
		return ret;
	}

	public static List<String> getBranches(){
		return WorkersController.getBranches();
	}
}
