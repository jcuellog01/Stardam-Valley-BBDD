package entities.huerto;

import java.io.Serializable;

public enum Estacion implements Serializable {
    Primavera,Verano,Otoño,Invierno;

    public Estacion siguiente() {
        // Obtener el índice del nivel actual
        int siguienteOrdinal = (this.ordinal() + 1) % Estacion.values().length;
        return Estacion.values()[siguienteOrdinal];
    }
}
