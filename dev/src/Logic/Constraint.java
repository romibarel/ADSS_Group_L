package Logic;

import InterfaceLayer.InterfaceConstraint;

import java.util.Date;

public class Constraint {

    private Date date;
    private boolean morning;
    private int id;
    private String reason;
    private int cid;

    public Constraint(Date date, boolean morning, int id, String reason,int cid) {
        this.date = date;
        this.morning = morning;
        this.id = id;
        this.reason = reason;
        this.cid=cid;
    }

    public static Result check(InterfaceConstraint c){
        if(WorkersController.get_by_id(c.getId())==null)
            return new Result(false,"Employee does not exist in the system");
        if(ShiftController.is_worker_scheduled_at(c.getId(),c.getDate(),c.isMorning()))
            return new Result(false,"Employee is already scheduled in a shift at the same date and time");
        return new Result(true,"");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
