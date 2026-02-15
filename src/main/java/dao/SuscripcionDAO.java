/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Suscripcion;

/**
 *
 * @author User
 */
public class SuscripcionDAO extends DAO<Suscripcion> {

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
        List<Suscripcion> lista = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(LISTALL);

            //Ejecutar la consulta y obtener el resultado
            rs = statement.executeQuery();
            conexion.commit();

            //Recorrer cada fila de la consulta
            while (rs.next()) {

                lista.add(mostrarSuscripcion(rs));

            }
        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            //Cerrar ResultSet y PreparedStatement
            cerrarEstados(rs, statement);
        }
        return lista;
    }

    @Override
    public Suscripcion listOne(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            //Preparar la consulta SQL
            statement = conexion.prepareStatement(LISTONE);
            statement.setInt(1, id);
            //Ejecutar la consulta y obtener el resultado
            rs = statement.executeQuery();
            conexion.commit();

            if (rs.next()) {

                return mostrarSuscripcion(rs);
            }

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            //Cerrar ResultSet y PreparedStatement
            cerrarEstados(rs, statement);
        }
        return null;
    }

    @Override
    public void insertar(Suscripcion s) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(INSERT);
            statement.setInt(1, s.getCuenta_id());
            statement.setInt(2, s.getTipo_id());
            statement.setDate(3, s.getFecha_contratacion());
            statement.setDate(4, s.getFecha_fin());
            //Ejecutar la consulta y actualiza la tabla
            statement.executeUpdate();
            conexion.commit();
        } //Hacemos rollback si falla algo
        catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            //Cerrar ResultSet y PreparedStatement
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void actualizar(Suscripcion s, int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(UPDATE);
            statement.setInt(1, s.getCuenta_id());
            statement.setInt(2, s.getTipo_id());
            statement.setDate(3, s.getFecha_contratacion());
            statement.setDate(4, s.getFecha_fin());
            statement.setInt(5, id);

            //Ejecutar la consulta y actualiza la tabla
            statement.executeUpdate();
            conexion.commit();
        } //Hacemos rollback si falla algo
        catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            //Si no es nulo cerramos el statement
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(DELETE);
            statement.setInt(1, id);

            //Ejecutar la consulta y actualiza la tabla
            statement.executeUpdate();
            conexion.commit();
        } //Hacemos rollback si falla algo
        catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            //Si no es nulo cerramos el statement
            statement.close();
        }
    }

    private Suscripcion mostrarSuscripcion(ResultSet rs) {
        Suscripcion s = null;
        try {
            s = new Suscripcion();

            s.setCuenta_id(rs.getInt("cuenta_id"));
            s.setTipo_id(rs.getInt("tipo_id"));
            s.setFecha_contratacion(rs.getDate("fecha_contratacion"));
            s.setFecha_fin(rs.getDate("fecha_fin"));

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return s;

    }
}
