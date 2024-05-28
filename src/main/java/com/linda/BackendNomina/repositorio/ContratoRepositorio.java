package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Contrato;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato,Integer>{
    public Contrato findByNombre(String nombre);
}
