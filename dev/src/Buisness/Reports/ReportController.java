package Buisness.Reports;

import Buisness.Invenrory.ProductRepData;
import Presentation.PdataInventoryReport;

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

    public void creatInventoryReport(Date today){
        ProductReport productReport = new ProductReport();
        productReport.makeNewReport(today);
        this.transactionsReports.putIfAbsent(today, productReport);
    }

    public List<String> getMainCategories(Date today){
        ProductReport productReport = new ProductReport();
        productReport.makeNewReport(today);
        this.transactionsReports.putIfAbsent(today, productReport);
        List<String> mainCategories = new ArrayList(transactionsReports.get(today).getHierarchy().keySet());
        return mainCategories;
    }

    public List<String> getSubCateroies (Date date , String category){
        List<String> toRet = transactionsReports.get(date).getHierarchy().get(category);
        return toRet;
    }

   public List<PdataInventoryReport> dataOfReport (Date date , String category){
        List<ProductRepData> Categories = transactionsReports.get(date).getReportData().get(category);
        List<PdataInventoryReport> toRet = new LinkedList<>();
        for(ProductRepData p : Categories){
            PdataInventoryReport newP  = new PdataInventoryReport(p.getBarCode() , p.getProductName() , p.getAmount());
            toRet.add(newP);
        }

        return  toRet;
   }

   public Collection<List<String>> subcat(Date date){
        Collection<List<String>> toRet = null;
        ProductReport p  = transactionsReports.get(date);
        toRet = transactionsReports.get(date).getHierarchy().values();
        return toRet;
   }


}
