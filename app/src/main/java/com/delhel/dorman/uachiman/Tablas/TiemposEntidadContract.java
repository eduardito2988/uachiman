package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 24/08/2016.
 */
public class TiemposEntidadContract {

    public static abstract class TiemposEntidadEntry implements BaseColumns{

        public static final String TABLE_NAME ="mdi_entidadconfiguracion";
        public static final String COD_TIEMPO_ENTIDAD = "cod_tiempo_entidad";
        public static final String TDELIVERY = "tdelivery";
        public static final String TCORREDORES = "tcorredores";
        public static final String TSERVICIOS = "tservicios";
        public static final String TCAPTCHA = "tcaptcha";
        public static final String DIA_ENCARGO ="dia_encargo";

    }
}
