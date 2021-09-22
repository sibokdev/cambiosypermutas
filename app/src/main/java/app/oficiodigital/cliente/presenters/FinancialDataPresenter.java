package app.oficiodigital.cliente.presenters;

import android.content.Context;

import java.util.ArrayList;

import app.oficiodigital.cliente.contracts.FinancialDataContract;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.repos.FinancialRepo;

/**
 * Created by Roberasd on 25/10/16.
 */

public class FinancialDataPresenter implements FinancialDataContract.Presenter {

    private FinancialDataContract.View mFinancialDataListener;
    private FinancialRepo mFinancialRepo;

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
