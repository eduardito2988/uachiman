package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 24/08/2016.
 */
public class HorariosContract {

    public static abstract class HorariosEntry implements BaseColumns{

        public static final String TABLE_NAME ="config_horarios";
        public static final String CODCONFIG_HORARIO = "codconfig_horario";
        public static final String DIASEMANA = "dia_semana";
        public static final String HINICIO = "inicio";
        public static final String HFINAL = "hasta";
        public static final String DESCRIPCION = "descripcion";

    }
}
