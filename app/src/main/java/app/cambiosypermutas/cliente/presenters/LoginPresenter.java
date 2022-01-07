package app.cambiosypermutas.cliente.presenters;

import android.content.Context;

import app.cambiosypermutas.cliente.contracts.LoginContract;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.models.User;
import app.cambiosypermutas.cliente.repos.LoginRepo;

public class LoginPresenter implements LoginContract.Presenter {

    private final Context ctx;
    private final LoginContract.View viewListener;
    private final LoginRepo repo;

    public LoginPresenter(Context ctx, LoginContract.View viewListener) {
        this.ctx = ctx;
        this.viewListener = viewListener;
        repo = new LoginRepo(ctx, this);
    }

    public void login(User user) {
        repo.login(user);
    }

    @Override
    public void onLoginSuccess(Responses userLogin) {
        viewListener.onLoginSuccess(userLogin);
    }

    @Override
    public void onLoginFail(String error) {
        viewListener.onLoginFail(error);
    }

    @Override
    public void onNeedCompleteRegister() {
        viewListener.onNeedCompleteRegister();
    }

}
