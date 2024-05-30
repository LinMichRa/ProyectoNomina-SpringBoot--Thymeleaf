package com.linda.BackendNomina.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linda.BackendNomina.modelo.Area;
import com.linda.BackendNomina.modelo.Cargo;
import com.linda.BackendNomina.modelo.Contrato;
import com.linda.BackendNomina.modelo.CuentaBancaria;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.operaciones.OperacionesEmpleado;
import com.linda.BackendNomina.repositorio.AreaRepositorio;
import com.linda.BackendNomina.repositorio.CargoRepositorio;
import com.linda.BackendNomina.repositorio.ContratoRepositorio;
import com.linda.BackendNomina.repositorio.CuentaBancariaRepositorio;
import com.linda.BackendNomina.servicios.ServicioEmpleado;




@Controller
public class EmpleadoControlador {
    @Autowired
    private OperacionesEmpleado operacionesEmpleado;

    @Autowired
    private AreaRepositorio areaRepositorio;

    @Autowired
    private CargoRepositorio cargoRepositorio;

    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Autowired
    private CuentaBancariaRepositorio cuentaRepositorio;
    
    @Autowired
    private ServicioEmpleado empleadoServicio;

    @GetMapping("/inicio")
    public String listarTodosLosEmpleados(Model modelo){
        List<Empleado> empleados = operacionesEmpleado.listarEmpleados();
        modelo.addAttribute("empleado",empleados);
        return "inicio";
    }

    @GetMapping("/registroEmpleado")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "registroEmpleado";
    }

    @PostMapping("/registroEmpleado")
public String registrarEmpleado(
    @RequestParam(required = false) String fechaIngreso,
    @RequestParam(required = false) String fechaSalida,
    @ModelAttribute Empleado empleado,
    BindingResult result) {

    // Obtenemos los datos del formulario
    String nombreCargo = empleado.getCargo().getNombre();
    String nombreArea = empleado.getArea().getNombre();
    String nombreContrato = empleado.getContrato().getNombre();
    String tipoCuenta = empleado.getCuentaBancaria().getNombre();
    int numeroCuenta = empleado.getCuentaBancaria().getNumero();

    // Buscar o crear el cargo
    Cargo cargo = cargoRepositorio.findByNombre(nombreCargo);
    if (cargo == null) {
        cargo = new Cargo();
        cargo.setNombre(nombreCargo);
        cargo = cargoRepositorio.save(cargo);
    }
    empleado.setCargo(cargo);

    // Buscar o crear el área
    Area area = areaRepositorio.findByNombre(nombreArea);
    if (area == null) {
        area = new Area();
        area.setNombre(nombreArea);
        area = areaRepositorio.save(area);
    }
    empleado.setArea(area);

    // Buscar o crear el contrato
    Contrato contrato = contratoRepositorio.findByNombre(nombreContrato);
    if (contrato == null) {
        contrato = new Contrato();
        contrato.setNombre(nombreContrato);
        contrato = contratoRepositorio.save(contrato);
    }
    empleado.setContrato(contrato);

    // Buscar o crear la cuenta bancaria
    CuentaBancaria cuentaBancaria = cuentaRepositorio.findByNombreAndNumero(tipoCuenta, numeroCuenta);
    if (cuentaBancaria == null) {
        cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setNombre(tipoCuenta);
        cuentaBancaria.setNumero(numeroCuenta);
        cuentaBancaria = cuentaRepositorio.save(cuentaBancaria);
    }
    empleado.setCuentaBancaria(cuentaBancaria);

    // Manejo de las fechas
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    if (fechaIngreso != null && !fechaIngreso.isEmpty()) {
        empleado.setFechaIngreso(LocalDate.parse(fechaIngreso, formatter));
    } else {
        empleado.setFechaIngreso(null);  // Asegura que fechaIngreso sea null si no se proporciona
    }

    if (fechaSalida != null && !fechaSalida.isEmpty()) {
        empleado.setFechaSalida(LocalDate.parse(fechaSalida, formatter));
    } else {
        empleado.setFechaSalida(null);  // Asegura que fechaSalida sea null si no se proporciona
    }

    // Guardar el empleado en la base de datos
    operacionesEmpleado.guardarEmpleado(empleado);

    return "redirect:/inicio";
}

}
