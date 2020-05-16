package Storage.Buisness.Reports;

import Storage.Buisness.Defects.Defect;
import Storage.Buisness.Singletone_Storage_Management;
import Storage.DAL.DefectsDAL.DefectDAL;
import Storage.DAL.ReportsDAL.DefectReportDAL;
import StorageAndSupplier.Presentation.Pdefect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class DefectReport {
    private Date dateStart;
    private List<Defect> defects;

    public DefectReport(){
        this.defects = new ArrayList<>();
    }

    public DefectReport (DefectReportDAL defectReportDAL){
        this.dateStart = defectReportDAL.getDateStart();
        defects = new ArrayList<>();
        for (DefectDAL defectDAL : defectReportDAL.getDefects()) {
            this.defects.add(new Defect(defectDAL));
        }
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }

    public void makeNewReport(Date fromDate){
        List<Defect> allDefects = Singletone_Storage_Management.getInstance().getDefects().getDefects();
        List<Defect> relevantReports = new ArrayList<>();
        for (Defect defect: allDefects) {
            if (defect.getDate().compareTo(fromDate)>=0){
                relevantReports.add(defect);
            }
        }
        this.defects = relevantReports;
        this.dateStart = fromDate;
    }

    public List<Pdefect> creatNewReport(Date fromDate){
        List<Defect> allDefects = Singletone_Storage_Management.getInstance().getDefects().getDefects();
        List<Defect> relevantReports = new ArrayList<>();
        for (Defect defect: allDefects) {
            if (defect.getDate().compareTo(fromDate)>=0){
                relevantReports.add(defect);
            }
        }
        this.defects = relevantReports;
        this.dateStart = fromDate;

        List<Pdefect> toRet = new ArrayList<>();
        for(Defect d: relevantReports){
            Pdefect defectToShow = new Pdefect(d.getDate() , d.getBarCode() , d.getAmount() , d.getReason() , d.getCreator() , d.getLocation() , d.getExpiration() );
            toRet.add(defectToShow);
        }
        return toRet;
    }

    public DefectReportDAL createDAL() {
        List<DefectDAL> defectDALS = new ArrayList<>();
        for (Defect defect: defects){
            DefectDAL d = defect.createDAL();
            defectDALS.add(d);
        }
        DefectReportDAL defectReportDAL = new DefectReportDAL(this.dateStart, defectDALS);
        return defectReportDAL;
    }

}