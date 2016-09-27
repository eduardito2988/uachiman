package com.delhel.dorman.uachiman.Fragmentos;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_Entidades_con_encargos;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_residentes;
import com.delhel.dorman.uachiman.Clases.Modelo_residentes;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_departamentos;
import com.delhel.dorman.uachiman.DB.MySqlOpenHelper;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.delhel.dorman.uachiman.Tablas.Tabla_depa_con_encargo;
import com.delhel.dorman.uachiman.Tablas.Tabla_residentes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_principal_residente extends Fragment {


    public Fragment_principal_residente() {
        // Required empty public constructor
    }

    View rootview;
  //  private Modelobasedatos bd;
    private Cursor c;
    private RecyclerView recyclerView;
    private List<Modelo_residentes> list;
    AdaptadorRecycle_residentes adaptadorRecycle_residentes;

    AutoCompleteTextView caja_unida;
    List<Modelo_departamentos> list_departamento;
    Dencargos myDaoSQLite ;
    List<Modelo_tipo_encargo> listado;
    private List<Modelo_departamento_con_encargo> LisModelos;
    Button btnButton ;
    RadioButton dni,depa;
    EditText buscar_dni_residente;
    String COD_USER,COD_UNID,DNI_RES;
    int ENTIDAD,PUERTA;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_principal_residente, container, false);

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ENTIDAD= sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD, 0);
        PUERTA= sharedPreferencesE.getInt(QuickstartPreferences.COD_PUERTA, 0);

        getActivity().setTitle("RESIDENTE");// TITULO FRAGMENT
        myDaoSQLite = new Dencargos(getActivity());


        caja_unida = (AutoCompleteTextView)rootview.findViewById(R.id.autocomplet_unidad);
        buscar_dni_residente = (EditText) rootview.findViewById(R.id.buscar_dni_residente);

        depa = (RadioButton) rootview.findViewById(R.id.check_num_unid);
        dni = (RadioButton) rootview.findViewById(R.id.check_buscar_dni);
        btnButton = (Button) rootview.findViewById(R.id.btn_buscar_personal);

        recyclerView =  (RecyclerView) rootview.findViewById(R.id.recycle_residente);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


         /* autocompletador */
        list_departamento= new ArrayList<>();
        caja_unida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list_departamento = myDaoSQLite.llenarCombo_departamentos(charSequence.toString());
                CustomerAdapter_departamentos customerAdapter = new CustomerAdapter_departamentos(getActivity(), R.layout.customer_auto, list_departamento);
                caja_unida.setThreshold(0);
                caja_unida.setAdapter(customerAdapter);

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        caja_unida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   adapterView.getItemAtPosition(i);

                Modelo_departamentos unidades = list_departamento.get(0);
                String nombre = unidades.getUs_pel_ch100()+" "+unidades.getUsu_nomb_ch100();
                caja_unida.setText(nombre);

                COD_USER= unidades.getUsu_codi_in20();
                COD_UNID=unidades.getUni_codi_in20();

              //  Log.e("-->",""+COD_USER+"-"+COD_UNID);
               // numerodep.setText(productos.getUni_codi_in20());
                //  list_departamento.get(i).getUsu_nomb_ch100();

            }
        });


        /*****************VALIDAR CHEKEND***********************/
        depa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                buscar_dni_residente.setVisibility(View.VISIBLE);
                caja_unida.setVisibility(View.GONE);
                buscar_dni_residente.setText("");

            }
        });

        dni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                buscar_dni_residente.setVisibility(View.GONE);
                caja_unida.setVisibility(View.VISIBLE);
                caja_unida.setText("");

            }
        });



        /******BOTON BUSCAR*******/

        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(depa.isChecked()){

                    if(caja_unida.getText().equals("")){
                        Toast.makeText(getActivity(), "Ingrese Numero departamento.", Toast.LENGTH_SHORT).show();
                    }else {

                        Log.e("datos","->"+COD_USER+""+COD_UNID);
                       ConsultarResidente(COD_USER, COD_UNID, null);
                    }

                }else if (dni.isChecked()){

                     if(buscar_dni_residente.getText().equals("")) {
                         Toast.makeText(getActivity(), "Ingrese DNI", Toast.LENGTH_SHORT).show();
                     }else{
                         DNI_RES = buscar_dni_residente.getText().toString();
                         ConsultarResidente(null, null, DNI_RES);
                     }

                }

            }
        });
        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**CHEKEND**/

    }


    public void ConsultarResidente(String id_usu,String cod_enti,String dni) {

        /** POST ENVIO A NUVE*/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("cod_user", id_usu);
        requestParams.add("cod_unida", cod_enti);
        requestParams.add("cod_entida", String.valueOf(ENTIDAD));
        requestParams.add("cod_dni", dni);


        RequestHandle post = client.post(Constantes.URL_CONSULTA_RESIDENTES, requestParams, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    JSONObject o = null;
                    try {
                        list= new ArrayList();
                        o = new JSONObject(new String(responseBody));
                        JSONArray en = o.optJSONArray(Constantes.DATOS_RESIDENTES_UNIDAD);

                        for (int i = 0; i < en.length(); i++) {
                            Modelo_residentes residentes = new Modelo_residentes();
                            JSONObject jsonEncargo = en.getJSONObject(i);
                            residentes.setCodigo(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.CODIGO_USUARIO));
                            residentes.setNombre(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.NOMBRE_RESIDEN));
                            residentes.setApellido(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.APELL_RESIDEN));
                            residentes.setDni(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.DOC_RESIDETN));
                            residentes.setFoto(jsonEncargo.getString(Tabla_residentes.ResidentesEntry.FOTO_RESIDENT));

                            list.add(residentes);


                        }

                        adaptadorRecycle_residentes = new AdaptadorRecycle_residentes(list,getActivity());
                        recyclerView.setAdapter(adaptadorRecycle_residentes);

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
