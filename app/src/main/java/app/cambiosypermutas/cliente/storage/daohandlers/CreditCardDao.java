package app.cambiosypermutas.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.models.CreditCard;
import app.cambiosypermutas.cliente.parsers.CreditCardParser;
import app.cambiosypermutas.cliente.storage.DaoBase;
import app.cambiosypermutas.cliente.storage.DaoInt;
import app.cambiosypermutas.cliente.utils.TableConstants.CreditCardsTable;

/**
 * Created by Roberasd on 13/01/17.
 */

public class CreditCardDao extends DaoBase implements DaoInt {

    private final CreditCardParser mCreditCardParser;

    public CreditCardDao(Context ctx) {
        super(ctx);
        table = CreditCardsTable.TABLE_NAME;
        fields = CreditCardsTable.FIELDS;
        mCreditCardParser = new CreditCardParser();
    }

    @Override
    public boolean add(Object object) {
        return super.add(mCreditCardParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        List<CreditCard> creditCards = (List<CreditCard>) list;
        boolean wasInserted = true;

        for (CreditCard card : creditCards) {
            if (!add(card))
                wasInserted = false;
        }

        return wasInserted;
    }

    @Override
    public boolean deleteItemByID(String id) {
        String condition = CreditCardsTable.ID + " = " + id;
        return super.deleteAll(condition);
    }

    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public Object getItemByID(String id) {
        String condition = CreditCardsTable.ID + " = " + id;
        return mCreditCardParser.deserialize(getAll(condition)).get(0);
    }

    @Override
    public ArrayList<? extends Object> getAll() {
        return mCreditCardParser.deserialize(getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
