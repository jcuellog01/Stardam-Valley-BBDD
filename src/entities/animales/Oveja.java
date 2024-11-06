package entities.animales;

import Utils.Constantes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Oveja extends Animal {

    private LocalDateTime fechaEsquilado;
    public Oveja() {
        super();
        fechaEsquilado = LocalDateTime.now();
    }

    public Oveja(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.OVEJA, producto);
        fechaEsquilado = LocalDateTime.now();
    }

    @Override
    public ArrayList<Producto> producir(){
        LocalDateTime ahora = LocalDateTime.now();
        ArrayList<Producto> productos = new ArrayList<>();
        if(ChronoUnit.DAYS.between(fechaEsquilado, ahora) >= 2){
            productos = new ArrayList<>();
            for(int i = 0; i< Constantes.PRODUCCION_OVEJAS; i++){
                productos.add(this.getProducto());
            }
        }
        return productos;
    }
}
