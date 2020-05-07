package Storage.DAL.DefectsDAL;

import java.util.Date;

public class DefectDAL {
    private Date date;
    private int barCode;
    private int amount;
    private String reason;
    private String creator;
    private int location;
    private Date expiration;

    public DefectDAL(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
        this.date = date;
        this.barCode = barCode;
        this.amount = amount;
        this.reason = reason;
        this.creator = creator;
        this.location = location;
        this.expiration = expiration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getAmount() {
        return amount;
    }

    public Date getExpiration(){return expiration;}

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setExpiration(Date accuredTime){this.expiration = accuredTime;}
}
