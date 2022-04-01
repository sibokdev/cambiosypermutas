package app.cambiosypermutas.cliente.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.clients.CatalogsClient;
import app.cambiosypermutas.cliente.clients.DOXClient;
import app.cambiosypermutas.cliente.fragments.BusquedaFragment;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.ModelsDB.Estado;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.ModelsDB.Token;
import app.cambiosypermutas.cliente.models.ModelsDB.TokenPhone;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 03/05/2021.
 */

public class AdapterTodos extends RecyclerView.Adapter<AdapterTodos.UsuarioViewHolder> implements ListAdapter {

    FragmentActivity context;
    private final List<Busqueda> list;
    private final List<Busqueda> originalList;
    String tok;
    BusquedaFragment busquedaFragment;
    String estado, estado2, estado3;
    String estados1 ="", estados2 = "", estados3 = "";
    Dialog dialog;
    String estad,tokenPhone;



    public AdapterTodos(List<Busqueda> list, FragmentActivity context) {
        this.context =  context;
        this.list =  list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
    }

    public AdapterTodos(List<Busqueda> list) {
        this.list = list;
        this.originalList = new ArrayList<>();
        originalList.addAll(list);
    }


    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todos, parent, false);
        dialog = new Dialog(parent.getContext());

        return new UsuarioViewHolder(v);

    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        final Busqueda item = list.get(position);
        holder.nombre.setText(item.getName());
        holder.estado.setText(item.getMunicipio()+", "+item.getEstado());

        holder.detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Details.class);
                intent.putExtra("datos", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        Call<List<app.cambiosypermutas.cliente.models.Estados>> call = BovedaClient.getInstanceClient().getApiClient().getEstados(item.getPhone());
        call.enqueue(new Callback<List<app.cambiosypermutas.cliente.models.Estados>>() {
            @Override
            public void onResponse(Call<List<app.cambiosypermutas.cliente.models.Estados>> call, Response<List<app.cambiosypermutas.cliente.models.Estados>> response) {
                List<app.cambiosypermutas.cliente.models.Estados> ejemplo = response.body();
                List<String> list = new ArrayList<String>();

                 String estados="";
                for (app.cambiosypermutas.cliente.models.Estados estado : ejemplo) {
                    list.add(estado.getEstado());

                    for (int i = 0; i < list.size(); i++) {
                        if (i == 2) {

                            if (list.get(0).contains(list.get(1)) && list.get(0).contains(list.get(2))) {
                                estados1 = list.get(0);
                                estados = estados1;
                            } else if (list.get(1).contains(list.get(2))) {
                                //interes.setText(list.get(0) + " " + list.get(1));
                                estados1 = list.get(0);
                                estados3 = list.get(2);
                                estados = estados1 + ", " + estados3;
                            } else if (list.get(0).contains(list.get(1))) {
                                estados2 = list.get(1);
                                estados3 = list.get(2);
                                estados = estados2 + ", " + estados3;
                            } else if (list.get(0).contains(list.get(2))) {
                                estados1 = list.get(0);
                                estados2 = list.get(1);
                                estados = estados1 + ", " + estados2;
                            } else {

                                estados1 = list.get(0);
                                estados2 = list.get(1);
                                estados3 = list.get(2);

                                estados = estados1 + ", " + estados2 + ", " + estados3;
                            }

                        }
                    }

                }
                holder.estadoBusca.setText(estados);

            }

            @Override
            public void onFailure(Call<List<app.cambiosypermutas.cliente.models.Estados>> call, Throwable t) {

            }
        });

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
              // pagos();
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

        CharSequence busca;

        String datos = (String) buscar;
        if(datos.equals("Mexico") || datos.equals("mexico")){
             busca = "México";
        }else if(datos.equals("Cuidad de Mexico") || datos.equals("ciudad de mexico")) {
            busca = "Ciudad de México";
        }else if(datos.equals("Michoacan") || datos.equals("michoacan")){
            busca = "Michoacán de Ocampo";
        }else if(datos.equals("Nuevo Leon") || datos.equals("nuevo leon")){
            busca = "Nuevo León";
        }else if(datos.equals("Queretaro") || datos.equals("queretaro")){
            busca = "Querétaro";
        }else if(datos.equals("Yucatan") || datos.equals("yucatan")){
            busca = "Yucatán";
        }else if(datos.equals("Veracruz") || datos.equals("veracruz")){
            busca = "Veracruz de Ignacio de la Llave";
        }else if(datos.equals("San Luis Potosi") || datos.equals("san luis potosi")) {
                busca = "San Luis Potosí";
        }else{
                busca = datos;
        }


        if(buscar.length()== 0){
            list.clear();
            list.addAll(originalList);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                list.clear();
                List<Busqueda> collect = originalList.stream()
                        .filter(i -> i.getEstado().toLowerCase().contains(busca) || i.getEstado().contains(busca))
                        .collect(Collectors.toList());

                list.addAll(collect);
                if(list.size() == 0){
                    Toast.makeText(dialog.getContext(), "No hay registros", Toast.LENGTH_SHORT).show();
                }else{

                }

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

        convertView = LayoutInflater.from(context).inflate(R.layout.item_todos, null);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView estado = (TextView) convertView.findViewById(R.id.estado);

        nombre.setText(item.getName());
        estado.setText(item.getEstado());

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

        public TextView nombre,estado,detalles,estadoBusca,telefono;
        public Button tria;
        public UsuarioViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tv_prueba);
            estado = (TextView) itemView.findViewById(R.id.estado);
            estadoBusca = (TextView) itemView.findViewById(R.id.estadoBusca);
            detalles = (Button) itemView.findViewById(R.id.detalles);
            telefono = (TextView) itemView.findViewById(R.id.telefono);

        }
    }


}