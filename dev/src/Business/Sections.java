package Business;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Sections {
    private HashMap<Integer, List<DataAccess.Location>> areas;

    public void addSection(int section)
    {
        areas.putIfAbsent(section, new LinkedList<>());
    }


    public boolean addLocationToSection(int section, DataAccess.Location location)
    {
        List<DataAccess.Location> list = areas.get(section);
        if (list==null) //new Section
        {
            list = new LinkedList<>();
            list.add(location);
            areas.put(section, list);
            return true;
        }
        return list.add(location);
    }

    public void removeLocationFromSection(int Section, DataAccess.Location location)
    {
        List<DataAccess.Location> list = areas.get(Section);
        if(list != null) {
            list.remove(location);
        }
    }

    public Sections(HashMap<Integer, List<DataAccess.Location>> areas) {
        this.areas = areas;
    }

    public Sections() {
        areas = new HashMap<>();
    }

    public HashMap<Integer, List<DataAccess.Location>> getAreas() {
        return areas;
    }

    public void setAreas(HashMap<Integer, List<Location>> areas) {
        this.areas = areas;
    }
}
