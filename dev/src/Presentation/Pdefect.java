package Presentation;

import java.util.Date;

public class Pdefect {
    private Date date;
    private int barCode;
    private int amount;
    private String reason;
    private String creator;
    private int location;
    private Date expiration;

    public Pdefect(Date date, int barCode, int amount, String reason, String creator, int location, Date expiration) {
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

    public int getBarCode() {
        return barCode;
    }

    public int getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public String getCreator() {
        return creator;
    }

    public int getLocation() {
        return location;
    }

    public Date getExpiration() {
        return expiration;
    }
}
