package com.linda.BackendNomina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linda.BackendNomina.modelo.Descuento;
import com.linda.BackendNomina.modelo.Devengado;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.repositorio.DevengadoRepositorio;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;



@Controller
public class EditarControlador {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private DevengadoRepositorio devengadoRepositorio;

    @GetMapping("/EditarEmpleado/{id}/menu")
    public String mostrarOpciones(@PathVariable("id") int idEmpleados,Model modelo) {
        Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
        modelo.addAttribute("empleado",empleado);
        return "opcionesEditar";
    }
    
    @GetMapping("/EditarEmpleado/{id}/InformacionBasica")
    public String editarEmpleado(@PathVariable("id") int idEmpleados,Model modelo) {
        Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
        modelo.addAttribute("empleado",empleado);
        return "registroEmpleado";
    }

    @GetMapping("/EditarEmpleado/{id}/Devengados")
    public String editarDevengados(@PathVariable("id") int idEmpleados,Model modelo) {
        Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
        modelo.addAttribute("empleado",empleado);
        return "AgregarDevengados";
    }

    @PostMapping("/registroDevengados/{id}/devengado")
    public String registrarDevengados(@RequestParam("empleadoId") Integer idEmpleado,@ModelAttribute Empleado empleado) {
        Empleado empleadoExistente = empleadoRepositorio.findByCedula(idEmpleado);

        if (empleadoExistente != null) {
            empleadoExistente.setDevengados(empleado.getDevengados());
            int salario = empleadoExistente.getSalario();
            int diasTrabajados = empleadoExistente.getDevengados().getDiasTrabajados();

            // Cálculos de Horas Extras Ordinarias
            double CHEDO = (salario / 240.0) * empleadoExistente.getDevengados().getHEDO() * 1.25;
            double CHENO = (salario / 240.0) * empleadoExistente.getDevengados().getHENO() * 1.75;
            double GananciaOrdinarias = CHEDO + CHENO;

            // Cálculos de Horas Extras Dominicales
            double CHEDDF = (salario / 240.0) * empleadoExistente.getDevengados().getHEDDF() * 2;
            double CHENDF = (salario / 240.0) * empleadoExistente.getDevengados().getHENDF() * 2.5;
            double GananciaDominicales = CHEDDF + CHENDF;

            // Calcular salario devengado
            double salarioDevengado = (salario / 30.0) * diasTrabajados;

            // Calcular subsidio de transporte
            double subTransporte = (162000 / 30.0) * diasTrabajados;

            // Calcular primas
            double totalGananciasExtras = GananciaOrdinarias + GananciaDominicales;
            double primas = ((salarioDevengado + subTransporte + totalGananciasExtras) * diasTrabajados) / 360.0;

            // Actualizar el devengado con los cálculos realizados
            empleadoExistente.getDevengados().setGananciaOrdinarias(GananciaOrdinarias);
            empleadoExistente.getDevengados().setGananciaDominicales(GananciaDominicales);
            empleadoExistente.getDevengados().setCHEDO(CHEDO);
            empleadoExistente.getDevengados().setCHENO(CHENO);
            empleadoExistente.getDevengados().setCHEDDF(CHEDDF);
            empleadoExistente.getDevengados().setCHENDF(CHENDF);
            empleadoExistente.getDevengados().setSalarioDevengado(salarioDevengado);
            empleadoExistente.getDevengados().setTransporte(subTransporte);
            empleadoExistente.getDevengados().setPrimas(primas);

            // Guardar el devengado y asociarlo al empleadoExistente
            empleadoExistente.setDevengados(empleadoExistente.getDevengados());
            empleadoRepositorio.save(empleadoExistente);
        }

        return "redirect:/inicio";
    }

    @GetMapping("/EditarEmpleado/{id}/Deducciones")
    public String editarDeducciones(@PathVariable("id") int idEmpleados,Model modelo) {
        Empleado empleado = empleadoRepositorio.findByCedula(idEmpleados);
        modelo.addAttribute("empleado",empleado);
        modelo.addAttribute("deducciones",new Descuento());
        return "AgregarDeducciones";
    }

    @PostMapping("/EditarEmpleado/{id}/Deducciones")
public String registrarDeducciones(@RequestParam("empleadoId") Integer idEmpleado, @ModelAttribute Empleado empleado) {
    Empleado empleadoExistente = empleadoRepositorio.findByCedula(idEmpleado);

    if (empleadoExistente != null) {
        empleadoExistente.setDescuentos(empleado.getDescuentos());
        Devengado devengadoExistente = empleadoExistente.getDevengados();
        Descuento deducciones = empleadoExistente.getDescuentos() != null ? empleadoExistente.getDescuentos() : new Descuento();

        if (devengadoExistente != null) {
            double salarioDevengado = devengadoExistente.getSalarioDevengado();
            double gananciaOrdinarias = devengadoExistente.getGananciaOrdinarias();
            double gananciaDominicales = devengadoExistente.getGananciaDominicales();
            double comisiones = devengadoExistente.getComisiones();
            
            // Calcular la ganancia total de horas extras
            double gananciaTotalHorasExtras = gananciaOrdinarias + gananciaDominicales;

            // Calcular EPS
            double eps = (salarioDevengado + gananciaTotalHorasExtras + comisiones) * 0.04;
            deducciones.setEps(eps);
            deducciones.setPension(eps);
            
            //Calcular Fondo Solidario
            if (salarioDevengado > 5200000) {
                double fondoSolidario = salarioDevengado * 0.01;
                deducciones.setFondoSol(fondoSolidario);
            } else {
                deducciones.setFondoSol(0);
            }

            //Calcular Fondo Empleados
            double porcentajeFondoEmpleados = empleado.getDescuentos().getPorcentaje();
            double fondoEmpleados = salarioDevengado * (porcentajeFondoEmpleados / 100);
            deducciones.setFondoEmp(fondoEmpleados);

            // Asignar las variables al empleado
            empleadoExistente.setDescuentos(deducciones);
            empleadoRepositorio.save(empleadoExistente);

        } else {
            System.out.println("No se encontraron devengados para el empleado.");
        }
    } else {
        System.out.println("Empleado no encontrado.");
    }

    return "redirect:/inicio";
}
    
}
