package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class Tabla_detalle_encargos {

    public static  abstract class EncargosDetalleEntry implements BaseColumns {

        public static final String TABLA_DET_ENCARGO = "Detalle_Encargos";
        public static final String USU_CODIGO = "usu_codi_in20";
        public static final String ENC_CODIGO = "enc_codi_in20";
        public static final String ENC_ENTIDAD = "ent_codi_in20";
        public static final String ENC_DETALLE = "enc_deta_vc200";
        public static final String FECH_INGRESO = "enc_ingr_dati";
        public static final String FECH_ENTREGADO = "enc_entr_dati";
        public static final String COD_UNIDAD = "uni_codi_in20";
        public static final String COD_USUARIO = "usu_codi_in20";
        public static final String TIPO_ENCARGO = "ten_deta_vc200";
        public static final String NUM_UNIDAD = "uni_numi_vc20";
        public static final String NOM_USUARIO = "usu_nomb_ch100";
        public static final String APE_USUARIO = "usu_apel_ch100";
        public static final String PUERTA="pue_nomb_vc90";



    }

}
