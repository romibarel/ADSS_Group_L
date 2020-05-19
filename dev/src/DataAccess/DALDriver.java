package DataAccess;

import Business.Worker;

import java.util.Date;
import java.util.List;

public class DALDriver extends DALWorker
{
	private List<String> licenses;

	public DALDriver(DALWorker worker , List<String> licenses)
	{
		super(worker.getName(),worker.getId(),worker.getBank_account_number(),worker.getSalary(),
			  worker.getPension(),worker.getVacation_days(),worker.getSick_days(),worker.getStart_date(),worker.getRole(),worker.getBranchAddress());
		this.licenses=licenses;
	}

	public DALDriver(){}

	public DALDriver(String name, int id, int bank_account_number, int salary, int pension, int vacation_days, int sick_days, Date start_date, String role, String branchAddress, List<String> licenses)
	{
		super(name,id,bank_account_number,salary,pension,vacation_days,sick_days,start_date,role,branchAddress);
		this.licenses=licenses;
	}

	public List<String> getLicenses()
	{
		return licenses;
	}

	public void setLicenses(List<String> licenses)
	{
		this.licenses = licenses;
	}
}
