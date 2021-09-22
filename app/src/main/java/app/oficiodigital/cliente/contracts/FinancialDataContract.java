package app.oficiodigital.cliente.contracts;


import java.util.ArrayList;

import app.oficiodigital.cliente.models.CreditCard;

/**
 * Created by Roberasd on 25/10/16.
 */

public class FinancialDataContract {

    public interface View {
        void onGetCards(ArrayList<CreditCard> cards);
        void onGetCardsFail(String error);
    }

    public interface Presenter {
        void getCards();
        void getCardsInCache();
        void onGetCards(ArrayList<CreditCard> cards);
        void onGetCardsFail(String error);

    }

}
