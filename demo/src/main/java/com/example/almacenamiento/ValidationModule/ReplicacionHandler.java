/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.almacenamiento.ValidationModule;

import com.example.almacenamiento.Models.FormularioCampos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author jt251
 */
public class ReplicacionHandler {

    private String miDireccionIP;
    private static String direccion;
    private boolean soyLider;
    private List<String> direccionesIPReplicas; // Lista de direcciones IP de otras réplicas

    public void enviarAReplicasSeguidoras(FormularioCampos formulario) {
        for (String direccion : direccionesIPReplicas) {
            if (!direccion.equals(miDireccionIP)) {
                enviarAReplica(formulario, direccion); // Llama al método para enviar a réplica
            }
        }
    }

    public void recibirDeReplica(FormularioCampos formulario) {
        // Convertir el formulario a JSON
        guardarFormularioLocal(formulario);
        
        // Otra lógica relacionada con el procesamiento o confirmación de recepción del formulario
    }

    public void promoverSeguidorComoLider(String nuevaDireccionIP) {
        try {
            // Lógica para promover un seguidor como líder
            // Por ejemplo, actualizar el estado de 'soyLider' y establecer la nueva dirección IP del líder
            soyLider = true;
            miDireccionIP = nuevaDireccionIP;

            // Otra lógica relacionada con la promoción a líder
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores durante la promoción
        }
    }

    private void guardarFormularioLocal(FormularioCampos formulario) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonFormulario = mapper.writeValueAsString(formulario);

            // Guardar el JSON en disco en la réplica destino (suponiendo que la dirección representa la ruta del archivo)
            // Ejemplo: Guardar en la carpeta especificada por la dirección IP
            Files.write(Paths.get(direccion, "formulario.json"), jsonFormulario.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarAReplica(FormularioCampos formulario, String direccion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
