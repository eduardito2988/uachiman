package com.delhel.dorman.uachiman.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.delhel.dorman.uachiman.Tablas.Tabla_depa_con_encargo;
import com.delhel.dorman.uachiman.Tablas.Tabla_detalle_encargos;
import com.delhel.dorman.uachiman.Tablas.Tabla_tipo_encargos;


import com.delhel.dorman.uachiman.Tablas.EntidadesContract.EntidadesEntry;
import com.delhel.dorman.uachiman.Tablas.HorariosContract.HorariosEntry;
import com.delhel.dorman.uachiman.Tablas.HorariosPersonalContract.HorariosPersonalEntry;
import com.delhel.dorman.uachiman.Tablas.PuertasContract.PuertasEntry;
import com.delhel.dorman.uachiman.Tablas.TiemposEntidadContract.TiemposEntidadEntry;
import com.delhel.dorman.uachiman.Tablas.TrabajadoresContract.TrabajadoresEntry;
import com.delhel.dorman.uachiman.Tablas.UnidadInmobiliariaContract.UnidadInmobiliariaEntry;



/**
 * Created by Usuario on 25/07/2016.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper{

    private static final String nameBD = "myDB.db";
    private static final int versionDB = 17;


    public MySqlOpenHelper(Context context) {
        super(context, nameBD, null, versionDB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("CREATE TABLE "+ Tabla_depa_con_encargo.DepaconEncargoEntry.NOM_TABL_ENCARGOS + " ("
                + Tabla_depa_con_encargo.DepaconEncargoEntry.ID_ENCARGO + " INTEGER PRIMARY KEY  AUTOINCREMENT ,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.CANTIDAD + " INTEGER NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NUMI + " INTEGER NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.COD_UNIDAD + " INTEGER NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.COD_ENDTIDAD + " INTEGER NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_NOMB + " varchar NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.UNIDAD_APELL + " varchar NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.DNI_USUARIO + " varchar NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.FOTO_USUARIO + " varchar NOT NULL,"
                + Tabla_depa_con_encargo.DepaconEncargoEntry.COD_USUARIO + " varchar NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE "+ Tabla_tipo_encargos.TipoEncargoEntry.NOM_TABLA_TIPO + " ("
               + Tabla_tipo_encargos.TipoEncargoEntry.COD_TIP_ENCARGO + "  INTEGER PRIMARY KEY  AUTOINCREMENT,"
                + Tabla_tipo_encargos.TipoEncargoEntry.DET_TIP_ENCARGO + "  varchar NOT NULL);");


        sqLiteDatabase.execSQL("CREATE TABLE "+ Tabla_detalle_encargos.EncargosDetalleEntry.TABLA_DET_ENCARGO + " ("
                + Tabla_detalle_encargos.EncargosDetalleEntry.ENC_CODIGO + "  INTEGER PRIMARY KEY  AUTOINCREMENT,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.USU_CODIGO + "  INTEGER NOT NULL,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.COD_UNIDAD + "   INTEGER NOT NULL,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.ENC_ENTIDAD + "  varchar NOT NULL,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.FECH_INGRESO + "  varchar NOT NULL,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.FECH_ENTREGADO + "  varchar NOT NULL,"
                + Tabla_detalle_encargos.EncargosDetalleEntry.TIPO_ENCARGO + "  varchar NOT NULL);");


        sqLiteDatabase.execSQL("CREATE TABLE " + UnidadInmobiliariaEntry.TABLE_NAME + " ("
                + UnidadInmobiliariaEntry.UNI_CODI_IN20 + " INTEGER NOT NULL,"
                + UnidadInmobiliariaEntry.UNI_NUMI_VC20 + " varchar NOT NULL,"
                + UnidadInmobiliariaEntry.USU_APEL_CH100 + " varchar NOT NULL,"
                + UnidadInmobiliariaEntry.USU_NOMB_CH100 + " varchar NOT NULL,"
                + UnidadInmobiliariaEntry.UNI_TELF_VC12 + " INTEGER NOT NULL,"
                + UnidadInmobiliariaEntry.USU_CODI_IN20 + " INTEGER NOT NULL,"
                + UnidadInmobiliariaEntry.USU_CORR_VC100 + " varchar NOT NULL)");


        sqLiteDatabase.execSQL("CREATE TABLE " + EntidadesEntry.TABLE_NAME + " ("
                + EntidadesEntry.ENT_CODI_IN20 + " INTEGER NOT NULL,"
                + EntidadesEntry.ENT_NOMB_VC200 + " varchar NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + PuertasEntry.TABLE_NAME + " ("
                + PuertasEntry.PUE_CODI_IN20 + " INTEGER NOT NULL,"
                + PuertasEntry.ENT_CODI_IN20 + " INTEGER NOT NULL,"
                + PuertasEntry.PUE_NOMB_VC90 + " varchar NOT NULL)");


        sqLiteDatabase.execSQL("CREATE TABLE " + TiemposEntidadEntry.TABLE_NAME + " ("
                + TiemposEntidadEntry.COD_TIEMPO_ENTIDAD + " INTEGER primary key,"
                + TiemposEntidadEntry.TDELIVERY + " TIME NOT NULL,"
                + TiemposEntidadEntry.TCORREDORES + " TIME NOT NULL,"
                + TiemposEntidadEntry.TSERVICIOS + " TIME NOT NULL,"
                + TiemposEntidadEntry.TCAPTCHA + " TIME NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + HorariosEntry.TABLE_NAME + " ("
                + HorariosEntry.CODCONFIG_HORARIO + " INTEGER primary key,"
                + HorariosEntry.DIASEMANA + " varchar NOT NULL,"
                + HorariosEntry.HINICIO + " TIME NOT NULL,"
                + HorariosEntry.HFINAL + " TIME NOT NULL,"
                + HorariosEntry.DESCRIPCION + " varchar NOT NULL)");


        /* Trabajadores */

        sqLiteDatabase.execSQL("CREATE TABLE " + TrabajadoresEntry.TABLE_NAME + " ("
                + TrabajadoresEntry.USU_CODI_IN20 + " INTEGER NOT NULL,"
                + TrabajadoresEntry.USU_APEL_CH100 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_NOMB_CH100 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_DOCU_CH20 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_NUMC_VC20 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_MOVI_VC15 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_DIRE_VC120 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_CORR_VC100 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_TSAN_VC15 + " varchar NOT NULL,"
                + TrabajadoresEntry.USU_FOTO_LONG + " varchar NOT NULL,"
                + TrabajadoresEntry.PERF_DETA_VC200 + " varchar NOT NULL,"
                + TrabajadoresEntry.CON_TELE_VC15 + " varchar NOT NULL,"
                + TrabajadoresEntry.CON_NOME_VC60 + " varchar NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + HorariosPersonalEntry.TABLE_NAME + " ("
                + HorariosPersonalEntry.HOR_CODI_IN20 + " INTEGER NOT NULL,"
                + HorariosPersonalEntry.USU_CODI_IN20 + " INTEGER NOT NULL,"
                + HorariosPersonalEntry.DIASEMANA + " varchar NOT NULL,"
                + HorariosPersonalEntry.HINICIO + " TIME NOT NULL,"
                + HorariosPersonalEntry.HREFRIGERIO + " TIME NOT NULL,"
                + HorariosPersonalEntry.HSALIDA + " TIME NOT NULL)");



        /*****************************/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* Log.d("Persistencia","onUpdate-DB");

        String sql = "create table producto (id_producto integer not null, id_talla integer not null, id_categoria integer not null, precio NUMERIC(10,2) not null, descripcion string not null , imagen string not null)";
        db.execSQL(sql);*/

    }

}
