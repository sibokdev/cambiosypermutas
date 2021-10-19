package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.GetUrgentCostContract;
import app.oficiodigital.cliente.repos.FinancialRepo;


/**
 * Created by Roberasd on 13/01/17.
 */

public class GetUrgentCostPresenter implements GetUrgentCostContract.Presenter {

    private final FinancialRepo mFinancialRepo;
    private final GetUrgentCostContract.View mGetUrgentCostListener;

    public GetUrgentCostPresenter(Context context, GetUrgentCostContract.View listener){
        mGetUrgentCostListener = listener;
        mFinancialRepo = new FinancialRepo(context, this);
    }

    @Override
    public void getUrgentCost() {
        mFinancialRepo.getUrgentCost();
    }

    @Override
    public void onGetUrgenCost(float cost) {
        mGetUrgentCostListener.onGetUrgenCost(cost);
    }

    @Override
    public void onGetUrgentCostFail(String error) {
        mGetUrgentCostListener.onGetUrgentCostFail(error);
    }
}
