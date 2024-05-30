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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.modelo.Nomina;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;
import com.linda.BackendNomina.repositorio.NominaRepositorio;

@Controller
public class PdfControlador {
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private NominaRepositorio nominaRepositorio;

    @GetMapping("/GenerarPdfNomina/{id}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Integer id) throws DocumentException {
        Empleado empleado = empleadoRepositorio.findByCedula(id);
        Nomina nomina = nominaRepositorio.findByEmpleadoCedula(id);

        if (empleado == null || nomina == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font subFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        // Encabezado
        Paragraph title = new Paragraph("Nómina del Empleado", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Espacio en blanco

        // Información básica del empleado
        document.add(new Paragraph("Empleado: " + empleado.getNombre() + " " + empleado.getApellido(), subFont));
        document.add(new Paragraph("Cédula: " + empleado.getCedula(), subFont));
        document.add(new Paragraph("Salario: " + empleado.getSalario(), subFont));
        document.add(new Paragraph(" ")); // Espacio en blanco

        // Tabla de Devengados y Descuentos
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Encabezados de la tabla
        PdfPCell cell1 = new PdfPCell(new Phrase("Concepto", subFont));
        PdfPCell cell2 = new PdfPCell(new Phrase("Valor", subFont));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);
        table.addCell(cell2);

        // Agregar devengados a la tabla
        table.addCell(new PdfPCell(new Phrase("Salario Devengado", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getSalarioDevengado()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Subsidio de Transporte", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getTransporte()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Gastos de Representación", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getGastos()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Sobresueldo", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getSobresueldo()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Viáticos", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getViaticos()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Comisiones", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getComisiones()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Prima", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDevengados().getPrimas()), tableFont)));

        // Agregar descuentos a la tabla
        table.addCell(new PdfPCell(new Phrase("EPS (Salud)", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDescuentos().getEps()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Pensión", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDescuentos().getPension()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Fondo de Solidaridad", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDescuentos().getFondoSol()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Banco", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDescuentos().getBanco()), tableFont)));

        table.addCell(new PdfPCell(new Phrase("Fondo de Empleados", tableFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(empleado.getDescuentos().getFondoEmp()), tableFont)));

        document.add(table);

        // Agregar salario bruto y salario neto
        document.add(new Paragraph("Salario Bruto: " + nomina.getSalarioBruto(), subFont));
        document.add(new Paragraph("Salario Neto: " + nomina.getSalarioNeto(), subFont));

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
