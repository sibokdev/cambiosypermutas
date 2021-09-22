package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.models.Service;
import app.oficiodigital.cliente.parsers.ServiceParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.ServiceTable;

import static android.R.attr.id;

/**
 * Created by Roberasd on 23/12/16.
 */

public class ServiceDao extends DaoBase implements DaoInt {

    private ServiceParser mServiceParser;

    public ServiceDao(Context ctx) {
        super(ctx);
        mServiceParser = new ServiceParser();
        table = ServiceTable.TABLE_NAME;
        fields = ServiceTable.FIELDS;
    }

    @Override
    public boolean add(Object object) {
        return super.add(mServiceParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<Service> services = (ArrayList<Service>) list;
        boolean wasInserted = true;
        for (Service service: services){
            boolean insertedresult = add(service);
            if (!insertedresult)
                wasInserted = false;
        }
        return wasInserted;
    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public boolean deleteItemByID(String clause) {
        String condition;
        switch (clause) {
            case Service.ONGOING:
                condition = ServiceTable.STATUS + " = 1 OR " + ServiceTable.STATUS + " = 2 OR " + ServiceTable.STATUS + " = 3";
                break;
            case Service.COMPLETED_CANCELED:
                condition = ServiceTable.STATUS + " = 4 ";
                break;
            default:
                condition = ServiceTable.ID + " = " + id;
                break;
        }

        return super.deleteAll(condition);
    }

    @Override
    public Object getItemByID(String id) {
        String condition = ServiceTable.ID + " = " + id;

        ArrayList<HashMap> mapList = getAll(condition);
        Service service = null;

        if (mapList != null && mapList.size() > 0)
            service = (Service) mServiceParser.deserialize(mapList).get(0);

        return service;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mServiceParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
