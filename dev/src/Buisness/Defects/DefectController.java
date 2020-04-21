package Buisness.Defects;

import DAL.DefectsDAL.DefectControllerDAL;
import DAL.DefectsDAL.DefectDAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectController {
    private List<Defect> defects;
    private DefectControllerDAL defectControllerDAL;

    public DefectController() {
        this.defects = new ArrayList<>();
        this.defectControllerDAL= DefectControllerDAL.getInstance();
        restore();
    }

    private void restore() {
        for (DefectDAL defectDAL:this.defectControllerDAL.getDefects()) {
            Defect defect = new Defect(defectDAL);
            this.defects.add(defect);
        }
    }

    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        Defect defect = new Defect(date, barCode, amount, reason, creator, location, expiration);
        //DAL issues
        DefectDAL defectDAL = defect.createDAL();
        this.defectControllerDAL.addDefect(defectDAL);

        this.defects.add(defect);
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }
}
