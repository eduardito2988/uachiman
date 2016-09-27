package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class Tabla_encargos {

    public static  abstract class EncargosEntry implements BaseColumns {

        public static final String TABLA_ENCARGOS = "new_encargos";
        public static final String CODIGO_ENCARGOS = "enc_codi_in20";
        public static final String CODIGO_ENTIDAD = "ent_codi_in20";
        public static final String DETALLE_ENCARGOS = "enc_deta_vc200";
        public static final String CODIGO_USUARIO = "usu_codi_in20";
        public static final String FECHA_REGISTRO = "enc_ingr_dati";
        public static final String FECHA_ENTRAGA = "enc_entr_dati";
        public static final String TIPO_ENCARGO = "ten_codi_in20";
        public static final String CODIGO_UNIDAD = "uni_codi_in20";
        public static final String PER_INGRESADO = "per_ingr_in20";
        public static final String PER_ENCARGADO = "enc_upos_in20";
        public static final  String COD_PUERTA = "pue_codi_in20";
        public static final  String ENC_CONS = "enc_cons_ch01";

    }

}
