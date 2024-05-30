package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Nomina;

@Repository
public interface NominaRepositorio extends JpaRepository<Nomina,Integer>{

    public Nomina findByEmpleadoCedula(Integer cedula);
}
