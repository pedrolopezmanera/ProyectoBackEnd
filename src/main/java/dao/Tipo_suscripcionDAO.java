package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Tipo_suscripcion;

public class Tipo_suscripcionDAO extends DAO<Tipo_suscripcion> {

    //Constantes SQL
    private static final String INSERT = 
        "INSERT INTO tipo_suscripcion (nombre, precio, duracion_meses) VALUES (?, ?, ?)";

    private static final String DELETE = 
        "DELETE FROM tipo_suscripcion WHERE tipo_id = ?";

    private static final String UPDATE = 
        "UPDATE tipo_suscripcion SET nombre = ?, precio = ?, duracion_meses = ? WHERE tipo_id = ?";

    private static final String LISTALL = 
        "SELECT * FROM tipo_suscripcion";

    private static final String LISTONE = 
        "SELECT * FROM tipo_suscripcion WHERE tipo_id = ?";

    //Constructor
    public Tipo_suscripcionDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public List<Tipo_suscripcion> listAll() throws SQLException {
        List<Tipo_suscripcion> lista = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTALL);
            rs = statement.executeQuery();
            conexion.commit();

            while (rs.next()) {
                lista.add(mostrarTipoSuscripcion(rs));
            }

        } catch (SQLException ex) {
            System.getLogger(Tipo_suscripcionDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return lista;
    }

    @Override
    public Tipo_suscripcion listOne(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTONE);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            conexion.commit();

            if (rs.next()) {
                return mostrarTipoSuscripcion(rs);
            }

        } catch (SQLException ex) {
            System.getLogger(Tipo_suscripcionDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return null;
    }

    @Override
    public void insertar(Tipo_suscripcion tipo) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(INSERT);

            statement.setString(1, tipo.getNombre());
            statement.setDouble(2, tipo.getPrecio());
            statement.setInt(3, tipo.getDuracion_meses());

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

    @Override
    public void actualizar(Tipo_suscripcion tipo, int id) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(UPDATE);

            statement.setString(1, tipo.getNombre());
            statement.setDouble(2, tipo.getPrecio());
            statement.setInt(3, tipo.getDuracion_meses());
            statement.setInt(4, id);

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

    @Override
    public void eliminar(int id) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(DELETE);
            statement.setInt(1, id);

            statement.executeUpdate();
            conexion.commit();

        } catch (SQLException e) {
            hacerRollback(conexion);
            throw e;
        } finally {
            statement.close();
        }
    }

    private Tipo_suscripcion mostrarTipoSuscripcion(ResultSet rs) {
        Tipo_suscripcion t = null;

        try {
            t = new Tipo_suscripcion(
                rs.getInt("tipo_id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("duracion_meses")
            );

            System.out.println(t.toString());

        } catch (SQLException ex) {
            System.getLogger(Tipo_suscripcionDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return t;
    }
}
