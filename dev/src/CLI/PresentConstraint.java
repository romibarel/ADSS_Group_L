package CLI;



public class PresentConstraint {
    private String date;
    private boolean morning;
    private int id;
    private String reason;

    public PresentConstraint(String date, boolean morning, int id, String reason) {
        this.date = date;
        this.morning = morning;
        this.id = id;
        this.reason = reason;
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

    @Override
    public String toString() {
        return "PresentConstraint{" +
                "date=" + date  +
                ", morning=" + morning +
                ", id=" + id +
                ", reason=" + reason  +
                '}';
    }
}
