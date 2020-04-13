package CLI;


public class PresentWorker {
    private String name;
    private int id;
    private int bank_account_number;
    private int salary;
    private int pension;
    private int vacation_days;
    private int sick_days;
    private String start_date;
    private String role;

    public PresentWorker(String name, int id, int bank_account_number, int salary, int pension, int vacation_days, int sick_days, String start_date, String role) {
        this.name = name;
        this.id = id;
        this.bank_account_number = bank_account_number;
        this.salary = salary;
        this.pension = pension;
        this.vacation_days = vacation_days;
        this.sick_days = sick_days;
        this.start_date = start_date;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(int bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getPension() {
        return pension;
    }

    public void setPension(int pension) {
        this.pension = pension;
    }

    public int getVacation_days() {
        return vacation_days;
    }

    public void setVacation_days(int vacation_days) {
        this.vacation_days = vacation_days;
    }

    public int getSick_days() {
        return sick_days;
    }

    public void setSick_days(int sick_days) {
        this.sick_days = sick_days;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PresentWorker{" +
                "name=" + name +
                ", id=" + id +
                ", bank_account_number=" + bank_account_number +
                ", salary=" + salary +
                ", pension=" + pension +
                ", vacation_days=" + vacation_days +
                ", sick_days=" + sick_days +
                ", start_date=" + start_date  +
                ", role=" + role +
                '}';
    }
}
