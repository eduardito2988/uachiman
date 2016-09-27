package com.delhel.dorman.uachiman.Dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.delhel.dorman.uachiman.Custom.Capitalizar;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.DB.MySqlOpenHelper;
import com.delhel.dorman.uachiman.Interface.MyDao;
import com.delhel.dorman.uachiman.Tablas.Tabla_depa_con_encargo;
import com.delhel.dorman.uachiman.Tablas.Tabla_tipo_encargos;
import com.delhel.dorman.uachiman.Tablas.TrabajadoresContract;
import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Clases.Modelo_detalle_unididad_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_perfil_trabajadores;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Usuario on 25/07/2016.
 */
public class Dencargos implements MyDao {

    Context context;
    List<Modelo_departamento_con_encargo> list = new ArrayList<>();
    Modelo_departamento_con_encargo encar;

    public Dencargos(Context context) {
        this.context = context;
    }


    @Override
    public List<Modelo_departamentos> llenarCombo_departamentos(String dato) {

        List<Modelo_departamentos> lista = new ArrayList<>();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

        String sql = "SELECT * FROM UnidadInmobiliaria  where uni_numi_vc20 like '" + dato +"%' or usu_apel_ch100 like '"+dato+"%'";


        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                Modelo_departamentos p = new Modelo_departamentos();

                p.setUni_codi_in20(cursor.getString(0));
                p.setUni_numi_vc20(cursor.getString(1));
                p.setUs_pel_ch100(cursor.getString(2));
                p.setUsu_nomb_ch100(cursor.getString(3));
                p.setUni_telf_vc12(cursor.getString(4));
                p.setUsu_codi_in20(cursor.getString(5));
                p.setUsu_corr_vc100(cursor.getString(6));

                lista.add(p);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return lista;
    }

