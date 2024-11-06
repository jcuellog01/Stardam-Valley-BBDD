package entities;

import Ficheros.Guardado;
import Ficheros.HuertoDAT;
import Ficheros.XML;
import Utils.Pedir;
import entities.animales.Establo;
import entities.huerto.Almacen;
import entities.huerto.Estacion;
import entities.huerto.Semilla;
import entities.huerto.Tienda;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Granja implements Serializable {

    private int diaActual;
    private Estacion estacion;
    private int duracionEstacion;
    private int presupuesto;
    private int filasHuerto;
    private int columnasHuerto;
    private Tienda tienda;
    private Almacen almacen;
    private  ArrayList<Semilla> semillasDisponibles;
    private  HashMap<Estacion, ArrayList<Semilla>> semillasPorEstacion;
    private  HashMap<Integer, Semilla> semillasPorId;
    private  Semilla semillaComprada;
    private Establo e;
    private static Granja instance; //si un atributo es estatico, ya es transient por definicion

    private Granja() {
        this.diaActual = 0;
        estacion = null;
        duracionEstacion = 0;
        presupuesto = 0;
        filasHuerto=0;
        columnasHuerto=0;
        this.tienda = new Tienda();
        this.almacen = new Almacen();
        this.semillaComprada = new Semilla();
        Document doc = XML.crearXML();
        this.semillasDisponibles = XML.getSemillas(doc);
        this.semillasPorEstacion = XML.getSemillasEstacion(doc);
        this.semillasPorId = XML.getSemillasId(doc);
        this.e=Establo.getInstance();

    }

    public void copiarGranja(Granja aux){
        this.diaActual = aux.diaActual;
        estacion = aux.estacion;
        duracionEstacion = aux.duracionEstacion;
        presupuesto = aux.presupuesto;
        filasHuerto=aux.filasHuerto;
        columnasHuerto=aux.columnasHuerto;
        this.tienda = aux.tienda;
        this.almacen = aux.almacen;
        this.semillasDisponibles = aux.semillasDisponibles;
        this.semillasPorEstacion = aux.semillasPorEstacion;
        this.semillasPorId = aux.semillasPorId;
        this.semillaComprada = aux.semillaComprada;
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Deserializa los campos principales

        // Asegura la inicialización de listas y mapas si están en null para evitar errores posteriores
        if (semillasDisponibles == null) {
            semillasDisponibles = new ArrayList<>();
        }
        if (semillasPorEstacion == null) {
            semillasPorEstacion = new HashMap<>();
        }
        if (semillasPorId == null) {
            semillasPorId = new HashMap<>();
        }

        // Inicializa tienda y almacén si están en null, para evitar NullPointerException al usarlos
        if (tienda == null) {
            tienda = new Tienda();
        }
        if (almacen == null) {
            almacen = new Almacen();
        }

    }


    public void cargarPartida() {
        if (Guardado.existePartida()) {
            // Carga propiedades solo si no se ha cargado previamente

            Granja cargada = Guardado.cargar(Objects.requireNonNull(Guardado.getInput()));
            if (cargada != null) {
                instance.copiarGranja(cargada);
                if (instance.estacion != null && !instance.semillasPorEstacion.isEmpty()) {
                    instance.generarSemillas();
                }
            }
        }
    }




        public void cargarNueva() {

        this.inicializarHuerto();
        this.generarSemillas();

    }

    public static Granja getInstance() {
        if (instance == null) {
            instance = new Granja();
        }
        return instance;
    }

    public int getFilasHuerto() {
        return filasHuerto;
    }

    public void setFilasHuerto(int filasHuerto) {
        this.filasHuerto = filasHuerto;
    }

    public Establo getEstablo() {
        return e;
    }

    public int getColumnasHuerto() {
        return columnasHuerto;
    }

    public void setColumnasHuerto(int columnasHuerto) {
        this.columnasHuerto = columnasHuerto;
    }

    public int getDiaActual() {
        return diaActual;
    }

    public void setDiaActual(int diaActual) {
        this.diaActual = diaActual;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getDuracionEstacion() {
        return duracionEstacion;
    }

    public void setDuracionEstacion(int duracionEstacion) {
        this.duracionEstacion = duracionEstacion;
    }


    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public ArrayList<Semilla> getSemillasDisponibles() {
        return semillasDisponibles;
    }

    public void setSemillasDisponibles(ArrayList<Semilla> semillasDisponibles) {
        this.semillasDisponibles = semillasDisponibles;
    }

    public HashMap<Integer, Semilla> getSemillasPorId() {
        return semillasPorId;
    }

    public void setSemillasPorId(HashMap<Integer, Semilla> semillasPorId) {
        this.semillasPorId = semillasPorId;
    }

    public HashMap<Estacion, ArrayList<Semilla>> getSemillasPorEstacion() {
        return semillasPorEstacion;
    }

    public void setSemillasPorEstacion(HashMap<Estacion, ArrayList<Semilla>> semillasPorEstacion) {
        this.semillasPorEstacion = semillasPorEstacion;
    }

    public Semilla getSemillaComprada() {
        return semillaComprada;
    }

    public void setSemillaComprada(Semilla semillaComprada) {
        this.semillaComprada = semillaComprada;
    }

    public void nuevaPartida() {
        if (Guardado.existePartida()) {
            Guardado.delete();
        }
        Guardado.nuevaPartida();
        cargarNueva();
    }

    public static boolean existePartida() {
        return Guardado.existePartida();
    }


    public void atenderCultivos() {
        HashMap<Integer, Integer> mapa = HuertoDAT.atenderCultivos(semillasPorId);

        if (!mapa.isEmpty())
            almacen.almacenar(mapa);

    }

    public void analizarCultivos() {
        HuertoDAT.analizarCultivos();
    }

    public void plantarCultivosColumna() {
        int col = Pedir.pedirInt("¿Que columna desea cultivar?");
        col--;
        if (!isPlantada(col) && col<this.columnasHuerto && col>=0) {
            this.presupuesto = presupuesto - tienda.vender(semillasDisponibles, presupuesto);
            this.semillaComprada = tienda.getSemillaComprada();
            HuertoDAT.plantarSemillas(this.semillaComprada, col);
            this.semillaComprada = null;
        }else{
            System.out.println("La columna a plantar no es válida");
        }
    }

    public boolean isPlantada(int col){
        return HuertoDAT.isPlantada(col);
    }



    public void venderCosecha() {
        this.setPresupuesto(presupuesto + almacen.vender(semillasPorId));
        almacen.getMapa().clear();
    }

    public void mostrarInfo() {
        System.out.println("INFORMACION DE LA GRANJA: ");
        System.out.println("- Día de juego: " + this.diaActual);
        System.out.println("- Dinero disponible: " + this.presupuesto + " pesetas");
        System.out.println("- Estacion: " + this.estacion);
        System.out.println("- Semillas en venta: ");
        tienda.mostrarSemillasEnVenta(semillasDisponibles);
        System.out.println(" ");
        System.out.print("- Frutos en almacén: ");
        almacen.mostrarAlmacen(this.semillasPorId);
        System.out.println("- Estado del huerto: ");
        HuertoDAT.mostrarHuerto();

    }

    public void inicializarHuerto() {
        HuertoDAT.inicializar();
    }

    public void generarSemillas() {
        semillasDisponibles = Tienda.generarSemillas(semillasPorEstacion.get(estacion));
    }

    public void nuevoDia() {
        diaActual++;
        if (diaActual > duracionEstacion) {
            estacion = estacion.siguiente();
            inicializarHuerto();
        }
        analizarCultivos();
        this.generarSemillas();
    }

    @Override
    public String toString() {
        return "Granja{" +
                "diaActual=" + diaActual +
                ", estacion=" + estacion +
                ", duracionEstacion=" + duracionEstacion +
                ", presupuesto=" + presupuesto +
                ", filasHuerto=" + filasHuerto +
                ", columnasHuerto=" + columnasHuerto +
                ", tienda=" + tienda +
                ", almacen=" + almacen +
                ", semillasDisponibles=" + semillasDisponibles +
                ", semillasPorEstacion=" + semillasPorEstacion +
                ", semillasPorId=" + semillasPorId +
                ", semillaComprada=" + semillaComprada +
                '}';
    }
}
