package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.Address;
import app.oficiodigital.cliente.utils.TableConstants.AddressTable;

/**
 * Created by roberasd on 21/03/17.
 */

public class AddressParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Address address = (Address) object;
        ContentValues values = new ContentValues();

        values.put(AddressTable.ID_ADDRESS, address.getId());
        values.put(AddressTable.ALIAS, address.getAlias());
        values.put(AddressTable.ADDRESS, address.getAddress());
        values.put(AddressTable.LATITUDE, address.getLatitude());
        values.put(AddressTable.LONGITUDE, address.getLongitude());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Address address = new Address();
        address.setId((String) map.get(AddressTable.ID_ADDRESS));
        address.setAlias((String) map.get(AddressTable.ALIAS));
        address.setAddress((String) map.get(AddressTable.ADDRESS));
        address.setLatitude(Double.parseDouble((String) map.get(AddressTable.LATITUDE)));
        address.setLongitude(Double.parseDouble((String) map.get(AddressTable.LONGITUDE)));

        return address;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Address> addresses = new ArrayList<>();

        for (HashMap map : mapList) {
            addresses.add((Address) deserialize(map));
        }

        return addresses;
    }
}
