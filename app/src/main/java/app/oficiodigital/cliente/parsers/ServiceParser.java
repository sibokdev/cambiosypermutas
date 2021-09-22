package app.oficiodigital.cliente.parsers;


import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Document;
import app.oficiodigital.cliente.models.Service;
import app.oficiodigital.cliente.utils.TableConstants.ServiceTable;

/**
 * Created by Roberasd on 23/12/16.
 */

public class ServiceParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Service service = (Service) object;
        ContentValues values = new ContentValues();
        String[] documents = new String[service.getDocuments().size()];

        values.put(ServiceTable.ID_CLIENT, service.getIdClient());
        values.put(ServiceTable.SERVICE_TYPE, service.getServiceType());
        values.put(ServiceTable.URGENT, service.getUrgent());
        values.put(ServiceTable.LATITUDE, service.getLatitude());
        values.put(ServiceTable.LONGITUDE, service.getLongitude());
        values.put(ServiceTable.DATE, service.getDate());
        values.put(ServiceTable.ADDRESS, service.getAddress());
        values.put(ServiceTable.NOTES, service.getNotes());
        for (int i = 0; i < service.getDocuments().size(); i++){
            Document document = service.getDocuments().get(i);
            documents[i] = document.getId();
        }
        values.put(ServiceTable.DOCUMENTS, Arrays.toString(documents));
        values.put(ServiceTable.ID, service.getId());
        values.put(ServiceTable.TYPE, service.getType());
        values.put(ServiceTable.STATUS, service.getStatus());
        values.put(ServiceTable.FOLIO, service.getFolio());
        values.put(ServiceTable.DESCRIPTION, service.getDescription());
        values.put(ServiceTable.CREATED_AT, service.getCreatedAt());
        values.put(ServiceTable.UPDATED_AT, service.getUpdatedAt());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Service service = new Service();

        service.setIdClient((String) map.get(ServiceTable.ID_CLIENT));
        service.setServiceType(Integer.parseInt(map.get(ServiceTable.SERVICE_TYPE).toString()));
        service.setUrgent(Integer.parseInt(map.get(ServiceTable.URGENT).toString()));
        service.setLatitude(Double.parseDouble(map.get(ServiceTable.LATITUDE).toString()));
        service.setLongitude(Double.parseDouble(map.get(ServiceTable.LONGITUDE).toString()));
        service.setDate((String) map.get(ServiceTable.DATE));
        service.setAddress((String) map.get(ServiceTable.ADDRESS));
        service.setNotes((String) map.get(ServiceTable.NOTES));
        service.setDocsInService((String) map.get(ServiceTable.DOCUMENTS));
        service.setId((String) map.get(ServiceTable.ID));
        service.setType(Integer.parseInt(map.get(ServiceTable.TYPE).toString()));
        service.setStatus(Integer.parseInt(map.get(ServiceTable.STATUS).toString()));
        service.setFolio((String) map.get(ServiceTable.FOLIO));
        service.setDescription((String) map.get(ServiceTable.DESCRIPTION));
        service.setCreatedAt((String) map.get(ServiceTable.CREATED_AT));
        service.setUpdatedAt((String) map.get(ServiceTable.UPDATED_AT));

        return service;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Service> services = new ArrayList<>();

        for (HashMap map : mapList){
            services.add((Service) deserialize(map));
        }

        return services;
    }
}
