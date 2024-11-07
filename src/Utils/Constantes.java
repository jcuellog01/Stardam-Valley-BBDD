package Utils;

import java.io.Serializable;

public class Constantes implements Serializable {

    public static final int MAX_ESTACIONES_SEMILLA = 2;
    public static final String RUTA_XML = "resources/semillas.xml";
    public static final String RUTA_HUERTO = "resources/huerto.dat";
    public static final String RUTA_GUARDADO = "resources/stardam_valley.bin";
    public static final String RUTA_PROPERTIES = "resources/default_config.properties";
    public static final String RUTA_PROPERTIES_PERSONALIZADO = "resources/personalized_config.properties";
    public static final int TAMANIO_SEMILLA_HUERTO = Integer.BYTES + 1 + Integer.BYTES;
    public static final String LECTURA = "r";
    public static final String LECTURA_Y_ESCRITURA = "rw";
    public static final int TAMANIO_INT = Integer.BYTES;
    public static final int VALOR_POR_DEFECTO_INT = -1;
    public static final boolean VALOR_POR_DEFECTO_BOOLEAN = false;
    public static final int PRODUCCION_OVEJAS = 5;
    public static final int PRODUCCION_GALLINAS_NUEVAS = 2;
    public static final int PRODUCCION_GALLINAS_VIEJAS = 1;
    public static final int DIAS_MIN_GALLINAS_NUEVAS = 3;
    public static final int DIAS_MAX_GALLINAS_NUEVAS = 40;
    public static final int ALIMENTO_OGC = 1;
    public static final int CANTIDAD_MAX_ALIMENTO = 25;

}
