package Storage.Buisness.Transactions;

import java.util.Date;

public abstract class Transaction {
    private int transactionID;
    private Date date;

    public Transaction(int i, Date date) {
        this.transactionID = i;
        this.date = date;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

