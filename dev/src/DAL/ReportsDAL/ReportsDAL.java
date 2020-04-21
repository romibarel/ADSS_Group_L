package DAL.ReportsDAL;


import java.util.ArrayList;
import java.util.List;

public class ReportsDAL {
    private List <DefectReportDAL> defectReportDALList;
    private List<ProductReportDAL> productReportDALList;
    private static ReportsDAL instance;

    private ReportsDAL(){
        this.defectReportDALList = new ArrayList<DefectReportDAL>();
        this.productReportDALList = new ArrayList<ProductReportDAL>();
    }

    public static ReportsDAL getInstance(){

        if (instance == null)
            instance = new ReportsDAL();

        return instance;
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
