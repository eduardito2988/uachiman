package com.delhel.dorman.uachiman;

import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Custom.AdaptadorAcordeon;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_lista_encargos;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Printer.Bluetooh;
import com.delhel.dorman.uachiman.Printer.ConstantesPrinter;
import com.delhel.dorman.uachiman.Tablas.Tabla_detalle_encargos.EncargosDetalleEntry;
import com.delhel.dorman.uachiman.Clases.Modelo_detalle_unididad_encargo;
import com.delhel.dorman.uachiman.Custom.CircleTransform;
import com.delhel.dorman.uachiman.Tablas.TiemposEntidadContract;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.zj.btsdk.BluetoothService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Detalle_encargos extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Modelo_detalle_unididad_encargo> LisModelos;
    Dencargos myDaoSQLite;
    AdaptadorRecycle_lista_encargos adaptadorRecycle;
    String unidad;
    TextView d_encargo;

    public BluetoothService mService = null;
    BluetoothDevice con_dev = null;

    @Override
    public void onStart() {
        super.onStart();
       /* Bluetooh bluetooh = new Bluetooh(getApplicationContext());
        bluetooh.conectarBluetooth();
        SystemClock.sleep(2000);
        mService = bluetooh.rmService();
        con_dev = mService.getDevByMac(ConstantesPrinter.printer_name);
        mService.connect(con_dev);*/
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  mService.stop();*/
      //  Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detalle_encargos);

        Bundle bundle = getIntent().getExtras();

        int cod_unidad = bundle.getInt("cod_unidad");
        unidad = bundle.getString("unidad");
        String usuario = bundle.getString("usuario");
        int cod_usuario = bundle.getInt("cod_usuario");
        String foto = bundle.getString("foto");

        myDaoSQLite = new Dencargos(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lista de Encargos");

        d_encargo = (TextView) findViewById(R.id.d_encargo);
        ImageView imagen = (ImageView) findViewById(R.id.foto_user);
        TextView numero_uni = (TextView) findViewById(R.id.nro_dep);
        TextView nom = (TextView) findViewById(R.id.txtnombre);
        nom.setText(usuario);
        numero_uni.setText(unidad);
        Picasso.with(this).load(foto).error(R.drawable.sin_perfin).transform(new CircleTransform()).into(imagen);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_encargos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        LisModelos = new ArrayList<>();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ndia = sp.getString(TiemposEntidadContract.TiemposEntidadEntry.DIA_ENCARGO, "");
        String dia_encargo = "LOS ENCARGOS PUEDEN ESTAR PENDIENTE SOLO "+ndia+" DIAS.";
        d_encargo.setText(dia_encargo);

        ConsultarDetalleEncargo(cod_unidad, cod_usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ConsultarDetalleEncargo(int unidad, int usuario) {

        /** POST ENVIO **/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        requestParams.add("cod_usuario", String.valueOf(usuario));
        requestParams.add("cod_unidad", String.valueOf(unidad));

        RequestHandle post = client.post(Constantes.URL_DETALLE_ENCARGO, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                AdaptadorAcordeon.dialogo.setMessage("Espere un momento");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String msj;
                if (statusCode == 200) {
                    JSONObject o = null;

                    try {

                        JSONObject datos = new JSONObject(new String(responseBody));
                        JSONArray encargo = datos.optJSONArray(Constantes.DATOS_ENCARGO_DE_UNIDAD);

                        for (int i = 0; i < encargo.length(); i++) {

                            JSONObject jsondetalle = encargo.getJSONObject(i);
                            Modelo_detalle_unididad_encargo unidad = new Modelo_detalle_unididad_encargo();
                            unidad.setEnc_codi_in20(jsondetalle.getInt(EncargosDetalleEntry.ENC_CODIGO));
                            unidad.setEnc_deta_vc200(jsondetalle.getString(EncargosDetalleEntry.ENC_DETALLE));
                            unidad.setEnc_ingr_dati(jsondetalle.getString(EncargosDetalleEntry.FECH_INGRESO));
                            unidad.setTen_deta_vc200(jsondetalle.getString(EncargosDetalleEntry.TIPO_ENCARGO));
                            unidad.setUsu_apel_ch100(jsondetalle.getString(EncargosDetalleEntry.APE_USUARIO));
                            unidad.setUsu_nomb_ch100(jsondetalle.getString(EncargosDetalleEntry.NOM_USUARIO));
                            unidad.setPuerta(jsondetalle.getString(EncargosDetalleEntry.PUERTA));
                            LisModelos.add(unidad);
                        }

                        adaptadorRecycle = new AdaptadorRecycle_lista_encargos(LisModelos, getApplication(), mService);
                        recyclerView.setAdapter(adaptadorRecycle);

                        AdaptadorAcordeon.dialogo.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplication(), "Error Inesperado buelva intentar.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
