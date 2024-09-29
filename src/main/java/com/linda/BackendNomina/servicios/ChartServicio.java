package com.linda.BackendNomina.servicios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linda.BackendNomina.modelo.Empleado;
import com.linda.BackendNomina.repositorio.EmpleadoRepositorio;

@Service
public class ChartServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    public byte[] crearGraficaBarras() throws IOException {
        List<Empleado> empleados = empleadoRepositorio.findAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Empleado empleado : empleados) {
            if (empleado.getDescuentos() != null && empleado.getDescuentos().getEps()!= 0)  {
                dataset.addValue(empleado.getDescuentos().getEps(), "EPS", empleado.getNombre());
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "EPS por Empleado",
                "Empleado",
                "EPS",
                dataset);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(outputStream, barChart, 800, 600); // Tamaño de la gráfica
        return outputStream.toByteArray();
    }
}
