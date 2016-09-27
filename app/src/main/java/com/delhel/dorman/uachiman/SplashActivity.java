package com.delhel.dorman.uachiman;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.delhel.dorman.uachiman.AsyncTask.SincronizarData;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends Activity {


    /* Bluetooth */
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice con_dev = null;
   // public static BluetoothService mService = null;
    /* fIN DE Bluetooth */


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = getApplicationContext();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int cod_entidad =  sharedPreferences.getInt(QuickstartPreferences.COD_ENTIDAD,0);
        String perfil = sharedPreferences.getString(QuickstartPreferences.PERFIL,"");

        Toast.makeText(SplashActivity.this, "-->"+perfil, Toast.LENGTH_SHORT).show();

        if(cod_entidad>0){
            if(perfil.equals("port")){
                Log.e("mensaje","1");
                obtenerDataBd(cod_entidad);
            }else{
                Log.e("mensaje","2");
                Intent intent = new Intent(getApplication(),LoginActivity.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(getApplication(),LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void obtenerDataBd(int entidad){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("entidad", String.valueOf(entidad));

        RequestHandle post = client.post(Constantes.URL_DATABD, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject dataBd = null;
                try {
                    dataBd = new JSONObject(new String(responseBody));
                    new SincronizarData(context,SplashActivity.this).execute(dataBd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void accederPanel(){
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }


}
