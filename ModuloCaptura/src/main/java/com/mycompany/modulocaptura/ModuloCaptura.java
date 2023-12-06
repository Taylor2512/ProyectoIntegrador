/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.modulocaptura;

import Controllers.CapturaController;
import QueueModules.RabitConnection;

import java.util.Scanner;

/**
 *
 * @author jt251
 */
public class ModuloCaptura {

    private static final String NOMBRE_COLA = "CapturaDeDatos";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            RabitConnection connecxionRa = new RabitConnection();
            boolean generarMas = true;
            while (generarMas) {
                try {
                    System.out.print("Ingrese la cantidad de formularios a generar: ");
                    int cantidadFormularios = Integer.parseInt(scanner.nextLine());
                    CapturaController captura = new CapturaController();
                    for (int i = 0; i < cantidadFormularios; i++) {
                        String formularioJSON = captura.generarFormularioJSON();
                        connecxionRa.enviarMensaje(formularioJSON, NOMBRE_COLA);
                        System.out.println("Formulario #" + (i + 1) + " enviado a la cola.");
                    }
                    
                    System.out.print("¿Desea generar más formularios? (Sí/No): ");
                    String respuesta = scanner.nextLine();
                    generarMas = "s".equalsIgnoreCase(respuesta != null ? respuesta.trim() : "")
                            || "si".equalsIgnoreCase(respuesta != null ? respuesta.trim() : "");
                    
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
                }
            }
            // Cierra el Scanner antes de terminar el programa
        }
        System.exit(0); // Termina el programa
    }
}
