package app.cambiosypermutas.cliente.contracts;

import app.cambiosypermutas.cliente.models.Payment;
import app.cambiosypermutas.cliente.models.Paypal;

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
