package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.oficiodigital.cliente.models.User;
import app.oficiodigital.cliente.storage.TableStructures;

public class UserParser implements ParserInt {

    private String[] fields;

    public UserParser() {
        fields = TableStructures.TableUser.FIELDS;
    }

    @Override
    public ContentValues serialize(Object object) {
        User user = (User) object;
        ContentValues values = new ContentValues();

        values.put(fields[0], user.getId());
        values.put(fields[1], user.getName());
        values.put(fields[2], user.getSurname1());
        values.put(fields[3], user.getSurname2());
        values.put(fields[4], String.valueOf(user.getGender()));
        values.put(fields[5], user.getBirthday());
        values.put(fields[6], user.getPhone());
        values.put(fields[7], user.getEmail());
        values.put(fields[8], user.getPassword());
        values.put(fields[9], user.getSecurityQuestion());
        values.put(fields[10], user.getSecurityAnswer());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        String id = (String) map.get(fields[0]);
        String name = (String) map.get(fields[1]);
        String lastname = (String) map.get(fields[2]);
        String lastname2 = (String) map.get(fields[3]);
        int genre = (int) map.get(fields[4]);
        String birthday = (String) map.get(fields[5]);
        String phone = (String) map.get(fields[6]);
        String email = (String) map.get(fields[7]);
        String password = (String) map.get(fields[8]);
        String recoveryQuestion = (String) map.get(fields[9]);
        String recoveryAnswer = (String) map.get(fields[10]);

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname1(lastname);
        user.setSurname2(lastname2);
        user.setGender(genre);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setSecurityQuestion(recoveryQuestion);
        user.setSecurityAnswer(recoveryAnswer);

        return user;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<User> list = new ArrayList<>();

        for (HashMap map : mapList) {
            User user = (User) deserialize(map);
            list.add(user);
        }

        return list;
    }
}
