package EjercicioNetflix.DAOs;

import EjercicioNetflix.Models.Factura;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO extends DAO<Factura> {

    private static final String INSERTAR =
            "INSERT INTO Factura (suscripcion_id, cuenta_id, importe_pvp, metodo_pago, fecha_factura) VALUES (?, ?, ?, ?, ?)";

    private static final String ACTUALIZAR =
            "UPDATE Factura SET suscripcion_id = ?, cuenta_id = ?, importe_pvp = ?, metodo_pago = ?, fecha_factura = ? WHERE num_factura = ?";

    private static final String ELIMINAR =
            "DELETE FROM Factura WHERE num_factura = ?";

    private static final String LIST_ONE =
            "SELECT num_factura, suscripcion_id, cuenta_id, importe_pvp, metodo_pago, fecha_factura FROM Factura WHERE num_factura = ?";

    private static final String LIST_ALL =
            "SELECT num_factura, suscripcion_id, cuenta_id, importe_pvp, metodo_pago, fecha_factura FROM Factura";

    @Override
    public Factura listOne(int id) throws MyException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Factura factura = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ONE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                factura = new Factura();
                factura.setNumFactura(rs.getInt("num_factura"));
                factura.setSuscripcionId(rs.getInt("suscripcion_id"));
                factura.setCuentaId(rs.getInt("cuenta_id"));
                factura.setImportePvp(rs.getDouble("importe_pvp"));
                factura.setMetodoPago(rs.getString("metodo_pago"));
                factura.setFechaFactura(rs.getDate("fecha_factura").toLocalDate());
            }

        } catch (SQLException e) {
            throw new MyException("Error al buscar factura con ID: " + id);
        } finally {
            cerrarEstados(stmt, rs);
        }

        return factura;
    }

    @Override
    public List<Factura> listAll() throws MyException {

        List<Factura> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = database_connection.prepareStatement(LIST_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Factura f = new Factura(
                        rs.getInt("num_factura"),
                        rs.getInt("suscripcion_id"),
                        rs.getInt("cuenta_id"),
                        rs.getDouble("importe_pvp"),
                        rs.getString("metodo_pago"),
                        rs.getDate("fecha_factura").toLocalDate()
                );
                lista.add(f);
            }

        } catch (SQLException e) {
            throw new MyException("Error al listar facturas");
        } finally {
            cerrarEstados(stmt, rs);
        }

        return lista;
    }

    @Override
    public void insertar(Factura f) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(INSERTAR);
            cargarDatosStatement(stmt, f);
            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al insertar factura");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    public void update(Factura f) throws MyException {

        PreparedStatement stmt = null;

        try {
            stmt = database_connection.prepareStatement(ACTUALIZAR);

            stmt.setInt(1, f.getSuscripcionId());
            stmt.setInt(2, f.getCuentaId());
            stmt.setDouble(3, f.getImportePvp());
            stmt.setString(4, f.getMetodoPago());
            stmt.setDate(5, Date.valueOf(f.getFechaFactura()));
            stmt.setInt(6, f.getNumFactura());

            stmt.executeUpdate();
            database_connection.commit();

        } catch (SQLException e) {
            hacerRollback(database_connection);
            throw new MyException("Error al actualizar factura");
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
            throw new MyException("Error al eliminar factura");
        } finally {
            cerrarEstados(stmt, null);
        }
    }

    @Override
    protected void cargarDatosStatement(PreparedStatement stmt, Factura f) throws SQLException {

        stmt.setInt(1, f.getSuscripcionId());
        stmt.setInt(2, f.getCuentaId());
        stmt.setDouble(3, f.getImportePvp());
        stmt.setString(4, f.getMetodoPago());
        stmt.setDate(5, Date.valueOf(f.getFechaFactura()));
    }
}
