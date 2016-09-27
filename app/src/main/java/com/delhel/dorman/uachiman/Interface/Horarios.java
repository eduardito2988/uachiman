package com.delhel.dorman.uachiman.Interface;

import com.delhel.dorman.uachiman.Clases.HorariosConfig;
import com.delhel.dorman.uachiman.Clases.TiemposEntidad;

/**
 * Created by Usuario on 24/08/2016.
 */
public interface Horarios {
    public long insertarHorarios(HorariosConfig horarios);
    public long insertarTiemposEntidad(TiemposEntidad tiemposEntidad);
    public void eliminarTable(String tabla);
}
