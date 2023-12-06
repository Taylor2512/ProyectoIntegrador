package com.example.almacenamiento.Services;
import com.example.almacenamiento.Models.Enum.TipoArchivo;
import com.example.almacenamiento.componets.FileManager;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class AlmacenamientoServices {

    private final FileManager fileManager;

    public AlmacenamientoServices(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void guardarArchivo(String directorio, TipoArchivo tipo, String jsonFormulario) throws IOException {
        fileManager.guardarArchivo(directorio, tipo, jsonFormulario);
    }
}
