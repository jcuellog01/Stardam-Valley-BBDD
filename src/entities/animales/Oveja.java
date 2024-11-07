package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.huerto.Tienda;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void producir() {
        LocalDateTime ahora = LocalDateTime.now();
        int cantidadProducida = 0;
        if (this.getAlimentado()) {
            if (ChronoUnit.DAYS.between(fechaEsquilado, ahora) >= 2) {
                cantidadProducida = Constantes.PRODUCCION_OVEJAS;
                fechaEsquilado = ahora;
            }
            registrarProduccion(this, cantidadProducida, Timestamp.valueOf(ahora));
            this.setAlimentado(false);
        }
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

    }

