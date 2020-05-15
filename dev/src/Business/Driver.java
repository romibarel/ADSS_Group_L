package Business;

import Interface.InterfaceWorker;

import java.util.List;

public class Driver extends Worker
{
    private List<String> licenses;

    public Driver(InterfaceWorker worker,List<String> licenses)
    {
        super(worker);
        this.licenses = licenses;
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
