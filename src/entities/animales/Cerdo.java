package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.huerto.Estacion;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void producir(){
    }

    public boolean alimentar() {
        GestionBBDD g = GestionBBDD.getInstance();
        if (!this.getAlimentado()) {
            int cantidadMax = 0;

            int alimentoId = this.getAlimento().getId();
            System.out.println("ID de alimento: " + alimentoId);

            cantidadMax = g.obtenerCantidadAlimento(this.getAlimento().getId());
            if (cantidadMax >= Constantes.ALIMENTO_OGC) {
                this.setAlimentado(true);
                g.updateCantidadAlimento(cantidadMax - Constantes.ALIMENTO_OGC, alimentoId);

            } else {
                System.out.println("No hay suficiente cantidad de alimento disponible.");
                return false;
            }
            registrarConsumo(this, Constantes.ALIMENTO_OGC, Timestamp.valueOf(LocalDateTime.now()));
            return true;
        } else {
            return false;
        }
    }

    public void producir(Estacion estacion) {

        int max = 0;
        if (this.getAlimentado()) {
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
            this.setAlimentado(false);
        }
    }




}
