package entities.animales;

import Utils.Constantes;
import entities.huerto.Tienda;

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
    public int producir() {
        LocalDateTime ahora = LocalDateTime.now();
        int cantidadProducida = 0;
        if (alimentado) {
            if (ChronoUnit.DAYS.between(fechaEsquilado, ahora) >= 2) {
                cantidadProducida = Constantes.PRODUCCION_OVEJAS;
                fechaEsquilado = ahora;
            }
            registrarProduccion(this, cantidadProducida, Timestamp.valueOf(ahora));
        }
        return cantidadProducida;
    }

    }

