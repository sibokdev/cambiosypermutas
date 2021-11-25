package app.oficiodigital.cliente.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.fragments.MetodosPago;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.models.Datos;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Tarjetas;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTarjetas extends RecyclerView.Adapter<AdapterTarjetas.UsuarioViewHolder> implements ListAdapter {


    FragmentActivity context;
    private final Responses list;
    private String tok;
    private Tarjetas api;
    String  phon;
    String id_datos;

    public AdapterTarjetas(Responses list) {
        this.list = list;

    }


    @Override
    public AdapterTarjetas.UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagos, parent, false);
        return new AdapterTarjetas.UsuarioViewHolder(v);

    }

    public void alerta1(){

    }

    @Override
    public void onBindViewHolder(AdapterTarjetas.UsuarioViewHolder holder, int position) {
        final CreditCard item = list.getResponse().getCards().get(position);
        holder.num.setText(item.getNumber());
        holder.api.setText(item.getId());

        tarjetaPrincipal();

        if (item.getMain() == 1){

            holder.principal.setBackgroundResource(R.drawable.ic_baseline_check_circle_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                builder.setTitle("Cambiar Tarjeta principal");
                builder.setMessage("¿Desea cambia tarjeta principal?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
                        for (TokenAuth pho : list1) {
                            String phone = "";

                            phone = pho.getTokenauth();
                            tok = phone;
                        }
                        String mToken = "Bearer " + tok;

                        String id = id_datos;

                        api = new Tarjetas();
                         api.setId_api_card(holder.api.getText().toString());

                        Call<Responses> call = DOXClient.getInstanceClient().getApiClient().getCardprincipal(mToken,id,api);

                        call.enqueue(new Callback<Responses>() {
                            @Override
                            public void onResponse(Call<Responses> call, Response<Responses> response) {

                                if (response.body() != null) {
                                    if (response.code() == 200) {
                                        Toast.makeText(v.getContext(), "Tarjeta principal cambiada", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(holder.itemView.getContext(),PrincipalMetodos.class);
                                        // intent.putExtra("datos",  item);
                                        holder.itemView.getContext().startActivity(intent);

                                    } else
                                        Toast.makeText(v.getContext(), "Error cambiar tarjeta principal", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<Responses> call, Throwable t) {
                                L.error("Get cards " + t.getMessage());
                                //mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.MyDialogTheme);
                builder.setTitle("Elimnar Tarjeta");
                builder.setMessage("¿Desea elimnar esta tarjeta?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
                        for (TokenAuth pho : list1) {
                            String phone = "";

                            phone = pho.getTokenauth();
                            tok = phone;
                        }

                        String card = item.getId();
                        String token2 = "Bearer " + tok;

                        Call<Responses> call = DOXClient.getInstanceClient().getApiClient().dropCard(card, token2);
                        call.enqueue(new Callback<Responses>() {
                            @Override
                            public void onResponse(Call<Responses> call, Response<Responses> response) {
                                if (response.isSuccessful()) {
                                    if (response.code() == 200) {
                                        ProgressDialog progress = new ProgressDialog(holder.itemView.getContext());
                                        progress.setTitle("Eliminando tarjeta");
                                        progress.setMessage("Espere...");
                                        progress.show();
                                        Toast.makeText(v.getContext(), "Tarjeta Eliminada", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(holder.itemView.getContext(),PrincipalMetodos.class);
                                       //intent.putExtra("token",  tok);
                                        holder.itemView.getContext().startActivity(intent);


                                    }else{
                                        Toast.makeText(v.getContext(), "No se elimino la tarjeta", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Responses> call, Throwable t) {
                                // mDeleteCreditCardPresenter.onDeleteCardError(t.getMessage());
                            }
                        });

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                    }
                });
              builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
/*
    @Override
    public int getItemCount() {
       *//* return list.size();*//*
    }*/

    public void alerta(){}


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
/*
    @Override
    public int getCount() {
        return list.size();
    }*/


  /*  @Override
    public Object getItem(int position) {
        return list.get(position);
    }*/

    public void getPhone() {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tarjetas item = (Tarjetas) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_pagos, null);
        TextView nombre = (TextView) convertView.findViewById(R.id.num);


        nombre.setText(item.getNumber());
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

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
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

        public TextView num,api,main,principal,token, borrar;
        public UsuarioViewHolder(View itemView) {
            super(itemView);
            num = (TextView) itemView.findViewById(R.id.num);
            api = (TextView) itemView.findViewById(R.id.api);
            main = (TextView) itemView.findViewById(R.id.main);
            principal = (TextView) itemView.findViewById(R.id.principal);
            token = (TextView) itemView.findViewById(R.id.token);
            borrar = (TextView) itemView.findViewById(R.id.borrar);


        }
    }

    public void tarjetaPrincipal(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }
   //String phone = "2481674511";
        Call<List<Datos>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDatos(phon);
        callVersiones.enqueue(new Callback<List<Datos>>() {
            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                List<Datos> respuestas = response.body();


                for (Datos res: respuestas) {
                    id_datos = ""+ res.getId();

                }
            }
            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {
                //  L.error("getOficios " + t.getMessage());
            }
        });
    }


}
