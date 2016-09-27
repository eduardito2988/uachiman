package com.delhel.dorman.uachiman.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Clases.UnidadInmobiliaria;
import com.delhel.dorman.uachiman.Interface.Descargas;
import com.delhel.dorman.uachiman.Clases.HorarioPersonal;
import com.delhel.dorman.uachiman.Clases.HorariosConfig;
import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.Clases.TiemposEntidad;
import com.delhel.dorman.uachiman.Clases.Trabajadores;
import com.delhel.dorman.uachiman.Clases.UnidadInmobiliaria;
import com.delhel.dorman.uachiman.DB.MySqlOpenHelper;
import com.delhel.dorman.uachiman.Tablas.HorariosContract.HorariosEntry;
import com.delhel.dorman.uachiman.Tablas.HorariosPersonalContract.HorariosPersonalEntry;
import com.delhel.dorman.uachiman.Tablas.PuertasContract.PuertasEntry;
import com.delhel.dorman.uachiman.Tablas.TiemposEntidadContract.TiemposEntidadEntry;
import com.delhel.dorman.uachiman.Tablas.TrabajadoresContract.TrabajadoresEntry;
import com.delhel.dorman.uachiman.Tablas.UnidadInmobiliariaContract.UnidadInmobiliariaEntry;
import com.delhel.dorman.uachiman.Tablas.Tabla_tipo_encargos.TipoEncargoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 15/08/2016.
 */
public class Ddescargas implements Descargas {

    Context context;

    public Ddescargas(Context context) {
        this.context = context;
    }

    @Override
    public long insertarUnidades(UnidadInmobiliaria uni) {

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UnidadInmobiliariaEntry.UNI_CODI_IN20, uni.getUni_codi_in20());
        contentValues.put(UnidadInmobiliariaEntry.UNI_NUMI_VC20, uni.getUni_numi_vc20());
        contentValues.put(UnidadInmobiliariaEntry.USU_APEL_CH100, uni.getUsu_apel_ch100());
        contentValues.put(UnidadInmobiliariaEntry.USU_NOMB_CH100, uni.getUsu_nomb_ch100());
        contentValues.put(UnidadInmobiliariaEntry.UNI_TELF_VC12, uni.getUni_telf_vc12());
        contentValues.put(UnidadInmobiliariaEntry.USU_CODI_IN20, uni.getUsu_codi_in20());
        contentValues.put(UnidadInmobiliariaEntry.USU_CORR_VC100, uni.getUsu_corr_vc100());
        long resultado = sqLiteDatabase.insert(UnidadInmobiliariaEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public long insertarTipoentida(Modelo_tipo_encargo tipo) {

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TipoEncargoEntry.COD_TIP_ENCARGO, tipo.getCodigo());
        contentValues.put(TipoEncargoEntry.DET_TIP_ENCARGO, tipo.getDetalle());
        long resultado = sqLiteDatabase.insert(TipoEncargoEntry.NOM_TABLA_TIPO, null, contentValues);
        sqLiteDatabase.close();
        return resultado;

    }


