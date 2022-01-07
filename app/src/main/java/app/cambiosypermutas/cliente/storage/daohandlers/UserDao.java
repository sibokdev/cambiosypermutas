package app.cambiosypermutas.cliente.storage.daohandlers;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.cambiosypermutas.cliente.models.User;
import app.cambiosypermutas.cliente.parsers.UserParser;
import app.cambiosypermutas.cliente.storage.DaoBase;
import app.cambiosypermutas.cliente.storage.DaoInt;
import app.cambiosypermutas.cliente.storage.TableStructures;

public class UserDao extends DaoBase implements DaoInt {

    private final String idField;

    public UserDao(Context ctx) {
        super(ctx);

        table = TableStructures.TableUser.NAME;
        fields = TableStructures.TableUser.FIELDS;
        idField = fields[0];
    }

    @Override
    public boolean add(Object object) {
        User user = (User) object;
        UserParser parser = new UserParser();
        ContentValues values = parser.serialize(user);

        return super.add(values);
    }

    @Override
    public boolean add(List<? extends Object> list) {
        boolean wasInserted = true;
        ArrayList<User> usersList = (ArrayList<User>) list;

        for (User user : usersList) {
            boolean insertResult = add(user);
            if (!insertResult)
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        return super.deleteByValue(id, idField);
    }

    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        UserParser parser = new UserParser();
        String condition = idField + " = '" + id + "'";

        ArrayList<HashMap> mapList = getAll(condition);
        User user = null;

        if (mapList != null && mapList.size() > 0)
            user = (User) parser.deserialize(mapList).get(0);

        return user;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        UserParser parser = new UserParser();
        ArrayList<HashMap> mapList = getAll(null);
        ArrayList<User> list = (ArrayList<User>) parser.deserialize(mapList);

        return list;
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String condition) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {
        User user = (User) object;
        UserParser parser = new UserParser();
        ContentValues values = parser.serialize(user);

        String condition = idField + " = " + id;

        super.update(values, condition);
    }
}
