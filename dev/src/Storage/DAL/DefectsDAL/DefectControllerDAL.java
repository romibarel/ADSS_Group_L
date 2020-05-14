package Storage.DAL.DefectsDAL;

import Storage.Buisness.Defects.Defect;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DefectControllerDAL {

    List<DefectDAL> defects;

    public DefectControllerDAL(){
        this.defects = new ArrayList<>();
    }

    public List<DefectDAL> getDefects() {
        return defects;
    }

    public void setDefects(List<DefectDAL> defects) {
        this.defects = defects;
    }

    public void addDefect (DefectDAL defectDAL){
        this.defects.add(defectDAL);

    }



}
