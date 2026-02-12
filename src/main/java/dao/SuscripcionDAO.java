/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.Suscripcion;

/**
 *
 * @author User
 */
public class SuscripcionDAO extends DAO<Suscripcion>{

    //Atributos
    private static final String INSERT = "INSERT INTO suscripcion ( cuenta_id, tipo_id, fecha_contratacion,  fecha_fin) VALUES (?,?,?,?)";
    private static final String DELETE = "DELETE FROM suscripcion WHERE id = ?";
    private static final String UPDATE = "UPDATE suscripcion SET  cuenta_id = ?, tipo_id = ?, fecha_contratacion = ?, fecha_fin = ? WHERE id = ?";
    private static final String LISTALL = "SELECT *  FROM suscripcion";
    private static final String LISTONE = "SELECT *  FROM suscripcion WHERE id = ?";
    //Constructor
    public SuscripcionDAO(Connection conexion) {
        super(conexion);
    }

    //Metodos
    @Override
    public List<Suscripcion> listAll() throws SQLException {
    }

    @Override
    public Suscripcion listOne(int id) throws SQLException {
    }

    @Override
    public void insertar(Suscripcion t) throws SQLException {
    }

    @Override
    public void actualizar(Suscripcion t, int id) throws SQLException {
    }

    @Override
    public void eliminar(int id) throws SQLException {
    }
    
}
