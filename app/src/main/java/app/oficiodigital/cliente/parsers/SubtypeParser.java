package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.SubTypes;
import app.oficiodigital.cliente.utils.TableConstants.SubTypesTable;

/**
 * Created by Roberasd on 16/01/17.
 */

public class SubtypeParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        SubTypes subTypes = (SubTypes) object;
        ContentValues values = new ContentValues();
        values.put(SubTypesTable.ID_SUBTYPE, subTypes.getId());
        values.put(SubTypesTable.NAME, subTypes.getName());
        values.put(SubTypesTable.ID_TYPE, subTypes.getTypeId());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        SubTypes subTypes = new SubTypes();
        subTypes.setId((String) map.get(SubTypesTable.ID_SUBTYPE));
        subTypes.setName((String) map.get(SubTypesTable.NAME));
        subTypes.setTypeId((String) map.get(SubTypesTable.ID_TYPE));

        return subTypes;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<SubTypes> subTypes = new ArrayList<>();

        for (HashMap map : mapList)
            subTypes.add((SubTypes) deserialize(map));

        return subTypes;
    }
}
