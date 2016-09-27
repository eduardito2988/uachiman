package com.delhel.dorman.uachiman.Clases;

/**
 * Created by Usuario on 16/08/2016.
 */
public class Modelo_lista_encargos {

    String nombre,tipo,fecha,mensaje,estado,codigo;

    public Modelo_lista_encargos(String nombre, String tipo, String fecha, String mensaje, String codigo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
