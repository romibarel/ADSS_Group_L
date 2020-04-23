package BusinessLayer;

import java.time.LocalDate;

public class CancellationReport extends Report {

    public CancellationReport(LocalDate dateReported, Order arrivedOrder){
        super(dateReported, arrivedOrder);
    }
}
