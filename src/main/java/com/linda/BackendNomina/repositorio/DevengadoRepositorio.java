package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Devengado;

@Repository
public interface DevengadoRepositorio extends JpaRepository<Devengado,Integer>{

    
}
