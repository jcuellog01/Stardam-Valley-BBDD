package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Animal {

    private int id;
    private String nombre;
    private Timestamp diaInsercion;
    float peso;
    private Tipo tipo;
    private Alimento alimento;
    private Producto producto;
    boolean alimentado;

    public Animal() {
        this.id = -1;
        this.nombre = "";
        this.tipo=Tipo.GALLINA;
        this.diaInsercion = Timestamp.from(Instant.now());
        this.alimento = new Alimento();
        this.producto = new Producto();
        alimentado = false;

    }

    public Animal(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Tipo tipo, Producto producto) {
        this.id = id;
        this.nombre = nombre;
        this.diaInsercion = diaInsercion;
        this.alimento = alimento;
        this.producto = producto;
        this.tipo = tipo;
        this.alimentado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getDiaInsercion() {
        return diaInsercion;
    }

    public void setDiaInsercion(Timestamp diaInsercion) {
        this.diaInsercion = diaInsercion;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public boolean getAlimentado() {
        return alimentado;
    }

    public void setAlimentado(boolean alimentado) {
        this.alimentado = alimentado;
    }

    public void alimentar() {

        if(!alimentado) {
            int cantidadMax;
            int cantidadConsumida = 0;
            ResultSet resultado = GestionBBDD.getInstance().select("SELECT cantidad_disponible FROM Alimentos al JOIN Animales an ON al.id=an.id_alimento where an.id=?", this.id);
            try {
                cantidadMax = resultado.getInt("cantidad_disponible");
                if (cantidadMax >= Constantes.ALIMENTO_OGC) {
                    alimentado = true;
                    GestionBBDD gestionBBDD = GestionBBDD.getInstance();
                    gestionBBDD.update("UPDATE Alimentos SET cantidad_disponible = ? WHERE id = ?", cantidadMax - Constantes.ALIMENTO_OGC, this.getAlimento().getId());

                }
            } catch (SQLException e) {
                System.out.println("Error al obtener cantidad disponible de un alimento");
            }

            registrarConsumo(this, cantidadConsumida, Timestamp.valueOf(LocalDateTime.now()));
        }
    }

    protected void registrarConsumo(Animal a,int cantidadConsumida,Timestamp now){
        GestionBBDD g = GestionBBDD.getInstance();
        g.update("INSERT INTO HistorialConsumo (id_animal,cantidad_consumida,fecha_consumo) values(?,?,?)",this.id,cantidadConsumida,now);

    }

    public void registrarProduccion(Animal animal, int cantidadConsumida,Timestamp now){
        GestionBBDD g = GestionBBDD.getInstance();
        g.update("INSERT INTO HistorialProduccion (id_animal,cantidad_producida,fecha_produccion) values(?,?,?)",animal.getId(),cantidadConsumida,now);
    }

    public abstract void producir();

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", diaInsercion=" + diaInsercion +
                ", peso=" + peso +
                ", tipo=" + tipo +
                ", alimento=" + alimento +
                ", producto=" + producto +
                '}';
    }
}
