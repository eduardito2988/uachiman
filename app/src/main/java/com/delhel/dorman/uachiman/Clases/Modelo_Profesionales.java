package com.delhel.dorman.uachiman.Clases;

/**
 * Created by Usuario on 20/07/2016.
 */
public class Modelo_Profesionales {
    String codigo;
    String nombre,dni,gerencia,servicio;

    public Modelo_Profesionales(String codigo, String nombre, String gerencia, String dni, String servicio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.gerencia = gerencia;
        this.dni = dni;
        this.servicio = servicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }


    @Override
    public String toString() {
        return this.dni;
    }
}
