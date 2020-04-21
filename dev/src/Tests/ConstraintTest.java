package Tests;
import CLI.PresentConstraint;
import CLI.PresentShift;
import CLI.PresentWorker;
import Logic.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ConstraintTest {

    @Before
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
        assertFalse(ConstrainsRepo.addConstraint(c).success);
    }

    //check if a constraint with an already scheduled shift isn't added.
    @Test
    public void addConstraint3() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        PresentShift s=new PresentShift(date, true, 209473891, new LinkedList<>());
        ShiftRepo.add_shift(s);
        PresentConstraint c=new PresentConstraint(date, true, 209473891, "wedding");
        assertFalse(ConstrainsRepo.addConstraint(c).success);
    }

    //check if constraint is edited successfully
    @Test
    public void editConstraint1() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        PresentConstraint c=new PresentConstraint(date, true, 209473891, "wedding");
        ConstrainsRepo.addConstraint(c);
        c.setCid(ConstrainsRepo.getConstraints().get(0).getCid());
        c.setReason("doctor");
        assertTrue(ConstrainsRepo.editConstraint(c).success);
    }

    //check if a constraint with a wrong worker id isn't edited.
    @Test
    public void editConstraint2() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        PresentConstraint c=new PresentConstraint(date, true, 209473891, "wedding");
        ConstrainsRepo.addConstraint(c);
        c.setCid(ConstrainsRepo.getConstraints().get(0).getCid());
        c.setId(123456789);
        assertFalse(ConstrainsRepo.editConstraint(c).success);
    }

    //check if a constraint with an already scheduled shift isn't added.
    @Test
    public void editConstraint3() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");
        PresentShift s=new PresentShift(date2, true, 209473891, new LinkedList<>());
        ShiftRepo.add_shift(s);
        PresentConstraint c=new PresentConstraint(date, true, 209473891, "wedding");
        ConstrainsRepo.addConstraint(c);
        c.setCid(ConstrainsRepo.getConstraints().get(0).getCid());
        c.setDate(date2);
        assertFalse(ConstrainsRepo.editConstraint(c).success);
    }

}
