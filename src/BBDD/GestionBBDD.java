package BBDD;

import entities.animales.*;

import java.sql.*;
import java.util.ArrayList;

public class GestionBBDD {

    private static GestionBBDD instance;
    private Connection connection;

    GestionBBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:2000/StardamValley";
            connection = DriverManager.getConnection(url, "root", "root");
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

    public ArrayList<Animal> obtenerAnimales(){
        ArrayList<Animal> animales = new ArrayList<>();
        int id;
        String nombre;
        Timestamp diaInsercion;
        Tipo tipo;

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM Animales an JOIN Alimentos al ON an.id_alimento=al.id JOIN Productos p ON an.id_producto=p.id;");
            resultado = statement.executeQuery();

            if (resultado == null) {
                return null;
            }
            if (!resultado.isBeforeFirst()) {
                System.out.println("La consulta no devolvió resultados");
            } else {
                System.out.println("Consulta ejecutada con éxito - Datos encontrados");
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return null;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return null;
        }

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return animales;
        }

        try {
            while (resultado.next()) {
                id = resultado.getInt("an.id");
                nombre = resultado.getString("an.nombre");
                tipo = Tipo.valueOf(resultado.getString("an.tipo"));
                float peso = resultado.getFloat("an.peso");
                diaInsercion = new Timestamp(resultado.getLong("an.dia_insercion"));
                Alimento alimento = new Alimento(resultado.getInt("al.id"), resultado.getString("al.nombre"), resultado.getFloat("al.precio"));
                Producto producto = new Producto(resultado.getInt("p.id"), resultado.getString("p.nombre"), resultado.getFloat("p.precio"));

                Animal animal;
                switch (tipo) {
                    case VACA:
                        animal = new Vaca(id, nombre, diaInsercion, peso, alimento, producto);
                        break;
                    case GALLINA:
                        animal = new Gallina(id, nombre, diaInsercion, alimento, producto);
                        break;
                    case OVEJA:
                        animal = new Oveja(id, nombre, diaInsercion, alimento, producto);
                        break;
                    case CERDO:
                        animal = new Cerdo(id, nombre, diaInsercion, alimento, producto);
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

    public int obtenerCantidadAlimento(int id){

        ResultSet resultados;

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT cantidad_disponible FROM Alimentos al WHERE id=?");
            statement.setInt(1, id);
            resultado = statement.executeQuery();

            if (!resultado.isBeforeFirst()) {
                System.out.println("La consulta no devolvió resultados");
            } else {
                System.out.println("Consulta ejecutada con éxito - Datos encontrados");
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return -1;
        }

        try {
            return resultado.getInt("cantidad_disponible");
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
            return -1;
        }
    }

    public float obtenerPrecioAlimento(int id){

        ResultSet resultados;

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT precio FROM Alimentos al WHERE id=?");
            statement.setInt(1, id);
            resultado = statement.executeQuery();

            if (!resultado.isBeforeFirst()) {
                System.out.println("La consulta no devolvió resultados");
            } else {
                System.out.println("Consulta ejecutada con éxito - Datos encontrados");
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }

        if (resultado == null) {
            System.out.println("El ResultSet es null");
            return -1;
        }

        try {
            return resultado.getFloat("precio");
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
            return -1;
        }
    }

    public int update(String query, Object... params) {
        PreparedStatement statement;
        int resultado;

        try {
            statement = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]); // Acordarse de que el parametro es i+1 porque en vectores empieza en 0 y ej JDBC en 1
            }

            resultado = statement.executeUpdate();

            return resultado;

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }
    }
    public int updateCantidadAlimento(int cantidad,int id) {
        PreparedStatement statement;
        int resultado;

        try {
            statement = connection.prepareStatement("UPDATE Alimentos SET cantidad_disponible = ? WHERE id = ?;");
            statement.setInt(1, cantidad);
            statement.setInt(2, id);
            resultado = statement.executeUpdate();
            return resultado;

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }
    }

    public int venderProducto(int id) {
        PreparedStatement statement;
        int resultado;

        try {
            statement = connection.prepareStatement("UPDATE Productos SET cantidad_disponible = ? WHERE id = ?;");
            statement.setInt(1, 0);
            statement.setInt(2, id);
            resultado = statement.executeUpdate();
            return resultado;

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }
    }

    public void cerrarConexion() {
        try {
            connection.close();
        } catch (SQLException sql) {
            System.out.println("Error al cerrar la conexion");
        }
    }

}
