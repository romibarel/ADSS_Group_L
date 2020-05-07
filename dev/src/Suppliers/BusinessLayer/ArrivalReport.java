package Suppliers.BusinessLayer;

import Suppliers.PersistenceLayer.LoanReport;

import java.time.LocalDateTime;

public class ArrivalReport extends Report {

    public ArrivalReport(LocalDateTime dateReported, Order arrivedOrder){
        super(dateReported, arrivedOrder);
    }

    public ArrivalReport(LoanReport lr){
        super(lr);
    }

    public LoanReport getLoan() {
        return new LoanReport("Arrival", getID(), getDateReported(), getReportedOrder().getLoan());
    }
}
