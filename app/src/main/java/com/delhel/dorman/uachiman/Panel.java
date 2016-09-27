package com.delhel.dorman.uachiman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.delhel.dorman.uachiman.AsyncTask.SaveSharedPreferences;
import com.delhel.dorman.uachiman.AsyncTask.SincronizarData;
import com.delhel.dorman.uachiman.Clases.Entidades;
import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Custom.CustomSpinnerAdapter;
import com.delhel.dorman.uachiman.Custom.CustomSpinnerAdapterPuertas;
import com.delhel.dorman.uachiman.DIALOG.obligatorio;
import com.delhel.dorman.uachiman.Dao.Dlogin;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Panel extends AppCompatActivity implements View.OnClickListener {

    /* Declaracion de variables */
    Context context;
    Spinner list_entidades, cbopuerta;
    Button sincronizacion;
    private CoordinatorLayout coordinatorLayout;
    int codigo_entidad;
    List<Entidades> entidades = new ArrayList<>();
    List<Puertas> puertasb = new ArrayList<>();
    ProgressDialog loadingDialog;
    Stringers stringers;
    Dlogin dlogin;
    /* Fin de declaracion de variables */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Establecer pantalla completa */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Mostrar el layout */
        setContentView(R.layout.activity_panel);

        /* Establecer el title en la barra */
        getSupportActionBar().setTitle("Doorman");

        /* Establecer el contexto */
        context = getApplicationContext();

        /* Instancias las clases */
        loadingDialog = new ProgressDialog(Panel.this);
        stringers = new Stringers(getApplicationContext());
        /* Fin de instanciar*/

        seteoElementos();

        sincronizacion.setOnClickListener(this);

        /* Instancia , entidades , puertas */
        dlogin = new Dlogin(getApplicationContext());
        entidades = dlogin.ListarEntidades();

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), entidades);
        list_entidades.setAdapter(customSpinnerAdapter);

        list_entidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Entidades ent = entidades.get(i);
                codigo_entidad = ent.getEnt_codi_in20();
                puertasb = dlogin.BuscarPuertas(ent.getEnt_codi_in20());
                CustomSpinnerAdapterPuertas customSpinnerAdapterPuertas = new CustomSpinnerAdapterPuertas(getApplicationContext(), puertasb);
                cbopuerta.setAdapter(customSpinnerAdapterPuertas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {

        boolean flag = false;

        ArrayList<String> stringArrayList = new ArrayList<String>();

        int posentidad = list_entidades.getSelectedItemPosition();
        int pospuerta = cbopuerta.getSelectedItemPosition();

        String entidad = list_entidades.getSelectedItem().toString();
        String puerta = cbopuerta.getSelectedItem().toString();

        if (posentidad == 0) {
            flag = true;
            stringArrayList.add(entidad);
        }

        if (pospuerta == 0) {
            flag = true;
            stringArrayList.add(puerta);
        }

        if (flag) {
            obligatorio dialog = new obligatorio();
            dialog.stringArrayList = stringArrayList;
            dialog.show(getSupportFragmentManager(), "dialog");
        } else {

            Puertas puertas = puertasb.get(pospuerta);
            int cod_puerta = puertas.getPue_codi_in20();

            JSONObject jsonObjectData = new JSONObject();

            try {

                jsonObjectData.put(QuickstartPreferences.COD_ENTIDAD, codigo_entidad);
                jsonObjectData.put(QuickstartPreferences.ENTIDAD, entidad);
                jsonObjectData.put(QuickstartPreferences.COD_PUERTA, cod_puerta);
                jsonObjectData.put(QuickstartPreferences.PUERTA, puerta);

                new SaveSharedPreferences(context, loadingDialog, Panel.this).execute(jsonObjectData);

                //obtenerDataBd(jsonObjectData);

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /* Iniciar con el sharepreferences */
            /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            sharedPreferences.edit().putInt(QuickstartPreferences.COD_ENTIDAD, codigo_entidad).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.ENTIDAD, entidad).apply();
            sharedPreferences.edit().putInt(QuickstartPreferences.COD_PUERTA, cod_puerta).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.PUERTA, puerta).apply();*/



           /* Puertas puertas = puertasb.get(pospuerta);
            int cod_puerta = puertas.getPue_codi_in20();
            JSONObject jsonObjectData = new JSONObject();

            try {
                jsonObjectData.put("cod_entidad", codigo_entidad);
                jsonObjectData.put("entidad", entidad);
                jsonObjectData.put("cod_puerta", cod_puerta);
                jsonObjectData.put("puerta", puerta);

                obtenerDataBd(jsonObjectData);

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void obtenerDataBd(JSONObject jsonObjectData) throws JSONException {

       /* JSONObject jsonObject = new JSONObject(jsonObjectData.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("entidad", String.valueOf(jsonObject.getInt("cod_entidad")));
        RequestHandle post = client.post(Constantes.URL_DATABD, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                loadingDialog.setIcon(R.drawable.cloud_computing);
                loadingDialog.setTitle(stringers.getStringResourceByName("mensaje_title"));
                loadingDialog.setMessage(stringers.getStringResourceByName("mensaje_body"));
                loadingDialog.setProgressStyle(loadingDialog.STYLE_SPINNER);
                loadingDialog.setProgress(0);
                loadingDialog.setMax(20);
                loadingDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject dataBd = null;
                try {
                    dataBd = new JSONObject(new String(responseBody));
                    new SincronizarData(context, loadingDialog, Panel.this).execute(dataBd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });*/
    }

    public void CustoSnackBar(String mensaje) {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, mensaje, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("Siguiente", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /* Obtener la entidad y la puerta */
                        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), FinalizarConfiguracion.class);

                        intent.putExtra(QuickstartPreferences.ENTIDAD, sharedPreferencesE.getString(QuickstartPreferences.ENTIDAD,""));
                        intent.putExtra(QuickstartPreferences.PUERTA, sharedPreferencesE.getString(QuickstartPreferences.PUERTA,""));
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        finish();
                    }
                });
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundResource(R.drawable.backgroundnarback);
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void seteoElementos() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        list_entidades = (Spinner) findViewById(R.id.list_entidades);
        cbopuerta = (Spinner) findViewById(R.id.cbopuerta);
        sincronizacion = (Button) findViewById(R.id.sincronizacion);
    }


}
