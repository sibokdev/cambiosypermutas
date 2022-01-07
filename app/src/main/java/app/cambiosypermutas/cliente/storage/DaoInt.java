package app.cambiosypermutas.cliente.storage;

import java.util.ArrayList;
import java.util.List;

public interface DaoInt {

    boolean add(Object object);

    boolean add(List<? extends Object> list);

    boolean deleteItemByID(String id);

    Object getItemByID(String id);

    ArrayList<? extends Object> getAll();

    ArrayList<? extends Object> getAllByCondition(String id);

    void updateItemByID(Object object, int id);

}
