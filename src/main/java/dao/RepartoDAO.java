/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.Reparto;

/**
 *
 * @author User
 */
public class RepartoDAO extends DAO<Reparto>{

    //Atributos
    private static final String INSERT = "INSERT INTO reparto (id_filmografia, nombre_actor, papel) VALUES (?,?,?)";
    private static final String DELETE = "DELETE FROM reparto WHERE id = ?";
    private static final String UPDATE = "UPDATE reparto SET id_filmografia = ?, nombre_actor = ?, papel = ?, WHERE id = ?";
    private static final String LISTALL = "SELECT *  FROM reparto";
    private static final String LISTONE = "SELECT *  FROM reparto WHERE id = ?";
    
    //Constructor
    public RepartoDAO(Connection conexion) {
        super(conexion);
    }

    //Metodos
    @Override
    public List<Reparto> listAll() throws SQLException {
    }

    @Override
    public Reparto listOne(int id) throws SQLException {
    }

    @Override
    public void insertar(Reparto t) throws SQLException {
    }

    @Override
    public void actualizar(Reparto t, int id) throws SQLException {
    }

    @Override
    public void eliminar(int id) throws SQLException {
    }
    
}
