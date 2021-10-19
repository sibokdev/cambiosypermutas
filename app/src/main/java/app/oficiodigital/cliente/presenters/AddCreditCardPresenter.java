package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.AddCreditCardContract;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.repos.FinancialRepo;

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
