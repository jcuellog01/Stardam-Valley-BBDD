package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionBBDD {

    private static GestionBBDD instance;

    private GestionBBDD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:2000/StardamValley";
            Connection connection = DriverManager.getConnection(url,"root","triana123");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM animales ");
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al definir el driver");
        }

    }

    public static GestionBBDD getInstance(){
        if(instance == null){
            instance = new GestionBBDD();
        }
        return instance;
    }

}
