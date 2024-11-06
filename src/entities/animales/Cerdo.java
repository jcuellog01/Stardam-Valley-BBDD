package entities.animales;

import Utils.Constantes;
import entities.huerto.Estacion;

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

    public ArrayList<Producto> producir(Estacion estacion){
        ArrayList<Producto> productos = new ArrayList<>();
        int max;
        switch(estacion){
            case Primavera:
            case Verano:
                max = (int)(Math.random()*2)+2;
                for(int i=0;i<max;i++){
                    productos.add(this.getProducto());
                }
                break;
            case Otoño:
                max = (int)(Math.random()* 2);
                for(int i=0;i<max;i++){
                    productos.add(this.getProducto());
                }
                break;
        }
        return productos;
    }
}
