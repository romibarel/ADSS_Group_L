package StorageAndSupplier.Storage.Buisness.Defects;

import StorageAndSupplier.Storage.DAL.DataAccess;
import StorageAndSupplier.Storage.DAL.DefectsDAL.DefectDAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectController {
    private List<Defect> defects;
    private DataAccess dataAccess;

    public DefectController() {
        this.defects = new ArrayList<>();
        this.dataAccess= DataAccess.getInstance();
        //restore();
    }

    public void restore() {
        for (DefectDAL defectDAL:this.dataAccess.getDefects()) {
            Defect defect = new Defect(defectDAL);
            this.defects.add(defect);
        }
    }

    public void addDefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        Defect defect = new Defect(date, barCode, amount, reason, creator, location, expiration);
        //DAL issues
        DefectDAL defectDAL = defect.createDAL();
        this.dataAccess.addDefect(defectDAL);

        this.defects.add(defect);
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }
}
