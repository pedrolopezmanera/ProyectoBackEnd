package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Acceso;

public class AccesoDAO extends DAO<Acceso> {

    //Constantes SQL
    private static final String INSERT = 
        "INSERT INTO acceso (id_filmografia, id_cuenta, fecha_acceso, tipo_suscripcion_id) VALUES (?, ?, ?, ?)";

    private static final String DELETE = 
        "DELETE FROM acceso WHERE id_acceso = ?";

    private static final String UPDATE = 
        "UPDATE acceso SET id_filmografia = ?, id_cuenta = ?, fecha_acceso = ?, tipo_suscripcion_id = ? WHERE id_acceso = ?";

    private static final String LISTALL = 
        "SELECT * FROM acceso";

    private static final String LISTONE = 
        "SELECT * FROM acceso WHERE id_acceso = ?";

    //Constructor
    public AccesoDAO(Connection conexion) {
        super(conexion);
    }

    @Override
    public List<Acceso> listAll() throws SQLException {
        List<Acceso> lista = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTALL);
            rs = statement.executeQuery();
            conexion.commit();

            while (rs.next()) {
                lista.add(mostrarAcceso(rs));
            }

        } catch (SQLException ex) {
            System.getLogger(AccesoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return lista;
    }

    @Override
    public Acceso listOne(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conexion.prepareStatement(LISTONE);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            conexion.commit();

            if (rs.next()) {
                return mostrarAcceso(rs);
            }

        } catch (SQLException ex) {
            System.getLogger(AccesoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrarEstados(rs, statement);
        }

        return null;
    }

    @Override
    public void insertar(Acceso acceso) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(INSERT);

            statement.setInt(1, acceso.getId_filmografia());
            statement.setInt(2, acceso.getId_cuenta());
            statement.setDate(3, acceso.getFecha_acceso());
            statement.setInt(4, acceso.getTipo_suscripcion_id());

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
    public void actualizar(Acceso acceso, int id) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(UPDATE);

            statement.setInt(1, acceso.getId_filmografia());
            statement.setInt(2, acceso.getId_cuenta());
            statement.setDate(3, acceso.getFecha_acceso());
            statement.setInt(4, acceso.getTipo_suscripcion_id());
            statement.setInt(5, id);

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

    private Acceso mostrarAcceso(ResultSet rs) {
        Acceso a = null;

        try {
            a = new Acceso();

            a.setId_acceso(rs.getInt("id_acceso"));
            a.setId_filmografia(rs.getInt("id_filmografia"));
            a.setId_cuenta(rs.getInt("id_cuenta"));
            a.setFecha_acceso(rs.getDate("fecha_acceso"));
            a.setTipo_suscripcion_id(rs.getInt("tipo_suscripcion_id"));

            System.out.println(a.toString());

        } catch (SQLException ex) {
            System.getLogger(AccesoDAO.class.getName())
                  .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return a;
    }
}
