package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Ficheros.HuertoDAT;
import Utils.Pedir;

public class Tienda implements Serializable {

    private Semilla semillaComprada;

    public Tienda() {
        semillaComprada = new Semilla();
    }

    public Semilla getSemillaComprada() {
        return semillaComprada;
    }

    public static ArrayList<Semilla> generarSemillas(ArrayList<Semilla> semillasDisponibles) {

        int i, j, k;
        boolean enc = false;
        ArrayList<Semilla> semillasTemp = new ArrayList<>();

        if (!semillasDisponibles.isEmpty()) {

            while (!enc) {
                i = (int) (Math.random() * semillasDisponibles.size());
                j = (int) (Math.random() * semillasDisponibles.size());
                k = (int) (Math.random() * semillasDisponibles.size());

                if (i != j && j != k && i != k) {
                    semillasTemp.add(semillasDisponibles.get(i));
                    semillasTemp.add(semillasDisponibles.get(j));
                    semillasTemp.add(semillasDisponibles.get(k));
                    enc = true;
                }
            }
        }

        return semillasTemp;
    }

    private ArrayList<Semilla> getSemillasSegunDinero(ArrayList<Semilla> array, int dinero) {

        Iterator<Semilla> iterator = array.iterator();
        while (iterator.hasNext()) {

            Semilla s = iterator.next();

            if (s.getPrecioCompra() * HuertoDAT.COLUMNAS_HUERTO > dinero) {

                iterator.remove();

            }
        }
        return array;
    }

    public void mostrar(ArrayList<Semilla> semillasSegunDinero) {

        for (int i = 0; i < semillasSegunDinero.size(); i++) {
            System.out.println((i + 1) + ": " + semillasSegunDinero.get(i).getNombre());

        }
    }

    public int vender(ArrayList<Semilla> semillas, int dinero) {

        int dineroGastado = 0;
        int opc;
        ArrayList<Semilla> semillasComprar = getSemillasSegunDinero(semillas, dinero);

        Semilla act;

        System.out.println("TIENDA");

        System.out.println("SEMILLAS DISPONIBLES: ");
        mostrar(getSemillasSegunDinero(semillasComprar, dinero));

        opc = Pedir.pedirInt("¿Qué semilla desea comprar?");
        opc--;

        if (opc < 3 && opc >= 0) {
            act = semillasComprar.get(opc);
            dineroGastado = act.getPrecioCompra() * HuertoDAT.COLUMNAS_HUERTO;
            semillaComprada=act;
        }

        return dineroGastado;
    }

    public void mostrarSemillasEnVenta(ArrayList<Semilla> array) {

        for (Semilla s : array) {
            System.out.println(s.getNombre());
        }
    }
}
