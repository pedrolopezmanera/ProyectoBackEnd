/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author User
 */
public class Film_genero {
    
    //Atributos
    private int film_id;
    private int  genero_id;
    
    //Constructor
    public Film_genero(int film_id, int genero_id) {
        this.film_id = film_id;
        this.genero_id = genero_id;
    }
    //Getter and Setter

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getGenero_id() {
        return genero_id;
    }

    public void setGenero_id(int genero_id) {
        this.genero_id = genero_id;
    }
    
    //Metodos

    @Override
    public String toString() {
        return "Film_genero{" + "film_id=" + film_id + ", genero_id=" + genero_id + '}';
    }
    
}
