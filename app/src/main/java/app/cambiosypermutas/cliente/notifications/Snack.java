package app.cambiosypermutas.cliente.notifications;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by NandoVelazquez on 6/1/16.
 */
public class Snack {

    public static void show(View view, String message) {
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null);

        View snackView = snack.getView();
        //TextView tv = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        Toast.makeText(snackView.getContext(), "hola ari", Toast.LENGTH_SHORT).show();
        //tv.setTextColor(Color.WHITE);

        if (!snack.isShown())
            snack.show();
    }
}
