package Business;


import DataAccess.DalLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Sections {
    private HashMap<Integer, List<String>> areas;

    public Sections(HashMap<Integer, List<String>> areas){
        this.areas = areas;
    }

    public int getSection(Location location){
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

    public boolean addLocationToSection(int section, Location location)
    {
        List<String> list = areas.get(section);
        if (list==null) //new Section
        {
            list = new LinkedList<>();
            list.add(location.getAddress());
            areas.put(section, list);
            return true;
        }
        return list.add(location.getAddress());
    }

    public void removeLocationFromSection(int Section, DalLocation location)
    {
        List<String> list = areas.get(Section);
        if(list != null) {
            list.remove(location);
        }
    }
}
