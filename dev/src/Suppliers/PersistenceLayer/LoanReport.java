package Suppliers.PersistenceLayer;

import java.time.LocalDateTime;

public class LoanReport {
    private int reportID;
    private LocalDateTime dateReported;
    private LoanOrder reportedOrder;
    private String tag;

    public LoanReport(String tag, int reportID, LocalDateTime dateReported, LoanOrder reportedOrder){
        this.reportID = reportID;
        this.dateReported = dateReported;
        this.reportedOrder = reportedOrder;
        this.tag = tag;
    }

    public int getReportID() {
        return reportID;
    }

    public LocalDateTime getDateReported() {
        return dateReported;
    }

    public LoanOrder getReportedOrder() {
        return reportedOrder;
    }

    public String getTag() {
        return tag;
    }
}
