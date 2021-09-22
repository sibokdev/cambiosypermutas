package app.oficiodigital.cliente.contracts;

/**
 * Created by Roberasd on 12/01/17.
 */

public class AddPaymentContract {

    public interface View {
        void onAddPayment();
        void onAddPaymentFail(String error);
        void onGetAnnualCost(float cost);
        void onGetAnnualCostFail(String error);
    }

    public interface Presenter {
        void addPayment(String deviceSessionId);
        void getAnnualCost();
        void onAddPayment();
        void onAddPaymentFail(String error);
        void onGetAnnualCost(float cost);
        void onGetAnnualCostFail(String error);
    }

}
