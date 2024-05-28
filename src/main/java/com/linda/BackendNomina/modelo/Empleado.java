package com.linda.BackendNomina.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="empleados")
public class Empleado implements Serializable{
    @Id
    @Column(name="cedula", nullable =false)
    private Integer cedula;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="salario")
    private Integer salario;
    
    @Column(name="Fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name="Fecha_Retiro")
    private LocalDate fechaSalida;

    //relaciones

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id", referencedColumnName = "id")
    private Cargo cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    private Contrato contrato;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuenta_bancaria_id", referencedColumnName = "id")
    private CuentaBancaria cuentaBancaria;

    // Añadir los campos para Devengados y Descuentos

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "devengados_id", referencedColumnName = "id")
    private Devengado devengados;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "descuentos_id", referencedColumnName = "id")
    private Descuento descuentos;
}
