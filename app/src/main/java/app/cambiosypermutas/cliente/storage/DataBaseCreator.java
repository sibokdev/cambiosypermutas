package app.cambiosypermutas.cliente.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseCreator extends SQLiteOpenHelper {

    private static final String dbName = TableStructures.DATABASE_NAME;

    private static final int DB_VERSION = 1;

    private static final String TABLE_USER = TableStructures.TableUser.CREATE;

    private static final String TABLE_SERVICES = TableStructures.TableServices.CREATE;

    private static final String TABLE_DOCUMENTS = TableStructures.TableDocuments.CREATE;

    private static final String TABLE_DOCUMENTS_IN_SERVICES = TableStructures.TableDocInServices.CREATE;

    private static final String TABLE_MOVEMENTS = TableStructures.TableMovements.CREATE;

    private static final String TABLE_FRIENDS = TableStructures.TableFriends.CREATE;

    private static final String TABLE_AUDITS = TableStructures.TableAudits.CREATE;

    private static final String TABLE_CARDS = TableStructures.TableCreditCards.CREATE;

    private static final String TABLE_TYPES = TableStructures.TableTypes.CREATE;

    private static final String TABLE_SUBTYPES = TableStructures.TableSubTypes.CREATE;

    private static final String TABLE_ADDRESSES = TableStructures.TableAddress.CREATE;

    private static final String TABLE_CLARIFICATION = TableStructures.TableClarification.CREATE;

    private static final String TABLE_ACCOUNT = TableStructures.TableAccountStatus.CREATE;

    DataBaseCreator(Context context) {
        super(context, dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_SERVICES);
        db.execSQL(TABLE_DOCUMENTS);
        db.execSQL(TABLE_DOCUMENTS_IN_SERVICES);
        db.execSQL(TABLE_MOVEMENTS);
        db.execSQL(TABLE_FRIENDS);
        db.execSQL(TABLE_AUDITS);
        db.execSQL(TABLE_CARDS);
        db.execSQL(TABLE_TYPES);
        db.execSQL(TABLE_ADDRESSES);
        db.execSQL(TABLE_SUBTYPES);
        db.execSQL(TABLE_CLARIFICATION);
        db.execSQL(TABLE_ACCOUNT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
