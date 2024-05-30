package com.linda.BackendNomina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.linda.BackendNomina.modelo.Descuento;
import com.linda.BackendNomina.modelo.Devengado;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.modelo.Nomina;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;
import com.linda.BackendNomina.repositorio.NominaRepositorio;


@Controller
public class VisualizarControlador {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private NominaRepositorio nominaRepositorio;

    @GetMapping("/VisualizarEmpleado/{id}")
public String mostrarInformacionEmpleado(@PathVariable("id") int idEmpleados, Model modelo) {
    Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
    
    if (empleado != null) {
        Devengado devengados = empleado.getDevengados();
        Descuento descuentos = empleado.getDescuentos();
        
        // Cálculo del total de devengados
        double totalDevengados = devengados.getSalarioDevengado() + devengados.getTransporte() + devengados.getGastos() +
                                devengados.getPrimas() + devengados.getSobresueldo() + devengados.getViaticos() +
                                devengados.getComisiones() + devengados.getGananciaOrdinarias() + devengados.getGananciaDominicales();
        
        // Cálculo del total de descuentos
        double totalDescuentos = descuentos.getEps() + descuentos.getFondoSol() + descuentos.getFondoEmp() +
                                descuentos.getPension() + descuentos.getBanco();
        
        // Cálculo del salario neto y salario bruto
        double salarioNeto = totalDevengados - totalDescuentos;
        double salarioBruto = devengados.getSalarioDevengado() + devengados.getTransporte() + devengados.getGastos() +
                            devengados.getPrimas() + devengados.getSobresueldo() + devengados.getViaticos() +
                            devengados.getComisiones()+devengados.getGananciaDominicales()+devengados.getGananciaOrdinarias();
        
        modelo.addAttribute("salarioNeto", salarioNeto);
        modelo.addAttribute("salarioBruto", salarioBruto);

        Nomina nomina = new Nomina();
        nomina.setSalarioBruto((int) salarioBruto);
        nomina.setSalarioNeto((int) salarioNeto);
        nomina.setEmpleado(empleado);

        nominaRepositorio.save(nomina);
    }
    
    modelo.addAttribute("empleado", empleado);
    return "visualizarEmpleado";
}

    
}
