package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanReport;

import java.time.LocalDateTime;

public abstract class Report {
    private static int statID = 1;
    private int reportID;
    private LocalDateTime dateReported;
    private Order reportedOrder;

    public Report(LocalDateTime dateReported, Order reportedOrder){
        reportID = statID++;
        this.dateReported = dateReported;
        this.reportedOrder = reportedOrder;
    }

    public Report(LoanReport lr){
        statID++;
        reportID = lr.getReportID();
        dateReported = lr.getDateReported();
        reportedOrder = new Order(lr.getReportedOrder());
    }

    public static void setStatID(int ID){
        statID = ID;
    }

    public abstract LoanReport getLoan();

    public int getID(){
        return reportID;
    }

    public LocalDateTime getDateReported(){
        return dateReported;
    }

    public Order getReportedOrder(){
        return reportedOrder;
    }
}
