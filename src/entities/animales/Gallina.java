package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;
import entities.Granja;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        diaInsercionInt = 0;
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

    @Override
    public void producir() {

        int cantidadProducida = 0;
        if (this.getAlimentado()) {
            int resta = Granja.getInstance().getDiaActual() - diaInsercionInt;

            if (resta > Constantes.DIAS_MIN_GALLINAS_NUEVAS && resta < Constantes.DIAS_MAX_GALLINAS_NUEVAS) {
                cantidadProducida = Constantes.PRODUCCION_GALLINAS_NUEVAS;
            } else if (resta > Constantes.DIAS_MAX_GALLINAS_NUEVAS) {
                cantidadProducida = Constantes.PRODUCCION_GALLINAS_VIEJAS;
            }
            registrarProduccion(this, cantidadProducida, Timestamp.valueOf(LocalDateTime.now()));
            this.setAlimentado(false);
        }
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
