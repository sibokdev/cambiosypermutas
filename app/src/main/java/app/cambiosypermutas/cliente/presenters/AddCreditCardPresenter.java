package app.cambiosypermutas.cliente.presenters;

import android.content.Context;

import app.cambiosypermutas.cliente.contracts.AddCreditCardContract;
import app.cambiosypermutas.cliente.models.CreditCard;
import app.cambiosypermutas.cliente.repos.FinancialRepo;

/**
 * Created by Roberasd on 11/01/17.
 */

public class AddCreditCardPresenter implements AddCreditCardContract.Presenter {

    private final FinancialRepo mFinancialRepo;
    private final AddCreditCardContract.View mAddCreditCardListener;

    public AddCreditCardPresenter(Context context, AddCreditCardContract.View listener) {
        mFinancialRepo = new FinancialRepo(context, this);
        mAddCreditCardListener = listener;
    }

    @Override
    public void addCreditCard(CreditCard creditCard) {
        mFinancialRepo.addCreditCard(creditCard);
    }

    @Override
    public void onCreditCardAdded() {
        mAddCreditCardListener.onCreditCardAdded();
    }

    @Override
    public void onCreditCardAddedFail(String error) {
        mAddCreditCardListener.onCreditCardAddedFail(error);
    }
}
