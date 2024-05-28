package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Area;

@Repository
public interface AreaRepositorio extends JpaRepository<Area,Integer>{
    public Area findByNombre(String nombre);
}
