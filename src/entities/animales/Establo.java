package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.Granja;
import entities.TipoProducto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Establo implements Serializable {
    private ArrayList<Animal> animales;
    private static Establo instance;
    private final String[] nombresProductos = {"Leche","Huevos","Lana","Trufa"};
    boolean alimentados;

    private Establo() {
        animales = new ArrayList<>();
        cargarEstablo();
        alimentados = false;
    }

    public void nuevoDia(){
        for(Animal a:animales){
            a.setAlimentado(false);
        }
    }

    public void cargarEstablo() {

        GestionBBDD g = GestionBBDD.getInstance();
        cargarAnimales(g);

        alimentados = false;

    }

    public void alimentar() {

        boolean todosAlimentados = true;
        for (Animal a : animales) {

            if(!a.alimentar()){
                todosAlimentados = false;
            }
        }
        if(todosAlimentados){
            System.out.println("Todos los animales han sido alimentados");
        }else{
            System.out.println("No todos los animales han sido alimentados");
        }
    }

    public void producir() {

        if (!alimentados) {

            GestionBBDD g = GestionBBDD.getInstance();
            int cantidadLeche = 0;
            int cantidadHuevos = 0;
            int cantidadLana = 0;
            int cantidadTrufa = 0;
            try {
                cantidadLeche = g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 1).getInt("cantidad_disponible");
                cantidadHuevos = g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 2).getInt("cantidad_disponible");
                cantidadLana = g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 3).getInt("cantidad_disponible");
                cantidadTrufa = g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 4).getInt("cantidad_disponible");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Animal a : animales) {

                a.producir();
            }

            try {
                System.out.println("Se han producido " + (g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 1).getInt("cantidad_disponible") - cantidadLeche) + " unidades de leche.");
                System.out.println("Se han producido " + (g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 2).getInt("cantidad_disponible") - cantidadHuevos) + " huevos.");
                System.out.println("Se han producido " + (g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 3).getInt("cantidad_disponible") - cantidadLana) + " unidades de Lana.");
                System.out.println("Se han producido " + (g.select("SELECT cantidad_disponible FROM Productos WHERE ID = ?;", 4).getInt("cantidad_disponible") - cantidadTrufa) + " unidades de Trufa.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public float[] venderProductos(){

        GestionBBDD g = GestionBBDD.getInstance();
        float[] dinero = new float[nombresProductos.length];
        ResultSet resultado;
        int cantidad[] = new int[nombresProductos.length];
        float precio;

        for(int i = 0;i< nombresProductos.length;i++) {

            try {
                resultado = g.select("SELECT cantidad_disponible,precio FROM Productos WHERE ID = ?;", i);
                cantidad[i] =  resultado.getInt("cantidad_disponib");
                precio = resultado.getFloat("precio");
                dinero[i] = cantidad[i]*precio;
                g.update("UPDATE Productos SET cantidad_disponible = ? WHERE id = ?;",0,i);

                registrarTransaccion(TipoTransaccion.Venta, TipoProducto.PRODUCTO,dinero[i],Timestamp.valueOf(LocalDateTime.now()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        for(int i=0;i<dinero.length;i++){
            System.out.println("Se han vendido " +cantidad[i] + " unidades de " + nombresProductos[i] + " por " + dinero[i] + "€");
        }


        return dinero;
    }

    public void registrarTransaccion(TipoTransaccion tipoT, TipoProducto tipoP, float cantidad, Timestamp now){
        GestionBBDD g = GestionBBDD.getInstance();
        g.update("INSERT INTO Transacciones(tipo_transaccion,tipo_elemento,precio) values ?,?,?;",tipoT,tipoT,cantidad,now);
    }

    public void rellenarComedero() {
        GestionBBDD g = GestionBBDD.getInstance();
        int cantidadAct = 0;

        for (int i = 0; i < nombresProductos.length; i++) {
            try {
                cantidadAct = g.select("SELECT cantidad_disponible from Alimentos WHERE id = ?", i).getInt("cantidad_disponible");
                comprarAlimento(i, cantidadAct);
            } catch (SQLException sql) {
                System.out.println("Error al obtener cantidad disponible para rellenar comederos");
            }
        }
    }

    public void comprarAlimento(int id,int cantidadAct) {
        GestionBBDD g = GestionBBDD.getInstance();
        int presupuesto = Granja.getInstance().getPresupuesto();
        int cantidadComprar = Constantes.CANTIDAD_MAX_ALIMENTO - cantidadAct;
        if (cantidadAct != 25) {
            try {
                float precio = g.select("SELECT precio FROM Alimentos WHERE id =?;", id).getFloat("precio");
                if (precio * cantidadComprar < presupuesto) {
                    Granja.getInstance().setPresupuesto(presupuesto -= precio * cantidadComprar);
                    g.update("UPDATE Alimentos SET cantidad_disponible = ? WHERE id = ?;",Constantes.CANTIDAD_MAX_ALIMENTO,id);
                    System.out.println("Se han comprado " + cantidadComprar + " unidades de " + nombresProductos[id]);
                } else {
                    System.out.println("No se ha podido comprar " + nombresProductos + " . No nos alcanza el presupuesto.");
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener el precio para comprar Alimentos");
            }
        }else{
            System.out.println("El alimento " + nombresProductos[id] + " se encuentra a capacidad maxima");
        }
    }


    public static Establo getInstance() {
        if (instance == null) {
            instance = new Establo();
        }
        return instance;
    }

    public void cargarAnimales(GestionBBDD g) {

        this.animales.addAll(g.getAnimales(g.select("SELECT * FROM Animales an JOIN Alimentos al ON an.id_alimento=al.id JOIN Productos p ON an.id_producto=p.id;")));

    }

    public void mostrarAnimales() {
        System.out.println("ANIMALES");
        System.out.println("------------------------------------------------------");
        for (Animal a : this.animales) {
            System.out.println(a.toString());
        }

        System.out.println("------------------------------------------------------");
        System.out.println(" ");
    }

}
