package app.oficiodigital.cliente.models.ModelsDB;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.oficiodigital.cliente.R;

/**
 * Created by Ari on 14/04/2021.
 */

public class PreguntasAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Preguntas1> preguntasArrayList;

    public PreguntasAdapter(Context context, ArrayList<Preguntas1> preguntasArrayList) {
        this.context = context;
        this.preguntasArrayList = preguntasArrayList;
    }

    @Override
    public int getCount() {
        return preguntasArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return preguntasArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //convertView = View.inflate(context, R.layout.activity_cliente_register, null);
        }
        TextView p1 = (TextView) convertView.findViewById(R.id.pregunta1);

        Preguntas1 preguntas = preguntasArrayList.get(position);

        p1.setText(""+preguntas.getPreguntas());

        return convertView;
    }


}
