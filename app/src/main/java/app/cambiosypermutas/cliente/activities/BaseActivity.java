package app.cambiosypermutas.cliente.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.notifications.Alert;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;
import app.cambiosypermutas.cliente.utils.EmailValidator;
import app.cambiosypermutas.cliente.utils.PasswordValidator;
import app.cambiosypermutas.cliente.utils.PhoneValidator;

/**
 * Created by JJ on 15/07/2016.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected boolean validateEmail(String email) {
        return EmailValidator.validate(email);
    }

    protected boolean phoneValid(String phone){
        return PhoneValidator.validate(phone);
    }
    protected boolean passvalidator(String pass){
        return PasswordValidator.validate(pass);
    }

    protected boolean checkFields(String[] fields) {
        boolean fieldsOK = true;

        for (String field: fields)
            if (field == null || field.isEmpty())
                fieldsOK = false;

        showMsgIfFalse(fieldsOK, getString(R.string.fields_empty));

        return fieldsOK;
    }

    private void showMsgIfFalse(boolean option, String msg) {
        if (!option)
            showFailMsg(msg);
    }

    protected void showFailMsg(String msg) {
        String okLabel = getString(R.string.ok);

        Alert dialog = new Alert(this);
        dialog.setPositiveListener(okLabel, null);
        dialog.fire(msg);
    }

    protected void showLoadingMsg(boolean show) {
        if (show) {
            String msg = getString(R.string.loading_msg);
            LoadingDialog.show(this, msg);
        } else {
            LoadingDialog.dismiss();
        }
    }

    protected void closeKeyBoard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
