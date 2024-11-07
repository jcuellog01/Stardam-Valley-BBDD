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
    private final String[] nombresProductos = {"Leche", "Huevos", "Lana", "Trufa"};
    boolean alimentados;

    public Establo() {
        animales = new ArrayList<>();
        cargarEstablo();
        alimentados = false;
    }

    public void nuevoDia() {
        for (Animal a : animales) {
            a.setAlimentado(false);
            if (a.getTipo() == Tipo.GALLINA) {
                ((Gallina) a).incrementarDiasInsertada();
            }
        }
    }

    public void cargarEstablo() {

        cargarAnimales();

        alimentados = false;

    }

    public void alimentar() {

        boolean todosAlimentados = true;
        for (Animal a : animales) {

            if (a.getTipo() == Tipo.VACA) {
                a = (Vaca) a;
                a.alimentar();
            }
            if (!a.alimentar()) {
                todosAlimentados = false;
            }
        }
        if (todosAlimentados) {
            System.out.println("Todos los animales han sido alimentados");
        } else {
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
            cantidadLeche = g.obtenerCantidadAlimento(1);
            cantidadLeche = g.obtenerCantidadAlimento(2);
            cantidadLeche = g.obtenerCantidadAlimento(3);
            cantidadLeche = g.obtenerCantidadAlimento(4);

            for (Animal a : animales) {

                a.producir();
            }
            System.out.println("Se han producido " + (g.obtenerCantidadAlimento(1) - cantidadLeche) + " unidades de leche.");
            System.out.println("Se han producido " + (g.obtenerCantidadAlimento(2) - cantidadHuevos) + " huevos.");
            System.out.println("Se han producido " + (g.obtenerCantidadAlimento(3) - cantidadLana) + " unidades de Lana.");
            System.out.println("Se han producido " + (g.obtenerCantidadAlimento(1) - cantidadTrufa) + " unidades de Trufa.");

        }
    }

    public float[] venderProductos() {

        GestionBBDD g = GestionBBDD.getInstance();
        float[] dinero = new float[nombresProductos.length];
        ResultSet resultado;
        int cantidad[] = new int[nombresProductos.length];
        float precio;

        for (int i = 0; i < nombresProductos.length; i++) {

            cantidad[i] = g.obtenerCantidadAlimento(i);
            precio = g.obtenerPrecioAlimento(i);
            dinero[i] = cantidad[i] * precio;
            g.venderProducto(i);

            registrarTransaccion(TipoTransaccion.Venta, TipoProducto.PRODUCTO, dinero[i], Timestamp.valueOf(LocalDateTime.now()));

        }
        for (int i = 0; i < dinero.length; i++) {
            System.out.println("Se han vendido " + cantidad[i] + " unidades de " + nombresProductos[i] + " por " + dinero[i] + "€");
        }


        return dinero;
    }

    public void registrarTransaccion(TipoTransaccion tipoT, TipoProducto tipoP, float cantidad, Timestamp now) {
        GestionBBDD g = GestionBBDD.getInstance();
        g.update("INSERT INTO Transacciones(tipo_transaccion,tipo_elemento,precio) values ?,?,?;", tipoT, tipoT, cantidad, now);
    }

    public void rellenarComedero() {
        GestionBBDD g = GestionBBDD.getInstance();
        int cantidadAct = 0;

        for (int i = 0; i < nombresProductos.length; i++) {

            cantidadAct = g.obtenerCantidadAlimento(i);
            comprarAlimento(i, cantidadAct);

        }
    }

    public void comprarAlimento(int id, int cantidadAct) {
        GestionBBDD g = GestionBBDD.getInstance();
        int presupuesto = Granja.getInstance().getPresupuesto();
        int cantidadComprar = Constantes.CANTIDAD_MAX_ALIMENTO - cantidadAct;
        if (cantidadAct != 25) {

            float precio = g.obtenerPrecioAlimento(id);
            if (precio * cantidadComprar < presupuesto) {
                Granja.getInstance().setPresupuesto(presupuesto -= precio * cantidadComprar);
                g.updateCantidadAlimento(Constantes.CANTIDAD_MAX_ALIMENTO,id);
                System.out.println("Se han comprado " + cantidadComprar + " unidades de " + nombresProductos[id]);
            } else {
                System.out.println("No se ha podido comprar " + nombresProductos + " . No nos alcanza el presupuesto.");
            }
        } else {
            System.out.println("El alimento " + nombresProductos[id] + " se encuentra a capacidad maxima");
        }
    }

    public void cargarAnimales() {
        GestionBBDD g = GestionBBDD.getInstance();
        this.animales.addAll(g.obtenerAnimales());

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
