package app.cambiosypermutas.cliente.contracts;

/**
 * Created by Roberasd on 13/01/17.
 */

public class GetUrgentCostContract {

    public interface View {
        void onGetUrgenCost(float cost);
        void onGetUrgentCostFail(String error);
    }

    public interface Presenter {
        void getUrgentCost();
        void onGetUrgenCost(float cost);
        void onGetUrgentCostFail(String error);
    }
}
