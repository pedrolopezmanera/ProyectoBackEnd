package EjercicioNetflix.DAOs;

import EjercicioNetflix.Models.Clasificacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionDAO extends DAO<Clasificacion> {

    private static final String INSERTAR =
            "INSERT INTO Clasificacion (nombre) VALUES (?)";

    private static final String ACTUALIZAR =
            "UPDATE Clasificacion SET nombre = ? WHERE id = ?";

    private static final String ELIMINAR =
            "DELETE FROM Clasificacion WHERE id = ?";

    private static final String LIST_ONE =
            "SELECT id, nombre FROM Clasificacion WHERE id = ?";

    private static final String LIST_ALL =
            "SELECT id, nombre FROM Clasificacion";

    @Override
    public Clasificacion listOne(int id) throws MyException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Clasificacion clasificacion = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                clasificacion = new Clasificacion();
                clasificacion.setId(rs.getInt("id"));
                clasificacion.setNombre(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            throw new MyException("Error al buscar clasificación con ID: " + id);
        } finally {
            cerrarEstados(stmt, rs);
        }

        return clasificacion;
    }

    @Override
    public List<Clasificacion> listAll() throws MyException {

        List<Clasificacion> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Clasificacion c = new Clasificacion(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new MyException("Error al listar clasificaciones");
        } finally {
            cerrarEstados(stmt, rs);
        }

        return lista;
    }

    @Override
    public void insertar(Clasificacion c) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(INSERTAR);
            cargarDatosStatement(stmt, c);
            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al insertar clasificación");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Clasificacion c) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(ACTUALIZAR);
            stmt.setString(1, c.getNombre());
            stmt.setInt(2, c.getId());
            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al actualizar clasificación");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void eliminar(int id) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(ELIMINAR);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al eliminar clasificación");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    protected void cargarDatosStatement(PreparedStatement stmt, Clasificacion c) throws SQLException {
        stmt.setString(1, c.getNombre());
    }
}
