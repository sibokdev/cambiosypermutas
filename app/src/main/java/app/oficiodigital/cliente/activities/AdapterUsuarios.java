package app.oficiodigital.cliente.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.fragments.MetodosPago;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.Datos;
import app.oficiodigital.cliente.models.DatosPago;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Token;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Request.DatosIntereses;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Tarjetas;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 03/05/2021.
 */

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuarioViewHolder> implements ListAdapter {

    FragmentActivity context;
    private final List<Busqueda> list;
    private final List<Busqueda> originalList;
    String tok;

    Dialog dialog;



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
        dialog = new Dialog(parent.getContext());

        return new UsuarioViewHolder(v);

    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        final Busqueda item = list.get(position);
//        holder.nombre.setText(item.getName()+"    "+item.getSurname1().toUpperCase());
        holder.rol.setText(item.getRol());
        holder.nivel.setText(item.getNivel_escolar());
        holder.estado.setText(item.getEstado());
        holder.tipo.setText(item.getTipo_plantel());
        holder.token.setText(item.getTokenPhone());
        holder.telefono.setText(item.getPhone());
        holder.des.setText(item.getDescription());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids="";

                List<Token> list1 = Token.listAll(Token.class);
                for (Token token : list1) {
                    String userId = "";

                    userId = token.getUserId();
                    ids = userId;
                }

                Intent intent = new Intent(holder.itemView.getContext(), Details.class);
                intent.putExtra("datos", item);
                holder.itemView.getContext().startActivity(intent);

                //String id = "198";

               /* Call<Responses> callVersiones = BovedaClient.getInstanceClient().getApiClient().getPago(ids);
                callVersiones.enqueue(new Callback<Responses>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {


                        /*if(response.body().getCode() == 200) {
                            Toast.makeText(v.getContext(),"si hay pago",Toast.LENGTH_SHORT).show();

                            if(response.body().getResponse().getDatosPago().getDate()){
                                // Intent intent = new Intent(holder.itemView.getContext(), principalMenu.class);
                                //intent.putExtra("datos",  item);
                                //holder.itemView.getContext().startActivity(intent);
                            }else{//
                               // openTwoDialog();
                                Toast.makeText(v.getContext(),"volver a pagar",Toast.LENGTH_SHORT).show();
                            }

                        }else if(response.body().getCode() == 202){
                            List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
                            for (TokenAuth token : list1) {
                                String phone = "";

                                phone = token.getTokenauth();
                                tok = phone;
                            }
                            Toast.makeText(v.getContext(),"no hay pago",Toast.LENGTH_SHORT).show();

                            String mToken = "Bearer " + tok;
                            Call<Responses> call1 = DOXClient.getInstanceClient().getApiClient().getCard(mToken);

                            call1.enqueue(new Callback<Responses>() {
                                @Override
                                public void onResponse(Call<Responses> call, Response<Responses> response) {

                                    if (response.body().getCode() == 200) {
                                        openOneDialog();
                                        //Toast.makeText(v.getContext(),"si hay tarjetas",Toast.LENGTH_SHORT).show();

                                    }else if (response.body().getCode() == 202){

                                        Toast.makeText(v.getContext(),"no hay tarjetas",Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Responses> call, Throwable t) {
                                    L.error("Get cards " + t.getMessage());
                                    //mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                                }
                            });

                               /* Call<Responses> call1 = DOXClient.getInstanceClient().getApiClient().getCards(authHeader);

                                call1.enqueue(new Callback<Responses>() {
                                    @Override
                                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                                        if (response.body().getCode() == 200) {/////////
                                            openOneDialog();
                                        }else if (response.body().getCode() == 202){

                                            Toast.makeText(v.getContext(),"no hay tarjetas",Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Responses> call, Throwable t) {
                                        L.error("Get cards " + t.getMessage());
                                        //mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                                    }
                                });

                        }*/

                   /* }
                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {
                        L.error("getDataSchool " + t.getMessage());
                    }
                });*/

                //Intent intent = new Intent(holder.itemView.getContext(),Details.class);
                //intent.putExtra("datos",  item);
                //holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    private void openTwoDialog() {
        dialog.setContentView(R.layout.twodialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose= dialog.findViewById(R.id.imageViewClose);
        Button btnOk= dialog.findViewById(R.id.btnOk);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Toast.makeText(btnOk.getContext(), "Dialog Close", Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Toast.makeText(btnOk.getContext(), "Button OK", Toast.LENGTH_SHORT).show();


            }
        });


        dialog.show();
    }

    private void openOneDialog() {
        dialog.setContentView(R.layout.onedialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose= dialog.findViewById(R.id.imageViewClose);
        Button btnOk= dialog.findViewById(R.id.btnOk);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Toast.makeText(btnOk.getContext(), "Dialog Close", Toast.LENGTH_SHORT).show();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //alerta();
               pagos();
                Toast.makeText(btnOk.getContext(), "se muestra pantalla donde se muestran los datos de los usuarios", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }

    public void pagos(){

        String deviceSessionId = "139e5a687c52A428b41e0f8cce2b5dba";
        String mToken = "Bearer " + tok ;

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                .addPayment(deviceSessionId, mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        // mAddPaymentPresenter.onAddPayment();
                        openOneDialog();
                       // Toast.makeText(dialog.getContext(), "success", Toast.LENGTH_SHORT).show();
                    } else
                        // mAddPaymentPresenter.onAddPaymentFail(response.body().getMessage());
                        Toast.makeText(dialog.getContext(), "ocurrio un error al realizar el pago", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(dialog.getContext(), "no hay tarjetas", Toast.LENGTH_SHORT).show();

                    // mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
                    //
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Add payment " + t.getMessage());
                //mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
            }
        });
    }


    /*public void alerta(){

        String msg = "realizando cobro";
        LoadingDialog.show(context.getApplication(), msg);

    }*/


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

    public void filtro(){

    }

    public void filtrar(final CharSequence buscar){
        if(buscar.length()==0){
            list.clear();
            list.addAll(originalList);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                list.clear();
                List<Busqueda> collect = originalList.stream()
                        .filter(i -> i.getEstado().contains(buscar))
                        .collect(Collectors.toList());

                list.addAll(collect);

            }
            else {
                list.clear();
                for(Busqueda i: originalList){
                    if (i.getEstado().toLowerCase().contains(buscar)){
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
        TextView estado = (TextView) convertView.findViewById(R.id.estado);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo);
        TextView token = (TextView) convertView.findViewById(R.id.token);
        TextView tele = (TextView) convertView.findViewById(R.id.telefono);

        nombre.setText(item.getName());
        nivel.setText(item.getNivel_escolar());
        rol.setText(item.getRol());
        estado.setText(item.getEstado());
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

        public TextView nombre,nivel,rol,token,telefono,des,estado,tipo;
        public UsuarioViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            nivel = (TextView) itemView.findViewById(R.id.nivel);
            rol = (TextView) itemView.findViewById(R.id.rol);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
            estado = (TextView) itemView.findViewById(R.id.estado);
            token = (TextView) itemView.findViewById(R.id.token);
            telefono = (TextView) itemView.findViewById(R.id.telefono);
            des = (TextView) itemView.findViewById(R.id.des);
        }
    }


}