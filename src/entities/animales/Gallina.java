package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.Granja;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public void producir(){

        ArrayList<Producto> productos= new ArrayList<>();
        int resta = Granja.getInstance().getDiaActual()-diaInsercionInt;
        int cantidadProducida = 0;
        if(resta>Constantes.DIAS_MIN_GALLINAS_NUEVAS && resta <Constantes.DIAS_MAX_GALLINAS_NUEVAS){
            cantidadProducida = Constantes.PRODUCCION_GALLINAS_NUEVAS;
        }else if(resta >Constantes.DIAS_MAX_GALLINAS_NUEVAS ){
            cantidadProducida = Constantes.PRODUCCION_GALLINAS_VIEJAS;
        }
        registrarProduccion(this,cantidadProducida,Timestamp.valueOf(LocalDateTime.now()));
    }

    public void setDiaInsercionInt(int diaInsercionInt) {
        this.diaInsercionInt = diaInsercionInt;
    }

    public int getDiaInsercionInt() {
        return diaInsercionInt;
    }
}
