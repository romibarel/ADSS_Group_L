

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShiftRepo
{
	private static List<Shift> shifts=new LinkedList<>();

	// checks if a worker is scheduled for any shift
	public static boolean is_scheduled(int worker_id)
	{
		for (int i=0;i<shifts.size();i++)
		{
			if (shifts.get(i).getWorkers().contains(worker_id))
				return true;
			if (shifts.get(i).getManager_id()==worker_id)
				return true;
		}
		return false;
	}

	public static Result add_shift(String date,boolean morning,String manager_id,List<Integer> workers,String branch)
	{
		return null;
	}
	public static Result edit_shift(Shift shift)
	{
		return null;
	}
	public static Result delete_shift(String date, boolean morning)
	{
		return null;
	}

	public static List<Shift> get_shift_by_branch(String branch)
	{
		return null;
	}

	public static Shift get_shift(Date date, String branch, boolean morning)
	{
		return null;
	}




}
