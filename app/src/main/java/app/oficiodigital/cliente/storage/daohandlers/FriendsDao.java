package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.models.Friend;
import app.oficiodigital.cliente.parsers.FriendsParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.FriendsTable;


/**
 * Created by Roberasd on 03/01/17.
 */

public class FriendsDao extends DaoBase implements DaoInt {

    private final FriendsParser mFriendsParser;

    public FriendsDao(Context ctx) {
        super(ctx);
        table = FriendsTable.FRIENDS_TABLE;
        fields = FriendsTable.FIELDS;
        mFriendsParser = new FriendsParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mFriendsParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        List<Friend> friends = (List<Friend>) list;
        boolean wasInserted = true;

        for (Friend friend : friends) {
            boolean insertResult = add(friend);
            if (!insertResult)
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        return false;
    }

    public void deleteAll(){
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        return null;
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mFriendsParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
