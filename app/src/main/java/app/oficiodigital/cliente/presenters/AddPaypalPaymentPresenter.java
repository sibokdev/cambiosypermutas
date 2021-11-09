package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.AddPaypalPaymentContract;
import app.oficiodigital.cliente.models.Payment;
import app.oficiodigital.cliente.models.Paypal;
import app.oficiodigital.cliente.repos.PaymentRepo;


/**
 * Created by roberasd on 05/04/17.
 */

public class AddPaypalPaymentPresenter implements AddPaypalPaymentContract.Presenter {

    private final AddPaypalPaymentContract.View mAddPaypalPaymentListener;
    private final PaymentRepo mPaymentRepo;

    public AddPaypalPaymentPresenter(Context context, AddPaypalPaymentContract.View listener) {
        mAddPaypalPaymentListener = listener;
        mPaymentRepo = new PaymentRepo(context, this);
    }

    @Override
    public void addPaypalPayment(Paypal paypal) {
        mPaymentRepo.addPaypalPayment(paypal);
    }

    @Override
    public void onPaymentAdded(Payment payment) {
        mAddPaypalPaymentListener.onPaymentAdded(payment);
    }

    @Override
    public void onAddPaymentError(String error) {
        mAddPaypalPaymentListener.onAddPaymentError(error);
    }
}
