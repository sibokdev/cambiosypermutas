package app.cambiosypermutas.cliente.storage.daohandlers;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.cambiosypermutas.cliente.models.Document;
import app.cambiosypermutas.cliente.parsers.DocumentParser;
import app.cambiosypermutas.cliente.storage.DaoBase;
import app.cambiosypermutas.cliente.storage.DaoInt;
import app.cambiosypermutas.cliente.utils.L;
import app.cambiosypermutas.cliente.utils.TableConstants.DocumentTable;

/**
 * Created by Roberasd on 15/12/16.
 */

public class DocumentDao extends DaoBase implements DaoInt {

    DocumentParser mParser;

    public DocumentDao(Context ctx) {
        super(ctx);
        table = DocumentTable.TABLE_NAME;
        fields = DocumentTable.ALL_FIELDS;
        mParser = new DocumentParser();
    }

    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public boolean add(Object object) {
        L.info("Nuevo documento agregado");
        return super.add(mParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {

        List<Document> documents = (List<Document>) list;
        boolean wasInserted = true;

        for (Document document : documents) {
            boolean insertResult = add(document);
            if (!insertResult)
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        String conditio = DocumentTable.ID + " = " + id;
        return super.deleteAll(conditio);
    }

    @Override
    public Object getItemByID(String id) {
        DocumentParser parser = new DocumentParser();

        String condition = DocumentTable.ID + " = '" + id + "'";

        ArrayList<HashMap> mapList = getAll(condition);
        Document document = null;

        if (mapList != null && mapList.size() > 0)
            document = (Document) parser.deserialize(mapList).get(0);

        return document;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        ArrayList<HashMap> map = getAll(null);
        ArrayList<Document> documents = (ArrayList<Document>) mParser.deserialize(map);

        return documents;
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return super.getAll("");
    }

    @Override
    public void updateItemByID(Object object, int id) {
        Document document = (Document) object;
        ContentValues values = mParser.serialize(document);
        String condition = DocumentTable.ID + " = " + id;
        super.update(values, condition);
    }
}
