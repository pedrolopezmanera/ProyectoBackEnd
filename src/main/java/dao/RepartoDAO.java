/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Reparto;

/**
 *
 * @author User
 */
public class RepartoDAO extends DAO<Reparto> {

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
        List<Reparto> lista = new ArrayList<>();
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

                lista.add(mostrarReparto(rs));

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
    public Reparto listOne(int id) throws SQLException {
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

                return mostrarReparto(rs);
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
    public void insertar(Reparto r) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(INSERT);
            statement.setInt(1, r.getId_filmografia());
            statement.setString(2, r.getNombre_actor());
            statement.setString(3, r.getPapel());

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
    public void actualizar(Reparto r, int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(UPDATE);
            statement.setInt(1, r.getId_filmografia());
            statement.setString(2, r.getNombre_actor());
            statement.setString(3, r.getPapel());
            statement.setInt(4, id);

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

    private Reparto mostrarReparto(ResultSet rs) {
        Reparto r = null;
        try {
            r = new Reparto();

            r.setId_reparto(rs.getInt("id_reparto"));
            r.setId_filmografia(rs.getInt("id_filmografia"));
            r.setNombre_actor(rs.getString("nombre_actor"));
            r.setPapel(rs.getString("papel"));

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return r;

    }
}