    @Override
    public List<Modelo_tipo_encargo> llenar_combo_tipo_encargo() {

        List<Modelo_tipo_encargo> lista = new ArrayList<>();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+ Tabla_tipo_encargos.TipoEncargoEntry.NOM_TABLA_TIPO;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            Modelo_tipo_encargo p1 = new Modelo_tipo_encargo();
            p1.setCodigo(0);
            p1.setDetalle("Seleccione");
            lista.add(p1);

            do {
                Modelo_tipo_encargo p2 = new Modelo_tipo_encargo();
                p2.setCodigo(cursor.getInt(0));
                p2.setDetalle(cursor.getString(1));
                lista.add(p2);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return lista;
    }

    @Override
    public List<Modelo_perfil_trabajadores> listado_trabajadores() {

        List<Modelo_perfil_trabajadores> lista_trabajadores = new ArrayList<>();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+ TrabajadoresContract.TrabajadoresEntry.TABLE_NAME ;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        //obtener dia
        Capitalizar mayuscula = new Capitalizar();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE");
        String dia = df.format(c.getTime());
        String diasemana = mayuscula.ucFirst(dia);

        if (cursor.moveToFirst()) {

            do {
                Modelo_perfil_trabajadores p = new Modelo_perfil_trabajadores();
                Log.e("trabajador","-"+ cursor.getString(2)+"-"+diasemana+" cod="+cursor.getString(0));
                p.setCodigo(cursor.getString(0));
                p.setApellido(cursor.getString(1));
                p.setNombre(cursor.getString(2));
                p.setDni(cursor.getString(3));
                p.setFoto(cursor.getString(9));
             //  Log.e("inseta","-"+cursor.getString(9));

                String sqlhora = "select * from horario_personal where diasemana='"+diasemana+"' and usu_codi_in20="+cursor.getString(0);
                Cursor cursor2 = sqLiteDatabase.rawQuery(sqlhora, null);


                for (cursor2.moveToFirst(); !cursor2.isAfterLast();cursor2.moveToNext()) {
                    p.setHorainicio(cursor2.getString(3));
                    p.setHorafin(cursor2.getString(5));

                }

              //  Log.e("dia bd","--- "+diabd+" inicio "+inicio+" final "+fin+"");

                lista_trabajadores.add(p);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return lista_trabajadores;

    }



    @Override
    public List<Modelo_encargo> listar_encargo() {

        List<Modelo_encargo> lista_encargo = new ArrayList<>();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM Nuevos_encargos ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                Modelo_encargo p = new Modelo_encargo();

                p.setEnt_codi_in20(cursor.getInt(1));
                p.setEnc_deta_vc200(cursor.getString(2));
                p.setUsu_codi_in20(cursor.getInt(3));
                p.setEnc_ingr_dati(cursor.getString(4));
                p.setTen_codi_in20(cursor.getInt(6));
                p.setUni_codi_in20(cursor.getInt(7));
                p.setPer_ingr_in20(cursor.getInt(8));
                p.setEnc_upos_in20(cursor.getInt(10));
                p.setPue_codi_in20(cursor.getInt(11));

                lista_encargo.add(p);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return lista_encargo;
    }

    @Override
    public long Insertar_Encargos(Modelo_encargo objeto) {

       // limpiar tabla
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ent_codi_in20", objeto.getEnt_codi_in20());
        contentValues.put("enc_deta_vc200", objeto.getEnc_deta_vc200());
        contentValues.put("usu_codi_in20", objeto.getUsu_codi_in20());
        contentValues.put("enc_ingr_dati", objeto.getEnc_ingr_dati());
        contentValues.put("ten_codi_in20", objeto.getTen_codi_in20());
        contentValues.put("uni_codi_in20", objeto.getUni_codi_in20());
        contentValues.put("per_ingr_in20", objeto.getPer_ingr_in20());
        contentValues.put("enc_upos_in20", objeto.getEnc_upos_in20());
        contentValues.put("pue_codi_in20", objeto.getPue_codi_in20());

        long resultado = sqLiteDatabase.insert("Nuevos_encargos", null, contentValues);
        sqLiteDatabase.close();
        return  resultado;
    }

    @Override
    public List<Modelo_departamentos> listar_entidades_con_encargo(int dato) {

         return null;
    }

    @Override
    public void eliminarTable(String tabla) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + tabla);
        sqLiteDatabase.close();
    }

    @Override
    public List<Modelo_departamento_con_encargo> listar_departamentos_con_encargo() {

        List<Modelo_departamento_con_encargo> lis= new ArrayList<>();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM "+Tabla_depa_con_encargo.DepaconEncargoEntry.NOM_TABL_ENCARGOS;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {

                Modelo_departamento_con_encargo encargo = new Modelo_departamento_con_encargo();

                encargo.setId_encargo(cursor.getInt(0));
                encargo.setCantidad(cursor.getInt(1));
                encargo.setNum_unidad(cursor.getInt(2));
                encargo.setUni_numi(cursor.getString(3));
                encargo.setCod_entida(cursor.getInt(4));
                encargo.setNombre(cursor.getString(5));
                encargo.setApellid(cursor.getString(6));
                encargo.setDni(cursor.getString(7));
                encargo.setFoto(cursor.getString(8));
                encargo.setCod_usuario(cursor.getInt(9));
                lis.add(encargo);

            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();

        return lis;
    }

    @Override
    public List<Modelo_detalle_unididad_encargo> listar_encargo_de_entidad(String numero) {
        return null;
    }


    @Override
    public void DescargarEncargo(int dato) {


        /** POST ENVIO A NUVE*/
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("cod_entidad", String.valueOf(dato));

        RequestHandle post = client.post(Constantes.URL_SELECT_DATA_DE_ENTIDAD, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    try {

                        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
                        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

                        eliminarTable(Tabla_depa_con_encargo.DepaconEncargoEntry.NOM_TABL_ENCARGOS);

                        JSONObject o = new JSONObject(new String(responseBody));
                        JSONArray en = o.optJSONArray(Constantes.DATOS_ENCARGOS_DE_UNIDAD);

                        for (int i = 0; i < en.length(); i++) {
                            JSONObject jsonEncargo = en.getJSONObject(i);

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.CANTIDAD, jsonEncargo.getInt(Tabla_depa_con_encargo.DepaconEncargoEntry.CANTIDAD));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NUMI, jsonEncargo.getInt(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NUMI));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_UNIDAD, jsonEncargo.getInt(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_UNIDAD));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_ENDTIDAD, jsonEncargo.getInt(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_ENDTIDAD));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NOMB, jsonEncargo.getString(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NOMB));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_APELL, jsonEncargo.getString(Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_APELL));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.DNI_USUARIO, jsonEncargo.getString(Tabla_depa_con_encargo.DepaconEncargoEntry.DNI_USUARIO));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.FOTO_USUARIO, jsonEncargo.getString(Tabla_depa_con_encargo.DepaconEncargoEntry.FOTO_USUARIO));
                            contentValues.put(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_USUARIO, jsonEncargo.getString(Tabla_depa_con_encargo.DepaconEncargoEntry.COD_USUARIO));
                            long resultado = sqLiteDatabase.insert(Tabla_depa_con_encargo.DepaconEncargoEntry.NOM_TABL_ENCARGOS, null, contentValues);

                        }
                        sqLiteDatabase.close();

                        //Crouton.makeText(Login_app.this,estado, Style.ALERT).show();
                    } catch (JSONException e) {


                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    @Override
    public void DescargarDetalleEncargo(String dato) {

        /** POST ENVIO A NUVE*/
     /*   AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("cod_unidad", dato);

        RequestHandle post = client.post(Constantes.URL_DETALLE_ENCARGO, requestParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    try {

                        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
                        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

                        eliminarTable(Tabla_detalle_encargos.EncargosDetalleEntry.TABLA_DET_ENCARGO);

                        JSONObject o = new JSONObject(new String(responseBody));
                        JSONArray en = o.optJSONArray(Constantes.DATOS_DETALLE_ENCARGO);
                        Log.e("array",en.toString());

                        for (int i = 0; i < en.length(); i++) {
                            JSONObject jsonDetEncargo = en.getJSONObject(i);

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Tabla_detalle_encargos.EncargosDetalleEntry.CODIGO_DET_ENC , jsonDetEncargo.getInt(Tabla_detalle_encargos.EncargosDetalleEntry.CODIGO_DET_ENC));
                            contentValues.put(Tabla_detalle_encargos.EncargosDetalleEntry.DETALLE_DET_ENC, jsonDetEncargo.getString(Tabla_detalle_encargos.EncargosDetalleEntry.DETALLE_DET_ENC));
                            contentValues.put(Tabla_detalle_encargos.EncargosDetalleEntry.TIPO_ENC, jsonDetEncargo.getString(Tabla_detalle_encargos.EncargosDetalleEntry.TIPO_ENC));
                            contentValues.put(Tabla_detalle_encargos.EncargosDetalleEntry.FECHA_REG_ENC, jsonDetEncargo.getString(Tabla_detalle_encargos.EncargosDetalleEntry.FECHA_REG_ENC));
                            contentValues.put(Tabla_detalle_encargos.EncargosDetalleEntry.NUMERO_UNIDAD, jsonDetEncargo.getString(Tabla_detalle_encargos.EncargosDetalleEntry.NUMERO_UNIDAD));

                            long resultado = sqLiteDatabase.insert(Tabla_detalle_encargos.EncargosDetalleEntry.TABLA_DET_ENCARGO, null, contentValues);
                         Log.e("INSERT","--"+jsonDetEncargo.getString(Tabla_detalle_encargos.EncargosDetalleEntry.DETALLE_DET_ENC));
                        }
                        sqLiteDatabase.close();

                        //Crouton.makeText(Login_app.this,estado, Style.ALERT).show();
                    } catch (JSONException e) {


                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        */



    }

}
