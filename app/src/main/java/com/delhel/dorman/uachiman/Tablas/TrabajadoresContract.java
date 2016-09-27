package com.delhel.dorman.uachiman.Tablas;
import android.provider.BaseColumns;

/**
 * Created by Usuario on 01/09/2016.
 */
public class TrabajadoresContract {

  public static abstract class TrabajadoresEntry implements BaseColumns{

      public static final String TABLE_NAME ="trabajadores";
      public static final String USU_CODI_IN20 = "usu_codi_in20";
      public static final String USU_APEL_CH100 = "usu_apel_ch100";
      public static final String USU_NOMB_CH100 ="usu_nomb_ch100";
      public static final String USU_DOCU_CH20 ="usu_docu_ch20";
      public static final String USU_NUMC_VC20 ="usu_numc_vc20";
      public static final String USU_MOVI_VC15 ="usu_movi_vc15";
      public static final String USU_DIRE_VC120 ="usu_dire_vc120";
      public static final String USU_CORR_VC100 = "usu_corr_vc100";
      public static final String USU_TSAN_VC15 = "usu_tsan_vc15";
      public static final String USU_FOTO_LONG = "usu_foto_long";
      public static final String PERF_DETA_VC200 = "perf_deta_vc200";
      public static final String CON_TELE_VC15 = "con_tele_vc15";
      public static final String CON_NOME_VC60 = "con_nome_vc60";

  }

}
