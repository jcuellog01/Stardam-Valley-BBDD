package Ficheros;

import Utils.Constantes;
import entities.Granja;

import java.io.*;
import java.nio.file.*;

public class Guardado {

    // Obtener ObjectOutputStream
    public static ObjectOutputStream getOutput() {
        try {
            OutputStream archivo = Files.newOutputStream(Paths.get(Constantes.RUTA_GUARDADO));
            return new ObjectOutputStream(archivo);
        } catch (Exception e) {
            System.out.println("Error al obtener el fichero de guardado");
            return null;
        }
    }

    // Obtener ObjectInputStream
    public static ObjectInputStream getInput() {
        try {
            InputStream archivo = Files.newInputStream(Paths.get(Constantes.RUTA_GUARDADO));
            return new ObjectInputStream(archivo);
        } catch (Exception e) {
            System.out.println("Error al obtener el fichero de guardado");
            return null;
        }
    }

    // Guardar la granja
    public static void guardar(ObjectOutputStream ous, Granja g) {
        try {
            ous.writeObject(g);
        } catch (IOException e) {
            System.out.println("Error al guardar granja");
        } finally {
            try {
                if (ous != null) {
                    ous.close();  // Asegura cerrar el ObjectOutputStream
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el ObjectOutputStream");
            }
        }
    }

    // Crear nueva partida
    public static void nuevaPartida() {
        try {
            Path path = Paths.get(Constantes.RUTA_GUARDADO);
            if (!Files.exists(path)) {
                Files.createFile(path);  // Crear archivo solo si no existe
            }
            LeerProperties.cargarProperties();  // Cargar configuración, si es necesario
        } catch (FileAlreadyExistsException e) {
            System.out.println("La partida ya existe.");
        } catch (IOException e) {
            System.out.println("Error al crear nueva partida granja");
        }
    }

    // Verificar si existe una partida
    public static boolean existePartida() {
        Path path = Paths.get(Constantes.RUTA_GUARDADO);

        if (!Files.exists(path)) {
            System.out.println("No existe partida.");
            return false;
        }

        try {
            return Files.size(path) > 0;
        } catch (IOException e) {
            System.out.println("Error al ver si existe partida");
        }

        return false;
    }

    // Cargar la partida
    public static Granja cargar(ObjectInputStream ois) {

        try {
            return (Granja)ois.readObject();
        } catch (IOException e) {
            System.out.println("Error IO al cargar info de la Granja");
        } catch (ClassNotFoundException e) {
            System.out.println("Error CNFE al cargar info de la Granja");
        } finally {
            try {
                if (ois != null) {
                    ois.close();  // Asegura cerrar el ObjectInputStream
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el ObjectInputStream");
            }
        }


        return null;
    }


    // Eliminar el archivo de guardado
    public static void delete() {
        Path path = Paths.get(Constantes.RUTA_GUARDADO);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.println("Error al eliminar el archivo de guardado");
            }
        }
    }
}