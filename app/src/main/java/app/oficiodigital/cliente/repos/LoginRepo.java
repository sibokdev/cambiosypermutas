package app.oficiodigital.cliente.repos;

import android.content.Context;

import java.util.HashMap;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.User;
import app.oficiodigital.cliente.presenters.LoginPresenter;
import app.oficiodigital.cliente.storage.daohandlers.UserDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private LoginPresenter presenter;
    private UserDao dao;
    private Context mContext;

    public LoginRepo(Context context, LoginPresenter presenter) {
        this.presenter = presenter;
        dao = new UserDao(context);
        mContext = context;
    }

    public void login(User user) {

        HashMap<String, String> params = new HashMap<>();
        params.put("username", user.getEmail());
        params.put("password", user.getPassword());
        params.put("client_id", "g3b259fde3ed9ff3843839b");
        params.put("client_secret", "3d7f5f8f793d59c25502c0ae8c4a95b");
        params.put("grant_type", "password");

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient().login(params);
        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                Responses userLogin = response.body();

                if (userLogin != null){
                    if (response.code() == 200){
                        if (response.body().getResponse() != null)
                            presenter.onLoginSuccess(response.body());
                        else
                            presenter.onLoginFail(userLogin.getMessage());

                    } else{
                        presenter.onLoginFail(userLogin.getMessage());
                    }
                }else {
                    presenter.onLoginFail(mContext.getString(R.string.login_error));
                }

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                presenter.onLoginFail(t.getMessage());
            }
        });

        /*dao.add(user);
        presenter.onLoginSuccess();*/
    }

}
