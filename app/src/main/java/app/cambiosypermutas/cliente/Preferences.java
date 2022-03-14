package app.cambiosypermutas.cliente;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    
    private SharedPreferences preferences;

    //constructor para recibir el contexto
    public Preferences(Context context){
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void keepRate(String version){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("rate", version);
        editor.apply();
    }
    public String getVersion(){
        return preferences.getString("rate","1");
    }
}
