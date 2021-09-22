package app.oficiodigital.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

public interface ParserInt {

    public abstract ContentValues serialize(Object object);

    public Object deserialize(HashMap map);

    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList);

}
