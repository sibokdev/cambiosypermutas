package app.cambiosypermutas.cliente.utils.TableConstants;

/**
 * Created by Roberasd on 20/12/16.
 */

public final class MovementsTable {
    public static final String MOVEMENTS_TABLE = "movements";

    public static final String ID = "id";
    public static final String DOCUMENT_ID = "document_id";
    public static final String NEW_LOCATION = "new_location";
    public static final String DATE = "date";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public static final String[] FIELDS = {
            ID,
            DOCUMENT_ID,
            NEW_LOCATION,
            DATE,
            CREATED_AT,
            UPDATED_AT
    };
}
