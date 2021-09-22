package app.oficiodigital.cliente.storage.daohandlers;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.models.Address;
import app.oficiodigital.cliente.parsers.AddressParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.AddressTable;

/**
 * Created by roberasd on 21/03/17.
 */

public class AddressDao extends DaoBase implements DaoInt {

    private AddressParser mAddressParser;

    public AddressDao(Context context) {
        super(context);
        table = AddressTable.TABLE_NAME;
        fields = AddressTable.FIELDS;
        mAddressParser = new AddressParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mAddressParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<Address> addresses = (ArrayList<Address>) list;
        boolean wasInserted = true;
        for (Address address : addresses){
            boolean insertedresult = add(address);
            if (!insertedresult)
                wasInserted = false;
        }
        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        String conditio = AddressTable.ID_ADDRESS + " = " + id;
        return super.deleteAll(conditio);
    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        String condition = AddressTable.ID_ADDRESS + " = " + id;
        ArrayList<Address> addresses = (ArrayList<Address>) mAddressParser.deserialize(super.getAll(condition));
        Address address = new Address();
        if (addresses != null)
            address = addresses.get(0);

        return address;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mAddressParser.deserialize(super.getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {
        Address address = (Address) object;
        ContentValues values = mAddressParser.serialize(address);
        String condition = AddressTable.ID_ADDRESS + " = " + id;
        super.update(values, condition);
    }
}
