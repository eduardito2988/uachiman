package com.delhel.dorman.uachiman.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.delhel.dorman.uachiman.Clases.HorariosConfig;
import com.delhel.dorman.uachiman.DB.MySqlOpenHelper;
import com.delhel.dorman.uachiman.Interface.Horarios;
import com.delhel.dorman.uachiman.Clases.TiemposEntidad;
import com.delhel.dorman.uachiman.Tablas.HorariosContract.HorariosEntry;
import com.delhel.dorman.uachiman.Tablas.TiemposEntidadContract.TiemposEntidadEntry;


/**
 * Created by Usuario on 24/08/2016.
 */
public class Dhorarios implements Horarios {

    Context context;
    public Dhorarios(Context context) {
        this.context = context;

    }

    @Override
    public long insertarHorarios(HorariosConfig horariosConfig) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HorariosEntry.DIASEMANA,horariosConfig.getDiasemana());
        contentValues.put(HorariosEntry.HINICIO,horariosConfig.getHinicio());
        contentValues.put(HorariosEntry.HFINAL,horariosConfig.getHfinal());
        contentValues.put(HorariosEntry.DESCRIPCION,horariosConfig.getDescripcion());
        long resultado = sqLiteDatabase.insert(HorariosEntry.TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return 0;
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
    public void eliminarTable(String tabla) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + tabla);
        sqLiteDatabase.close();
    }
}
