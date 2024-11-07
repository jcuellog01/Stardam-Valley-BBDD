import Ficheros.Guardado;
import Utils.Pedir;
import entities.Granja;

import java.util.Objects;

public class Main {

    private static Granja g;

    public static void mostrarMenuPartida(boolean guardada) {
        System.out.println("BIENVENIDO A STARDAM VALLEY");
        System.out.println("--------------------------------");
        System.out.println("1: NUEVA PARTIDA");
        if (guardada)
            System.out.println("2: CARGAR PARTIDA");

    }

    public static void mostrarMenuPrincipal() {
        System.out.println("STARDAM VALLEY");
        System.out.println("--------------------------------");
        System.out.println("1: INICIAR NUEVO DIA");
        System.out.println("2: ATENDER CULTIVOS");
        System.out.println("3: PLANTAR CULTIVOS EN COLUMNA");
        System.out.println("4: VENDER COSECHA");
        System.out.println("5: MOSTRAR INFORMACIÓN DE LA GRANJA");
        System.out.println("6: SALIR");

    }

    public static void menuPartida() {

        mostrarMenuPartida(Granja.existePartida());
        int opc = Pedir.pedirInt("Introduce tu opcion: ");
        if (Granja.existePartida()) {
            switch (opc) {
                case 1:
                    g.nuevaPartida();
                    menuPrincipal();
                    break;
                case 2:
                    g.cargarPartida();
                    menuPrincipal();
                    break;
            }
        } else {

            g.nuevaPartida();
            menuPrincipal();

        }
    }

    public static void menuPrincipal() {

        int opc;

        do {

            mostrarMenuPrincipal();
            opc = Pedir.pedirInt("Introduce tu opcion: ");
            switch (opc) {
                case 1:
                    //Nuevo día
                    g.nuevoDia();
                    break;

                case 2:
                    //Atender cultivos
                    g.atenderCultivos();
                    break;

                case 3:
                    //Plantar cultivos en columna
                    g.plantarCultivosColumna();
                    break;

                case 4:
                    //Vender cosecha
                    g.venderCosecha();
                    break;

                case 5:
                    //Mostrar Información de la Granja
                    g.mostrarInfo();
                    break;
                case 6:
                    break;
            }
        } while (opc != 6);

        Guardado.guardar(Objects.requireNonNull(Guardado.getOutput()), g);
    }

    public static void main(String[] args) {

        g = Granja.getInstance();
        g.getEstablo().cargarEstablo();
        g.getEstablo().mostrarAnimales();
        //menuPartida();

    }
}