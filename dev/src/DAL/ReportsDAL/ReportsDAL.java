package DAL.ReportsDAL;


import java.util.ArrayList;
import java.util.List;

public class ReportsDAL {
    List <DefectReportDAL> defectReportDALList;
    List<ProductReportDAL> productReportDALList;

    public ReportsDAL(){
        this.defectReportDALList = new ArrayList<DefectReportDAL>();
        this.productReportDALList = new ArrayList<ProductReportDAL>();
    }

    public void addNewProductReport (ProductReportDAL productReportDAL){
        this.productReportDALList.add(productReportDAL);
    }
    public void addNewDefectReport (DefectReportDAL defectReportDAL){
        this.defectReportDALList.add(defectReportDAL);
    }

    public List <DefectReportDAL> restoreDefectsReportsList (){return this.defectReportDALList;}

    public List <ProductReportDAL> restoreProductsReportList(){return this.productReportDALList;}
}
