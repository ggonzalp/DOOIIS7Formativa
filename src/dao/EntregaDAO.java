package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase EntregaDAO que permite el acceso a datos de la tabla Entrega.
 */
public class EntregaDAO {

    /**
     * Inserta una entrega en la base de datos.
     * @param entrega Entrega que asocia un pedido con un repartidor.
     * @return true si la fila se insertó correctamente, de lo contrario devuelve false.
     */
    public boolean guardar(Entrega entrega) {

        String sql =
                "INSERT INTO entrega (idPedido, idRepartidor) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar entrega: " + e.getMessage());
            return false;
        }
    }
}
