package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Film_genero;

public class Film_generoDAO extends DAO<Film_genero> {

    //Constantes SQL
    private static final String INSERT = 
        "INSERT INTO film_genero (film_id, genero_id) VALUES (?, ?)";

    private static final String DELETE = 
        "DELETE FROM film_genero WHERE film_id = ? AND genero_id = ?";

    private static final String LISTALL = 
        "SELECT * FROM film_genero";

    private static final String LISTONE = 
        "SELECT * FROM film_genero WHERE film_id = ?";

    //Constructor
    public Film_generoDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public List<Film_genero> listAll() throws SQLException {
        List<Film_genero> lista = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTALL);
            rs = statement.executeQuery();
            conexion.commit();

            while (rs.next()) {
                lista.add(mostrarFilmGenero(rs));
            }

        } catch (SQLException ex) {
            System.getLogger(Film_generoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return lista;
    }

    @Override
    public Film_genero listOne(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTONE);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            conexion.commit();

            if (rs.next()) {
                return mostrarFilmGenero(rs);
            }

        } catch (SQLException ex) {
            System.getLogger(Film_generoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return null;
    }

    @Override
    public void insertar(Film_genero fg) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(INSERT);

            statement.setInt(1, fg.getFilm_id());
            statement.setInt(2, fg.getGenero_id());

            statement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    //En tablas intermedias NO suele haber update → se elimina e inserta
    @Override
    public void actualizar(Film_genero fg, int id) throws SQLException {
        throw new UnsupportedOperationException("Film_genero no requiere UPDATE");
    }

    public void eliminar(int filmId, int generoId) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(DELETE);

            statement.setInt(1, filmId);
            statement.setInt(2, generoId);

            statement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            statement.close();
        }
    }

    private Film_genero mostrarFilmGenero(ResultSet rs) {
        Film_genero fg = null;

        try {
            fg = new Film_genero(
                rs.getInt("film_id"),
                rs.getInt("genero_id")
            );

            System.out.println(fg.toString());

        } catch (SQLException ex) {
            System.getLogger(Film_generoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return fg;
    }

    @Override
    public void eliminar(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
