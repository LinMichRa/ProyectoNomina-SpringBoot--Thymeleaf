package com.linda.BackendNomina.operaciones;

import java.util.List;

import com.linda.BackendNomina.modelo.Empleado;

public interface OperacionesEmpleado {
    public List<Empleado> listarEmpleados();

    public Empleado guardarEmpleado(Empleado empleado);

    public Empleado modificarEmpleado(Empleado empleado);

}
