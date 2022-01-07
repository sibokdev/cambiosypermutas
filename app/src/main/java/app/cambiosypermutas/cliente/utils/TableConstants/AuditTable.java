package app.cambiosypermutas.cliente.utils.TableConstants;

/**
 * Created by Roberasd on 06/01/17.
 */

public class AuditTable {

    public static final String TABLE_NAME = "audits";

    public static final String ID = "id";
    public static final String CLIENT_ID = "client_id";
    public static final String DATE = "date";
    public static final String STATUS = "status";
    public static final String NOTES = "notes";
    public static final String DOCUMENTS = "documents";

    public static final String[] FIELDS = {
            ID,
            CLIENT_ID,
            DATE,
            STATUS,
            NOTES,
            DOCUMENTS
    };
}
