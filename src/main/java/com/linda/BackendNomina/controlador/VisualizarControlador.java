package com.linda.BackendNomina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;


@Controller
public class VisualizarControlador {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @GetMapping("/VisualizarEmpleado/{id}")
    public String mostrarInformacionEmpleado(@PathVariable("id") int idEmpleados, Model modelo) {
        Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
        modelo.addAttribute("empleado",empleado);
        return "visualizarEmpleado";
    }
    
}
