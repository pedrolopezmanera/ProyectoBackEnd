/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author User
 */
public class Filmografia {
    //Atributos
    private int id;
    private String titulo;
    private Date fecha_estreno;
    private String sinopsis;
    private int pais_id;
    private int clasificacion_id;

    //Constructor
    public Filmografia(int id, String titulo, Date fecha_estreno, String sinopsis, int pais_id, int clasificacion_id) {
        this.id = id;
        this.titulo = titulo;
        this.fecha_estreno = fecha_estreno;
        this.sinopsis = sinopsis;
        this.pais_id = pais_id;
        this.clasificacion_id = clasificacion_id;
    }
public Filmografia(String titulo, Date fecha_estreno, String sinopsis, int pais_id, int clasificacion_id) {
        this.titulo = titulo;
        this.fecha_estreno = fecha_estreno;
        this.sinopsis = sinopsis;
        this.pais_id = pais_id;
        this.clasificacion_id = clasificacion_id;
    }
    public Filmografia() {
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(Date fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

    public int getClasificacion_id() {
        return clasificacion_id;
    }

    public void setClasificacion_id(int clasificacion_id) {
        this.clasificacion_id = clasificacion_id;
    }
    
    
   //Metodos

    @Override
    public String toString() {
        return "Filmografia{" + " id = " + id + ", titulo = " + titulo + ", fecha_estreno = " + fecha_estreno + ", sinopsis = " + sinopsis + ", pais_id = " + pais_id + ", clasificacion_id = " + clasificacion_id + '}';
    }
    
    
}
