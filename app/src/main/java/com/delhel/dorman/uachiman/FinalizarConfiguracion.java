package com.delhel.dorman.uachiman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.delhel.dorman.uachiman.AsyncTask.SincronizarDataInicio;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FinalizarConfiguracion extends AppCompatActivity {


    TextView sel_entidad, sel_puerta;
    Context context;
    ProgressDialog loadingDialog;
    Stringers stringers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_finalizar_configuracion);
        getSupportActionBar().setTitle("Doorman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sel_entidad = (TextView) findViewById(R.id.sel_entidad);
        sel_puerta = (TextView) findViewById(R.id.sel_puerta);
        context = getApplicationContext();

        loadingDialog = new ProgressDialog(FinalizarConfiguracion.this);

        stringers = new Stringers(context);

        Bundle bundle = getIntent().getExtras();

        sel_entidad.setText(bundle.getString(QuickstartPreferences.ENTIDAD));
        sel_puerta.setText(bundle.getString(QuickstartPreferences.PUERTA));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) volverAtras();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        volverAtras();
    }

    public void volverAtras() {
        Intent intent = new Intent(getApplicationContext(), Panel.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void finalizar(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        sharedPreferences.edit().putString(QuickstartPreferences.USUARIO,"").apply();
        int cod_entidad =  sharedPreferences.getInt(QuickstartPreferences.COD_ENTIDAD,0);
        obtenerDataBd(cod_entidad);
    }


    private void obtenerDataBd(int entidad){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("entidad", String.valueOf(entidad));

        RequestHandle post = client.post(Constantes.URL_DATABD, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {

                loadingDialog.setIcon(R.drawable.cloud_computing);
                loadingDialog.setTitle(stringers.getStringResourceByName("mensaje_title_finalizar"));
                loadingDialog.setMessage(stringers.getStringResourceByName("mensaje_body_finalizar"));
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
                    new SincronizarDataInicio(context,loadingDialog,FinalizarConfiguracion.this).execute(dataBd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }




    public void accederLogin(){
        Intent intent = new Intent(getApplication(),SplashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
