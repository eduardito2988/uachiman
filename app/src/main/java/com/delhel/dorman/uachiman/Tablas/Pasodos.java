package com.delhel.dorman.uachiman.Tablas;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Custom.CustomSpinnerAdapterPuertas;
import com.delhel.dorman.uachiman.Dao.Ddescargas;
import com.delhel.dorman.uachiman.Panel;
import com.delhel.dorman.uachiman.R;

import java.util.List;

public class Pasodos extends AppCompatActivity {

    Spinner list_puertas;
    List<Puertas> puertas;
    Button seleccion_puerta;
    Bundle bundle;
    private CoordinatorLayout coordinatorLayout;
    int cod_puertaSeleccionado = 0;
    Snackbar snackbar;
    /* String */
    Stringers stringers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_pasodos);
        getSupportActionBar().setTitle("Doorman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stringers = new Stringers(getApplicationContext());

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        list_puertas = (Spinner) findViewById(R.id.list_puertas);
        seleccion_puerta = (Button) findViewById(R.id.seleccion_puerta);

        bundle = getIntent().getExtras();

        int cod_entidad = bundle.getInt(Constantes.COD_ENTIDAD);

        Ddescargas ddescargas = new Ddescargas(getApplicationContext());

        puertas = ddescargas.getPuertas(cod_entidad);

        CustomSpinnerAdapterPuertas customSpinnerAdapterPuertas = new CustomSpinnerAdapterPuertas(getApplicationContext(), puertas);
        list_puertas.setAdapter(customSpinnerAdapterPuertas);

        cod_puertaSeleccionado = bundle.getInt(Constantes.COD_PUERTA);

        list_puertas.setSelection(getIndex(puertas, cod_puertaSeleccionado));

        seleccion_puerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int posc = list_puertas.getSelectedItemPosition();

                if (posc > 0) {
                    int cod_puerta = puertas.get(posc).getPue_codi_in20();
                    String puerta = puertas.get(posc).getPue_nomb_vc90();

                    Intent intent = new Intent(getApplicationContext(), FinalizarConfiguracion.class);

                    intent.putExtra(Constantes.COD_ENTIDAD, bundle.getInt(Constantes.COD_ENTIDAD));
                    intent.putExtra(Constantes.ENTIDAD, bundle.getString(Constantes.ENTIDAD));
                    intent.putExtra(Constantes.COD_PUERTA, cod_puerta);
                    intent.putExtra(Constantes.PÙERTA, puerta);

                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                } else {


                    snackbar = Snackbar.make(coordinatorLayout, stringers.getStringResourceByName("empty_door"), 4000)
                            .setActionTextColor(Color.WHITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                }
                            });
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.color_boton));
                    TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(getApplicationContext().getResources().getColor(R.color.blanco));
                    snackbar.show();

                }
            }
        });
    }

    private int getIndex(List<Puertas> puertas, int cod_puertaSeleccionado) {

        int index = 0;

        for (int i = 0; i < puertas.size(); i++) {
            int codigo = puertas.get(i).getPue_codi_in20();
            if (codigo == cod_puertaSeleccionado)
                index = i;
        }
        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            volverAtras();
        }
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
}
