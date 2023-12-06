/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QueueModules;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author jt251
 */
public class RabitConnection {

    private Connection connection;

    public RabitConnection() {
        // Configuración de conexión con RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Reemplaza esto con la dirección de tu servidor RabbitMQ

        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
        }
    }

    public void enviarMensaje(String mensaje, String NOMBRE_COLA) {
        try (Channel channel = connection.createChannel()) {
            // Declara la cola a la que enviarás los mensajes
            channel.queueDeclare(NOMBRE_COLA, false, false, false, null);

            // Publica el mensaje en la cola
            channel.basicPublish("", NOMBRE_COLA, null, mensaje.getBytes());
            System.out.println(" [x] Mensaje enviado: '" + mensaje + "'");
        } catch (Exception e) {
        }
    }

   public String recibirMensaje(String NOMBRE_COLA)  {
      try (Channel channel = connection.createChannel()) {
          
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        // Se configura el consumidor para recibir el mensaje
        channel.basicConsume(NOMBRE_COLA, true, (consumerTag, delivery) -> {
            String mensajeRecibido = new String(delivery.getBody(), "UTF-8");
            queue.offer(mensajeRecibido);
        }, consumerTag -> {});

        // Se espera a recibir un mensaje
        return queue.take();
        } catch (Exception e) {
        }
        return "";
    }

    public void cerrarConexion() {
        try {
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
        }
    }
}
