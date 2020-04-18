package Buisness.Transactions;

import java.util.Date;

public abstract class Transaction {
    private int transactionID;
    private Date date;

    public Transaction(int i, Date date) {
        this.transactionID = i;
        this.date = date;
    }
}