    @Override
    public long insertarTiemposEntidad(TiemposEntidad tiemposEntidad) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TiemposEntidadEntry.TDELIVERY, tiemposEntidad.getTdelivery());
        contentValues.put(TiemposEntidadEntry.TCORREDORES, tiemposEntidad.getTcorredores());
        contentValues.put(TiemposEntidadEntry.TSERVICIOS, tiemposEntidad.getTservicios());
        contentValues.put(TiemposEntidadEntry.TCAPTCHA, tiemposEntidad.getTcaptcha());
        long resultado = sqLiteDatabase.insert(TiemposEntidadEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public long insertarHorarios(HorariosConfig horariosConfig) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HorariosEntry.DIASEMANA, horariosConfig.getDiasemana());
        contentValues.put(HorariosEntry.HINICIO, horariosConfig.getHinicio());
        contentValues.put(HorariosEntry.HFINAL, horariosConfig.getHfinal());
        contentValues.put(HorariosEntry.DESCRIPCION, horariosConfig.getDescripcion());
        long resultado = sqLiteDatabase.insert(HorariosEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public void eliminarTable(String tabla) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + tabla);
        sqLiteDatabase.close();
    }

    @Override
    public List<Puertas> getPuertas(int entidad) {

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

        List<Puertas> Listpuertas = new ArrayList<>();

        String sql = "select " + PuertasEntry.PUE_CODI_IN20 + ", " + PuertasEntry.PUE_NOMB_VC90;
        sql += " from " + PuertasEntry.TABLE_NAME + " where " + PuertasEntry.ENT_CODI_IN20 + "=" + entidad;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        Puertas puertas2 = new Puertas();
        puertas2.setPue_codi_in20(0);
        puertas2.setPue_nomb_vc90("Seleccione Puerta");
        Listpuertas.add(puertas2);

        if (cursor.moveToFirst()) {
            do {
                Puertas puertas = new Puertas();
                puertas.setPue_codi_in20(cursor.getInt(0));
                puertas.setPue_nomb_vc90(cursor.getString(1));
                Listpuertas.add(puertas);
            } while (cursor.moveToNext());

        }
        sqLiteDatabase.close();
        return Listpuertas;
    }


    @Override
    public long insetarTrabajador(Trabajadores trabajadores) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrabajadoresEntry.USU_CODI_IN20, trabajadores.getUsu_codi_in20());
        contentValues.put(TrabajadoresEntry.USU_APEL_CH100, trabajadores.getUsu_apel_ch100());
        contentValues.put(TrabajadoresEntry.USU_NOMB_CH100, trabajadores.getUsu_nomb_ch100());
        contentValues.put(TrabajadoresEntry.USU_DOCU_CH20, trabajadores.getUsu_docu_ch20());
        contentValues.put(TrabajadoresEntry.USU_NUMC_VC20, trabajadores.getUsu_numc_vc20());
        contentValues.put(TrabajadoresEntry.USU_MOVI_VC15, trabajadores.getUsu_movi_vc15());
        contentValues.put(TrabajadoresEntry.USU_DIRE_VC120, trabajadores.getUsu_dire_vc120());
        contentValues.put(TrabajadoresEntry.USU_CORR_VC100, trabajadores.getUsu_corr_vc100());
        contentValues.put(TrabajadoresEntry.USU_TSAN_VC15, trabajadores.getUsu_tsan_vc15());
        contentValues.put(TrabajadoresEntry.USU_FOTO_LONG, trabajadores.getUsu_foto_long());
        contentValues.put(TrabajadoresEntry.PERF_DETA_VC200, trabajadores.getPerf_deta_vc200());
        contentValues.put(TrabajadoresEntry.CON_TELE_VC15, trabajadores.getCon_tele_vc15());
        contentValues.put(TrabajadoresEntry.CON_NOME_VC60, trabajadores.getCon_nome_vc60());
        long resultado = sqLiteDatabase.insert(TrabajadoresEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public long insertarHTrabajadores(HorarioPersonal horarioPersonal) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HorariosPersonalEntry.HOR_CODI_IN20,horarioPersonal.getHor_codi_in20());
        contentValues.put(HorariosPersonalEntry.USU_CODI_IN20,horarioPersonal.getUsu_codi_in20());
        contentValues.put(HorariosPersonalEntry.DIASEMANA,horarioPersonal.getDiasemana());
        contentValues.put(HorariosPersonalEntry.HINICIO,horarioPersonal.getHinicio());
        contentValues.put(HorariosPersonalEntry.HREFRIGERIO,horarioPersonal.getHrefrigerio());
        contentValues.put(HorariosPersonalEntry.HSALIDA,horarioPersonal.getHsalida());
        long resultado = sqLiteDatabase.insert(HorariosPersonalEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }
}
