package app.cambiosypermutas.cliente.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import app.cambiosypermutas.cliente.R;

public class MainActivity extends AppCompatActivity {
    private AlertDialog alertRateShow;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        showRate();
    }

    private void showRate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertRate = getLayoutInflater().inflate(R.layout.activity_alert_rate, null);
        RatingBar ratingBar = alertRate.findViewById(R.id.rbRate);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                try {
                    alertRateShow.dismiss();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store?hl=es/details?id=" + getPackageName())));
                }
            }
        });

        builder.setView(alertRate);
        alertRateShow = builder.create();
        alertRateShow.show();

    }
}

