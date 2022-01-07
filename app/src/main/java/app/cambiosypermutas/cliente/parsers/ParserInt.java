package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

public interface ParserInt {

    ContentValues serialize(Object object);

    Object deserialize(HashMap map);

    ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList);

}
