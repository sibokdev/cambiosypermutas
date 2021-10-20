package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.parsers.DocumentsInServiceParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.DocumentInServiceTable;

/**
 * Created by roberasd on 30/12/16.
 */

public class DocumentsInServiceDao extends DaoBase implements DaoInt {

    private final DocumentsInServiceParser mDocumentsInServiceParser;

    public DocumentsInServiceDao(Context ctx) {
        super(ctx);
        table = DocumentInServiceTable.TABLE_NAME;
        fields = DocumentInServiceTable.FIELDS;
        mDocumentsInServiceParser = new DocumentsInServiceParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mDocumentsInServiceParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        return false;
    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public boolean deleteItemByID(String id) {
        return false;
    }

    @Override
    public Object getItemByID(String id) {
        return null;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mDocumentsInServiceParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        String condition = DocumentInServiceTable.SERVICE_ID + " = " + id;
        return mDocumentsInServiceParser.deserialize(getAll(condition));
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
