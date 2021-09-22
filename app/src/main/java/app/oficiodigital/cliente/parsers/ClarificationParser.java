package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Clarification;
import app.oficiodigital.cliente.utils.TableConstants.ClarificationTable;

/**
 * Created by roberasd on 10/04/17.
 */

public class ClarificationParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Clarification clarification = (Clarification) object;
        ContentValues values = new ContentValues();
        values.put(ClarificationTable.CLIENT_ID, clarification.getClientId());
        values.put(ClarificationTable.CLARIFICATION_TYPE, clarification.getClarificationType());
        values.put(ClarificationTable.CONTENT, clarification.getContent());
        values.put(ClarificationTable.STATUS, clarification.getStatus());
        values.put(ClarificationTable.FOLIO, clarification.getFolio());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Clarification clarification = new Clarification();
        clarification.setClientId((String) map.get(ClarificationTable.CLIENT_ID));
        clarification.setClarificationType(Integer.parseInt((String) map.get(ClarificationTable.CLARIFICATION_TYPE)));
        clarification.setContent((String) map.get(ClarificationTable.CONTENT));
        clarification.setStatus((String) map.get(ClarificationTable.STATUS));
        clarification.setFolio((String) map.get(ClarificationTable.FOLIO));
        return clarification;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Clarification> clarifications = new ArrayList<>();

        for (HashMap map : mapList) {
            clarifications.add((Clarification) deserialize(map));
        }

        return clarifications;
    }
}
