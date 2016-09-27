package com.delhel.dorman.uachiman.Tablas;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 15/08/2016.
 */
public class PuertasContract {


    public static abstract class PuertasEntry implements BaseColumns {

        public static final String TABLE_NAME ="puertas";
        public static final String ENT_CODI_IN20 = "ent_codi_in20";
        public static final String PUE_CODI_IN20 = "pue_codi_in20";
        public static final String PUE_NOMB_VC90 = "pue_nomb_vc90";

    }

}
