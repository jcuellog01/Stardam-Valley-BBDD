package entities.animales;

import java.sql.Timestamp;
import java.time.Instant;
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
        this.alimentado = true;
    }

    public abstract ArrayList<Producto> producir();

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
