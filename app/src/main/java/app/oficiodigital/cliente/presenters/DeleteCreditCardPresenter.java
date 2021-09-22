package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.DeleteCreditCardContract;
import app.oficiodigital.cliente.repos.FinancialRepo;


/**
 * Created by roberasd on 11/04/17.
 */

public class DeleteCreditCardPresenter implements DeleteCreditCardContract.Presenter {

    private FinancialRepo mFinancialRepo;
    private DeleteCreditCardContract.View mDeleteCreditCardListener;

    public DeleteCreditCardPresenter(Context context, DeleteCreditCardContract.View listener) {
        mFinancialRepo = new FinancialRepo(context, this);
        mDeleteCreditCardListener = listener;
    }

    @Override
    public void deleteCreditCard(String cardId) {
        mFinancialRepo.deleteCrediCard(cardId);
    }

    @Override
    public void doMainCard(String cardId) {
        mFinancialRepo.mainCard(cardId);
    }

    @Override
    public void onCardMadeMain() {
        mDeleteCreditCardListener.onCardMadeMain();
    }

    @Override
    public void onMainCardError(String error) {
        mDeleteCreditCardListener.onMainCardError(error);
    }

    @Override
    public void onCardDeleted() {
        mDeleteCreditCardListener.onCardDeleted();
    }

    @Override
    public void onDeleteCardError(String error) {
        mDeleteCreditCardListener.onDeleteCardError(error);
    }
}
