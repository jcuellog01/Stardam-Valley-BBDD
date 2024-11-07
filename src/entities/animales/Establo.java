package entities.animales;

import BBDD.GestionBBDD;

import java.util.ArrayList;

public class Establo {
    private ArrayList<Animal> animales;
    private static Establo instance;

    private Establo() {
        animales = new ArrayList<>();
    }

    public void cargarEstablo() {

        GestionBBDD g = GestionBBDD.getInstance();
        cargarAnimales(g);

        g.cerrarConexion();

    }

    public void alimentar(){
        for (Animal a : animales) {
            a.alimentar();
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

    public void mostrarAnimales(){
        System.out.println("ANIMALES");
        System.out.println("------------------------------------------------------");
        for(Animal a: this.animales){
            System.out.println(a.toString());
        }

        System.out.println("------------------------------------------------------");
        System.out.println(" ");
    }

}
