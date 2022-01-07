package app.cambiosypermutas.cliente.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import app.cambiosypermutas.cliente.models.CreditCard;
import app.cambiosypermutas.cliente.utils.TableConstants.CreditCardsTable;

/**
 * Created by Roberasd on 13/01/17.
 */

public class CreditCardParser implements ParserInt {

    @Override
    public ContentValues serialize(Object object) {
        CreditCard creditCard = (CreditCard) object;
        ContentValues values = new ContentValues();

        values.put(CreditCardsTable.ID, creditCard.getId());
        values.put(CreditCardsTable.CARD_NUMBER, creditCard.getNumber());
        values.put(CreditCardsTable.TITULAR, creditCard.getName());
        values.put(CreditCardsTable.MONTH, creditCard.getExpirationMonth());
        values.put(CreditCardsTable.YEAR, creditCard.getExpirationYear());
        values.put(CreditCardsTable.MAIN, creditCard.getMain());

        return values;
    }

    @Override
    public Object deserialize(HashMap map) {
        CreditCard creditCard = new CreditCard();
        creditCard.setId((String) map.get(CreditCardsTable.ID));
        creditCard.setName((String) map.get(CreditCardsTable.TITULAR));
        creditCard.setNumber((String) map.get(CreditCardsTable.CARD_NUMBER));
        creditCard.setExpirationMonth((String) map.get(CreditCardsTable.MONTH));
        creditCard.setExpirationYear((String) map.get(CreditCardsTable.YEAR));
        creditCard.setMain(Integer.parseInt((String) map.get(CreditCardsTable.MAIN)));

        return creditCard;
    }

    @Override
    public ArrayList<? extends Object> deserialize(ArrayList<HashMap> mapList) {
        ArrayList<CreditCard> cards = new ArrayList<>();
        for (HashMap map : mapList){
            cards.add((CreditCard) deserialize(map));
        }

        return cards;
    }
}
