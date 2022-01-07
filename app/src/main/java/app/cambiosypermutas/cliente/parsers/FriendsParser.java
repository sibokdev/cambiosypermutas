package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.cambiosypermutas.cliente.models.Friend;
import app.cambiosypermutas.cliente.utils.TableConstants.FriendsTable;

/**
 * Created by Roberasd on 03/01/17.
 */

public class FriendsParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        Friend friend = (Friend) object;
        ContentValues values = new ContentValues();
        values.put(FriendsTable.EMAIL, friend.getEmail());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        Friend friend = new Friend();
        friend.setEmail((String) map.get(FriendsTable.EMAIL));

        return friend;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<Friend> friends = new ArrayList<>();

        for (HashMap map : mapList){
            friends.add((Friend) deserialize(map));
        }

        return friends;
    }
}
