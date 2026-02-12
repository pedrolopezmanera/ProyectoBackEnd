/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author User
 */
public class Tipo_suscripcion {
    //Atributos
    private int tipo_id;
    private String nombre;
    private double precio;
    private int duracion_meses;
    
    //Constructor

    public Tipo_suscripcion(int tipo_id, String nombre, double precio, int duracion_meses) {
        this.tipo_id = tipo_id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion_meses = duracion_meses;
    }
    
    //Getter and Setter

    public int getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDuracion_meses() {
        return duracion_meses;
    }

    public void setDuracion_meses(int duracion_meses) {
        this.duracion_meses = duracion_meses;
    }
    
    //Metodos

    @Override
    public String toString() {
        return "Tipo_suscripcion{" + "tipo_id=" + tipo_id + ", nombre=" + nombre + ", precio=" + precio + ", duracion_meses=" + duracion_meses + '}';
    }
    
}
