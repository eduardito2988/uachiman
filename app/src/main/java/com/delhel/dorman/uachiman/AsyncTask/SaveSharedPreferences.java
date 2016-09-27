package com.delhel.dorman.uachiman.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Panel;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Usuario on 05/09/2016.
 */
public class SaveSharedPreferences extends AsyncTask<JSONObject, Void, String> {

    Stringers stringers;
    Context context;
    ProgressDialog progressDialog;
    public Panel activity;

    /*public SaveSharedPreferences(Context context, ProgressDialog progressDialog, Panel activity) {
        this.progressDialog = progressDialog;
        stringers = new Stringers(context);
        this.context = context;
        this.activity = activity;
    }*/

    public SaveSharedPreferences(Context context, ProgressDialog loadingDialog, Panel panel) {
        this.progressDialog = loadingDialog;
        stringers = new Stringers(context);
        this.context = context;
        this.activity = panel;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog.setIcon(R.drawable.smartphone);
        this.progressDialog.setTitle(stringers.getStringResourceByName("mensaje_title2"));
        this.progressDialog.setMessage(stringers.getStringResourceByName("mensaje_body2"));
        this.progressDialog.setProgressStyle(this.progressDialog.STYLE_SPINNER);
        this.progressDialog.setProgress(0);
        this.progressDialog.setMax(20);
        this.progressDialog.show();
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject dataBD = jsonObjects[0];

        /* Iniciar con el sharepreferences */
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            sharedPreferences.edit().putInt(QuickstartPreferences.COD_ENTIDAD, dataBD.getInt(QuickstartPreferences.COD_ENTIDAD)).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.ENTIDAD,dataBD.getString(QuickstartPreferences.ENTIDAD)).apply();
            sharedPreferences.edit().putInt(QuickstartPreferences.COD_PUERTA,dataBD.getInt(QuickstartPreferences.COD_PUERTA)).apply();
            sharedPreferences.edit().putString(QuickstartPreferences.PUERTA,dataBD.getString(QuickstartPreferences.PUERTA)).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("ok")) {
            this.progressDialog.dismiss();
            activity.CustoSnackBar(stringers.getStringResourceByName("response_sincronizado"));
        }
    }
}
