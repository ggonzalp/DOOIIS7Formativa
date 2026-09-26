package dao;

import modelo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase PedidoDAO que permite el acceso a datos de la tabla Pedido
 */

public class PedidoDAO {

    /**
     * Inserta un pedido en la base de datos.
     *
     * @param pedido Pedido que se necista registrar.
     * @return True si la fila se insertó correctamente, de lo contrario devuelve false.
     */

    public boolean guardar(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (tipoPedido, idPedido, descripcion, direccionNumero, direccionCalle, direccionCiudad, distanciaKm, validacion, prioridadPedido, estadoPedido) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getTipoPedido());
            ps.setInt(2, pedido.getIdPedido());
            ps.setString(3, pedido.getDescripcion());
            ps.setInt(4, pedido.getDireccionEntrega().getNumero());
            ps.setString(5, pedido.getDireccionEntrega().getCalle());
            ps.setString(6, pedido.getDireccionEntrega().getCiudad());
            ps.setInt(7, pedido.getDistanciaKm());
            ps.setBoolean(8, pedido.getValidacion());
            ps.setString(9, pedido.getPrioridadPedido().name());
            ps.setString(10, pedido.getEstadoPedido().name());


            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los pedidos registrados en la base de datos.
     *
     * @return Lista con pedidos
     */
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT idPedido, tipoPedido, descripcion, direccionNumero, direccionCalle, " +
                "direccionCiudad, distanciaKm, validacion, prioridadPedido, estadoPedido " +
                "FROM pedido";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DireccionEntrega direccion = new DireccionEntrega(
                        rs.getInt("direccionNumero"),
                        rs.getString("direccionCalle"),
                        rs.getString("direccionCiudad"));

                String tipoPedido = rs.getString("tipoPedido");
                int idPedido = rs.getInt("idPedido");
                String descripcion = rs.getString("descripcion");
                int distanciaKm = rs.getInt("distanciaKm");
                boolean validacion = rs.getBoolean("validacion");

                PrioridadPedido prioridad = PrioridadPedido.valueOf(rs.getString("prioridadPedido"));
                EstadoPedido estado = EstadoPedido.valueOf(rs.getString("estadoPedido"));

                Pedido pedido;

                switch (tipoPedido) {
                    case "Pedido Express" -> pedido = new PedidoExpress(
                            tipoPedido, idPedido, descripcion, direccion, distanciaKm, validacion, prioridad);
                    case "Pedido Comida" -> pedido = new PedidoComida(
                            tipoPedido, idPedido, descripcion, direccion, distanciaKm, validacion, prioridad);
                    case "Pedido Encomienda" -> pedido = new PedidoEncomienda(
                            tipoPedido, idPedido, descripcion, direccion, distanciaKm, validacion, prioridad);
                    default -> {
                        System.out.println("Tipo de pedido desconocido en la base de datos: " + tipoPedido);
                        continue;
                    }
                }

                pedido.cambiarEstado(estado);

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    /**
     * Metodo actualizar estado
     * @param idPedido Identifiación del pedido.
     * @param estado Estado del pedido.
     * @return el estado del pedido.
     */
    public boolean actualizarEstado(int idPedido, String estado) {
        String sql = "UPDATE pedido SET estadoPedido = ? WHERE idPedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString (1, estado);
            ps.setInt(2, idPedido);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Metodo existe
     * @param idPedido Identificación del pedido.
     * @return la actualización de idPedido.
     */
    public boolean existe(int idPedido) {
        String sql = "SELECT idPedido FROM pedido WHERE idPedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar id de pedido: " + e.getMessage());

            return false;
        }
    }
}
