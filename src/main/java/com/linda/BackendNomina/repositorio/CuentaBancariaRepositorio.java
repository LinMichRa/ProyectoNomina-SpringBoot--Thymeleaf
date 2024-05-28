package com.linda.BackendNomina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linda.BackendNomina.modelo.CuentaBancaria;

@Repository
public interface CuentaBancariaRepositorio extends JpaRepository<CuentaBancaria, Integer>{
    public CuentaBancaria findByNombre(String nombre);

    public CuentaBancaria findByNombreAndNumero(String nombre, Integer numero);
    
}
