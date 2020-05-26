package StorageAndSupplier.Suppliers.BusinessLayer;

import StorageAndSupplier.Suppliers.PersistenceLayer.LoanReport;

import java.time.LocalDateTime;

public class CancellationReport extends Report {

    public CancellationReport(LocalDateTime dateReported, Order arrivedOrder){
        super(dateReported, arrivedOrder);
    }

    public CancellationReport(LoanReport lr){
        super(lr);
    }

    public LoanReport getLoan() {
        return new LoanReport("Cancel", getID(), getDateReported(), getReportedOrder().getLoan());
    }
}
