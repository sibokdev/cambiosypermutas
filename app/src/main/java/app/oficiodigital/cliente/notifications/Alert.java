package app.oficiodigital.cliente.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by NandoVelazquez on 5/11/16.
 */
public class Alert {

    private Context ctx;
    private AlertDialog.Builder builder;
    private AlertDialog dialog = null;

    public Alert(Context ctx) {
        builder = new AlertDialog.Builder(ctx);
    }

    public void setPositiveListener(String label, DialogInterface.OnClickListener positiveListener) {
        builder.setPositiveButton(label, positiveListener);
    }

    public void setNegativeListener(String label, DialogInterface.OnClickListener negativeListener) {
        builder.setNegativeButton(label, negativeListener);
    }

    public void fire(String message) {
        builder.setMessage(message);
        //if (!dialog.isShowing())
        dialog = builder.create();
        dialog.show();
    }

    public void close() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

}
