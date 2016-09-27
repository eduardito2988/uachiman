package com.delhel.dorman.uachiman.Clases.Acordeon;

/**
 * Created by Usuario on 16/09/2016.
 */
public class ChildrenEncargo {

    int cod_usuario;
    int cod_unidad;

    String unidad, usuario, cantida , foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getCod_unidad() {
        return cod_unidad;
    }

    public void setCod_unidad(int cod_unidad) {
        this.cod_unidad = cod_unidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCantida() {
        return cantida;
    }

    public void setCantida(String cantida) {
        this.cantida = cantida;
    }
}
