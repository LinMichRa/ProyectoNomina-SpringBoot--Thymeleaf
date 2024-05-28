package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.Descuento;

@Repository
public interface DescuentoRepositorio extends JpaRepository<Descuento,Integer>{

    
}
