package BBDD;

import entities.TipoProducto;
import entities.animales.*;

import java.sql.*;
import java.time.LocalDateTime;
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

    public ArrayList<Animal> obtenerAnimales() {
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

    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        int id;
        String nombre;
        float precio;
        int cantidadDisponible;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM Productos p;");
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

        try {
            while (resultado.next()) {
                id = resultado.getInt("p.id");
                nombre = resultado.getString("p.nombre");
                precio = resultado.getFloat("p.peso");

                productos.add(new Producto(id, nombre, precio));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
        }
        return productos;
    }

    public ArrayList<Alimento> obtenerAlimentos() {
        ArrayList<Alimento> productos = new ArrayList<>();
        int id;
        String nombre;
        float precio;
        int cantidadDisponible;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM Alimentos al;");
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

        try {
            while (resultado.next()) {
                id = resultado.getInt("al.id");
                nombre = resultado.getString("al.nombre");
                precio = resultado.getFloat("al.peso");

                productos.add(new Alimento(id, nombre, precio));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
        }
        return productos;
    }

    public int obtenerCantidadAlimento(int id) {

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT cantidad_disponible FROM Alimentos WHERE id=?");
            statement.setInt(1, id);
            resultado = statement.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("cantidad_disponible");

            } else {
                System.out.println("No se encontró alimento con id: " + id);
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }

    }

    public int obtenerCantidadProducto(int id) {

        ResultSet resultados;

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT cantidad_disponible FROM Productos p WHERE id=?");
            statement.setInt(1, id);
            resultado = statement.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error SQL al ejecutar la consulta: " + e.getMessage());
            return -1;

        } catch (Exception e) {
            System.out.println("Error general al ejecutar la consulta: " + e.getMessage());
            return -1;
        }
        try {
            resultado.next();
            return resultado.getInt("p.cantidad_disponible");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float obtenerPrecioProducto(int id) {

        ResultSet resultados;

        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            statement = connection.prepareStatement("SELECT precio FROM Productos p WHERE id=?");
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

        try {
            resultado.next();
            return resultado.getFloat("p.precio");
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
            return -1;
        }
    }

    public float obtenerPrecioAlimento(int id) {

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

        try {
            resultado.next();
            return resultado.getFloat("precio");
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.getMessage());
            return -1;
        }
    }


    public int registrarConsumo(int idAnimal, int cantidadConsumida, Timestamp fechaConsumo) {
        PreparedStatement statement;
        int resultado;

        try {
            statement = connection.prepareStatement("INSERT INTO HistorialConsumo (id_animal, cantidad_consumida, fecha_consumo) VALUES (?, ?, ?)");
            statement.setInt(1, idAnimal);
            statement.setInt(2, cantidadConsumida);
            statement.setTimestamp(3, fechaConsumo);
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

    public int registrarProduccion(int idAnimal, int cantidadProducida, Timestamp fechaConsumo) {
        PreparedStatement statement;
        int resultado;

        try {

            statement = connection.prepareStatement("INSERT INTO HistorialProduccion (id_animal, cantidad, fecha_produccion) VALUES (?, ?, ?)");
            statement.setInt(1, idAnimal);
            statement.setInt(2, cantidadProducida);
            statement.setTimestamp(3, fechaConsumo);
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


    public int registrarTransaccion(TipoTransaccion tipoT, TipoProducto tipoP, float cantidad) {
        PreparedStatement statement;
        int resultado;

        try {

            statement = connection.prepareStatement("INSERT INTO Transacciones(tipo_transaccion, tipo_elemento, precio,fecha_transaccion) VALUES (?, ?, ?,?)");
            statement.setString(1, tipoT.toString()); // Convierte el tipoT enum a String
            statement.setString(2, tipoP.toString());
            statement.setFloat(3, cantidad);
            statement.setTimestamp(4,Timestamp.valueOf(LocalDateTime.now()));
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


    public int updateCantidadAlimento(int cantidad, int id) {
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

    public int updateCantidadProducto(int cantidad, int id) {
        PreparedStatement statement;
        int resultado;

        try {
            statement = connection.prepareStatement("UPDATE Productos SET cantidad_disponible = ? WHERE id = ?;");
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
