package BusinessLayer;

import java.time.LocalDate;

public abstract class Report {
    private static int statID = 1;
    private int reportID;
    private LocalDate dateReported;
    private Order reportedOrder;

    public Report(LocalDate dateReported, Order reportedOrder){
        reportID = statID++;
        this.dateReported = dateReported;
        this.reportedOrder = reportedOrder;
    }

    public int getID(){
        return reportID;
    }

    public LocalDate getDateReported(){
        return dateReported;
    }

    public Order getReportedOrder(){
        return reportedOrder;
    }
}
