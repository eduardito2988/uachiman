package com.delhel.dorman.uachiman.Fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Repartidor;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Custom.CustomSpinnerUsuario;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_departamentos;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_delivery extends Fragment {

    public Ingreso_delivery() {
        // Required empty public constructor
    }

    View rootView;
    Dencargos myDaoSQLite;
    List<Modelo_departamentos> list_departamento;
    List<Repartidor> listreparidor = new ArrayList<Repartidor>();
    AutoCompleteTextView departamento;
    CustomSpinnerUsuario customSpinnerUsuario;
    Button buscar_ruc;
    Spinner cbo_repartidor;
    EditText ruc_autocomplete;
    EditText empresa_delivery;
    String valor_depa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_ingreso_delivery, container, false);

        myDaoSQLite = new Dencargos(getActivity());


        /* Proveedores */

        empresa_delivery = (EditText) rootView.findViewById(R.id.empresa_delivery);
        cbo_repartidor = (Spinner) rootView.findViewById(R.id.cbo_repartidor);
        buscar_ruc = (Button) rootView.findViewById(R.id.buscar_ruc);
        buscar_ruc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruc_autocomplete = (EditText) rootView.findViewById(R.id.ruc_autocomplete);
                String ruc = ruc_autocomplete.getText().toString();
                LlenarComboUsuarios(ruc);
            }
        });


        departamento = (AutoCompleteTextView) rootView.findViewById(R.id.cbo_gerencia);
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
                Modelo_departamentos productos = list_departamento.get(0);
                String nombre = productos.getUs_pel_ch100() + " " + productos.getUsu_nomb_ch100();
                departamento.setText(nombre);
                valor_depa = String.valueOf(productos.getUni_codi_in20());
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar_repartidor:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void Agregar_Repartidor(final View view) {

        AlertDialog.Builder builder;
        final AlertDialog alertDialog;
        Button btn_registrar_repartidor;

        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        final View layout = inflater.inflate(R.layout.repartidor_add_dialog, null);

        builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layout);
        builder.setTitle("REGISTRAR REPARTIDOR");
        builder.setIcon(R.drawable.icon);
        alertDialog = builder.create();
        alertDialog.show();

        btn_registrar_repartidor = (Button) layout.findViewById(R.id.btn_registrar_repartidor);
        btn_registrar_repartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean flag = true;

                EditText dni_repartidor = (EditText) layout.findViewById(R.id.dni_repartidor);
                EditText repartidor = (EditText) layout.findViewById(R.id.repartidor);

                if (dni_repartidor.getText().toString().isEmpty()) {
                    dni_repartidor.requestFocus();
                    dni_repartidor.setError(getString(R.string.obligatorio));
                    flag = false;
                }
                if (flag) {
                    if (repartidor.getText().toString().isEmpty()) {
                        repartidor.requestFocus();
                        repartidor.setError(getString(R.string.obligatorio));
                        flag = false;
                    }
                }

                if (flag) {
                    Repartidor repartidornew = new Repartidor();
                    repartidornew.setRvi_dnii_in20(dni_repartidor.getText().toString());
                    repartidornew.setRvi_nomb_vc200(repartidor.getText().toString());
                    listreparidor.add(repartidornew);
                    customSpinnerUsuario.notifyDataSetChanged();
                    alertDialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.msg_new_repartidor), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void LlenarComboUsuarios(String codigo) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("term", String.valueOf(codigo));
        requestParams.add("tipo_empresa", String.valueOf(2));

        RequestHandle handle = client.post(Constantes.URL_USUARIOS_PROVEEDORES, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONObject dataBd = null;

                try {

                    dataBd = new JSONObject(new String(responseBody));
                    JSONArray jsonArray = dataBd.optJSONArray(Constantes.USUARIO_EMPRESA);
                    JSONArray datoEmpresa = dataBd.optJSONArray(Constantes.DATA_EMPRESA);
                    JSONObject requestEmpresa = datoEmpresa.getJSONObject(0);
                    empresa_delivery.setText(requestEmpresa.getString("pem_nomb_vc200"));
                    Repartidor repartidor2 = new Repartidor();
                    repartidor2.setRvi_dnii_in20("");
                    repartidor2.setRvi_nomb_vc200(getString(R.string.repartidor_hint));
                    listreparidor.add(repartidor2);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Repartidor repartidor = new Repartidor();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        repartidor.setRvi_dnii_in20(jsonObject.getString(Constantes.RVI_DNII_IN20));
                        repartidor.setRvi_nomb_vc200(jsonObject.getString(Constantes.RVI_NOMB_VC200));
                        listreparidor.add(repartidor);
                    }

                    customSpinnerUsuario = new CustomSpinnerUsuario(getActivity(), listreparidor);
                    cbo_repartidor.setAdapter(customSpinnerUsuario);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }
}
