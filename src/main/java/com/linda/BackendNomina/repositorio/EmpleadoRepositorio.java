package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Empleado;

@Repository
public interface EmpleadoRepositorio extends JpaRepository<Empleado,Integer>{
    public Empleado findByCedula(Integer cedula);
}
