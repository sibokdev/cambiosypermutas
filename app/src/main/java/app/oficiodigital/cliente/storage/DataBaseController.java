package app.oficiodigital.cliente.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import app.oficiodigital.cliente.utils.L;


public class DataBaseController {

    private DataBaseCreator mDbHelper;
    private SQLiteDatabase mDb;
    private Context mContext;

    public DataBaseController(Context context) {
        mContext = context;
    }

    public DataBaseController open() throws SQLException {
        mDbHelper = new DataBaseCreator(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long insert(String table, ContentValues cValues){
        long wasInserted = -1;

        try {
            open();
            wasInserted = mDb.insert(table, null, cValues);
            close();
        } catch(Exception e) {
            close();
            L.error("DATABASE insert error: " + e.toString());
        }

        return wasInserted;
    }

    public void update(String table, ContentValues cValues, String condition){
        try {
            open();
            int update = mDb.update(table, cValues, condition, null);
            L.error("DATABASE update: " + update);
            close();
        } catch(Exception e) {
            close();
            L.error("DATABASE update error: " + e.toString());
        }
    }

    public int delete(String table,  String condition){
        try{
            open();
            mDb.delete(table, condition, null);
            close();
            return 1;
        }catch(Exception e){
            close();
            L.error("DATABASE delete error: " + e.toString());
            return -1;
        }
    }

    public int deleteAll(String table) {
        try{
            open();
            mDb.delete(table, null, null);
            close();
            return 1;
        }catch(Exception e){
            close();
            L.error("DATABASE delete all error: " + e.toString());
            return -1;
        }
    }

    public Cursor getData(String tables, String[] columns, String conditional){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);

        return queryBuilder.query(mDb, columns , conditional, null, null, null, null);
    }

    public Cursor getData(String tables, String[] columns, String conditional, String orderBy){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tables);

        return queryBuilder.query(mDb, columns , conditional, null, null, null, orderBy);
    }

    public Cursor getData(String rawQuery) {
        return mDb.rawQuery(rawQuery, null);
    }

}
