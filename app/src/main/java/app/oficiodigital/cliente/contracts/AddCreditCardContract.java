package app.oficiodigital.cliente.contracts;


import app.oficiodigital.cliente.models.CreditCard;

/**
 * Created by Roberasd on 11/01/17.
 */

public class AddCreditCardContract {

    public interface View {
        void onCreditCardAdded();
        void onCreditCardAddedFail(String error);
    }

    public interface Presenter{
        void addCreditCard(CreditCard creditCard);
        void onCreditCardAdded();
        void onCreditCardAddedFail(String error);
    }
}
