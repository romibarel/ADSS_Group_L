package Buisness.Reports;

import DAL.DataAccess;
import DAL.ReportsDAL.DefectReportDAL;
import DAL.ReportsDAL.ProductReportDAL;
import DAL.ReportsDAL.ReportsDAL;
import Presentation.PdataInventoryReport;
import Presentation.Pdefect;

import java.util.*;

public class ReportController {
    private Map<Date , ProductReport> transactionsReports;
    private Map<Date, DefectReport> defectivesReports;
    private DataAccess dataAccess;

    public ReportController(){
        this.transactionsReports = new HashMap<>();
        this.defectivesReports = new HashMap<>();
        dataAccess = DataAccess.getInstance();
        restore();  //get from DAL all the previous date
    }

    private void restore() {
        List <DefectReportDAL> def =  dataAccess.restoreDefectsReportsList();
        List <ProductReportDAL> rep = dataAccess.restoreProductsReportList();
        for (DefectReportDAL defectReportDAL : def){
            this.defectivesReports.put(defectReportDAL.getDateStart() , new DefectReport(defectReportDAL));
        }
        for (ProductReportDAL productReportDAL : rep){
            this.transactionsReports.put(productReportDAL.getDate() , new ProductReport(productReportDAL));
        }

    }

    public DefectReport getDefectReports(Date today, Date fromDate) {

        DefectReport defectReport = new DefectReport();
        defectReport.makeNewReport(fromDate);
        this.defectivesReports.putIfAbsent(today, defectReport); //only one report per day
        //DAL issues
        DefectReportDAL defectReportDAL = defectReport.createDAL();
        this.dataAccess.addNewDefectReport(defectReportDAL );

        return this.defectivesReports.get(today);
    }

    public List<Pdefect> creatDefectReport(Date today, Date fromDate) {

       DefectReport defectReport = new DefectReport();
       List<Pdefect> toRet =  defectReport.creatNewReport(fromDate);
       this.defectivesReports.putIfAbsent(today, defectReport); //only one report per day
       //DAL issues
       DefectReportDAL defectReportDAL = defectReport.createDAL();
       this.dataAccess.addNewDefectReport(defectReportDAL);

        return toRet;
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
        ProductReportDAL productRepDataDAL = productReport.createDAL();
        this.dataAccess.addNewProductReport(productRepDataDAL);
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
        if (toRet == null){return new LinkedList<>();}
        return toRet;
    }

   public List<PdataInventoryReport> dataOfReport (Date date , String category){
        List<ProductRepData> Categories = transactionsReports.get(date).getReportData().get(category);
        if (Categories == null){return new LinkedList<>();}
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
