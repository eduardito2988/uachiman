package com.delhel.dorman.uachiman.Interface;


import com.delhel.dorman.uachiman.Clases.Modelo_departamento_con_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Clases.Modelo_detalle_unididad_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_tipo_encargo;
import com.delhel.dorman.uachiman.Clases.Modelo_perfil_trabajadores;

import java.util.List;

/**
 * Created by Usuario on 25/07/2016.
 */


public interface MyDao {


    public List<Modelo_departamentos> llenarCombo_departamentos(String dato);
    public List<Modelo_tipo_encargo> llenar_combo_tipo_encargo();
    public List<Modelo_perfil_trabajadores> listado_trabajadores();
    public List<Modelo_encargo> listar_encargo();// todo los encargos
    public long Insertar_Encargos(Modelo_encargo objeto );

    public List<Modelo_departamentos> listar_entidades_con_encargo(int dato);

    public void DescargarEncargo(int dato);
    public void DescargarDetalleEncargo(String dato);
    public void eliminarTable(String tabla);
    public List<Modelo_departamento_con_encargo> listar_departamentos_con_encargo();
    public List<Modelo_detalle_unididad_encargo> listar_encargo_de_entidad(String num);




}


