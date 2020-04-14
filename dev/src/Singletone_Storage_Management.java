public class Singletone_Storage_Management {
    private static Singletone_Storage_Management instance = null;
    private ReportController report;
    private DefectController defects;
    private TransactionController transactions;
    private LocationController locations;
    private ProductController inventory;

    private Singletone_Storage_Management() {}

    public static Singletone_Storage_Management getInstance(){

        if (instance == null)
            instance = new Singletone_Storage_Management();

        return instance;
    }
}

