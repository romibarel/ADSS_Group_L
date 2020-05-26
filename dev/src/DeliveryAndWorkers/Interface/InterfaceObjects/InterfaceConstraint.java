package DeliveryAndWorkers.Interface.InterfaceObjects;


import DeliveryAndWorkers.Business.BuisnessObjects.Constraint;

import java.util.Date;

public class InterfaceConstraint {
    private Date date;
    private boolean morning;
    private int id;
    private String reason;
    private int cid;

    public InterfaceConstraint(Date date, boolean morning, int id, String reason) {
        this.date = date;
        this.morning = morning;
        this.id = id;
        this.reason = reason;
        this.cid=cid;
    }

    public InterfaceConstraint(Date date, boolean morning, int id, String reason, int cid) {
        this.date = date;
        this.morning = morning;
        this.id = id;
        this.reason = reason;
        this.cid = cid;
    }

    public InterfaceConstraint() {
    }

    public InterfaceConstraint(Constraint c){
        this.date = c.getDate();
        this.morning = c.isMorning();
        this.id = c.getId();
        this.reason = c.getReason();
        this.cid=c.getCid();
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

    @Override
    public String toString() {
        return  "cid=" + cid  +
                "\ndate=" + date  +
                "\nmorning=" + morning +
                "\nid=" + id +
                "\nreason=" + reason;
    }
}
