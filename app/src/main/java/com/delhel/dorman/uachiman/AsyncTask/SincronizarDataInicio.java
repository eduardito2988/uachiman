package com.delhel.dorman.uachiman.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Clases.UnidadInmobiliaria;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Constantes.Stringers;
import com.delhel.dorman.uachiman.Dao.Ddescargas;
import com.delhel.dorman.uachiman.FinalizarConfiguracion;
import com.delhel.dorman.uachiman.Tablas.Tabla_tipo_encargos;
import com.delhel.dorman.uachiman.Tablas.TiemposEntidadContract;
import com.delhel.dorman.uachiman.Tablas.UnidadInmobiliariaContract.UnidadInmobiliariaEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Usuario on 29/08/2016.
 */
public class SincronizarDataInicio extends AsyncTask<JSONObject, Void, String> {

    Stringers stringers;
    Context context;
    ProgressDialog progressDialog;
    FinalizarConfiguracion finalizarConfiguracion;

    public SincronizarDataInicio(Context context, ProgressDialog loadingDialog, FinalizarConfiguracion finalizarConfiguracion) {
        this.stringers = new Stringers(context);
        this.context = context;
        this.progressDialog=loadingDialog;
        this.finalizarConfiguracion = finalizarConfiguracion;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*this.progressDialog.setIcon(R.drawable.smartphone);
        this.progressDialog.setTitle(stringers.getStringResourceByName("mensaje_title2_finalizar"));
        this.progressDialog.setMessage(stringers.getStringResourceByName("mensaje_body2_finalizar"));
        this.progressDialog.setProgressStyle(this.progressDialog.STYLE_SPINNER);
        this.progressDialog.setProgress(0);
        this.progressDialog.setMax(20);
        this.progressDialog.show();*/
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        JSONObject dataBD = jsonObjects[0];
        Ddescargas ddescargas = new Ddescargas(context);
        ddescargas.eliminarTable(UnidadInmobiliariaEntry.TABLE_NAME);
        ddescargas.eliminarTable(Tabla_tipo_encargos.TipoEncargoEntry.NOM_TABLA_TIPO);
        //ddescargas.eliminarTable(HorariosEntry.TABLE_NAME);
        //ddescargas.eliminarTable(TrabajadoresEntry.TABLE_NAME);
        //ddescargas.eliminarTable(HorariosPersonalEntry.TABLE_NAME);

        /* Insertar Unidad */
        JSONArray jsonUnidades = dataBD.optJSONArray(Constantes.UNIDADES);
        for (int i = 0; i < jsonUnidades.length(); i++) {
            try {
                JSONObject jsonUnidad = jsonUnidades.getJSONObject(i);
                UnidadInmobiliaria uni = new UnidadInmobiliaria();
                uni.setUni_codi_in20(jsonUnidad.getInt(UnidadInmobiliariaEntry.UNI_CODI_IN20));
                uni.setUni_numi_vc20(jsonUnidad.getString(UnidadInmobiliariaEntry.UNI_NUMI_VC20));
                uni.setUsu_apel_ch100(jsonUnidad.getString(UnidadInmobiliariaEntry.USU_APEL_CH100));
                uni.setUsu_nomb_ch100(jsonUnidad.getString(UnidadInmobiliariaEntry.USU_NOMB_CH100));
                uni.setUni_telf_vc12(String.valueOf(jsonUnidad.getString(UnidadInmobiliariaEntry.UNI_TELF_VC12)));
                uni.setUsu_codi_in20(jsonUnidad.getInt(UnidadInmobiliariaEntry.USU_CODI_IN20));
                uni.setUsu_corr_vc100(jsonUnidad.getString(UnidadInmobiliariaEntry.USU_CORR_VC100));
                ddescargas.insertarUnidades(uni);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /* Fin Insertar Unidad */

       /* Insertar tipo encargo */
        JSONArray jsonTipocambio = dataBD.optJSONArray(Constantes.TIPO_ENCARGO);
        for (int i = 0; i < jsonTipocambio.length(); i++) {
            try {
                JSONObject jsonUnidad = jsonTipocambio.getJSONObject(i);
                Modelo_tipo_encargo uni = new Modelo_tipo_encargo();
                uni.setCodigo(jsonUnidad.getInt(Tabla_tipo_encargos.TipoEncargoEntry.COD_TIP_ENCARGO));
                uni.setDetalle(jsonUnidad.getString(Tabla_tipo_encargos.TipoEncargoEntry.DET_TIP_ENCARGO));
                ddescargas.insertarTipoentida(uni);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /* Fin Insertar Unidad */

      /* Inicio de tiemposEntidad */
        JSONArray jsonTiemposEntidad = dataBD.optJSONArray(Constantes.TIEMPOS_ENTIDAD);
        try {
            JSONObject jsonTEntidad = jsonTiemposEntidad.getJSONObject(0);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            String TDELIVERY = TiemposEntidadContract.TiemposEntidadEntry.TDELIVERY;
            String TCORREDORES = TiemposEntidadContract.TiemposEntidadEntry.TCORREDORES;
            String TSERVICIOS = TiemposEntidadContract.TiemposEntidadEntry.TSERVICIOS;
            String TCAPTCHA = TiemposEntidadContract.TiemposEntidadEntry.TCAPTCHA;
            String DIA_ENCARGO = TiemposEntidadContract.TiemposEntidadEntry.DIA_ENCARGO;
            sp.edit().putString(TDELIVERY, jsonTEntidad.getString(TDELIVERY)).apply();
            sp.edit().putString(TCORREDORES, jsonTEntidad.getString(TCORREDORES)).apply();
            sp.edit().putString(TSERVICIOS, jsonTEntidad.getString(TSERVICIOS)).apply();
            sp.edit().putString(TCAPTCHA, jsonTEntidad.getString(TCAPTCHA)).apply();
            sp.edit().putString(DIA_ENCARGO, jsonTEntidad.getString(DIA_ENCARGO)).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /* Fin de tiemposEntidad */

        /* Inicio configuracio de horarios */
       /* String[] horarios = {
                Constantes.CORRETAJE,
                Constantes.REMODELACIONES,
                Constantes.PROVEEDORES
        };
        for (int i = 0; i < horarios.length; i++) {
            JSONArray JSONdata = dataBD.optJSONArray(horarios[i]);
            for (int x = 0; x < JSONdata.length(); x++) {
                try {
                    JSONObject jsonCorre = JSONdata.getJSONObject(x);
                    HorariosConfig horariosConfig = new HorariosConfig();
                    horariosConfig.setDiasemana(jsonCorre.getString(HorariosEntry.DIASEMANA));
                    horariosConfig.setHinicio(jsonCorre.getString(HorariosEntry.HINICIO));
                    horariosConfig.setHfinal(jsonCorre.getString(HorariosEntry.HFINAL));
                    horariosConfig.setDescripcion(horarios[i]);
                    ddescargas.insertarHorarios(horariosConfig);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        /* Fin de configuracion de horarios */

        /* Trabajadores Entidad */
        /*JSONArray jsonTrEntidad = dataBD.optJSONArray(Constantes.TRABAJADORESENTIDAD);

        for (int y = 0; y < jsonTrEntidad.length(); y++) {
            try {

                JSONObject jsonTrab = jsonTrEntidad.getJSONObject(y);
                int ndni = 0 ;
                if(String.valueOf(jsonTrab.getString(TrabajadoresEntry.USU_DOCU_CH20)).isEmpty()){
                    ndni= 00000000;
                }else{
                    ndni =  jsonTrab.getInt(TrabajadoresEntry.USU_DOCU_CH20);
                }

                Trabajadores trabajadores = new Trabajadores();
                trabajadores.setUsu_codi_in20(jsonTrab.getInt(TrabajadoresEntry.USU_CODI_IN20));
                trabajadores.setUsu_apel_ch100(jsonTrab.getString(TrabajadoresEntry.USU_APEL_CH100));
                trabajadores.setUsu_nomb_ch100(jsonTrab.getString(TrabajadoresEntry.USU_NOMB_CH100));
                trabajadores.setUsu_docu_ch20(ndni);
                trabajadores.setUsu_numc_vc20(jsonTrab.getString(TrabajadoresEntry.USU_NUMC_VC20));
                trabajadores.setUsu_movi_vc15(jsonTrab.getString(TrabajadoresEntry.USU_MOVI_VC15));
                trabajadores.setUsu_dire_vc120(jsonTrab.getString(TrabajadoresEntry.USU_DIRE_VC120));
                trabajadores.setUsu_corr_vc100(jsonTrab.getString(TrabajadoresEntry.USU_CORR_VC100));
                trabajadores.setUsu_tsan_vc15(jsonTrab.getString(TrabajadoresEntry.USU_TSAN_VC15));
                trabajadores.setUsu_foto_long(jsonTrab.getString(TrabajadoresEntry.USU_FOTO_LONG));
                trabajadores.setPerf_deta_vc200(jsonTrab.getString(TrabajadoresEntry.PERF_DETA_VC200));
                trabajadores.setCon_tele_vc15(jsonTrab.getString(TrabajadoresEntry.CON_TELE_VC15));
                trabajadores.setCon_nome_vc60(jsonTrab.getString(TrabajadoresEntry.CON_NOME_VC60));
                ddescargas.insetarTrabajador(trabajadores);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /* Fin de trabajadores entidad */

        /* Horario Trabajadores */
        /*JSONArray jsonHTrabajadores = dataBD.optJSONArray(Constantes.HORARIOSTRABAJADORES);

        for (int z = 0; z < jsonHTrabajadores.length(); z++) {
            try {
                JSONObject jsonHTrab = jsonHTrabajadores.getJSONObject(z);
                HorarioPersonal horarioPersonal = new HorarioPersonal();
                horarioPersonal.setHor_codi_in20(jsonHTrab.getInt(HorariosPersonalEntry.HOR_CODI_IN20));
                horarioPersonal.setUsu_codi_in20(jsonHTrab.getInt(HorariosPersonalEntry.USU_CODI_IN20));
                horarioPersonal.setDiasemana(jsonHTrab.getString(HorariosPersonalEntry.DIASEMANA));
                horarioPersonal.setHinicio(jsonHTrab.getString(HorariosPersonalEntry.HINICIO));
                horarioPersonal.setHrefrigerio(jsonHTrab.getString(HorariosPersonalEntry.HREFRIGERIO));
                horarioPersonal.setHsalida(jsonHTrab.getString(HorariosPersonalEntry.HSALIDA));
                ddescargas.insertarHTrabajadores(horarioPersonal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /* Fin de Horario de Trabajadores */


        return "ok";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("ok")) {
            progressDialog.dismiss();
            finalizarConfiguracion.accederLogin();
        }
    }
}
