package com.linda.BackendNomina.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cuentasBancarias")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable =false)
    private int id; //numero de la cuenta

    @Column(name="nombre")
    private String nombre;

    @Column(name="numero")
    private int numero;

    public CuentaBancaria(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }
}
