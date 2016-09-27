package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class Tabla_residentes {

    public static  abstract class ResidentesEntry implements BaseColumns {

        public static final String TABLA_RESIDENTE = "Tabla_residentes";
        public static final String CODIGO_USUARIO = "usu_codi_in20";
        public static final String NOMBRE_RESIDEN = "usu_nomb_ch100";
        public static final String APELL_RESIDEN = "usu_apel_ch100";

        public static final String DOC_RESIDETN = "usu_docu_ch20";
        public static final String FOTO_RESIDENT = "usu_foto_long";



    }

}
