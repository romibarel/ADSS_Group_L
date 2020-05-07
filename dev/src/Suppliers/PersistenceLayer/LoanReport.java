package Suppliers.PersistenceLayer;

import Suppliers.BusinessLayer.Order;

import java.time.LocalDate;

public class LoanReport {
    private static int statID = 1;
    private int reportID;
    private LocalDate dateReported;
    private Order reportedOrder;

    public LoanReport(LocalDate dateReported, Order reportedOrder){
        reportID = statID++;
        this.dateReported = dateReported;
        this.reportedOrder = reportedOrder;
    }
}
