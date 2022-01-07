package app.cambiosypermutas.cliente.storage.daohandlers;

import android.content.Context;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.parsers.TypeParser;
import app.cambiosypermutas.cliente.storage.DaoBase;
import app.cambiosypermutas.cliente.storage.DaoInt;
import app.cambiosypermutas.cliente.utils.TableConstants.TypesTable;

/**
 * Created by Roberasd on 16/01/17.
 */

public class TypeDao extends DaoBase implements DaoInt {

    private final TypeParser mTypeParser;

    public TypeDao(Context ctx) {
        super(ctx);
        table = TypesTable.TABLE_NAME;
        fields = TypesTable.FIELDS;
        mTypeParser = new TypeParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mTypeParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        List<Types> types = (List<Types>) list;
        boolean wasInserted = true;

        for (Types type : types) {
            boolean insertResult = add(type);
            if (!insertResult)
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
        String condition = TypesTable.ID + " = " + id;
        return mTypeParser.deserialize(getAll(condition)).get(0);
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mTypeParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
