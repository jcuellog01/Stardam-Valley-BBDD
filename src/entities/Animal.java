package entities;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

public class Animal {

    private int id;
    private String nombre;
    private Timestamp fecha_insercion;
    private int idAlimento;
    private int idProducto;

    public Animal() {
        this.id = -1;
        this.nombre = "";
        this.fecha_insercion = Timestamp.from(Instant.now());
        this.idAlimento = -1;
        this.idProducto = -1;
    }

    public Animal(int id, String nombre, Timestamp fecha_insercion, int idAlimento, int idProducto) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_insercion = fecha_insercion;
        this.idAlimento = idAlimento;
        this.idProducto = idProducto;
    }
}
