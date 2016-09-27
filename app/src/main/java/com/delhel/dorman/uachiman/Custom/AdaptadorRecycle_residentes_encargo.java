package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Acordeon.HeaderEncargo;
import com.delhel.dorman.uachiman.Clases.Modelo_residentes_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Fragmentos.Fragment_principal;
import com.delhel.dorman.uachiman.Fragmentos.Ingreso_encargo1;
import com.delhel.dorman.uachiman.Fragmentos.Salida_Invitado;
import com.delhel.dorman.uachiman.Fragmentos.Salida_encargo;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener2;
import com.delhel.dorman.uachiman.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Usuario on 16/08/2016.
 */
public class AdaptadorRecycle_residentes_encargo extends RecyclerView.Adapter<AdaptadorRecycle_residentes_encargo.Holder> {

    List<Modelo_residentes_encargo> listado;
    Context context;
    private FragmentManager fragmentManager;
    Spinner spinner1;
    EditText detalles;
    int tipo_encargo;
    String detalle_enc;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ViewGroup viewGroup;


    private RecyclerViewOnClickListener2 recyclerViewOnClickListener2;


    public AdaptadorRecycle_residentes_encargo(List<Modelo_residentes_encargo> listado, Context context, FragmentManager fragmentManager, ViewGroup viewGroup) {
        this.listado = listado;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.viewGroup = viewGroup;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lista_residentes, parent, false);
        Holder hol = new Holder(view);
        return hol;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.nombre.setText(listado.get(position).getApellido() + " " + listado.get(position).getNombre());
        holder.dni.setText(listado.get(position).getDni());
        String imagen = Constantes.RUTA_IMAGEN + "" + listado.get(position).getFoto();
        Picasso.with(context).load(imagen).error(R.drawable.sin_foto).into(holder.foto);

    }


    public void RecyclerViewOnClickListener2(RecyclerViewOnClickListener2 r){
        recyclerViewOnClickListener2 = (RecyclerViewOnClickListener2) r;
    }


    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView codigo, nombre, dni;
        final ImageView foto;
        public Button boton;

        public Holder(View itemView) {

            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.nom_residente);
            dni = (TextView) itemView.findViewById(R.id.dni);
            foto = (ImageView) itemView.findViewById(R.id.foto_residente);
            boton = (Button) itemView.findViewById(R.id.btn_asistensia);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            final String entidad = listado.get(position).getEntida();
            final String unidad = listado.get(position).getNr_depa();
            final String puerta = listado.get(position).getPuerta();
            final String propietario = listado.get(position).getCodigo();
            final String usuario = listado.get(position).getUser_reg();


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.encargo_dialog, null);

            spinner1 = (Spinner) layout.findViewById(R.id.spinner_tipo);
            detalles = (EditText) layout.findViewById(R.id.detalle_encargos);
            Button grabar = (Button) layout.findViewById(R.id.btn_registro);
            Button cancelar = (Button) layout.findViewById(R.id.cancelar_dialog);

            builder = new AlertDialog.Builder(context);
            builder.setTitle("Registrar Encargo");
            builder.setIcon(R.drawable.logo);
            builder.setView(layout);
            alertDialog = builder.create();
            alertDialog.show();


            /***************** tipo de encargo*********************/
            Dencargos myDaoSQLite = new Dencargos(layout.getContext());
            final List<Modelo_tipo_encargo> lis_encargo = myDaoSQLite.llenar_combo_tipo_encargo();
            CustomSpinnerAdapteTencargo customSpinnerAdapteTencargo = new CustomSpinnerAdapteTencargo(context, lis_encargo);
            spinner1.setAdapter(customSpinnerAdapteTencargo);


            /************************** boton grabar*************************************/

            grabar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = spinner1.getSelectedItemPosition();
                    int tipo_encargo = lis_encargo.get(pos).getCodigo();
                    detalle_enc = detalles.getText().toString();

                    JSONObject jsonObject = new JSONObject();

                    try {


                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String FECHA = dateFormat.format(new Date());

                        jsonObject.put("cod_entida", entidad); // codigo entidad
                        jsonObject.put("deta_dep", detalle_enc); // detalle de encargo
                        jsonObject.put("cod_usu", propietario); // codigo de usuario
                        jsonObject.put("fecha_registro", FECHA); // fecha registro
                        jsonObject.put("tipo_encar", tipo_encargo);//tipo encargo
                        jsonObject.put("num_dep", unidad);//numero departa,mento UNIDAD
                        jsonObject.put("pers_regitra", usuario);//personal que registra
                        jsonObject.put("pers_acargo", usuario); // personal encargado
                        jsonObject.put("num_puerta", puerta); // numero de puerta

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Grabar_encargo(jsonObject, fragmentManager);
                }
            });

            /**************CANCELAR******************/
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }

        public void Grabar_encargo(JSONObject object, final FragmentManager manager) {

            /** POST ENVIO A NUVE*/
            AsyncHttpClient client = new AsyncHttpClient();
            StringEntity params = null;
            try {
                params = new StringEntity("message=" + object.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            client.addHeader("content-type", "application/x-www-form-urlencoded");
            client.post(context, Constantes.URL_INGRESO_PEDIDO, params, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String msj;
                    if (statusCode == 200) {

                        JSONObject o = null;
                        try {
                            o = new JSONObject(new String(responseBody));
                            msj = o.getString("mensaje");
                            Log.e("respuesta", "" + msj.toString());
                            alertDialog.dismiss();
                            Toast.makeText(context, "" + msj.toString(), Toast.LENGTH_SHORT).show();


                           /* Fragment[] fragment = { new Salida_encargo()};
                            Bundle args = new Bundle();
                            args.putString("email", "quinto");
                            Fragment currentFragment = fragment[0];
                            currentFragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = manager.beginTransaction();
                            fragmentTransaction.detach(currentFragment);
                            fragmentTransaction.attach(currentFragment);
                            fragmentTransaction.commit();*/

                            recyclerViewOnClickListener2.onClickListener();

                            /*Salida_encargo salida_encargo = new Salida_encargo();
                            salida_encargo.re();*/
                            //Fragment[] fragment = {new Ingreso_encargo1(), new Salida_encargo()};
                            //FragmentTransaction ft = manager.beginTransaction();
                            // Fragment newFragment = fragment[1];
                            //ft.replace(viewGroup.getId(),newFragment);
                            //ft.addToBackStack(null);
                            //ft.commit();
                            //Salida_encargo salida_encargo = new Salida_encargo();
                            //salida_encargo.refrescar();
                            // fragmentManager = getSupportFragmentManager();

                            /*Class Framentclass = null;
                            Fragment fragmentss = null;


                            Framentclass = Salida_Invitado.class;
                            fragmentss = (Fragment) Framentclass.newInstance();*/


                            //manager.addToBackStack(null);
                            //manager.beginTransaction().replace(viewGroup.getId(), fragmentss).commit();

                           // FragmentTransaction ft = manager.beginTransaction();
                            //Fragment newFragment = fragment[1];

                            //ft.replace(viewGroup.getId(), newFragment);
                            //ft.addToBackStack(null);

                            //ft.detach(get);
                            //ft.attach(newFragment);

                            //ft.commit();

                            // manager.beginTransaction().replace(viewGroup.getId(), newFragment).commit();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        }
    }
}
