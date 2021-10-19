package app.oficiodigital.cliente.storage.daohandlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.models.AccountStatus;
import app.oficiodigital.cliente.parsers.AccountStatusParser;
import app.oficiodigital.cliente.storage.DaoBase;
import app.oficiodigital.cliente.storage.DaoInt;
import app.oficiodigital.cliente.utils.TableConstants.AccountStatusTable;

/**
 * Created by roberasd on 10/04/17.
 */

public class AccountStatusDao extends DaoBase implements DaoInt {

    private final AccountStatusParser mAccountStatusParser;

    public AccountStatusDao(Context ctx) {
        super(ctx);
        mAccountStatusParser = new AccountStatusParser();
        table = AccountStatusTable.TABLE_NAME;
        fields = AccountStatusTable.FIELDS;
    }

    @Override
    public boolean add(Object object) {
        return super.add(mAccountStatusParser.serialize(object));
    }

    @Override
    public boolean add(List<? extends Object> list) {
        ArrayList<AccountStatus> accountStatusList = (ArrayList<AccountStatus>) list;
        boolean wasInserted = true;
        for (AccountStatus accountStatus : accountStatusList){
            boolean insertedresult = add(accountStatus);
            if (!insertedresult)
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
        return mAccountStatusParser.deserialize(super.getAll(null));
    }

    @Override
    public ArrayList<? extends Object> getAllByCondition(String id) {
        return  null;
    }

    @Override
    public void updateItemByID(Object object, int id) {

    }
}
