package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Semilla implements Serializable {

    private int id;
    private String nombre;
    private ArrayList<Estacion> estaciones;
    private int precioCompra;
    private int precioVenta;
    private int diasCrecimiento;
    private int maxFrutos;

    public Semilla(){
        id=-1;
        nombre="";
        estaciones=new ArrayList<>();
        precioCompra=-1;
        precioVenta=-1;
        diasCrecimiento=-1;
        maxFrutos=-1;
    }

    public Semilla(int id, String nombre, int precioCompra,int precioVenta, ArrayList<Estacion> estaciones,int diasCrecimiento,int maxFrutos){
        this.id=id;
        this.nombre=nombre;
        this.estaciones=estaciones;
        this.precioCompra=precioCompra;
        this.precioVenta=precioVenta;
        this.diasCrecimiento=diasCrecimiento;
        this.maxFrutos=maxFrutos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Estacion> getEstaciones() {
        return estaciones;
    }

    public void setEstacion(ArrayList<Estacion> array) {

        this.estaciones=array;
    }

    public int getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getDiasCrecimiento(){
        return diasCrecimiento;
    }

    public void setDiasCrecimiento(int diasCrecimiento){
        this.diasCrecimiento = diasCrecimiento;
    }

    public int getMaxFrutos() {
        return maxFrutos;
    }

    public void setMaxFrutos(int maxFrutos) {
        this.maxFrutos = maxFrutos;
    }

    public int recolectar(){
        return (int)(Math.random()*maxFrutos+1);
    }

    public ArrayList<Estacion> toEstacion(String estaciones){
        String[] estacionesS = estaciones.split("-");
        ArrayList<Estacion> array = new ArrayList<>();
        if(estacionesS.length==2){

            array.add(Estacion.valueOf(estacionesS[1].trim()));
        }
        array.add(Estacion.valueOf(estacionesS[0].trim()));

        return array;
    }

    @Override
    public String toString() {
        return "SEMILLA: \n"
                + this.id + ". " +
                "\n NOMBRE: " + nombre + ". " +
                "\n PRECIO COMPRA: " + precioCompra + ". " +
                "\n PRECIO VENTA: " + precioVenta + ". " +
                "\n ESTACIONES: " + estaciones.toString() + ". ";
    }

}
