package com.delhel.dorman.uachiman.Clases;

/**
 * Created by Usuario on 15/08/2016.
 */
public class Puertas {

    int pue_codi_in20;
    int ent_codi_in20;
    String pue_nomb_vc90;

    public int getEnt_codi_in20() {
        return ent_codi_in20;
    }

    public void setEnt_codi_in20(int ent_codi_in20) {
        this.ent_codi_in20 = ent_codi_in20;
    }

    public int getPue_codi_in20() {
        return pue_codi_in20;
    }

    public void setPue_codi_in20(int pue_codi_in20) {
        this.pue_codi_in20 = pue_codi_in20;
    }

    public String getPue_nomb_vc90() {
        return pue_nomb_vc90;
    }

    public void setPue_nomb_vc90(String pue_nomb_vc90) {
        this.pue_nomb_vc90 = pue_nomb_vc90;
    }

    @Override
    public String toString() {
       return pue_nomb_vc90;
    }
}
