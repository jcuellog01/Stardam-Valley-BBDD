package entities.animales;

import BBDD.GestionBBDD;

import java.util.ArrayList;

public class Establo {
    private ArrayList<Animal> animales;
    private ArrayList<Producto> productos;
    private ArrayList<Alimento> alimentos;
    private static Establo instance;

    private Establo() {
        animales = new ArrayList<>();
        productos = new ArrayList<>();
        alimentos = new ArrayList<>();
    }

    public void cargarEstablo() {

        GestionBBDD g = GestionBBDD.getInstance();
        cargarAlimentos(g);
        cargarProductos(g);
        cargarAnimales(g);

        g.cerrarConexion();


    }

    public static Establo getInstance() {
        if (instance == null) {
            instance = new Establo();
        }
        return instance;
    }

    public void cargarAlimentos(GestionBBDD g){
        g = GestionBBDD.getInstance();

        this.alimentos.addAll(g.getAlimentos(g.select("SELECT * FROM Alimentos")));

    }

    public void cargarProductos(GestionBBDD g){
        g = GestionBBDD.getInstance();

        this.productos.addAll(g.getProductos(g.select("SELECT * FROM Productos")));

    }

    public void cargarAnimales(GestionBBDD g) {

        g = GestionBBDD.getInstance();

        this.animales.addAll(g.getAnimales(g.select("SELECT * FROM Animales;"),this.alimentos,this.productos));

    }

    public Alimento getAlimentoId(int id){
        for(Alimento a: this.alimentos){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    public Producto getProductoId(int id){
        for(Producto p: this.productos){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
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

    public void mostrarAlimentos(){
        System.out.println("ALIMENTOS");
        System.out.println("------------------------------------------------------");
        for(Alimento a: this.alimentos){
            System.out.println(a.toString());
        }

        System.out.println("------------------------------------------------------");
        System.out.println(" ");
    }

    public void mostrarProductos(){
        System.out.println("PRODUCTOS");
        System.out.println("------------------------------------------------------");
        for(Producto p: this.productos){
            System.out.println(p.toString());
        }

        System.out.println("------------------------------------------------------");
        System.out.println(" ");
    }
}
