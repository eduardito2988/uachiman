package com.delhel.dorman.uachiman.Tablas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.delhel.dorman.uachiman.Panel;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;


public class FinalizarConfiguracion extends AppCompatActivity {

    TextView sel_entidad, sel_puerta;

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

    }

}
