package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.BusquedaCP;
import app.oficiodigital.cliente.models.Datos;

/**
 * Created by Ari on 03/05/2021.
 */

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuarioViewHolder> implements ListAdapter {

    FragmentActivity context;
    private final List<Busqueda> list;
    private final List<Busqueda> originalList;



    public AdapterUsuarios(List<Busqueda> list, FragmentActivity context) {
        this.context =  context;
        this.list =  list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
    }

    public AdapterUsuarios(List<Busqueda> list) {
        this.list = list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
    }


    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busqueda, parent, false);
        return new UsuarioViewHolder(v);

    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
       final Busqueda item = list.get(position);
        holder.nombre.setText(item.getName()+"    "+item.getSurname1()+"    "+item.getSurname2().toUpperCase());
        holder.rol.setText(item.getRol());
        holder.nivel.setText(item.getNivel_escolar());
        holder.tipo.setText(item.getTipo_plantel());
        holder.token.setText(item.getTokenPhone());
        holder.telefono.setText(item.getPhone());
        holder.des.setText(item.getDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(),Details.class);
                //intent.putExtra("datos",  item);
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void filtrar(final String buscar){
        if(buscar.length()==0){
            list.clear();
            list.addAll(originalList);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                list.clear();
                List<Busqueda> collect = originalList.stream()
                        .filter(i -> i.getName().toLowerCase().contains(buscar))
                        .collect(Collectors.toList());

                List<Busqueda> collect1 = originalList.stream()
                        .filter(i -> i.getOffice().toLowerCase().contains(buscar))
                        .collect(Collectors.toList());


                list.addAll(collect);
                list.addAll(collect1);

            }
            else {
                list.clear();
                for(Busqueda i: originalList){
                    if (i.getName().toLowerCase().contains(buscar)){
                        list.add(i);
                    }

                }
                for(Busqueda i: originalList){
                    if (i.getOffice().toLowerCase().contains(buscar)){
                        list.add(i);
                    }

                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Busqueda item = (Busqueda) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_busqueda, null);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView nivel = (TextView) convertView.findViewById(R.id.nivel);
        TextView rol = (TextView) convertView.findViewById(R.id.rol);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo);
        TextView token = (TextView) convertView.findViewById(R.id.token);
        TextView tele = (TextView) convertView.findViewById(R.id.telefono);

        nombre.setText(item.getName());
        nivel.setText(item.getNivel_escolar());
        rol.setText(item.getRol());
        tipo.setText(item.getTipo_plantel());
        token.setText(item.getTokenPhone());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre,nivel,rol,token,telefono,des,tipo;
        public UsuarioViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            nivel = (TextView) itemView.findViewById(R.id.nivel);
            rol = (TextView) itemView.findViewById(R.id.rol);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
            token = (TextView) itemView.findViewById(R.id.token);
            telefono = (TextView) itemView.findViewById(R.id.telefono);
            des = (TextView) itemView.findViewById(R.id.des);
        }
    }


}
