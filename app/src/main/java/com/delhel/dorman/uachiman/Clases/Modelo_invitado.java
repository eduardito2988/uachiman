package com.delhel.dorman.uachiman.Clases;

/**
 * Created by Usuario on 20/07/2016.
 */
public class Modelo_invitado {
    String codigo;
    String nombre,dni,gerencia;


    public Modelo_invitado(String codigo, String nombre, String dni, String gerencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dni = dni;
        this.gerencia = gerencia;
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
