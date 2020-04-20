package Business;

import java.util.List;

public class Driver {
    private List<String> licenses;
    private String name;

    public Driver(List<String> licenses, String name) {
        this.licenses = licenses;
        this.name = name;
    }

    public boolean canDriveTruck(String truckType)
    {
        return licenses.contains(truckType);
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) {
        this.licenses = licenses;
    }

    public String getName() {
        return name;
    }
}