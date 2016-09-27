package com.delhel.dorman.uachiman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Entidades;
import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Dao.Dlogin;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import com.delhel.dorman.uachiman.Tablas.EntidadesContract.EntidadesEntry;
import com.delhel.dorman.uachiman.Tablas.PuertasContract.PuertasEntry;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText password, usuario;
    Button acceder;
    ImageView logo;
    Context context;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout linearLayout;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Establecer pantalla completa */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login2);

        /* Verificar si el usuario ya esta dentro del login */

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String usu = sharedPreferencesE.getString(QuickstartPreferences.USUARIO,"");

        if(!usu.isEmpty()){
            Intent intent = new Intent(getApplication(),MainActivity.class);
            startActivity(intent);
        }

        seteoWidgets();

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    handled = true;
                    acceder_panel(usuario.getText().toString(), password.getText().toString());
                }
                return handled;
            }
        });
        acceder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        acceder_panel(usuario.getText().toString(), password.getText().toString());
    }

    public void acceder_panel(final String usu, String pass) {

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int cod_entidad =  sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD,0);
        boolean estado = true;

        if (usu.isEmpty()) {
            usuario.requestFocus();
            usuario.setError("Dato es obligatorio");
            estado = false;

        } else if (pass.isEmpty()) {
            password.requestFocus();
            password.setError("Dato es obligatorio");
            estado = false;
        }

        if (estado) {

            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams requestParams = new RequestParams();
            requestParams.add("txtEmail", usu);
            requestParams.add("txtClave", pass);

            RequestHandle post = client.post(Constantes.URL_ACCESO, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    progressBar.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    logo.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        try {

                            Stringers stringers = new Stringers(context);
                            JSONObject dataBD = new JSONObject(new String(responseBody));
                            String mensaje = dataBD.getString("msg");

                            Log.e("usuario->",mensaje);

                            if (mensaje.equals("erroneo")) {
                                preload();
                                CustoSnackBar(stringers.getStringResourceByName("error_login"));
                            } else {
                                if (cod_entidad == 0) {
                                    if (mensaje.equals("port")) {
                                        preload();
                                        CustoSnackBar(stringers.getStringResourceByName("no_config"));
                                    } else {
                                        accederAdmistrador(dataBD , usu);
                                    }
                                } else {
                                    if(mensaje.equals("port")){

                                        JSONObject dataSession = new JSONObject();
                                        dataSession.put(QuickstartPreferences.PERFIL,dataBD.getString("msg"));
                                        dataSession.put(QuickstartPreferences.USUARIO,usu);
                                        dataSession.put(QuickstartPreferences.COD_USUARIO,dataBD.getString(QuickstartPreferences.COD_USUARIO));
                                        saveSession(dataSession);
                                        Intent intent = new Intent(getApplication(),MainActivity.class);
                                        startActivity(intent);

                                    }else{
                                        accederAdmistrador(dataBD , usu);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("MENSAJE", "QUINTO" + error.toString());
                }
            });
        }
    }

    public void preload(){
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
    }

    public void accederAdmistrador(JSONObject data , String usu){

        JSONArray lentidades, lpuertas;

        Dlogin login = new Dlogin(getApplicationContext());
        login.eliminarTable(EntidadesEntry.TABLE_NAME);
        login.eliminarTable(PuertasEntry.TABLE_NAME);

        try {

            lentidades = data.getJSONArray("entidades");
            for (int i = 0; i < lentidades.length(); i++) {
                JSONObject c = lentidades.getJSONObject(i);
                Entidades entidades = new Entidades();
                entidades.setEnt_codi_in20(c.getInt("ent_codi_in20"));
                entidades.setEnt_nomb_vc200(c.getString("ent_nomb_vc200"));
                login.insertarEntidad(entidades);
            }

            lpuertas = data.getJSONArray("puertas");
            for (int x = 0; x < lpuertas.length(); x++) {
                JSONObject jsonPuerta = lpuertas.getJSONObject(x);
                Puertas puertas = new Puertas();
                puertas.setPue_codi_in20(jsonPuerta.getInt(PuertasEntry.PUE_CODI_IN20));
                puertas.setEnt_codi_in20(jsonPuerta.getInt(PuertasEntry.ENT_CODI_IN20));
                puertas.setPue_nomb_vc90(jsonPuerta.getString(PuertasEntry.PUE_NOMB_VC90));
                login.insertarPuertas(puertas);
            }

            /* Add SESSION en el SharedPreference */

            JSONObject dataSession = new JSONObject();
            dataSession.put(QuickstartPreferences.PERFIL,data.getString("msg"));
            dataSession.put(QuickstartPreferences.USUARIO,usu);
            dataSession.put(QuickstartPreferences.COD_USUARIO,data.getString(QuickstartPreferences.COD_USUARIO));

            saveSession(dataSession);

            Intent i = new Intent(getApplicationContext(), Panel.class);
            startActivity(i);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void seteoWidgets() {
        password = (EditText) findViewById(R.id.password);
        usuario = (EditText) findViewById(R.id.usuario);
        acceder = (Button) findViewById(R.id.acceder);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        linearLayout = (LinearLayout) findViewById(R.id.contenedor_login);
        logo = (ImageView) findViewById(R.id.logo);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        context = getApplicationContext();
    }

    public void CustoSnackBar(String mensaje) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, mensaje, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE);

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.color_boton));
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getApplicationContext().getResources().getColor(R.color.blanco));
        snackbar.show();
    }

    public void saveSession(JSONObject data){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            sharedPreferences.edit().putString(QuickstartPreferences.PERFIL,data.getString(QuickstartPreferences.PERFIL)).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.USUARIO,data.getString(QuickstartPreferences.USUARIO)).apply();
            sharedPreferences.edit().putInt(QuickstartPreferences.COD_USUARIO,data.getInt(QuickstartPreferences.COD_USUARIO)).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}