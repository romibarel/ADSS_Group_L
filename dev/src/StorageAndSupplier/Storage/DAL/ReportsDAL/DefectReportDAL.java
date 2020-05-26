package StorageAndSupplier.Storage.DAL.ReportsDAL;

import StorageAndSupplier.Storage.DAL.DefectsDAL.DefectDAL;

import java.util.Date;
import java.util.List;

public class DefectReportDAL {
    private Date dateStart;
    private List<DefectDAL> defects;

    public DefectReportDAL(Date dateStart, List<DefectDAL> defects) {
        this.dateStart = dateStart;
        this.defects = defects;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public List<DefectDAL> getDefects() {
        return defects;
    }

    public void setDefects(List<DefectDAL> defects) {
        this.defects = defects;
    }
}
