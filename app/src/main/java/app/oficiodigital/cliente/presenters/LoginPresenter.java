package app.oficiodigital.cliente.presenters;

import android.content.Context;

import app.oficiodigital.cliente.contracts.LoginContract;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.User;
import app.oficiodigital.cliente.repos.LoginRepo;

public class LoginPresenter implements LoginContract.Presenter {

    private Context ctx;
    private LoginContract.View viewListener;
    private LoginRepo repo;

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
