package DataAccess;

import java.util.HashMap;
import java.util.List;

public class Sections {
    private HashMap<Integer, List<Location>> areas;

    public HashMap<Integer, List<Location>> getAreas() {
        return areas;
    }

    public void setAreas(HashMap<Integer, List<Location>> areas) {
        this.areas = areas;
    }
}
