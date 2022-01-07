package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.cambiosypermutas.cliente.models.Audit;
import app.cambiosypermutas.cliente.utils.TableConstants.AuditTable;

/**
 * Created by Roberasd on 06/01/17.
 */

public class AuditParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Audit audit = (Audit) object;
        ContentValues values = new ContentValues();
        values.put(AuditTable.ID, audit.getId());
        values.put(AuditTable.CLIENT_ID, audit.getClient_id());
        values.put(AuditTable.DATE, audit.getDate());
        values.put(AuditTable.STATUS, audit.getStatus());
        values.put(AuditTable.NOTES, audit.getNotes());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Audit audit = new Audit();
        audit.setId((String) map.get(AuditTable.ID));
        audit.setClient_id((String) map.get(AuditTable.CLIENT_ID));
        audit.setDate((String) map.get(AuditTable.DATE));
        audit.setStatus(Integer.parseInt((String) map.get(AuditTable.STATUS)));
        audit.setNotes((String) map.get(AuditTable.NOTES));

        return audit;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Audit> audits = new ArrayList<>();

        for (HashMap map : mapList){
            Audit audit = (Audit) deserialize(map);
            audits.add(audit);
        }

        return audits;
    }
}
