package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class Tabla_depa_con_encargo {

    public static  abstract class DepaconEncargoEntry implements BaseColumns {

        public static final String NOM_TABL_ENCARGOS = "Unidades_con_encargo";
        public static final String ID_ENCARGO = "id_encargo";
        public static final String CANTIDAD = "cantidad";
        public static final String UNIDAD_NUMI = "uni_numi_vc20";
        public static final String COD_UNIDAD = "uni_codi_in20";
        public static final String COD_ENDTIDAD = "ent_codi_in20";
        public static final String UNIDAD_NOMB = "usu_nomb_ch100";
        public static final String UNIDAD_APELL = "usu_apel_ch100";
        public static final String DNI_USUARIO = "usu_docu_ch20";
        public static final String FOTO_USUARIO = "usu_foto_long";
        public static final String COD_USUARIO = "usu_codi_in20";


    }

}
