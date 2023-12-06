package com.example.almacenamiento.controllers;

import com.example.almacenamiento.Models.FormularioCampos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
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
    private final String directorio = "directorio_de_archivos"; // Ruta donde se guardarán los archivos

 @PostMapping
public ResponseEntity<String> guardarFormulario(@RequestBody FormularioCampos formulario) {
    try {
        ObjectMapper mapper = new ObjectMapper();
        String jsonFormulario = mapper.writeValueAsString(formulario);

        // Verificar si el formulario ya existe
        if (formularioDuplicado(formulario)) {
            // Si el formulario está duplicado, almacenarlo en una carpeta diferente
            // (puedes ajustar la lógica para guardar en una carpeta diferente)
            String nombreArchivoDuplicado = "formulario_duplicado_" + UUID.randomUUID().toString() + ".json";
            Files.write(Paths.get(directorio, "duplicados", nombreArchivoDuplicado), jsonFormulario.getBytes());
            
            return new ResponseEntity<>("Formulario duplicado, almacenado en carpeta de duplicados", HttpStatus.BAD_REQUEST);
        } else {
            // Si el formulario no está duplicado, proceder con el guardado normal
            String nombreArchivo = "formulario_" + UUID.randomUUID().toString() + ".json";
            Files.write(Paths.get(directorio, nombreArchivo), jsonFormulario.getBytes());

            return new ResponseEntity<>("Formulario guardado como " + nombreArchivo, HttpStatus.CREATED);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return new ResponseEntity<>("Error al guardar el formulario", HttpStatus.INTERNAL_SERVER_ERROR);
    }
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
        Files.list(Paths.get(directorio))
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        byte[] bytes = Files.readAllBytes(file);
                        String jsonFormulario = new String(bytes);
                        ObjectMapper mapper = new ObjectMapper();
                        FormularioCampos formulario = mapper.readValue(jsonFormulario, FormularioCampos.class);
                        formularios.add(formulario);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return formularios;
}
    @DeleteMapping("/{nombreArchivo}")
    public ResponseEntity<String> eliminarFormulario(@PathVariable String nombreArchivo) {
        try {
            Files.deleteIfExists(Paths.get(directorio, nombreArchivo));
            return new ResponseEntity<>("Formulario eliminado: " + nombreArchivo, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al eliminar el formulario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{nombreArchivo}")
    public ResponseEntity<String> reemplazarFormulario(@PathVariable String nombreArchivo, @RequestBody FormularioCampos formulario) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFormulario = mapper.writeValueAsString(formulario);

            Files.write(Paths.get(directorio, nombreArchivo), jsonFormulario.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            
            return new ResponseEntity<>("Formulario reemplazado: " + nombreArchivo, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al reemplazar el formulario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{nombreArchivo}")
    public ResponseEntity<String> obtenerFormulario(@PathVariable String nombreArchivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(directorio, nombreArchivo)));
            return new ResponseEntity<>(contenido, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al obtener el formulario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<String>> obtenerTodosFormularios() {
        try {
            List<String> formularios = new ArrayList<>();
            Files.list(Paths.get(directorio))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            formularios.add(file.getFileName().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

            return new ResponseEntity<>(formularios, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Implementa otros métodos según la lógica de tu aplicación para operaciones adicionales y replicación de datos
}
