package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.Granja;
import entities.huerto.Estacion;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cerdo extends Animal implements Serializable {

    public Cerdo() {
        super();
    }

    public Cerdo(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.CERDO, producto);
    }

    @Override
    public int producir() {
        Estacion estacion = Granja.getInstance().getEstacion();
        int cantidadProducida = 0;
        if (this.getAlimentado()) {
            switch (estacion) {
                case Primavera:
                case Verano:
                    cantidadProducida = (int) (Math.random() * 2) + 2;
                    break;
                case Otoño:
                    cantidadProducida = (int) (Math.random() * 2);
                    break;
            }
            if (cantidadProducida > 0) {
                registrarProduccion(this, cantidadProducida, Timestamp.valueOf(LocalDateTime.now()));
                this.setAlimentado(false);
                almacenar(this.getProducto().getId(), cantidadProducida);
            }
        }

        return cantidadProducida;
    }

    @Override
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
            return true;
        }
    }


}

