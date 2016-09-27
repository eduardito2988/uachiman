package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 02/09/2016.
 */
public class HorariosPersonalContract {

    public static abstract class HorariosPersonalEntry implements BaseColumns {

        public static final String TABLE_NAME ="horario_personal";
        public static final String HOR_CODI_IN20 ="hor_codi_in20";
        public static final String USU_CODI_IN20 ="usu_codi_in20";
        public static final String DIASEMANA ="diasemana";
        public static final String HINICIO ="hinicio";
        public static final String HREFRIGERIO ="hrefrigerio";
        public static final String HSALIDA ="hsalida";

    }

}
