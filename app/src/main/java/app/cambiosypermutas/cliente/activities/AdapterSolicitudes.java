package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.models.Notificacion;
import app.cambiosypermutas.cliente.models.Solicitudes;

public class AdapterSolicitudes extends RecyclerView.Adapter<AdapterSolicitudes.UsuarioViewHolder> implements ListAdapter {

        FragmentActivity context;
private final List<Solicitudes> list;
private final List<Solicitudes> originalList;


public AdapterSolicitudes(List<Solicitudes> list, FragmentActivity context) {
        this.context =  context;
        this.list =  list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
        }

public AdapterSolicitudes(List<Solicitudes> list) {
        this.list = list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
        }


@Override
public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitudes, parent, false);
        return new UsuarioViewHolder(v);
        }

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Override
public void onBindViewHolder(UsuarioViewHolder holder, int position) {
final Solicitudes item = list.get(position);


        holder.nombre.setText(item.getName());
        holder.oficio.setText(item.getSurname());
        holder.token1.setText(item.getTokenPhone());
        holder.hora.setText(item.getHora());
        holder.fecha.setText(item.getFecha());
        holder.phone2.setText(item.getPhoneCliente());
        holder.status.setText(item.getStatus());
        holder.oficios.setText(item.getOficio());
        holder.fechas.setText(item.getCreated_at());
        holder.id.setText(item.getId());


        if (item.getStatus().equals("Aceptada")){
            holder.status.setText(item.getStatus());
            holder.status.setBackgroundResource(R.color.solicitudAceptada);

        }
        if (item.getStatus().equals("Declinada")){
            holder.status.setText(item.getStatus());
            holder.status.setBackgroundResource(R.color.solicitudDeclinada);
        }

        if (item.getStatus().equals("Pendiente")){
           holder.status.setText(item.getStatus());
           holder.status.setBackgroundResource(R.color.solicitudPendiente);
        }


    holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

    if (item.getStatus().equals("Aceptada")){
        Intent intent = new Intent(holder.itemView.getContext(), ResulSolicitudes.class);
        intent.putExtra("datos", item);
        holder.itemView.getContext().startActivity(intent);
    }else if(item.getStatus().equals("Declinada")) {
        Toast.makeText(holder.itemView.getContext(),"Declinada", Toast.LENGTH_SHORT).show();
    }else if (item.getStatus().equals("Pendiente")){
        Toast.makeText(holder.itemView.getContext(),"Pendiente", Toast.LENGTH_SHORT).show();
    }
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

    /*public void filtrar(final String buscar){
        if(buscar.length()==0){
            list.clear();
            list.addAll(originalList);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                list.clear();
                List<Notificacion> collect = originalList.stream()
                        .filter(i -> i.getName().toLowerCase().contains(buscar))
                        .collect(Collectors.toList());

                List<Notificacion> collect1 = originalList.stream()
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
    }*/

@Override
public Object getItem(int position) {
        return list.get(position);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        Notificacion item = (Notificacion) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_busqueda, null);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView oficio = (TextView) convertView.findViewById(R.id.oficio);
        TextView token = (TextView) convertView.findViewById(R.id.token);
        TextView hora = (TextView) convertView.findViewById(R.id.hora);
        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
        TextView phone2 = (TextView) convertView.findViewById(R.id.phone2);

        nombre.setText(item.getName());
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

public void eviardatos() {
        }

public class UsuarioViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre,oficio,token1, hora, fecha, phone2,status,oficios, fechas,id;
    public UsuarioViewHolder(View itemView) {
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.nombre);
        oficio = (TextView) itemView.findViewById(R.id.oficio);
        token1 = (TextView) itemView.findViewById(R.id.token);
        hora = (TextView) itemView.findViewById(R.id.hora);
        fecha = (TextView) itemView.findViewById(R.id.fecha);
        phone2 =(TextView) itemView.findViewById(R.id.phone2);
        id = (TextView) itemView.findViewById(R.id.ids);


    }
}


}
