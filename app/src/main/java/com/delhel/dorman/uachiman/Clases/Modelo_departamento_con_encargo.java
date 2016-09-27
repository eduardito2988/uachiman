package com.delhel.dorman.uachiman.Clases;

/**
 * Created by Usuario on 20/07/2016.
 */
public class Modelo_departamento_con_encargo {

    int id_encargo,cantidad,num_unidad,cod_entida,cod_usuario;
    String nombre,apellid,dni,uni_numi,foto;

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_encargo() {
        return id_encargo;
    }

    public void setId_encargo(int id_encargo) {
        this.id_encargo = id_encargo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNum_unidad() {
        return num_unidad;
    }

    public void setNum_unidad(int num_unidad) {
        this.num_unidad = num_unidad;
    }

    public int getCod_entida() {
        return cod_entida;
    }

    public void setCod_entida(int cod_entida) {
        this.cod_entida = cod_entida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUni_numi() {
        return uni_numi;
    }

    public void setUni_numi(String uni_numi) {
        this.uni_numi = uni_numi;
    }

    public String getApellid() {
        return apellid;
    }

    public void setApellid(String apellid) {
        this.apellid = apellid;
    }
}
