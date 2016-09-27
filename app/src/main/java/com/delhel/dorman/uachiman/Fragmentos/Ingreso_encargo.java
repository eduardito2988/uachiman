package com.delhel.dorman.uachiman.Fragmentos;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_Entidades_con_encargos;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_departamentos;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener2;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_encargo extends Fragment implements RecyclerViewOnClickListener2 {


    public Ingreso_encargo() {
        // Required empty public constructor
    }
    View rootView;
    Spinner spinner1;
    Button btngrabar;
    EditText txtinforme,numerodep;
    AutoCompleteTextView departamento;
    List<Modelo_departamentos> list_departamento;
    Dencargos myDaoSQLite ;
    int valor_spiner,PUERTA,ENTIDAD,COD_USUARIO;
    List<Modelo_tipo_encargo> listado;
    private List<Modelo_departamento_con_encargo> LisModelos;
    Salida_encargo encargo;
    String cod_uni;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.ingreso_encargo, container, false);

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ENTIDAD= sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD, 0);
        PUERTA= sharedPreferencesE.getInt(QuickstartPreferences.COD_PUERTA, 0);



       /* PUERTA=31;
        ENTIDAD = 2;
        COD_USUARIO=91;*/

        myDaoSQLite = new Dencargos(getActivity());
        departamento = (AutoCompleteTextView) rootView.findViewById(R.id.buscador_depa);
        numerodep = (EditText)rootView.findViewById(R.id.txtnombres);
        txtinforme =(EditText) rootView.findViewById(R.id.txt_informe);
        spinner1 = (Spinner) rootView.findViewById(R.id.spiner_encargo);

        encargo= new Salida_encargo();
        listado = myDaoSQLite.llenar_combo_tipo_encargo();
        ArrayList<String> strings = new ArrayList<>();
        for(int i=0;i<listado.size();i++){
            strings.add(listado.get(i).getDetalle());
        }

        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,strings);
        spinner1.setAdapter(adaptador1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                valor_spiner = listado.get(i).getCodigo();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       /* autocompletador */
        list_departamento= new ArrayList<>();
        departamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list_departamento = myDaoSQLite.llenarCombo_departamentos(charSequence.toString());
                CustomerAdapter_departamentos customerAdapter = new CustomerAdapter_departamentos(getActivity(), R.layout.customer_auto, list_departamento);
                departamento.setThreshold(0);
                departamento.setAdapter(customerAdapter);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        departamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             //   adapterView.getItemAtPosition(i);
                Modelo_departamentos productos = list_departamento.get(0);
                String nombre = productos.getUs_pel_ch100()+" "+productos.getUsu_nomb_ch100();
                departamento.setText(nombre);
                cod_uni = productos.getUni_codi_in20();
                numerodep.setText(productos.getUni_codi_in20());

              //  list_departamento.get(i).getUsu_nomb_ch100();

            }
        });

      /* boton grabar */
        btngrabar = (Button) rootView.findViewById(R.id.btn_registrar_encargo);
        btngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable informe = txtinforme.getText();
                String numero_depa =  numerodep.getText().toString();
                int NUMERO=Integer.parseInt(numero_depa);

                //hora y fecha panel principal   2014-11-29 10:27:25  / 2016-17-31 02:17:11
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String FECHA = dateFormat.format(new Date() );


                JSONObject object = new JSONObject();
                try {

                    object.put("cod_entida", ENTIDAD); // codigo entidad
                    object.put("deta_dep", informe); // detalle de encargo
                    object.put("cod_usu", COD_USUARIO); // codigo de usuario
                    object.put("fecha_registro", FECHA); // fecha registro
                    object.put("tipo_encar", valor_spiner);//tipo encargo
                    object.put("num_dep", NUMERO);//numero departa,mento UNIDAD
                    object.put("pers_regitra", COD_USUARIO);//personal que registra
                    object.put("pers_acargo",COD_USUARIO); // personal encargado
                    object.put("num_puerta", PUERTA); // numero de puerta

                } catch (JSONException e) {
                    e.printStackTrace();
                }


/*
                //** insertar sqlite/**
                Modelo_encargo encargo = new Modelo_encargo();

                encargo.setEnt_codi_in20(ENTIDAD);
                encargo.setEnc_deta_vc200(informe.toString());
                encargo.setUsu_codi_in20(COD_USUARIO);
                encargo.setEnc_ingr_dati(FECHA);
                encargo.setTen_codi_in20(valor_spiner);
                encargo.setUni_codi_in20(NUMERO);
                encargo.setPer_ingr_in20(COD_USUARIO);
                encargo.setEnc_upos_in20(COD_USUARIO);
                encargo.setPue_codi_in20(PUERTA);

               myDaoSQLite.Insertar_Encargos(encargo);*/


                /** POST ENVIO A NUVE*/
                AsyncHttpClient client = new AsyncHttpClient();
                StringEntity params = null;
                try {
                    params = new StringEntity("message=" + object.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                client.addHeader("content-type", "application/x-www-form-urlencoded");
                client.post(getActivity(), Constantes.URL_INGRESO_PEDIDO, params, "application/json", new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String  msj;
                        if (statusCode == 200) {

                            JSONObject o = null;
                            try {
                                o = new JSONObject(new String(responseBody));
                                msj = o.getString("mensaje");
                                Log.e("respuesta",""+msj.toString());

                                txtinforme.setText("");
                                numerodep.setText("");
                                departamento.setText("");

                                AdaptadorRecycle_Entidades_con_encargos adaptadorRecycle  = new AdaptadorRecycle_Entidades_con_encargos(LisModelos,getActivity());
                                adaptadorRecycle.clear2();

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
        });

        return  rootView;
    }


    @Override
    public void onClickListener() {
        Toast.makeText(getActivity(), "20000000000", Toast.LENGTH_SHORT).show();
    }
}