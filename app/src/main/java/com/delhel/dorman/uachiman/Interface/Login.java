package com.delhel.dorman.uachiman.Interface;

import com.delhel.dorman.uachiman.Clases.Entidades;
import com.delhel.dorman.uachiman.Clases.Puertas;


import java.util.List;

/**
 * Created by Usuario on 15/08/2016.
 */
public interface Login {

    public long insertarEntidad(Entidades entidades);
    public long insertarPuertas(Puertas puertas);
    public List<Entidades> ListarEntidades();
    public List<Puertas> BuscarPuertas(int cod_entidad);
    public void eliminarTable(String tabla);

}
