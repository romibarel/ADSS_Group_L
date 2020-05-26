package DeliveryAndWorkers.Interface.InterfaceObjects;


import DeliveryAndWorkers.Business.BuisnessObjects.Worker;

import java.util.Date;

public class InterfaceWorker {
    private String name;
    private int id;
    private int bank_account_number;
    private int salary;
    private int pension;
    private int vacation_days;
    private int sick_days;
    private Date start_date;
    private String role;
    private String branchAddress;

    public InterfaceWorker(String name, int id, int bank_account_number, int salary, int pension, int vacation_days, int sick_days, Date start_date, String role) {
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

    public InterfaceWorker() {
    }

    public InterfaceWorker(Worker worker) {
        this.name = worker.getName();
        this.id = worker.getId();
        this.bank_account_number = worker.getBank_account_number();
        this.salary = worker.getSalary();
        this.pension = worker.getPension();
        this.vacation_days = worker.getVacation_days();
        this.sick_days = worker.getSick_days();
        this.start_date = worker.getStart_date();
        this.role = worker.getRole();
        this.branchAddress=worker.getBranchAddress();
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Override
    public String toString() {
        return "name=" + name +
                "\nid=" + id +
                "\nbank_account_number=" + bank_account_number +
                "\nsalary=" + salary +
                "\npension=" + pension +
                "\nvacation_days=" + vacation_days +
                "\nsick_days=" + sick_days +
                "\nstart_date=" + start_date  +
                "\nrole=" + role+
                "\nbranch=" + branchAddress;
    }
}
