/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.validaciondeduplicacion;

import Controllers.FormularioController;
import QueueModules.RabitConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jt251
 */
public class ValidacionDeduplicacion implements Runnable {

    private final RabitConnection conexionRabbit;
    private final FormularioController formularioController;

    public ValidacionDeduplicacion(RabitConnection conexionRabbit) {
        this.formularioController = new FormularioController();
        this.conexionRabbit = conexionRabbit;
    }

    @Override

    public void run() {
        while (true) {
            try {
               //Procesar Controlador de validacion y de deduplicacion
                formularioController.ProcesarValidateDeduplica(conexionRabbit);

            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        // Establecer conexión con RabbitMQ
        RabitConnection conexionRabbit = new RabitConnection();

        // Configurar múltiples instancias concurrentes del módulo de validación
        int numeroInstancias = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numeroInstancias);
        for (int i = 0; i < numeroInstancias; i++) {
            executor.execute(new ValidacionDeduplicacion(conexionRabbit));
        }
    }
}
