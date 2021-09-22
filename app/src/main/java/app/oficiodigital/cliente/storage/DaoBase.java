package app.oficiodigital.cliente.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import app.oficiodigital.cliente.utils.L;

public class DaoBase {

    protected DataBaseController dbController;
    protected String table;
    protected String[] fields;

    public DaoBase(Context ctx) {
        dbController = new DataBaseController(ctx);
    }

    protected boolean add(ContentValues values) {
        boolean wasInserted = false;
        long insertResult = dbController.insert(table, values);

        if (insertResult > -1) {
            wasInserted = true;
            L.info("New item was added to " + table + " table");
        }

        return wasInserted;
    }

    protected boolean add(String table, ContentValues values) {
        boolean wasInserted = false;
        long insertResult = dbController.insert(table, values);

        if (insertResult > -1) {
            wasInserted = true;
            L.info("New item was added to " + table + " table");
        }

        return wasInserted;
    }

    protected void update(ContentValues values, String condition) {
        dbController.update(table, values, condition);
        L.info("An item from " + table + " table was updated");

        Set<Map.Entry<String, Object>> set = values.valueSet();
        Iterator iterator = set.iterator();

        String updateValues = "";
        while (iterator.hasNext()) {

            Map.Entry entry = (Map.Entry) iterator.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            updateValues += " key:"+key + " value:"+(String)(value == null?null:value.toString())+ ", ";
        }
    }

    protected boolean deleteByValue(String value, String field) {
        int deleted = dbController.delete(table, field + " = '" + value + "'");
        boolean wasDeleted = false;

        if (deleted > -1) {
            wasDeleted = true;
            L.info("The item " + field + "=" + value + " was deleted on " + table + " table");
        }

        return wasDeleted;
    }

    protected boolean deleteAll(String condition) {
        int deleted = dbController.delete(table, condition);
        boolean wasDeleted = false;

        if (deleted > -1) {
            wasDeleted = true;
            L.info("Deleted all items from " + table + " table where de condition was: '" + condition + "'");
        }

        return wasDeleted;
    }

    protected void deleteAll() {
        dbController.deleteAll(table);
        L.info("Deleted all items from " + table + " table");
    }

    protected ArrayList<HashMap> getAll(String condition) {
        ArrayList<HashMap> list = new ArrayList<>();
        dbController.open();

        Cursor cursor = dbController.getData(table, fields, condition);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap map = new HashMap();

            for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
                String fieldName = cursor.getColumnName(columnIndex);
                String columnValue = cursor.getString(columnIndex);
                map.put(fieldName, columnValue);
            }

            list.add(map);
            cursor.moveToNext();
        }

        cursor.close();
        dbController.close();

        return list;
    }

    protected ArrayList<HashMap> getAll(String condition, String[] fields, String orderBy) {
        ArrayList<HashMap> list = new ArrayList<>();
        dbController.open();

        Cursor cursor = dbController.getData(table, fields, condition, orderBy);
        cursor.moveToFirst();

        String data = "";

        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap map = new HashMap();

            data += "INSERT INTO " + table + " (";
            String columns = "";
            String values = "";
            for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
                String fieldName = cursor.getColumnName(columnIndex);
                String columnValue = cursor.getString(columnIndex);
                map.put(fieldName, columnValue);

                columns += "'" + fieldName + "', ";
                values += "'" + columnValue + "', ";
            }
            list.add(map);
            cursor.moveToNext();

            data += columns + ") VALUES (" + values + ");\n";
        }

        cursor.close();
        dbController.close();

        return list;
    }

    protected int getCount(String condition){
        dbController.open();
        Cursor cursor = dbController.getData(table, fields, condition);
        cursor.moveToFirst();

        return cursor.getCount();
    }

}
