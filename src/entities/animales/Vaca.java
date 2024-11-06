package entities.animales;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Vaca extends Animal {

    public Vaca() {
        super();
    }


    public Vaca(int id, String nombre, Timestamp diaInsercion, float peso,Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.VACA, producto);
        this.peso = peso;
    }

    public int calcularProduccion(){
        return (int) (peso/100);
    }
    @Override
    public ArrayList<Producto> producir(){
        ArrayList<Producto> producidos = new ArrayList<>();

        for(int i=0;i<calcularProduccion();i++){
            producidos.add(this.getProducto());
        }
        return producidos;
    }


}
