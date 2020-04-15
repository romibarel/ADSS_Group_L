import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectReport {
    private Date dateStart;
    private Date dateEnd;
    private List<Defect> defects;

    public DefectReport(){
        this.defects = new ArrayList<>();
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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
        //TODO: understand why end date is needed

    }
}
