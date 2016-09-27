package com.delhel.dorman.uachiman.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.delhel.dorman.uachiman.Clases.Entidades;
import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.DB.MySqlOpenHelper;
import com.delhel.dorman.uachiman.Interface.Login;
import com.delhel.dorman.uachiman.Tablas.PuertasContract;

import com.delhel.dorman.uachiman.Tablas.EntidadesContract.EntidadesEntry;
import com.delhel.dorman.uachiman.Tablas.PuertasContract;
import com.delhel.dorman.uachiman.Tablas.UnidadInmobiliariaContract;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 15/08/2016.
 */
public class Dlogin implements Login {

    Context context;

    public Dlogin(Context context) {
        this.context = context;
    }

    @Override
    public long insertarEntidad(Entidades entidades) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntidadesEntry.ENT_CODI_IN20,entidades.getEnt_codi_in20());
        contentValues.put(EntidadesEntry.ENT_NOMB_VC200,entidades.getEnt_nomb_vc200());
        long resultado = sqLiteDatabase.insert(EntidadesEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public long insertarPuertas(Puertas puertas) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PuertasContract.PuertasEntry.PUE_CODI_IN20, puertas.getPue_codi_in20());
        contentValues.put(PuertasContract.PuertasEntry.ENT_CODI_IN20, puertas.getEnt_codi_in20());
        contentValues.put(PuertasContract.PuertasEntry.PUE_NOMB_VC90, puertas.getPue_nomb_vc90());
        long resultado = sqLiteDatabase.insert(PuertasContract.PuertasEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return resultado;
    }

    @Override
    public List<Entidades> ListarEntidades() {

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

        List<Entidades> enti = new ArrayList<Entidades>();
        String sql = "SELECT * FROM "+EntidadesEntry.TABLE_NAME;

        Entidades ent2 = new Entidades();
        ent2.setEnt_codi_in20(0);
        ent2.setEnt_nomb_vc200("Seleccione Entidad");
        enti.add(ent2);

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Entidades ent = new Entidades();
                ent.setEnt_codi_in20(cursor.getInt(0));
                ent.setEnt_nomb_vc200(cursor.getString(1));
                enti.add(ent);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return enti;
    }

    @Override
    public List<Puertas> BuscarPuertas(int entidad) {

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();

        List<Puertas> Listpuertas = new ArrayList<>();

        String sql = "select " + PuertasContract.PuertasEntry.PUE_CODI_IN20 + ", " + PuertasContract.PuertasEntry.PUE_NOMB_VC90;
        sql += " from " + PuertasContract.PuertasEntry.TABLE_NAME + " where " + PuertasContract.PuertasEntry.ENT_CODI_IN20 + "=" + entidad;

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
    public void eliminarTable(String tabla) {
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = mySqlOpenHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+tabla);
    }
}
