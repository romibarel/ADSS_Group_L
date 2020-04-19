package Tests;
import CLI.PresentConstraint;
import CLI.PresentShift;
import CLI.PresentWorker;
import Logic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class ConstraintTest {

    @BeforeEach
    public void before_tests() throws ParseException
    {
        ConstrainsRepo.getConstraints().clear();
        ShiftRepo.get_shifts().clear();
        WorkersRepo.getWorkers().clear();
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2019");
        PresentWorker w=new PresentWorker("worker worker", 209473891,1515,30,3, 30,30,date,"manager" );
        WorkersRepo.add_worker(w);
    }

    //check if constraint is added successfully
    @Test
    public void addConstraint1() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        PresentConstraint c=new PresentConstraint(date, true, 209473891, "wedding");
        assertTrue(ConstrainsRepo.addConstraint(c).success);
    }

    //check if a constraint with a wrong worker id isn't added.
    @Test
    public void addConstraint2() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        PresentConstraint c=new PresentConstraint(date, true, 123456789, "wedding");
        ConstrainsRepo.addConstraint(c);
        assertFalse(ConstrainsRepo.addConstraint(c).success);
    }

}
