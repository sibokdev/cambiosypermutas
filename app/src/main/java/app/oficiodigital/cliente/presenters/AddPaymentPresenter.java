package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.AddPaymentContract;
import app.oficiodigital.cliente.repos.FinancialRepo;


/**
 * Created by Roberasd on 12/01/17.
 */

public class AddPaymentPresenter implements AddPaymentContract.Presenter {

    private FinancialRepo mFinancialRepo;
    private AddPaymentContract.View mAddPaymentListener;

    public AddPaymentPresenter(Context context, AddPaymentContract.View listener){
        mFinancialRepo = new FinancialRepo(context, this);
        mAddPaymentListener = listener;
    }

    @Override
    public void addPayment(String deviceSessionId) {
        mFinancialRepo.addPayment(deviceSessionId);
    }

    @Override
    public void getAnnualCost() {
        mFinancialRepo.getAnnualCost();
    }

    @Override
    public void onAddPayment() {
        mAddPaymentListener.onAddPayment();
    }

    @Override
    public void onAddPaymentFail(String error) {
        mAddPaymentListener.onAddPaymentFail(error);
    }

    @Override
    public void onGetAnnualCost(float cost) {
        mAddPaymentListener.onGetAnnualCost(cost);
    }

    @Override
    public void onGetAnnualCostFail(String error) {
        mAddPaymentListener.onGetAnnualCostFail(error);
    }
}
