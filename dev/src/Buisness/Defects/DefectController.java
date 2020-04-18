package Buisness.Defects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectController {
    List<Defect> defects;

    public DefectController() {
        this.defects = new ArrayList<>();
    }

    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        Defect defect = new Defect(date, barCode, amount, reason, creator, location, expiration);
        this.defects.add(defect);
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }
}
