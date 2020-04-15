import java.util.*;

public class ReportController {
    private Map<Date , ProductReport> transactionsReports;
    private Map<Date, DefectReport> defectivesReports;

    public ReportController(){
        this.transactionsReports = new HashMap<>();
        this.defectivesReports = new HashMap<>();
    }

    public DefectReport getDefectReports(Date today, Date fromDate) {

        DefectReport defectReport = new DefectReport();
        defectReport.makeNewReport(fromDate);
        this.defectivesReports.putIfAbsent(today, defectReport); //only one report per day
        return this.defectivesReports.get(today);
    }

    public ProductReport getTimeReports(Date today){
        ProductReport productReport = new ProductReport();
        productReport.makeNewReport(today);
        this.transactionsReports.putIfAbsent(today, productReport);
        return this.transactionsReports.get(today);
    }
}
