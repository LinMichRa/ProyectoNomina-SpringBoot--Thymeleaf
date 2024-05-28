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
@Table(name="devengados")
public class Devengado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable =false)
    private int id;

    @Column(name="salarioDevengado")
    private double salarioDevengado;

    @Column(name="SubTransporte")
    private double transporte;

    @Column(name="GastosRep")
    private int gastos;

    @Column(name="sobresueldo")
    private int sobresueldo;

    @Column(name="viaticos")
    private int viaticos;

    @Column(name="comisiones")
    private int comisiones;

    @Column(name="PrimasPagosExtras")
    private double primas;

    @Column(name="HorasExtrasDiurnasOrdinarias")
    private double HEDO;

    @Column(name="HorasExtrasNocturnasOrdinarias")
    private double HENO;

    @Column(name="HorasExtrasDiurnasDominicalesFestivas")
    private double HEDDF;

    @Column(name="HorasExtrasNocturnasDominicalesFestivas")
    private double HENDF;

    private double CHEDO;

    private double CHENO;

    private double CHEDDF;

    private double CHENDF;

    private double GananciaOrdinarias;

    private double GananciaDominicales;

    private int diasTrabajados;
}
