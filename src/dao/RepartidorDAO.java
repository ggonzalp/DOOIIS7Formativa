package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase RepartidorDAO que permite el acceso a datos de la tabla repartidor.
 */
public class RepartidorDAO {

    /**
     * Inserta un repartidor en la base de datos.
     * @param repartidor Repartidor que se necesita registrar.
     * @return true si la fila se insertó correctamente, de lo contrario devuelve false.
     */
    public boolean guardar(Repartidor repartidor) {

        //El id de repartidor es generado por MySQL con AUTO_INCREMENT.

        String sql = "INSERT INTO repartidor (nombreRepartidor) VALUES (?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombreRepartidor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los repartidores registrados en la base de datos.
     * @return Lista con repartidores encontrados.
     */
    public List<Repartidor> listarTodos() {
        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT idRepartidor, nombreRepartidor " + "FROM repartidor";

        try (Connection conexion = ConexionBD.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idRepartidor = rs.getInt("idRepartidor");
                String nombreRepartidor = rs.getString("nombreRepartidor");

                Repartidor repartidor = new Repartidor(idRepartidor, nombreRepartidor);

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar repartidores: " + e.getMessage());
        }

        return repartidores;

    }
}
