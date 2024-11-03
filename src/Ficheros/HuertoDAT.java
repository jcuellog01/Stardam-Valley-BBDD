package Ficheros;

import Utils.Constantes;
import entities.Granja;
import entities.Semilla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class HuertoDAT {
    private RandomAccessFile raf;
    private static HuertoDAT instance;
    public final static int COLUMNAS_HUERTO = Granja.getInstance().getColumnasHuerto();
    public final static int FILAS_HUERTO = Granja.getInstance().getFilasHuerto();

    private HuertoDAT(String modo) {

        try {
            raf = new RandomAccessFile(new File(Constantes.RUTA_HUERTO), modo);

        } catch (FileNotFoundException e) {
            System.out.println("Error FNFE al crear el fichero Huerto.dat");
        }

    }

    public static void inicializar() {
        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();

        try {

            raf.seek(0);
            while (raf.getFilePointer() < ((long) FILAS_HUERTO * COLUMNAS_HUERTO * Constantes.TAMANIO_SEMILLA_HUERTO)) {
                raf.writeInt(Constantes.VALOR_POR_DEFECTO_INT);
                raf.writeBoolean(Constantes.VALOR_POR_DEFECTO_BOOLEAN);
                raf.writeInt(Constantes.VALOR_POR_DEFECTO_INT);
            }

        } catch (IOException e) {
            System.out.println("Error IO al inicializar el fichero Huerto.dat");
        }
    }

    public static HuertoDAT getInstance(String modo) {
        if (instance == null) {
            instance = new HuertoDAT(modo);

        }
        return instance;
    }

    public RandomAccessFile getRaf() {

        return raf;
    }

    //calcula cuantos bytes me tengo que mover para ir a la fila fil y columna col
    public static long calcularSalto(int fil, int col) {
        return (((long) fil * COLUMNAS_HUERTO) + col) * Constantes.TAMANIO_SEMILLA_HUERTO;
    }

    //planta la semilla en la posicion indicada
    private static void plantar(Semilla plantar, int fil, int col) {

        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();

        try {
            raf.seek(calcularSalto(fil, col));
            raf.writeInt(plantar.getId());
            raf.writeBoolean(Constantes.VALOR_POR_DEFECTO_BOOLEAN);
            raf.writeInt(0);
        } catch (IOException e) {
            System.out.println("Error IO al escribir el fichero Huerto.dat");
        }
    }

    //planta la lista de semillas en la columna indicada
    public static void plantarSemillas(Semilla semilla, int col) {

        for (int i = 0; i < FILAS_HUERTO; i++) {
            plantar(semilla, i, col);

        }

    }

    public static boolean isPlantada(int col) {
        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();
        try {
            raf.seek(0);
            raf.seek(calcularSalto(0, col));
            return raf.readInt() != Constantes.VALOR_POR_DEFECTO_INT;
        } catch (IOException e) {
            System.out.println("Error al ver si está plantada la columna.");
            return true;
        }
    }

    public static void analizarCultivos() {
        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();
        int idAct;
        int diasPlantada;
        try {
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                idAct = raf.readInt();
                if (idAct != Constantes.VALOR_POR_DEFECTO_INT) {
                    if (raf.readBoolean()) {
                        raf.seek(raf.getFilePointer() - 1);
                        raf.writeBoolean(Constantes.VALOR_POR_DEFECTO_BOOLEAN);
                        diasPlantada = raf.readInt();
                        raf.seek((int) (raf.getFilePointer() - Constantes.TAMANIO_INT));
                        diasPlantada++;
                        raf.writeInt(diasPlantada);
                    } else {
                        raf.skipBytes(Constantes.TAMANIO_INT);
                    }
                } else {
                    raf.skipBytes(1 + Constantes.TAMANIO_INT);
                }
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static HashMap<Integer, Integer> atenderCultivos(HashMap<Integer, Semilla> mapa) {
        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();
        int idAct;
        int diasAct;
        HashMap<Integer, Integer> cosechadosAux = new HashMap<>();
        HashMap<Integer, Integer> frutosCosechados = new HashMap<>();
        Semilla semillaAct;
        int almacenadas;

        try {
            // Iteramos sobre cada columna
            for (int col = 0; col < COLUMNAS_HUERTO; col++) {
                cosechadosAux.clear();

                // Iteramos sobre cada fila dentro de la columna
                for (int fila = 0; fila < FILAS_HUERTO; fila++) {

                    long posicion = ((long) col * FILAS_HUERTO + fila) * Constantes.TAMANIO_SEMILLA_HUERTO;
                    raf.seek(posicion);

                    idAct = raf.readInt();
                    if (idAct != Constantes.VALOR_POR_DEFECTO_INT) {

                        semillaAct = mapa.get(idAct);
                        boolean regado = raf.readBoolean();
                        if (!regado) {
                            //regar(fila);
                            raf.seek(raf.getFilePointer()-1);//valor defecto
                            raf.writeBoolean(true);
                        }

                        diasAct = raf.readInt();
                        if (mapa.containsKey(idAct)) {
                            if (mapa.get(idAct).getDiasCrecimiento() == diasAct) {

                                if (!frutosCosechados.containsKey(semillaAct.getId())) {
                                    frutosCosechados.put(semillaAct.getId(), mapa.get(idAct).recolectar());
                                } else {
                                    almacenadas = frutosCosechados.get(semillaAct.getId());
                                    cosechadosAux.put(semillaAct.getId(), (almacenadas + mapa.get(idAct).recolectar()));
                                }

                                raf.seek(raf.getFilePointer() - Constantes.TAMANIO_SEMILLA_HUERTO);


                                    raf.writeInt(Constantes.VALOR_POR_DEFECTO_INT);
                                    raf.writeBoolean(Constantes.VALOR_POR_DEFECTO_BOOLEAN);
                                    raf.writeInt(Constantes.VALOR_POR_DEFECTO_INT);

                                aniadir(cosechadosAux, frutosCosechados);
                            }
                        }
                    } else {
                        raf.skipBytes(5); // Saltamos el booleano y el siguiente int
                    }
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error al atender cultivos: " + ioe.getMessage());
        }
        return frutosCosechados;
    }

    public static void aniadir(HashMap<Integer, Integer> mapa1, HashMap<Integer, Integer> mapa2) {
        for (Integer i : mapa1.keySet()) {
            mapa2.put(i, mapa1.get(i));
        }
    }

    public static void regar(int col) {
        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();

        int i = 0;
        try {
            raf.seek(0);

            while (i < FILAS_HUERTO) {

                raf.seek(calcularSalto(i, col));

                raf.skipBytes(Constantes.TAMANIO_INT);
                raf.writeBoolean(true);
                raf.skipBytes(Constantes.TAMANIO_INT);

                System.out.println("FILA REGADA");
                i++;
            }

        } catch (IOException e) {
            System.out.println("Error IO al regar el huerto");
        }
    }

    public static void mostrarHuerto() {

        RandomAccessFile raf = getInstance(Constantes.LECTURA_Y_ESCRITURA).getRaf();
        int contador = 0;
        int id;
        try {

            raf.seek(0);
            while (contador < COLUMNAS_HUERTO * FILAS_HUERTO) {
                id = raf.readInt();
                if (id == Constantes.VALOR_POR_DEFECTO_INT) {
                    System.out.print("[SS]");
                    raf.seek(raf.getFilePointer() - Integer.BYTES);
                    raf.skipBytes(Constantes.TAMANIO_SEMILLA_HUERTO);
                } else {
                    System.out.print("[" + id);
                    mostrarBooleano(raf.readBoolean());
                    System.out.print(raf.readInt() + "]");
                }
                contador++;
                if (contador % COLUMNAS_HUERTO == 0) {
                    System.out.println(" ");
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void mostrarBooleano(Boolean b) {
        if (b) {
            System.out.print("T");
        } else {
            System.out.print("F");
        }
    }
}




