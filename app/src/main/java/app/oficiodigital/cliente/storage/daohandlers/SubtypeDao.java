package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.models.SubTypes;
import app.oficiodigital.cliente.parsers.SubtypeParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.SubTypesTable;

/**
 * Created by Roberasd on 16/01/17.
 */

public class SubtypeDao extends DaoBase implements DaoInt {

    private SubtypeParser mSubtypeParser;

    public SubtypeDao(Context ctx) {
        super(ctx);
        table = SubTypesTable.TABLE_NAME;
        fields = SubTypesTable.FIELDS;

        mSubtypeParser = new SubtypeParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mSubtypeParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<SubTypes> subTypes = (ArrayList<SubTypes>) list;
        boolean wasInserted = true;
        for (SubTypes subType : subTypes){
            boolean insertedresult = add(subType);
            if (!insertedresult)
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
        return null;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return null;
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        String condition = SubTypesTable.ID_TYPE + " = " + id;
        return mSubtypeParser.deserialize(getAll(condition));
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
