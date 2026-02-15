package EjercicioNetflix.DAOs;

import EjercicioNetflix.Models.Cuenta;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO extends DAO<Cuenta> {

    private static final String INSERTAR =
            "INSERT INTO Cuenta (email, contrasena, fecha_creacion, tipo_cuenta, activa) VALUES (?, ?, ?, ?, ?)";

    private static final String ACTUALIZAR =
            "UPDATE Cuenta SET email = ?, contrasena = ?, fecha_creacion = ?, tipo_cuenta = ?, activa = ? WHERE id = ?";

    private static final String ELIMINAR =
            "DELETE FROM Cuenta WHERE id = ?";

    private static final String LIST_ONE =
            "SELECT id, email, contrasena, fecha_creacion, tipo_cuenta, activa FROM Cuenta WHERE id = ?";

    private static final String LIST_ALL =
            "SELECT id, email, contrasena, fecha_creacion, tipo_cuenta, activa FROM Cuenta";

    @Override
    public Cuenta listOne(int id) throws MyException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cuenta cuenta = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setId(rs.getInt("id"));
                cuenta.setEmail(rs.getString("email"));
                cuenta.setContrasena(rs.getString("contrasena"));
                cuenta.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                cuenta.setTipoCuenta(rs.getString("tipo_cuenta"));
                cuenta.setActiva(rs.getBoolean("activa"));
            }

        } catch (SQLException e) {
            throw new MyException("Error al buscar cuenta con ID: " + id);
        } finally {
            cerrarEstados(stmt, rs);
        }

        return cuenta;
    }

    @Override
    public List<Cuenta> listAll() throws MyException {

        List<Cuenta> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cuenta c = new Cuenta(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getString("tipo_cuenta"),
                        rs.getBoolean("activa")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            throw new MyException("Error al listar cuentas");
        } finally {
            cerrarEstados(stmt, rs);
        }

        return lista;
    }

    @Override
    public void insertar(Cuenta c) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(INSERTAR);
            cargarDatosStatement(stmt, c);
            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al insertar cuenta");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Cuenta c) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(ACTUALIZAR);

            stmt.setString(1, c.getEmail());
            stmt.setString(2, c.getContrasena());
            stmt.setDate(3, Date.valueOf(c.getFechaCreacion()));
            stmt.setString(4, c.getTipoCuenta());
            stmt.setBoolean(5, c.isActiva());
            stmt.setInt(6, c.getId());

            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al actualizar cuenta");
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
            throw new MyException("Error al eliminar cuenta");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    protected void cargarDatosStatement(PreparedStatement stmt, Cuenta c) throws SQLException {

        stmt.setString(1, c.getEmail());
        stmt.setString(2, c.getContrasena());
        stmt.setDate(3, Date.valueOf(c.getFechaCreacion()));
        stmt.setString(4, c.getTipoCuenta());
        stmt.setBoolean(5, c.isActiva());
    }
}

