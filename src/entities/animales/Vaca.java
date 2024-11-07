package entities.animales;

import BBDD.GestionBBDD;
import Utils.Constantes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Vaca extends Animal implements Serializable {

    int diasJuego;

    public Vaca() {
        super();
        diasJuego = 0;
    }


    public Vaca(int id, String nombre, Timestamp diaInsercion, float peso, Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.VACA, producto);
        this.setPeso(peso);
        diasJuego = 0;
    }

    public int calcularProduccion() {
        return (int) (this.getPeso() / 100);
    }

    @Override
    public int producir() {
        int cantidadProducida = 0;
        if (this.getAlimentado()){

            cantidadProducida = calcularProduccion();
            if (cantidadProducida > 0) {
                registrarProduccion(this, cantidadProducida, Timestamp.valueOf(LocalDateTime.now()));
                this.setAlimentado(false);
                almacenar(this.getProducto().getId(), cantidadProducida);
            }
        }
        return cantidadProducida;

    }
    public int getDiasJuego() {
        return diasJuego;
    }

    public void setDiasJuego(int diasJuego) {
        diasJuego++;
    }

    public int calcularComida() {
        if (diasJuego < 10) {
            return 1;
        } else if (diasJuego < 40) {
            return 3;
        } else {
            return 2;
        }
    }

    @Override
    public boolean alimentar() {
        GestionBBDD g = GestionBBDD.getInstance();
        if (!this.getAlimentado()) {
            int cantidadMax = 0;

            int alimentoId = this.getAlimento().getId();
            System.out.println("ID de alimento: " + alimentoId);

            cantidadMax = g.obtenerCantidadAlimento(this.getAlimento().getId());
            if (cantidadMax >= calcularComida()) {
                this.setAlimentado(true);
                g.updateCantidadAlimento(cantidadMax - calcularComida(), alimentoId);

            } else {
                System.out.println("No hay suficiente cantidad de alimento disponible.");
                return false;
            }
            registrarConsumo(this, calcularComida(), Timestamp.valueOf(LocalDateTime.now()));
            return true;
        } else {
            return true;
        }
    }

}
