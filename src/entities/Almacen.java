package entities;

import java.io.Serializable;
import java.util.HashMap;

public class Almacen implements Serializable {
    private HashMap<Integer,Integer> mapa;

    public Almacen() {
        mapa = new HashMap<>();
    }

    public HashMap<Integer,Integer> getMapa() {
        return mapa;
    }

    public int vender(HashMap<Integer,Semilla> semillasPorId){

        int contador=0;

            for (Integer clave: mapa.keySet()) {
                contador+=semillasPorId.get(clave).getPrecioVenta() * mapa.get(clave);
                System.out.println("Se han vendido " +  mapa.get(clave) +" unidades de " + semillasPorId.get(clave).getNombre());
            }

        mapa.clear();
        return contador;
    }


    public void almacenar(HashMap<Integer, Integer> semillasCosechadas) {
        for (int id : semillasCosechadas.keySet()) {
            int cantidad = semillasCosechadas.get(id);

            if (mapa.containsKey(id)) {
                mapa.replace(id, mapa.get(id) + cantidad);
            } else {
                mapa.put(id, cantidad);
            }
        }
    }

    public void mostrarAlmacen(HashMap<Integer,Semilla> mapa){
        for(Integer id:this.mapa.keySet()){
            System.out.println("["+ (mapa.get(id).getNombre()) + " - " + this.mapa.get(id) +"]");
        }
    }

}
