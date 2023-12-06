/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Dtos.FormularioCampos;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jt251
 */
public class FormularioService {

    /// Metodo para validar Formulario
    public boolean validarFormulario(String formularioJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FormularioCampos campos = objectMapper.readValue(formularioJSON, FormularioCampos.class);

            return camposSonValidos(campos);
        } catch (JsonProcessingException | IllegalAccessException e) {
            return false; // Manejo de errores al parsear el JSON
        }
    }
    // Metodo para validar si los campos son validos
    private boolean camposSonValidos(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                return false; // Si encuentra un campo null, retorna false
            }
        }
        return true; // Si todos los campos son diferentes de null, retorna true
    }
    // Metodo para verificar si el formulario es duplicado
    public boolean verificarDuplicado(String formularioJSON) {
        // Lógica para verificar duplicación en el sistema de almacenamiento
        // Retorna true si está duplicado, false si no lo está
        // (Debes implementar la lógica de verificación de duplicados con tu sistema de almacenamiento)
        return false;
    }
    // metodo para enviar por peticion http
    public void enviarPost(String jsonData, String endpointUrl) {
        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Configurar la conexión HTTP
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json"); // Configurar el Content-Type como application/json
            con.setDoOutput(true);

            // Enviar los datos JSON
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonData);
                wr.flush();
            }
            // Leer la respuesta del servidor (opcional)
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // Puedes utilizar 'responseCode' y 'response.toString()' para manejar la respuesta del servidor
            }
        } catch (IOException e) {
        }
    }
    // metodo para almacenar el formulario invalido
    public void almacenarFormularioInvalido(String formularioJSON) {
        // Almacenar formulario no válido en carpeta local
        guardarEnArchivo(formularioJSON, "FormulariosNoValidos");
    }
    // metodo para almacenar el formulario deduplicado
    public void almacenarFormularioDuplicado(String formularioJSON) {
        // Almacenar formulario duplicado en carpeta local diferente
        guardarEnArchivo(formularioJSON, "FormulariosDuplicados");
    }
    // metodo para enviar los datos de almacenamiento por http
    public void enviarDatosAlmacenamiento(String formularioJSON) {
        enviarPost(formularioJSON, "http://localhost:4545/formularios");

    }
    // metodo para creacion del direcctorio en caso de no existir
    public static void crearDirectorioSiNoExiste(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        File directorio = archivo.getParentFile(); // Obtiene el directorio padre del archivo

        // Verifica si el directorio existe, si no existe, lo crea
        if (!directorio.exists()) {
            boolean directorioCreado = directorio.mkdirs(); // Intenta crear los directorios

            if (directorioCreado) {
                System.out.println("Directorio creado en: " + directorio.getAbsolutePath());
            } else {
                System.out.println("No se pudo crear el directorio.");
            }
        } else {
            System.out.println("El directorio ya existe en: " + directorio.getAbsolutePath());
        }
    }
    // metodo para guardar el archivo
    private void guardarEnArchivo(String contenido, String nombreCarpeta) {
        if (contenido.isEmpty()) {
            // No hay contenido para guardar, se sale del método
            return;
        }

        try {
            UUID uniqueID = UUID.randomUUID();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String fechaActual = dateFormat.format(new Date());
            String nombreArchivo = "formulario_" + fechaActual + "_" + uniqueID.toString() + ".json";
            String rutaArchivo = nombreCarpeta + File.separator + nombreArchivo;

            // Verifica si la ruta del archivo existe, si no existe, la crea
            crearDirectorioSiNoExiste(rutaArchivo);

            try (FileWriter writer = new FileWriter(rutaArchivo) // Usa la ruta completa del archivo
                    ) {
                writer.write(contenido);
            }
        } catch (IOException e) {
        }
    }
}
