package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Cargo;

@Repository
public interface CargoRepositorio extends JpaRepository<Cargo,Integer> {
    public Cargo findByNombre(String nombre);
}
