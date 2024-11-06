package entities.animales;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Cerdo extends Animal {

    public Cerdo() {
        super();
    }

    public Cerdo(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.CERDO, producto);
    }

    @Override
    public ArrayList<Producto> producir(){
        return new ArrayList<>();
    }
}
