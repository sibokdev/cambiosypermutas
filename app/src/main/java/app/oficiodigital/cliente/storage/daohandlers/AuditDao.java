package app.oficiodigital.cliente.storage.daohandlers;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.models.Audit;
import app.oficiodigital.cliente.parsers.AuditParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.AuditTable;

/**
 * Created by Roberasd on 06/01/17.
 */

public class AuditDao extends DaoBase implements DaoInt {

    private AuditParser mAuditParser;

    public AuditDao(Context context) {
        super(context);
        table = AuditTable.TABLE_NAME;
        fields = AuditTable.FIELDS;
        mAuditParser = new AuditParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mAuditParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<Audit> audits = (ArrayList<Audit>) list;
        boolean wasInserted = true;

        for (Audit audit : audits){
            boolean inserted = add(audit);
            if (!inserted)
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        return false;
    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        String condition = AuditTable.ID + " = " + id;
        return mAuditParser.deserialize(getAll(condition).get(0));
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mAuditParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {
        Audit audit = (Audit) object;
        ContentValues values = mAuditParser.serialize(audit);
        String condition = AuditTable.ID + " = " + id;
        super.update(values, condition);
    }
}
