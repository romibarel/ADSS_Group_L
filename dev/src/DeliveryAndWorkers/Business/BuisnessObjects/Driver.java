package DeliveryAndWorkers.Business.BuisnessObjects;

import DeliveryAndWorkers.DataAccess.DALObjects.DALDriver;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;

import java.util.List;

public class Driver extends Worker
{
    private List<String> licenses;

    public Driver(InterfaceWorker worker,List<String> licenses)
    {
        super(worker);
        this.licenses = licenses;
    }

    public Driver(DALDriver driver)
    {
        super(driver.getName(),driver.getId(),driver.getBank_account_number(),driver.getSalary(),driver.getPension(),driver.getVacation_days()
                ,driver.getSick_days(),driver.getStart_date(),driver.getRole(),driver.getBranchAddress());
        this.licenses=driver.getLicenses();
    }

    public boolean canDriveTruck(String truckType)
    {
        return licenses.contains(truckType);
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
