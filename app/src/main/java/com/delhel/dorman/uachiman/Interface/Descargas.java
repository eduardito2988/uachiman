package com.delhel.dorman.uachiman.Interface;

import com.delhel.dorman.uachiman.Clases.HorarioPersonal;
import com.delhel.dorman.uachiman.Clases.HorariosConfig;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Clases.Puertas;
import com.delhel.dorman.uachiman.Clases.TiemposEntidad;
import com.delhel.dorman.uachiman.Clases.Trabajadores;
import com.delhel.dorman.uachiman.Clases.UnidadInmobiliaria;

import java.util.List;

/**
 * Created by Usuario on 15/08/2016.
 */
public interface Descargas {

    public long insertarUnidades(UnidadInmobiliaria unidadInmobiliaria);
    public long insertarTipoentida(Modelo_tipo_encargo modelo_tipo_encargo);
    public long insertarTiemposEntidad(TiemposEntidad tiemposEntidad);
    public long insertarHorarios(HorariosConfig horariosConfig);
    public long insetarTrabajador(Trabajadores trabajadores);
    public long insertarHTrabajadores(HorarioPersonal horarioPersonal);
    public void eliminarTable(String tabla);
    public List<Puertas> getPuertas(int entidad);


}
