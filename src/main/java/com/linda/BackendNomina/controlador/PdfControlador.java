package com.linda.BackendNomina.controlador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;


@Controller
public class PdfControlador {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @GetMapping("/GenerarPdfNomina/{id}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Integer id) throws DocumentException {
        Empleado empleado = empleadoRepositorio.findByCedula(id);

        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Empleado: " + empleado.getNombre() + " " + empleado.getApellido()));
        document.add(new Paragraph("Cedula: " + empleado.getCedula()));
        document.add(new Paragraph("Salario: " + empleado.getSalario()));
        document.add(new Paragraph("Dias Trabajados: " + empleado.getDevengados().getDiasTrabajados()));
        document.add(new Paragraph("Salario Devengado: " + empleado.getDevengados().getSalarioDevengado()));
        document.add(new Paragraph("Subsidio de Transporte: " + empleado.getDevengados().getTransporte()));
        document.add(new Paragraph("Prima: " + empleado.getDevengados().getPrimas()));

        document.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=nomina.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
}
