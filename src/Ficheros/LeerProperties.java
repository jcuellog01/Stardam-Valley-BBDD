package Ficheros;

import Utils.Constantes;
import Utils.Pedir;
import entities.Estacion;
import entities.Granja;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class LeerProperties {

    private static Path path;

    public static void cargarProperties() {


        Properties properties = new Properties();
        ObjectOutputStream objectOutputStream;

        boolean personalizado = Pedir.pedirString("¿Desea personalizar sus opciones de arranque del juego?: ").equalsIgnoreCase("si");

        Granja g = Granja.getInstance();

        if (personalizado) {

            path = Path.of(Constantes.RUTA_PROPERTIES_PERSONALIZADO);
            try {
                objectOutputStream = new ObjectOutputStream(getFileOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setProperties(objectOutputStream);

        } else {

            path = Path.of(Constantes.RUTA_PROPERTIES);

        }

        // Cargar las propiedades
        try (FileInputStream fis = new FileInputStream(path.toString())) {

            properties.load(fis);
            g.setPresupuesto(Integer.parseInt(properties.get("presupuesto").toString()));
            g.setEstacion(Estacion.valueOf(properties.get("estacion").toString()));
            g.setDuracionEstacion(Integer.parseInt(properties.get("duracion").toString()));
            g.setColumnasHuerto(Integer.parseInt(properties.get("columnas").toString()));
            g.setFilasHuerto(Integer.parseInt(properties.get("filas").toString()));

        } catch (IOException e) {

            System.out.println("Error al cargar las propiedades: " + e.getMessage());

        }

    }

    public static FileInputStream getFileInputStream() {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(path.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Error FNFE al devolver el flujo de entrada");
        }
        return fis;
    }

    public static FileOutputStream getFileOutputStream() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path.toFile());
        } catch (FileNotFoundException e) {
            System.out.println("Error FNFE al devolver el flujo de entrada");
        }
        return fos;
    }

    public static int getFilasHuerto(FileInputStream fis) {

        Properties properties = new Properties();
        try {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error IO al leer .properties");
        }
        return Integer.parseInt(properties.getProperty("filas"));
    }

    public static int getColumnasHuerto(FileInputStream fis) {

        Properties properties = new Properties();
        try {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error IO al leer .properties");
        }
        return Integer.parseInt(properties.getProperty("columnas"));
    }

    public static void setProperties(ObjectOutputStream oos) {

        Properties properties = new Properties();
        properties.setProperty("filas", String.valueOf(Pedir.pedirInt("Introduce las filas del huerto: ")));
        properties.setProperty("columnas", String.valueOf(Pedir.pedirInt("Introduce las columnas del huerto: ")));
        properties.setProperty("presupuesto", String.valueOf(Pedir.pedirInt("Introduce el presupuesto inicial de la Granja: ")));
        properties.setProperty("estacion", Pedir.pedirString("Introduce la estacion inicial: "));
        properties.setProperty("duracion", String.valueOf(Pedir.pedirInt("Introduce la duracion de las estaciones: ")));
        try {
            properties.store(oos, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

