package app.oficiodigital.cliente.notifications;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by NandoVelazquez on 4/14/16.
 */
public class LoadingDialog {

    private static ProgressDialog dialog;

    public static void show(Context ctx, String msg) {

        if (dialog == null)
            dialog = new ProgressDialog(ctx);

        if (!dialog.isShowing()) {
            dialog = ProgressDialog.show(ctx, "", msg, true);
            dialog.setCancelable(false);
        }
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
