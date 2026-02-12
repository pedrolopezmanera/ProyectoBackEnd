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
import java.util.Scanner;
import models.*;

/**
 *
 * @author User
 */
public class FilmografiaDAO extends DAO<Filmografia> {

    //Atributos
    private static final String INSERT = "INSERT INTO filmografia ( titulo, fecha_estreno, sinopsis,  pais_id, clasificacion_id) VALUES (?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM filmografia WHERE id = ?";
    private static final String UPDATE = "UPDATE filmografia SET titulo = ?, fecha_estreno = ?, sinopsis = ?, pais_id = ?, clasificacion_id = ? WHERE id = ?";
    private static final String LISTALL = "SELECT *  FROM filmografia";
    private static final String LISTONE = "SELECT *  FROM filmografia WHERE id = ?";

    //Constructor
    public FilmografiaDAO(Connection conexion) {
        super(conexion);
    }

    //Metodos
    //Metodo listar todo
    @Override
    public List<Filmografia> listAll() throws SQLException {
        List<Filmografia> lista = new ArrayList<>();
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
                
                lista.add(mostrarFilmografia(rs));

            }
        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            //Cerrar ResultSet y PreparedStatement
            cerrarEstados(rs, statement);
        }
        return lista;
    }

    //Metodo para listar uno
    @Override
    public Filmografia listOne(int id) throws SQLException {
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

            return mostrarFilmografia(rs);
            }

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            //Cerrar ResultSet y PreparedStatement
            cerrarEstados(rs, statement);
        }
        return null;
    }

    //Metodo insertar
    @Override
    public void insertar(Filmografia film) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(INSERT);
            statement.setString(1, film.getTitulo());
            statement.setDate(2, film.getFecha_estreno());
            statement.setString(3, film.getSinopsis());
            statement.setInt(4, film.getPais_id());
            statement.setInt(5, film.getClasificacion_id());

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

    //Metodo actualizar
    @Override
    public void actualizar(Filmografia film, int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Preparar la consulta SQL  
            statement = conexion.prepareStatement(UPDATE);
            statement.setString(1, film.getTitulo());
            statement.setDate(2, film.getFecha_estreno());
            statement.setString(3, film.getSinopsis());
            statement.setInt(4, film.getPais_id());
            statement.setInt(5, film.getClasificacion_id());
            statement.setInt(6, id);

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

    //Metodo  eliminar
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

    private Filmografia mostrarFilmografia(ResultSet rs) {
        Filmografia f = null;
        try {
            f = new Filmografia();

            f.setId(rs.getInt("id"));
            f.setTitulo(rs.getString("titulo"));
            f.setFecha_estreno(rs.getDate("fecha_estreno"));
            f.setSinopsis(rs.getString("sinopsis"));
            f.setPais_id(rs.getInt("pais_id"));
            f.setClasificacion_id(rs.getInt("clasificacion_id"));

        } catch (SQLException ex) {
            System.getLogger(FilmografiaDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return f;

    }

}
