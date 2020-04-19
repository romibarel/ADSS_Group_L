package DAL.ReportsDAL;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductReportDAL {

    private Date date;
    private Map<String, List<String>> hierarchy; //Map<categoryName, List[subCategory]>
    private Map<String, List<ProductRepDataDAL>> reportData; //Map<categoryName, List[Buisness.Reports.ProductRepData]>

    public ProductReportDAL(Date date, Map<String, List<String>> hierarchy, Map<String, List<ProductRepDataDAL>> reportData) {
        this.date = date;
        this.hierarchy = hierarchy;
        this.reportData = reportData;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, List<String>> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Map<String, List<String>> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Map<String, List<ProductRepDataDAL>> getReportData() {
        return reportData;
    }

    public void setReportData(Map<String, List<ProductRepDataDAL>> reportData) {
        this.reportData = reportData;
    }
}
