/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
 
import Models.FormularioCampos;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author jt251
 */
public class CapturaController {
    private static final String[] NOMBRES = {"Juan", "María", "Carlos", "Laura", "Pedro", "Ana"};
    private static final String[] APELLIDOS = {"Pérez", "Gómez", "López", "Díaz", "García", "Martínez"};
    private static final String[] DIRECCIONES = {"Calle Principal", "Avenida Central", "Callejon Secundario"};
    private static final String[] CIUDADES = {"Quito", "Guayaquil", "Cuenca", "Manta", "Loja"};
    private static final String[] OCUPACIONES = {"Estudiante", "Doctor", "Ingeniero", "Profesor", "Abogado"};
    private static final String[] ESTADOS_CIVILES = {"Soltero", "Casado", "Viudo", "Divorciado"};
    private static final String[] GENEROS = {"Masculino", "Femenino"};



    private static final Random random = new Random();

     private static String generarNombre() {
        return NOMBRES[random.nextInt(NOMBRES.length)];
    }

    private static String generarApellido() {
        return APELLIDOS[random.nextInt(APELLIDOS.length)];
    }

    private static int generarEdad() {
        return random.nextInt(90) + 18; // Edad entre 18 y 107 años
    }

    private static String generarDireccion() {
        return DIRECCIONES[random.nextInt(DIRECCIONES.length)] + ", " + CIUDADES[random.nextInt(CIUDADES.length)];
    }

    private static String generarTelefono() {
        StringBuilder telefono = new StringBuilder("+593-");
        for (int i = 0; i < 9; i++) {
            telefono.append(random.nextInt(10));
        }
        return telefono.toString();
    }

    private static String generarCedula() {
        StringBuilder cedula = new StringBuilder("17");
        for (int i = 0; i < 8; i++) {
            cedula.append(random.nextInt(10));
        }
        return cedula.toString();
    }

    private static String generarOcupacion() {
        return OCUPACIONES[random.nextInt(OCUPACIONES.length)];
    }

    private static String generarEstadoCivil() {
        return ESTADOS_CIVILES[random.nextInt(ESTADOS_CIVILES.length)];
    }

    private static String generarGenero() {
        return GENEROS[random.nextInt(GENEROS.length)];
    }

    // Métodos para campos adicionales
    private static String generarEmail() {
        return generarNombre().toLowerCase() + "." + generarApellido().toLowerCase() + "@example.com";
    }

    private static Date generarFechaNacimiento() {
        int dia = random.nextInt(28) + 1;
        int mes = random.nextInt(12) + 1;
        int anio = 1920 + random.nextInt(90); // Asumiendo personas de hasta 90 años
      return  new Date(mes, anio, dia);
   
    }

    private static String generarCiudadNacimiento() {
        return CIUDADES[random.nextInt(CIUDADES.length)];
    }

    private static String generarGrupoSanguineo() {
        String[] gruposSanguineos = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        return gruposSanguineos[random.nextInt(gruposSanguineos.length)];
    }

    private static double generarPeso() {
        return random.nextDouble() * (120 - 40) + 40; // Peso entre 40 y 120 kg
    }

    private static double generarAltura() {
        return random.nextDouble() * (2 - 1.5) + 1.5; // Altura entre 1.5 y 2 metros
    }

    private static String generarColorOjos() {
        String[] coloresOjos = {"Azul", "Verde", "Marrón", "Negro"};
        return coloresOjos[random.nextInt(coloresOjos.length)];
    }

    private static String generarColorPelo() {
        String[] coloresPelo = {"Rubio", "Castallo", "Negro", "Pelirrojo"};
        return coloresPelo[random.nextInt(coloresPelo.length)];
    }

    private static String generarHobbies() {
        String[] hobbies = {"Leer", "Cocinar", "Deportes", "Pintar", "Viajar"};
        int numHobbies = random.nextInt(4) + 1; // Entre 1 y 4 hobbies
        StringBuilder hobbiesGenerados = new StringBuilder();
        for (int i = 0; i < numHobbies; i++) {
            hobbiesGenerados.append(hobbies[random.nextInt(hobbies.length)]);
            if (i != numHobbies - 1) {
                hobbiesGenerados.append(", ");
            }
        }
        return hobbiesGenerados.toString();
    }
/// metodo para generar el Formulario 
    public   String generarFormularioJSON() {

        FormularioCampos formularios = new FormularioCampos();
        formularios.setCedula(generarCedula());
        formularios.setNombre(generarNombre());
        formularios.setApellido(generarApellido());
        formularios.setEdad(generarEdad());
        formularios.setDireccion(generarDireccion());
        formularios.setTelefono(generarTelefono());
        formularios.setOcupacion(generarOcupacion());
        formularios.setEstadoCivil(generarEstadoCivil());
        formularios.setEmail(generarEmail());
        formularios.setFechaNacimiento(generarFechaNacimiento());
        formularios.setPais("Ecuador");
        formularios.setCiudadNacimiento(generarCiudadNacimiento());
        formularios.setGrupoSanguineo(generarGrupoSanguineo());
        formularios.setPeso(generarPeso());
        formularios.setAltura(generarAltura());
        formularios.setGenero(generarGenero());
        formularios.setColorOjos(generarColorOjos());
        formularios.setColorPelo(generarColorPelo());
        formularios.setHobbies(generarHobbies());
        return formularios.toJSON().toString();

    }  
}
