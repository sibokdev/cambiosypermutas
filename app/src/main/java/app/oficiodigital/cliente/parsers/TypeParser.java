package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Types;
import app.oficiodigital.cliente.utils.TableConstants.TypesTable;

/**
 * Created by Roberasd on 16/01/17.
 */

public class TypeParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Types types = (Types) object;
        ContentValues values = new ContentValues();
        values.put(TypesTable.ID, types.getId());
        values.put(TypesTable.NAME, types.getName());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Types types = new Types();
        types.setId((String) map.get(TypesTable.ID));
        types.setName((String) map.get(TypesTable.NAME));

        return types;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Types> types = new ArrayList<Types>();

        for (HashMap map : mapList)
            types.add((Types) deserialize(map));

        return types;
    }
}
