package com.linda.BackendNomina.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linda.BackendNomina.modelo.Cargo;
import com.linda.BackendNomina.modelo.CuentaBancaria;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.operaciones.OperacionesEmpleado;
import com.linda.BackendNomina.repositorio.CargoRepositorio;
import com.linda.BackendNomina.repositorio.CuentaBancariaRepositorio;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;

@Service
public class ServicioEmpleado implements OperacionesEmpleado{

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private CargoRepositorio cargoRepositorio;

    @Autowired
    private CuentaBancariaRepositorio cuentaBancariaRepositorio;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    @Override
    public Empleado modificarEmpleado(Empleado empleado) {
        return empleadoRepositorio.save(empleado);
    }

    public Empleado registrarEmpleado(Empleado empleado, String nombreCargo, String tipoCuenta) {
        // Verificar si el cargo existe, sino, crear uno nuevo
        Cargo cargo = cargoRepositorio.findByNombre(nombreCargo);
        if (cargo == null) {
            cargo = cargoRepositorio.save(new Cargo(nombreCargo));
        }
        empleado.setCargo(cargo);

        // Verificar si la cuenta bancaria existe, sino, crear una nueva
        CuentaBancaria cuentaBancaria = cuentaBancariaRepositorio.findByNombre(tipoCuenta);
        if (cuentaBancaria == null) {
            cuentaBancaria = cuentaBancariaRepositorio.save(new CuentaBancaria(tipoCuenta, empleado.getCuentaBancaria().getNumero()));
        }
        empleado.setCuentaBancaria(cuentaBancaria);

        return empleadoRepositorio.save(empleado);
    }
    
}
