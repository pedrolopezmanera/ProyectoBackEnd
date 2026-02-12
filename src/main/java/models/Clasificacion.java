/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author User
 */
public class Clasificacion {
    
    //Atributo
    private int id;
    private String nombre;

    //Constructor
    public Clasificacion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    //Getter and Setter
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
    
    //Metodos

    @Override
    public String toString() {
        return "Clasificacion{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
}
