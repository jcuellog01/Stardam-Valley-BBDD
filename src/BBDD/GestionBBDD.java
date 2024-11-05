package BBDD;

import entities.Animal;
import entities.Tipo;

import java.sql.*;
import java.util.ArrayList;

public class GestionBBDD {

    private static GestionBBDD instance;
    private Connection connection;

    private GestionBBDD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:2000/StardamValley";
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public ArrayList<Animal> getAnimales(ResultSet resultado) {
        ArrayList<Animal> animales = new ArrayList<>();
        int id;
        String nombre;
        Timestamp fecha;
        int idAlimento;
        int idProducto;
        Tipo tipo;

        try {

            while (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                tipo = Tipo.valueOf(resultado.getString("tipo"));
                fecha = resultado.getTimestamp("dia_insercion");
                idAlimento = resultado.getInt("id_alimento");
                idProducto = resultado.getInt("id_producto");

                animales.add(new Animal(id,nombre,fecha,idAlimento,idProducto));
            }
        } catch (SQLException e) {

        }
        return animales;
    }

    public void cerrarConexion(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion");
        }
    }

}
