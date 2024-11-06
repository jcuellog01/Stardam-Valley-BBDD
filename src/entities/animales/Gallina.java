package entities.animales;

import Utils.Constantes;
import entities.Granja;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Gallina extends Animal {

    private int diaInsercionInt;
    public Gallina() {
        super();
        diaInsercionInt = 0;
    }

    public Gallina(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.GALLINA, producto);
        diaInsercionInt= Granja.getInstance().getDiaActual();
    }

    @Override
    public ArrayList<Producto> producir(){

        ArrayList<Producto> productos= new ArrayList<>();
        int resta = Granja.getInstance().getDiaActual()-diaInsercionInt;
        if(resta>Constantes.DIAS_MIN_GALLINAS_NUEVAS && resta <Constantes.DIAS_MAX_GALLINAS_NUEVAS){
            for(int i=0;i< Constantes.PRODUCCION_GALLINAS_NUEVAS;i++){
                productos.add(this.getProducto());
            }
        }
        return productos;
    }

    public void setDiaInsercionInt(int diaInsercionInt) {
        this.diaInsercionInt = diaInsercionInt;
    }

    public int getDiaInsercionInt() {
        return diaInsercionInt;
    }
}
