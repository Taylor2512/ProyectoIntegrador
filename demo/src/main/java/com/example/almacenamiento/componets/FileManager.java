/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.almacenamiento.componets;
import com.example.almacenamiento.Models.Enum.TipoArchivo;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
/**
 *
 * @author jt251
 */
@Component
public class FileManager {

    public void guardarArchivo(String directorio, TipoArchivo tipo, String jsonFormulario) throws IOException {
        String directorioTipo = directorio + "/" + tipo.toString().toLowerCase();

        // Verificar si el directorio existe, si no, lanzar una excepción
        Path path = Paths.get(directorioTipo);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IOException("El directorio especificado no existe: " + directorioTipo);
        }

        String nombreArchivo = "formulario_" + UUID.randomUUID().toString() + ".json";
        Path archivoPath = Paths.get(directorioTipo, nombreArchivo);

        Files.write(archivoPath, jsonFormulario.getBytes());
    }
}
