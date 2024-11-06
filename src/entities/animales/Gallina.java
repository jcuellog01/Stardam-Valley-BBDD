package entities.animales;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Gallina extends Animal {
    public Gallina() {
        super();
    }

    public Gallina(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.GALLINA, producto);
    }

    @Override
    public ArrayList<Producto> producir(){
        return new ArrayList<>();
    }
}
