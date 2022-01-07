package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.cambiosypermutas.cliente.models.AccountStatus;
import app.cambiosypermutas.cliente.utils.TableConstants.AccountStatusTable;

/**
 * Created by roberasd on 10/04/17.
 */

public class AccountStatusParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        AccountStatus accountStatus = (AccountStatus) object;
        ContentValues values = new ContentValues();

        values.put(AccountStatusTable.ID, accountStatus.getId());
        values.put(AccountStatusTable.PAYMENT_METHOD, accountStatus.getPaymentMethod());
        values.put(AccountStatusTable.STATUS, accountStatus.getStatus());
        values.put(AccountStatusTable.AMOUNT, accountStatus.getAmount());
        values.put(AccountStatusTable.DATE, accountStatus.getDate());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setId((String) map.get(AccountStatusTable.ID));
        accountStatus.setPaymentMethod((String) map.get(AccountStatusTable.PAYMENT_METHOD));
        accountStatus.setAmount((String) map.get(AccountStatusTable.AMOUNT));
        accountStatus.setDate((String) map.get(AccountStatusTable.DATE));
        accountStatus.setStatus((String) map.get(AccountStatusTable.STATUS));
        return accountStatus;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<AccountStatus> accountStatus = new ArrayList<>();

        for (HashMap map : mapList) {
            accountStatus.add((AccountStatus) deserialize(map));
        }

        return accountStatus;
    }
}
