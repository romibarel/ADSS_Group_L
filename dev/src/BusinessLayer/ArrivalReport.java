package BusinessLayer;

import java.time.LocalDate;

public class ArrivalReport extends Report {

    public ArrivalReport(LocalDate dateReported, Order arrivedOrder){
        super(dateReported, arrivedOrder);
    }
}
