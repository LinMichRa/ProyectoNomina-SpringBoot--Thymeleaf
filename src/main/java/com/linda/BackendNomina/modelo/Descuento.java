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
@Table(name="descuentos")
public class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable =false)
    private int id;

    @Column(name="Eps_salud")
    private double eps;

    @Column(name="pension")
    private double pension;

    @Column(name="FdoSol")
    private double fondoSol;

    @Column(name="bancos")
    private int banco;

    @Column(name="FdoEmpleados")
    private double fondoEmp;

    private int porcentaje;
}
