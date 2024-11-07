package entities.animales;

import BBDD.GestionBBDD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Vaca extends Animal {

    int diasJuego;
    public Vaca() {
        super();
        diasJuego=0;
    }


    public Vaca(int id, String nombre, Timestamp diaInsercion, float peso,Alimento alimento, Producto producto) {
        super(id, nombre, diaInsercion, alimento, Tipo.VACA, producto);
        this.peso = peso;
        diasJuego = 0;
    }

    public int calcularProduccion(){
        return (int) (peso/100);
    }
    @Override
    public ArrayList<Producto> producir(){
        ArrayList<Producto> producidos = new ArrayList<>();

        for(int i=0;i<calcularProduccion();i++){
            producidos.add(this.getProducto());
        }
        return producidos;
    }

    public int getDiasJuego() {
        return diasJuego;
    }
    public void setDiasJuego(int diasJuego) {
        diasJuego++;
    }

    public int calcularComida(){

        if(diasJuego<10){
            return 1;
        }else if(diasJuego<40){
            return 3;
        }else{
            return 2;
        }
    }

    @Override
    public void alimentar() {

        int cantidadMax;
        int cantidadConsumida = calcularComida();
        ResultSet resultado = GestionBBDD.getInstance().select("SELECT cantidad_disponible FROM Alimentos al JOIN Animales an ON al.id=an.id_alimento where an.id=?",this.getId());
        try {
            cantidadMax = resultado.getInt("cantidad_disponible");
            if(cantidadMax>= cantidadConsumida){
                alimentado = true;
                GestionBBDD gestionBBDD = GestionBBDD.getInstance();
                gestionBBDD.update("UPDATE Alimentos SET cantidad_disponible = ? WHERE id = ?",cantidadMax-cantidadConsumida,this.getAlimento().getId());

            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cantidad disponible de un alimento");
        }
        super.registrarConsumo(this,cantidadConsumida, Timestamp.valueOf(LocalDateTime.now()));
    }





}
