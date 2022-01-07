package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.cambiosypermutas.cliente.models.DocumentsInService;
import app.cambiosypermutas.cliente.utils.TableConstants.DocumentInServiceTable;

/**
 * Created by roberasd on 30/12/16.
 */

public class DocumentsInServiceParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        ContentValues values = new ContentValues();
        DocumentsInService documentsInService = (DocumentsInService) object;
        values.put(DocumentInServiceTable.SERVICE_ID, documentsInService.getServiceId());
        values.put(DocumentInServiceTable.DOCUMENT_ID, documentsInService.getDocumentId());
        values.put(DocumentInServiceTable.LOCATION, documentsInService.getLocation());
        values.put(DocumentInServiceTable.ALIAS, documentsInService.getAlias());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        DocumentsInService documentsInService = new DocumentsInService();
        documentsInService.setServiceId((String) map.get(DocumentInServiceTable.SERVICE_ID));
        documentsInService.setDocumentId((String) map.get(DocumentInServiceTable.DOCUMENT_ID));
        documentsInService.setLocation((String) map.get(DocumentInServiceTable.LOCATION));
        documentsInService.setAlias((String) map.get(DocumentInServiceTable.ALIAS));

        return documentsInService;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<DocumentsInService> services = new ArrayList<>();

        for (HashMap map : mapList){
            services.add((DocumentsInService) deserialize(map));
        }

        return services;
    }
}
