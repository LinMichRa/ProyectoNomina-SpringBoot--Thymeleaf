package com.linda.BackendNomina.controlador;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.linda.BackendNomina.servicios.ChartServicio;

@Controller
public class GraficaControlador {

    @Autowired
    private ChartServicio chartServicio;

    @GetMapping("/barChart")
    public ResponseEntity<byte[]> getBarChart() throws IOException {
        byte[] chart = chartServicio.crearGraficaBarras();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.IMAGE_PNG);

        return new ResponseEntity<>(chart, headers, HttpStatus.OK);
    }
}
