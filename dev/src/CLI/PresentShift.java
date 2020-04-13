package CLI;

import java.util.List;

public class PresentShift {

    private String date;
    private boolean morning;
    private int manager_id;
    private List<Integer> workers;

    public PresentShift(String date, boolean morning, int manager_id, List<Integer> workers) {
        this.date = date;
        this.morning = morning;
        this.manager_id = manager_id;
        this.workers = workers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    @Override
    public String toString() {
        return "PresentShift{" +
                "date=" + date  +
                ", morning=" + morning +
                ", manager_id=" + manager_id +
                ", workers=" + workers +
                '}';
    }
}
