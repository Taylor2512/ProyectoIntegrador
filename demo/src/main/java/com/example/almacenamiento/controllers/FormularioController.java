package com.example.almacenamiento.controllers;

import com.example.almacenamiento.Models.Dtos.ApiResponse;
import com.example.almacenamiento.Models.FormularioCampos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/formularios")
public class FormularioController {

    private final String directorio = "C://Files"; // Ruta donde se guardarán los archivos

    @PostMapping("/guardarFormulario")
    public ResponseEntity<ApiResponse> guardarFormulario(@RequestBody FormularioCampos formulario) {
        ApiResponse response = new ApiResponse();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFormulario = mapper.writeValueAsString(formulario);

            // Verificar si el formulario está duplicado
            if (formularioDuplicado(formulario)) {
                // Si está duplicado, maneja la lógica correspondiente
                // ...

                response.setSuccess(false);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Formulario duplicado, almacenado en carpeta de duplicados");
                // Añade más detalles según tu necesidad a data o errors si es relevante
            } else {
                // Si no está duplicado, procede con el guardado normal
                String nombreArchivo = "formulario_" + UUID.randomUUID().toString() + ".json";
                Files.write(Paths.get(directorio + "/Validos", nombreArchivo), jsonFormulario.getBytes());

                response.setSuccess(true);
                response.setStatusCode(HttpStatus.CREATED.value());
                response.setMessage("Formulario guardado como " + nombreArchivo);
                // Puedes añadir más detalles a data si es necesario
            }
        } catch (IOException e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al guardar el formulario: " + e.getMessage());
            // Puedes agregar más detalles o errores si es necesario
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    private boolean formularioDuplicado(FormularioCampos formulario) {
        // Lógica para verificar si el formulario está duplicado
        // Puedes implementar la búsqueda del formulario en el sistema de almacenamiento
        // y comparar si ya existe un formulario con los mismos datos
        // Devuelve true si está duplicado, false si no lo está (esto es un ejemplo básico)
        // Aquí podrías utilizar la lógica específica para tu sistema de archivos/local
        // para buscar si ya existe un formulario similar en el directorio

        // Por ejemplo:
        // Supongamos que tienes una lista de formularios guardados en archivos en el directorio
        List<FormularioCampos> formulariosGuardados = obtenerTodosLosFormulariosGuardados();

        for (FormularioCampos formGuardado : formulariosGuardados) {
            if (formGuardado.equals(formulario)) {
                return true; // El formulario está duplicado
            }
        }

        return false; // El formulario no está duplicado
    }

    private List<FormularioCampos> obtenerTodosLosFormulariosGuardados() {

        List<FormularioCampos> formularios = new ArrayList<>();
        try {
            Files.list(Paths.get(directorio + "/Validos"))
                    .filter(Files::isRegularFile)
                    .forEach((Path file) -> {
                        try {
                            byte[] bytes = Files.readAllBytes(file);
                            String jsonFormulario = new String(bytes);
                            ObjectMapper mapper = new ObjectMapper();
                            FormularioCampos formulario = mapper.readValue(jsonFormulario, FormularioCampos.class);
                            formularios.add(formulario);
                        } catch (IOException e) {
                        }
                    });
        } catch (IOException e) {
        }

        return formularios;
    }

    @DeleteMapping("/{nombreArchivo}")
    public ResponseEntity<ApiResponse> eliminarFormulario(@PathVariable String nombreArchivo) {
        ApiResponse response = new ApiResponse();

        try {
            Files.deleteIfExists(Paths.get(directorio, nombreArchivo));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Formulario eliminado: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al eliminar el formulario");
            response.setErrors(Collections.singletonList(e.getMessage()));
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{nombreArchivo}")
    public ResponseEntity<ApiResponse> reemplazarFormulario(@PathVariable String nombreArchivo, @RequestBody FormularioCampos formulario) {
        ApiResponse response = new ApiResponse();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFormulario = mapper.writeValueAsString(formulario);

            Files.write(Paths.get(directorio, nombreArchivo), jsonFormulario.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Formulario reemplazado: " + nombreArchivo);
        } catch (IOException e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al reemplazar el formulario");
            response.setErrors(Collections.singletonList(e.getMessage()));
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{nombreArchivo}")
    public ResponseEntity<ApiResponse> obtenerFormulario(@PathVariable String nombreArchivo) {
        ApiResponse response = new ApiResponse();

        try {
            String contenido = new String(Files.readAllBytes(Paths.get(directorio, nombreArchivo)));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(contenido);
        } catch (IOException e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al obtener el formulario");
            response.setErrors(Collections.singletonList(e.getMessage()));
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> obtenerTodosFormularios() {
        ApiResponse response = new ApiResponse();

        try {
            List<String> formularios = new ArrayList<>();
            Files.list(Paths.get(directorio))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            formularios.add(file.getFileName().toString());
                        } catch (Exception e) {
                        }
                    });

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(formularios);
        } catch (IOException e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error al obtener los formularios");
            response.setErrors(Collections.singletonList(e.getMessage()));
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Implementa otros métodos según la lógica de tu aplicación para operaciones adicionales y replicación de datos
}
