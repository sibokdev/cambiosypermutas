package app.cambiosypermutas.cliente.contracts;


import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.models.User;


public class LoginContract {

    public interface Presenter {

        void login(User user);

        void onLoginSuccess(Responses userLogin);

        void onLoginFail(String error);

        void onNeedCompleteRegister();
    }

    public interface View {

        void displayLoadingMsg(boolean display);

        void onLoginSuccess(Responses userLogin);

        void onLoginFail(String error);

        void onNeedCompleteRegister();
    }
}
