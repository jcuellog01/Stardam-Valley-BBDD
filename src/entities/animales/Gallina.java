package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.Granja;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Gallina extends Animal implements Serializable {

    private int diaInsercionInt;

    public Gallina() {
        super();
        diaInsercionInt=Granja.getInstance().getDiaActual();
    }

    public Gallina(int id, String nombre, Timestamp diaInsercion, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.GALLINA, producto);
        diaInsercionInt=Granja.getInstance().getDiaActual();
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
            return true;
        }
    }

    @Override
    public int producir() {
        int cantidadProducida = 0;

        // Verificar si la gallina ha sido alimentada
        if (this.getAlimentado()) {
            // Calcular los días transcurridos desde el día de inserción
            int diasTranscurridos = Granja.getInstance().getDiaActual() - diaInsercionInt;

            // Condición para comenzar la producción a partir del tercer día
            if (diasTranscurridos >= Constantes.DIAS_MIN_GALLINAS_NUEVAS) {
                if (diasTranscurridos < Constantes.DIAS_MAX_GALLINAS_NUEVAS) {
                    // Producción de gallinas nuevas
                    cantidadProducida = Constantes.PRODUCCION_GALLINAS_NUEVAS;
                } else {
                    // Producción de gallinas viejas
                    cantidadProducida = Constantes.PRODUCCION_GALLINAS_VIEJAS;
                }

                // Registrar la producción si se produjo algo
                if (cantidadProducida > 0) {
                    registrarProduccion(this, cantidadProducida, Timestamp.valueOf(LocalDateTime.now()));
                    this.setAlimentado(false); // Resetear el estado de alimentación después de producir
                    almacenar(this.getProducto().getId(), cantidadProducida); // Almacenar los huevos producidos
                }
            }
        }
        return cantidadProducida;
    }

    public void incrementarDiasInsertada() {
        this.diaInsercionInt++;
    }

    public void setDiaInsercionInt(int diaInsercionInt) {
        this.diaInsercionInt = diaInsercionInt;
    }

    public int getDiaInsercionInt() {
        return diaInsercionInt;
    }
}
