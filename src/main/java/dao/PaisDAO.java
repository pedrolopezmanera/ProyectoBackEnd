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
import models.Pais;

/**
 *
 * @author User
 */
public class PaisDAO extends DAO<Pais> {

    //Atributos
    private static final String INSERT = "INSERT INTO pais (nombre) VALUES (?)";
    private static final String DELETE = "DELETE FROM pais WHERE id = ?";
    private static final String UPDATE = "UPDATE pais SET nombre = ? WHERE id = ?";
    private static final String LISTALL = "SELECT *  FROM pais";
    private static final String LISTONE = "SELECT *  FROM pais WHERE id = ?";

    //Constructor
    public PaisDAO(Connection conexion) {
        super(conexion);
    }

    //Metodos
    @Override
    public List<Pais> listAll() throws SQLException {
                List<Pais> lista = new ArrayList<>();
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

                lista.add(mostrarPais(rs));
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
    public Pais listOne(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            //Preparar la consulta SQL
            statement = conexion.prepareStatement(LISTONE);
            statement.setInt(1, id);
            //Ejecutar la consulta y obtener el resultado
            rs = statement.executeQuery();
            conexion.commit();


            //Recorrer cada fila de la consulta
            if (rs.next()) {


                return mostrarPais(rs);

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
    public void insertar(Pais p) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(INSERT);
            statement.setString(1, p.getNombre());

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
    public void actualizar(Pais p, int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(UPDATE);
            statement.setString(1, p.getNombre());
            statement.setInt(2, id);

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

    private Pais mostrarPais(ResultSet rs) {
        Pais p = null;
        try {
            p = new Pais();

            p.setId(rs.getInt("id"));
            p.setNombre(rs.getString("nombre"));

            System.out.println(p.toString());

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return p;
    }

}
