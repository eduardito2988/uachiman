package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class Tabla_tipo_encargos {

    public static  abstract class TipoEncargoEntry implements BaseColumns {

        public static final String NOM_TABLA_TIPO = "Tipo_encargo";
        public static final String COD_TIP_ENCARGO = "ten_codi_in20";
        public static final String DET_TIP_ENCARGO = "ten_deta_vc200";

    }

}
