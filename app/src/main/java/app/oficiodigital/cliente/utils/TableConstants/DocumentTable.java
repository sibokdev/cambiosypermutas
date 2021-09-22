package app.oficiodigital.cliente.utils.TableConstants;

/**
 * Created by Roberasd on 15/12/16.
 */

public final class DocumentTable {
    public final static String TABLE_NAME = "documents";

    public static final String ID = "id";
    public static final String FOLIO = "folio";
    public static final String LOCATION = "location";
    public static final String TYPE = "type";
    public static final String SUBTYPE = "subtype";
    public static final String ALIAS = "alias";
    public static final String NUMBER = "number";
    public static final String EXPEDITION = "expedition";
    public static final String EXPIRATION = "expiration";
    public static final String PICTURE = "picture";
    public static final String NOTES = "notes";
    public static final String STATUS = "status";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String IS_IN_SERVICE = "is_in_service";

    public static final String[] ALL_FIELDS = {
        ID,
        FOLIO,
        LOCATION,
        TYPE,
        SUBTYPE,
        ALIAS,
        NUMBER,
        EXPEDITION,
        EXPIRATION,
        PICTURE,
        NOTES,
        STATUS,
        CUSTOMER_ID,
        CREATED_AT,
        UPDATED_AT ,
        IS_IN_SERVICE
    };
}
