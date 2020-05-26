package DeliveryAndWorkers.DataAccess.DALObjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DalSections {
    //todo change to string
    private HashMap<Integer, List<String>> areas;

    public DalSections(HashMap<Integer, List<String>> areas){
        this.areas = areas;
    }

    public int getSection(DalLocation location){
        int area = 0;
        for (int key : areas.keySet()){
            if (areas.get(key).contains(location.getAddress())){
                area = key;
            }
        }
        return area;
    }

    public void addSection(int section)
    {
        areas.putIfAbsent(section, new LinkedList<>());
    }

    public boolean addLocationToSection(int section, String location)
    {
        List<String> list = areas.get(section);
        if (list==null) //new Section
        {
            list = new LinkedList<>();
            list.add(location);
            areas.put(section, list);
            return true;
        }
        return list.add(location);
    }

    public void removeLocationFromSection(int Section, DalLocation dalLocation)
    {
        List<String> list = areas.get(Section);
        if(list != null) {
            list.remove(dalLocation);
        }
    }

    public DalSections() {
        areas = new HashMap<>();
    }

    public HashMap<Integer, List<String>> getAreas() {
        return areas;
    }

    public void setAreas(HashMap<Integer, List<String>> areas) {
        this.areas = areas;
    }

}
