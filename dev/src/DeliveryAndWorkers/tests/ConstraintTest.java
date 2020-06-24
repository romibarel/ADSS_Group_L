package DeliveryAndWorkers.tests;
import DeliveryAndWorkers.Business.ConstrainsController;
import DeliveryAndWorkers.Business.ShiftController;
import DeliveryAndWorkers.Business.WorkersController;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceConstraint;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceShift;
import DeliveryAndWorkers.Interface.InterfaceObjects.InterfaceWorker;
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
        ConstrainsController.getConstraints().clear();
        ShiftController.get_shifts().clear();
        WorkersController.getWorkers().clear();
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2019");
        InterfaceWorker w=new InterfaceWorker("worker worker", 209473891,1515,30,3, 30,30,date,"manager" );
        w.setBranchAddress("Costco");
        WorkersController.add_worker(w, null);
    }

    //check if constraint is added successfully
    @Test
    public void addConstraint1() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        InterfaceConstraint c=new InterfaceConstraint(date, true, 209473891, "wedding");
        assertTrue(ConstrainsController.addConstraint(c).success);
    }

    //check if a constraint with a wrong worker id isn't added.
    @Test
    public void addConstraint2() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        InterfaceConstraint c=new InterfaceConstraint(date, true, 123456789, "wedding");
        assertFalse(ConstrainsController.addConstraint(c).success);
    }

    //check if a constraint with an already scheduled shift isn't added.
    @Test
    public void addConstraint3() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        InterfaceShift s=new InterfaceShift(date, true, 209473891, new LinkedList<>(), "Costco");
        ShiftController.add_shift(s);
        InterfaceConstraint c=new InterfaceConstraint(date, true, 209473891, "wedding");
        assertFalse(ConstrainsController.addConstraint(c).success);
    }

    //check if constraint is edited successfully
    @Test
    public void editConstraint1() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        InterfaceConstraint c=new InterfaceConstraint(date, true, 209473891, "wedding");
        ConstrainsController.addConstraint(c);
        c.setCid(ConstrainsController.getConstraints().get(0).getCid());
        c.setReason("doctor");
        assertTrue(ConstrainsController.editConstraint(c).success);
    }

    //check if a constraint with a wrong worker id isn't edited.
    @Test
    public void editConstraint2() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        InterfaceConstraint c=new InterfaceConstraint(date, true, 209473891, "wedding");
        ConstrainsController.addConstraint(c);
        c.setCid(ConstrainsController.getConstraints().get(0).getCid());
        c.setId(123456789);
        assertFalse(ConstrainsController.editConstraint(c).success);
    }

    //check if a constraint with an already scheduled shift isn't added.
    @Test
    public void editConstraint3() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("20/5/2020");
        Date date2=new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");
        InterfaceShift s=new InterfaceShift(date2, true, 209473891, new LinkedList<>(), "Costco");
        ShiftController.add_shift(s);
        InterfaceConstraint c=new InterfaceConstraint(date, true, 209473891, "wedding");
        ConstrainsController.addConstraint(c);
        c.setCid(ConstrainsController.getConstraints().get(0).getCid());
        c.setDate(date2);
        assertFalse(ConstrainsController.editConstraint(c).success);
    }

}
