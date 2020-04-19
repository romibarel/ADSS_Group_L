package DataAccess;

import java.util.List;

public class Driver {
    private List<String> licenses;
    private String name;

    public boolean addLicense(String license)
    {
        return licenses.add(license);
    }

    public boolean removeLicense(String license)
    {
        return licenses.remove(license);
    }

    public Driver(List<String> licenses, String name) {
        this.licenses = licenses;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) {
        this.licenses = licenses;
    }
}
