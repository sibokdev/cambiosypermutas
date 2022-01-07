package app.cambiosypermutas.cliente.presenters;

import android.content.Context;

import java.util.ArrayList;

import app.cambiosypermutas.cliente.contracts.FinancialDataContract;
import app.cambiosypermutas.cliente.models.CreditCard;
import app.cambiosypermutas.cliente.repos.FinancialRepo;

/**
 * Created by Roberasd on 25/10/16.
 */

public class FinancialDataPresenter implements FinancialDataContract.Presenter {

    private final FinancialDataContract.View mFinancialDataListener;
    private final FinancialRepo mFinancialRepo;

    public FinancialDataPresenter(Context context, FinancialDataContract.View financialDataListener){
        mFinancialDataListener = financialDataListener;
        mFinancialRepo = new FinancialRepo(context, this);
    }

    @Override
    public void getCards() {
        mFinancialRepo.getCards();
    }

    @Override
    public void getCardsInCache() {
        mFinancialRepo.getCardsInCache();
    }

    @Override
    public void onGetCards(ArrayList<CreditCard> cards) {
        mFinancialDataListener.onGetCards(cards);
    }

    @Override
    public void onGetCardsFail(String error) {
        mFinancialDataListener.onGetCardsFail(error);
    }
}
