package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Document;
import app.oficiodigital.cliente.utils.TableConstants.DocumentTable;

/**
 * Created by Roberasd on 15/12/16.
 */

public class DocumentParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Document document = (Document) object;
        ContentValues values = new ContentValues();

        values.put(DocumentTable.ID, document.getId());
        values.put(DocumentTable.FOLIO, document.getFolio());
        values.put(DocumentTable.LOCATION, document.getLocation());
        values.put(DocumentTable.TYPE, document.getType());
        values.put(DocumentTable.SUBTYPE, document.getSubtype());
        values.put(DocumentTable.ALIAS, document.getAlias());
        values.put(DocumentTable.NUMBER, document.getNumber());
        values.put(DocumentTable.EXPEDITION, document.getExpedition());
        values.put(DocumentTable.EXPIRATION, document.getExpiration());
        values.put(DocumentTable.PICTURE, document.getPicture());
        values.put(DocumentTable.STATUS, document.getStatus());
        values.put(DocumentTable.NOTES, document.getNotes());
        values.put(DocumentTable.CREATED_AT, document.getCreated_at());
        values.put(DocumentTable.UPDATED_AT, document.getUpdated_at());
        values.put(DocumentTable.IS_IN_SERVICE, document.isInService());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Document document = new Document();

        document.setId((String) map.get(DocumentTable.ID));
        document.setFolio((String) map.get(DocumentTable.FOLIO));
        document.setLocation((String) map.get(DocumentTable.LOCATION));
        document.setType((String) map.get(DocumentTable.TYPE));
        document.setSubtype((String) map.get(DocumentTable.SUBTYPE));
        document.setAlias((String) map.get(DocumentTable.ALIAS));
        document.setNumber((String) map.get(DocumentTable.NUMBER));
        document.setExpedition((String) map.get(DocumentTable.EXPEDITION));
        document.setExpiration((String) map.get(DocumentTable.EXPIRATION));
        document.setPicture((String) map.get(DocumentTable.PICTURE));
        document.setStatus(Integer.parseInt((String) map.get(DocumentTable.STATUS)));
        document.setNotes((String) map.get(DocumentTable.NOTES));
        document.setCreated_at((String) map.get(DocumentTable.CREATED_AT));
        document.setUpdated_at((String) map.get(DocumentTable.UPDATED_AT));
        document.setInService(Integer.parseInt((String) map.get(DocumentTable.IS_IN_SERVICE)));

        return document;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Document> list = new ArrayList<>();

        for (HashMap map : mapList) {
            Document document = (Document) deserialize(map);
            list.add(document);
        }

        return list;
    }
}
