package BBDD;

import entities.animales.*;

import java.sql.*;
import java.util.ArrayList;

public class GestionBBDD {

     static GestionBBDD instance;
     Connection connection;

     GestionBBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:2000/StardamValley";
            connection = DriverManager.getConnection(url, "root", "triana123");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al definir el driver");
        }

    }

    public static GestionBBDD getInstance() {
        if (instance == null) {
            instance = new GestionBBDD();
        }
        return instance;
    }

    public ResultSet select(String query) {

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {

            statement = connection.prepareStatement(query);
            resultado = statement.executeQuery();

            if (resultado == null) {
                return null;
            }
            if (!resultado.isBeforeFirst()) {
                System.out.println("La consulta no devolvió resultados");
            } else {
                System.out.println("Consulta ejecutada con éxito - Datos encontrados");
            }
            return resultado;

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace(); // Imprimir stack trace completo
            return null;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            return null;

        }
    }

    public ArrayList<Animal> getAnimales(ResultSet resultado, ArrayList<Alimento> alimentos, ArrayList<Producto> productos) {
        ArrayList<Animal> animales = new ArrayList<>();
         int id;
         String nombre;
         Timestamp diaInsercion;
         Tipo tipo;
         Alimento alimento;
         Producto producto;

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return animales;
        }

        try {
            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                tipo = Tipo.valueOf(resultado.getString("tipo"));
                float peso = resultado.getFloat("peso");
                diaInsercion = new Timestamp(resultado.getLong("dia_insercion"));
                alimento = Establo.getInstance().getAlimentoId(resultado.getInt("id_alimento"));
                producto = Establo.getInstance().getProductoId(resultado.getInt("id_producto"));

                Animal animal;
                switch (tipo) {
                    case VACA:
                        animal = new Vaca(id, nombre, diaInsercion, peso,alimento, producto);
                        break;
                    case GALLINA:
                        animal = new Gallina(id, nombre, diaInsercion, alimento, producto);
                        break;
                    case OVEJA:
                        animal= new Oveja(id,nombre,diaInsercion,alimento,producto);
                        break;
                    case CERDO:
                        animal= new Cerdo(id,nombre,diaInsercion,alimento,producto);
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de animal no reconocido: " + tipo);
                }
                animales.add(animal);
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
        }

        System.out.println("Número de animales cargados: " + animales.size());
        return animales;
    }

    public ArrayList<Producto> getProductos(ResultSet resultado) {
        ArrayList<Producto> productos = new ArrayList<>();
        int id;
        String nombre;
        float precio;
        int cantidadDisponible;

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return productos;
        }

        try {
            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                precio=resultado.getFloat("precio");
                cantidadDisponible=resultado.getInt("cantidad_disponible");
                productos.add(new Producto(id, nombre, precio,cantidadDisponible));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
        }

        System.out.println("Número de productos cargados: " + productos.size());
        return productos;
    }

    public ArrayList<Alimento> getAlimentos(ResultSet resultado) {
        ArrayList<Alimento> alimentos = new ArrayList<>();
        int id;
        String nombre;
        float precio;
        int cantidadDisponible;

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return alimentos;
        }

        try {
            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                precio=resultado.getFloat("precio");
                cantidadDisponible=resultado.getInt("cantidad_disponible");
                alimentos.add(new Alimento(id, nombre, precio,cantidadDisponible));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
        }

        System.out.println("Número de alimentos cargados: " + alimentos.size());
        return alimentos;
    }

    public void cerrarConexion() {
        try {
            connection.close();
        } catch (SQLException sql) {
            System.out.println("Error al cerrar la conexion");
        }
    }

}
