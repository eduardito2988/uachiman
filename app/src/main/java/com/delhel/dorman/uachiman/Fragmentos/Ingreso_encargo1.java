package com.delhel.dorman.uachiman.Fragmentos;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Clases.Modelo_residentes;
import com.delhel.dorman.uachiman.Clases.Modelo_residentes_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_Entidades_con_encargos;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_residentes;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_residentes_encargo;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_departamentos;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Interface.Comunicacion;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener2;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener3;
import com.delhel.dorman.uachiman.MainActivity;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.delhel.dorman.uachiman.Tablas.Tabla_residentes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_encargo1 extends Fragment implements RecyclerViewOnClickListener2 {

    private static final int REQUEST_CODE = 1;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View rootView;
    Spinner spinner1;
    EditText txtinforme, numerodep;
    AutoCompleteTextView departamento;
    List<Modelo_departamentos> list_departamento;
    Dencargos myDaoSQLite;
    int PUERTA, ENTIDAD, COD_USUARIO;
    String cod_uni;

    private List<Modelo_residentes_encargo> list;
    AdaptadorRecycle_residentes_encargo adaptadorRecycle_residentes_encargo;
    RecyclerView recyclerView;
    ProgressDialog pDialog;
    ViewGroup viewGroup;
    Fragment activity;

    private Comunicacion mListener;


    public Ingreso_encargo1() {
        // Required empty public constructor
    }

    public static Ingreso_encargo1 newInstance(String param1, String param2) {
        Ingreso_encargo1 fragment = new Ingreso_encargo1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.ingreso_encargo1, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ENTIDAD = sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD, 0);
        PUERTA = sharedPreferencesE.getInt(QuickstartPreferences.COD_PUERTA, 0);
        COD_USUARIO = sharedPreferencesE.getInt(QuickstartPreferences.COD_USUARIO, 0);

        myDaoSQLite = new Dencargos(getActivity());
        departamento = (AutoCompleteTextView) rootView.findViewById(R.id.buscador_depa);
        numerodep = (EditText) rootView.findViewById(R.id.txtnombres);
        txtinforme = (EditText) rootView.findViewById(R.id.txt_informe);
        spinner1 = (Spinner) rootView.findViewById(R.id.spiner_encargo);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_residentes_encargo);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        activity = this;

       /* autocompletador */
        list_departamento = new ArrayList<>();
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
                Modelo_departamentos unidades = list_departamento.get(0);
                String nombre = unidades.getUs_pel_ch100() + " " + unidades.getUsu_nomb_ch100();
                departamento.setText(nombre);
                cod_uni = unidades.getUni_codi_in20();
                numerodep.setText(unidades.getUni_numi_vc20());
                String COD_USER = unidades.getUsu_codi_in20();
                String COD_UNID = unidades.getUni_codi_in20();
                ConsultarResidente(COD_USER, COD_UNID, null, viewGroup, activity);

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Comunicacion) context;
        Toast.makeText(getActivity(), "esto dentro de", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public void ConsultarResidente(String id_usu, final String cod_enti, String dni, final ViewGroup viewGroup, final Fragment activity) {


        //Salida_encargo salida_encargo = ((Salida_encargo)getFragmentManager()).getFragmentbyPosition(0);

        //  ((Salida_encargo)getChildFragmentManager()).doPositiveClick();


        /** POST ENVIO A NUVE*/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("cod_user", id_usu);
        requestParams.add("cod_unida", cod_enti);
        requestParams.add("cod_entida", String.valueOf(ENTIDAD));
        requestParams.add("cod_dni", dni);


        RequestHandle post = client.post(Constantes.URL_CONSULTA_RESIDENTES, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Buscando, espere...");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                pDialog.setProgress(0);
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    JSONObject o = null;
                    try {
                        list = new ArrayList();
                        o = new JSONObject(new String(responseBody));
                        JSONArray en = o.optJSONArray(Constantes.DATOS_RESIDENTES_UNIDAD);

                        pDialog.dismiss();

                        for (int i = 0; i < en.length(); i++) {
                            Modelo_residentes_encargo residentes = new Modelo_residentes_encargo();
                            JSONObject jsonEncargo = en.getJSONObject(i);
                            residentes.setCodigo(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.CODIGO_USUARIO));
                            residentes.setNombre(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.NOMBRE_RESIDEN));
                            residentes.setApellido(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.APELL_RESIDEN));
                            residentes.setDni(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.DOC_RESIDETN));
                            residentes.setFoto(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.FOTO_RESIDENT));
                            residentes.setEntida(String.valueOf(ENTIDAD));
                            residentes.setNr_depa(String.valueOf(cod_enti));
                            residentes.setPuerta(String.valueOf(PUERTA));
                            residentes.setUser_reg(String.valueOf(COD_USUARIO));

                            list.add(residentes);


                        }

                        adaptadorRecycle_residentes_encargo = new AdaptadorRecycle_residentes_encargo(list, getActivity(), getFragmentManager(), viewGroup);
                        adaptadorRecycle_residentes_encargo.RecyclerViewOnClickListener2((RecyclerViewOnClickListener2) activity);

                        recyclerView.setAdapter(adaptadorRecycle_residentes_encargo);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "->"+error, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClickListener() {
        mListener.refrescar();

    }
}