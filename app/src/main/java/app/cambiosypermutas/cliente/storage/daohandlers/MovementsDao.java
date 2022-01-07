package app.cambiosypermutas.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.models.Movements;
import app.cambiosypermutas.cliente.parsers.MovementsParser;
import app.cambiosypermutas.cliente.storage.DaoBase;
import app.cambiosypermutas.cliente.storage.DaoInt;
import app.cambiosypermutas.cliente.utils.TableConstants.MovementsTable;


/**
 * Created by Roberasd on 20/12/16.
 */

public class MovementsDao extends DaoBase implements DaoInt {

    private final MovementsParser mMovementsParser;

    public MovementsDao(Context ctx) {
        super(ctx);
        mMovementsParser = new MovementsParser();
        table = MovementsTable.MOVEMENTS_TABLE;
        fields = MovementsTable.FIELDS;

    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mMovementsParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        List<Movements> movementsList = (List<Movements>) list;
        boolean wasInserted = true;

        for (Movements movements : movementsList){
            if (!add(movements))
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        String condition = MovementsTable.DOCUMENT_ID + " = " + id;
        return super.deleteAll(condition);
    }

    @Override
    public Object getItemByID(String id) {
        String condition = MovementsTable.DOCUMENT_ID + " = " + id;
        return mMovementsParser.deserialize(getAll(condition)).get(0);
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mMovementsParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        String condition = MovementsTable.DOCUMENT_ID + " = " + id;
        return mMovementsParser.deserialize(getAll(condition));
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
