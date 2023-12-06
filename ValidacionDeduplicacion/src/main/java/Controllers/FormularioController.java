/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import QueueModules.RabitConnection;
import Services.*;

/**
 *
 * @author jt251
 */
public class FormularioController {

    private final FormularioService formularioService = new FormularioService();

    public boolean procesarFormulario(String formularioJSON) {
        try {

            return formularioService.validarFormulario(formularioJSON);
        } catch (Exception e) {
            return false; // Manejo de errores al parsear el JSON
        }
    }
    // Metodo principal para validar y duplicar

    public void ProcesarValidateDeduplica(RabitConnection conexionRabbit) {
        try {
            // Obtener formulario JSON desde la cola de mensajes
            String formularioJSON;
            formularioJSON = conexionRabbit.recibirMensaje("CapturaDeDatos");

            // Procesar el formulario
            if (!formularioService.validarFormulario(formularioJSON)) {
                formularioService.almacenarFormularioInvalido(formularioJSON);
            } else if (formularioService.verificarDuplicado(formularioJSON)) {
                formularioService.almacenarFormularioDuplicado(formularioJSON);
            } else {
                formularioService.enviarDatosAlmacenamiento(formularioJSON);
            }
        } catch (Exception e) {
        }
    }
}
