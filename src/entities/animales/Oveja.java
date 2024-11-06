package entities.animales;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Oveja extends Animal {

    public Oveja() {
        super();
    }

    public Oveja(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.OVEJA, producto);
    }

    @Override
    public ArrayList<Producto> producir(){
        return new ArrayList<>();
    }
}
