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
    private final String[] nombresAlimentos = {"Maiz", "Avena", "Heno"};
    boolean alimentados;

    public Establo() {
        animales = new ArrayList<>();
        alimentados = false;
    }

    public void nuevoDia() {
        for (Animal a : animales) {
            a.setAlimentado(false);
        }
    }

    public void cargarEstablo() {

        cargarAnimales();
        alimentados = false;

    }

    public void alimentar() {

        boolean todosAlimentados = true;
        for (Animal a : animales) {

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


            GestionBBDD g = GestionBBDD.getInstance();
            int cantidadLecheDisponible =  g.obtenerCantidadProducto(1);
            int cantidadHuevosDisponible = g.obtenerCantidadProducto(2);
            int cantidadLanaDisponible = g.obtenerCantidadProducto(3);
            int cantidadTrufaDisponible = g.obtenerCantidadProducto(4);

            for (Animal a : animales) {

                a.producir();
            }

            System.out.println("Se han producido " + (g.obtenerCantidadProducto(1) -cantidadLecheDisponible) + " unidades de leche.");
            System.out.println("Se han producido " + (g.obtenerCantidadProducto(2)-cantidadHuevosDisponible) + " huevos.");
            System.out.println("Se han producido " + (g.obtenerCantidadProducto(3)-cantidadLanaDisponible) + " unidades de Lana.");
            System.out.println("Se han producido " + (g.obtenerCantidadProducto(4)-cantidadTrufaDisponible) + " unidades de Trufa.");

    }

    public float venderProductos() {

        GestionBBDD g = GestionBBDD.getInstance();
        float[] dinero = new float[Constantes.CANTIDAD_PRODUCTOS];
        float total=0;
        ResultSet resultado;
        int cantidad[] = new int[Constantes.CANTIDAD_PRODUCTOS];
        float precio;

        for (int i = 1; i <= Constantes.CANTIDAD_PRODUCTOS; i++) {

            cantidad[i-1] = g.obtenerCantidadProducto(i);
            precio = g.obtenerPrecioProducto(i);
            dinero[i-1] = cantidad[i-1] * precio;
            g.venderProducto(i);
            total+=cantidad[i-1]*precio;


        }

        registrarTransaccion(TipoTransaccion.VENTA, TipoProducto.PRODUCTO, total, Timestamp.valueOf(LocalDateTime.now()));
        for (int i = 0; i < dinero.length; i++) {
            System.out.println("Se han vendido " + cantidad[i] + " unidades de " + nombresProductos[i] + " por " + dinero[i] + "€");
        }


        return total;
    }

    public void registrarTransaccion(TipoTransaccion tipoT, TipoProducto tipoP, float cantidad, Timestamp now) {
        GestionBBDD.getInstance().registrarTransaccion(tipoT,tipoP,cantidad);
    }

    public float rellenarComedero() {
        GestionBBDD g = GestionBBDD.getInstance();
        int cantidadAct = 0;
        float precio = 0;
        String[] alimentos = {"Maiz","Avena","Heno"};

        for (int i = 1; i <=alimentos.length ; i++) {

            cantidadAct = g.obtenerCantidadAlimento(i);
            comprarAlimento(i, cantidadAct);
            precio+=cantidadAct*g.obtenerPrecioAlimento(i);


        }
        return precio;
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
                System.out.println("Se han comprado " + cantidadComprar + " unidades de " + nombresAlimentos[id-1]);
            } else {
                System.out.println("No se ha podido comprar " + nombresAlimentos[id-1] + " . No nos alcanza el presupuesto.");
            }
        } else {
            System.out.println("El alimento " + nombresAlimentos[id-1] + " se encuentra a capacidad maxima");
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
