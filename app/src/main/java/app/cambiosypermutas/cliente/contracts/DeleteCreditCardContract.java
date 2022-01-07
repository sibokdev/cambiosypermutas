package app.cambiosypermutas.cliente.contracts;

/**
 * Created by roberasd on 11/04/17.
 */

public class DeleteCreditCardContract {
    public interface View {
        void onCardMadeMain();
        void onMainCardError(String error);
        void onCardDeleted();
        void onDeleteCardError(String error);
    }

    public interface Presenter {
        void deleteCreditCard(String cardId);
        void doMainCard(String cardId);
        void onCardMadeMain();
        void onMainCardError(String error);
        void onCardDeleted();
        void onDeleteCardError(String error);
    }
}
