package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.huerto.Estacion;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cerdo extends Animal {

    public Cerdo() {
        super();
    }

    public Cerdo(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.CERDO, producto);
    }

    @Override
    public int producir(){
        return -1;
    }

    public int producir(Estacion estacion) {

        int max = 0;
        if (alimentado) {
            switch (estacion) {
                case Primavera:
                case Verano:
                    max = (int) (Math.random() * 2) + 2;
                    break;
                case Otoño:
                    max = (int) (Math.random() * 2);
                    break;
            }
            registrarProduccion(this, max, Timestamp.valueOf(LocalDateTime.now()));

        }
        return max;
    }


}
