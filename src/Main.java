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

    public static void mostrarMenuHuerto() {

        System.out.println("1: ATENDER CULTIVOS");
        System.out.println("2: PLANTAR CULTIVOS EN COLUMNA");
        System.out.println("3: VENDER COSECHA");
        System.out.println("4: MOSTRAR INFORMACIÓN DE LA GRANJA");
        System.out.println("5: SALIR");

    }
    public static void mostrarMenuEstablos() {

        System.out.println("1: PRODUCIR");
        System.out.println("2: ALIMENTAR");
        System.out.println("3: VENDER PRODUCTOS");
        System.out.println("4: RELLENAR COMEDEROS");
        System.out.println("5: MOSTRAR ANIMALES");
        System.out.println("6: SALIR");

    }

    public static void mostrarMenuPrincipal() {

        System.out.println("1: INICIAR NUEVO DIA");
        System.out.println("2: HUERTO");
        System.out.println("3: ESTABLOS");
        System.out.println("4: SALIR");

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

    public static void menuPrincipal(){
        int opc;

        do {

            mostrarMenuPrincipal();
            opc = Pedir.pedirInt("Introduce tu opcion: ");
            switch (opc) {

                case 1:
                    //Atender cultivos
                    g.nuevoDia();
                    break;

                case 2:
                    menuHuerto();
                    break;

                case 3:
                    menuEstablos();
                    break;

                case 4:

                    g.mostrarInfo();
                    break;
                case 5:
                    break;
            }
        } while (opc != 5);
        Guardado.guardar(Guardado.getOutput(),g);
    }

    public static void menuHuerto() {

        int opc;

        do {

            mostrarMenuHuerto();
            opc = Pedir.pedirInt("Introduce tu opcion: ");
            switch (opc) {

                case 1:
                    //Atender cultivos
                    g.atenderCultivos();
                    break;

                case 2:
                    //Plantar cultivos en columna
                    g.plantarCultivosColumna();
                    break;

                case 3:
                    //Vender cosecha
                    g.venderCosecha();
                    break;

                case 4:
                    //Mostrar Información de la Granja
                    g.mostrarInfo();
                    break;
                case 5:
                    break;
            }
        } while (opc != 5);
        menuPrincipal();

    }

    public static void menuEstablos() {

        int opc;

        do {

            mostrarMenuEstablos();
            opc = Pedir.pedirInt("Introduce tu opcion: ");
            switch (opc) {

                case 1:
                    //Atender cultivos
                    g.producir();
                    break;

                case 2:
                    //Plantar cultivos en columna
                    g.alimentar();
                    break;

                case 3:
                    //Vender cosecha
                    g.venderProductos();
                    break;

                case 4:
                    //Mostrar Información de la Granja
                    g.rellenarComedero();
                    break;
                case 5:
                    g.mostrarAnimales();
                    break;
            }
        } while (opc != 6);
        menuPrincipal();

    }

    public static void main(String[] args) {
        g=Granja.getInstance();
        menuPartida();

    }
}