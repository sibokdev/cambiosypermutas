package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.models.Clarification;
import app.oficiodigital.cliente.parsers.ClarificationParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.ClarificationTable;

/**
 * Created by roberasd on 10/04/17.
 */

public class ClarificationDao extends DaoBase implements DaoInt {

    private final ClarificationParser mClarificationParser;

    public ClarificationDao(Context ctx) {
        super(ctx);
        table = ClarificationTable.TABLE_NAME;
        fields = ClarificationTable.FIELDS;
        mClarificationParser = new ClarificationParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mClarificationParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<Clarification> clarifications = (ArrayList<Clarification>) list;
        boolean wasInserted = true;
        for (Clarification clarification : clarifications) {
            boolean insertedresult = add(clarification);
            if (!insertedresult)
                wasInserted = false;
        }
        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        String condition = ClarificationTable.FOLIO + " = " + id;
        return super.deleteAll(condition);
    }

    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        String condition = ClarificationTable.FOLIO + " = " + id;

        ArrayList<HashMap> mapList = getAll(condition);
        Clarification clarification = null;

        if (mapList != null && mapList.size() > 0)
            clarification = (Clarification) mClarificationParser.deserialize(mapList).get(0);

        return clarification;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mClarificationParser.deserialize(super.getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
