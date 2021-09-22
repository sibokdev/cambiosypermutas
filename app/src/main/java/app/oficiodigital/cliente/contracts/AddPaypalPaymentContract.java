package app.oficiodigital.cliente.contracts;

import app.oficiodigital.cliente.models.Payment;
import app.oficiodigital.cliente.models.Paypal;

/**
 * Created by roberasd on 05/04/17.
 */

public class AddPaypalPaymentContract {

    public interface View {
        void onPaymentAdded(Payment payment);

        void onAddPaymentError(String error);
    }

    public interface Presenter {
        void addPaypalPayment(Paypal paypal);

        void onPaymentAdded(Payment payment);

        void onAddPaymentError(String error);
    }
}
