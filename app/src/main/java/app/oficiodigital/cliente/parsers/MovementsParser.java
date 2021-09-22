package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Movements;
import app.oficiodigital.cliente.utils.TableConstants.MovementsTable;

/**
 * Created by Roberasd on 20/12/16.
 */

public class MovementsParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Movements movements = (Movements) object;
        ContentValues values = new ContentValues();

        values.put(MovementsTable.ID, movements.getId());
        values.put(MovementsTable.DOCUMENT_ID, movements.getDocument_id());
        values.put(MovementsTable.NEW_LOCATION, movements.getNewLocation());
        values.put(MovementsTable.DATE, movements.getDate());
        values.put(MovementsTable.CREATED_AT, movements.getCreatedAt());
        values.put(MovementsTable.UPDATED_AT, movements.getUpdatedAt());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Movements movements = new Movements();

        movements.setId((String) map.get(MovementsTable.ID));
        movements.setDocument_id((String) map.get(MovementsTable.DOCUMENT_ID));
        movements.setNewLocation((String) map.get(MovementsTable.NEW_LOCATION));
        movements.setDate((String) map.get(MovementsTable.DATE));
        movements.setCreatedAt((String) map.get(MovementsTable.CREATED_AT));
        movements.setUpdatedAt((String) map.get(MovementsTable.UPDATED_AT));

        return movements;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Movements> movementsList = new ArrayList<>();

        for (HashMap map : mapList){
            movementsList.add((Movements) deserialize(map));
        }

        return movementsList;
    }
}
