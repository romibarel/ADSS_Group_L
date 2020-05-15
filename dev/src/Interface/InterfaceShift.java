package Interface;

import Business.Shift;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InterfaceShift {

    private Date date;
    private boolean morning;
    private int manager_id;
    private List<Integer> workers;
    private String branchAddress;

    public InterfaceShift(Date date, boolean morning, int manager_id, List<Integer> workers,String branchAddress) {
        this.date = date;
        this.morning = morning;
        this.manager_id = manager_id;
        this.workers = workers;
        this.branchAddress=branchAddress;
    }

    public InterfaceShift() {
        workers=new LinkedList<>();
    }

    public InterfaceShift(Shift shift){
        this.date = shift.getDate();
        this.morning = shift.isMorning();
        this.manager_id = shift.getManager_id();
        this.workers = shift.getWorkers();
        this.branchAddress=shift.getBranchAddress();
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

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public List<Integer> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Integer> workers) {
        this.workers = workers;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Override
    public String toString() {
        return
                "date=" + date  +
                "\nmorning=" + morning +
                "\nmanager_id=" + manager_id +
                "\nworkers=" + workers+
                "\nbranch=" + branchAddress;
    }
}
