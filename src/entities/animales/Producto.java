package entities.animales;

import java.io.Serializable;

public class Producto implements Serializable {

    private int id;
    private String nombre;
    private float precio;

    public Producto(){
        id=-1;
        nombre="";
        precio=-1F;
    }

    public Producto(int id, String nombre, float precio){
        this.id=id;
        this.nombre=nombre;
        this.precio=precio;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
