package app.oficiodigital.cliente.contracts;


import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.User;


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
