package controlador;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.*;

import java.util.List;

/**
 * Clase que controla el flujo de información.
 */

public class Controlador {

    private RepartidorDAO repartidorDAO = new RepartidorDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();

    /**
     * Metodo que registra un repartidor nuevo.
     * @param nombreRepartidor nombre del repartidor.
     * @return
     */
    public boolean registrarRepartidor(String nombreRepartidor) {

        if (nombreRepartidor.isBlank()) {
            return false;
        }

        Repartidor repartidor = new Repartidor(nombreRepartidor);

        return repartidorDAO.guardar(repartidor);
    }

    /**
     * Obtiene los repartidores registrados.
     * @return lista de repartidores.
     */
    public List<Repartidor> obtenerRepartidores() {
        return repartidorDAO.listarTodos();
    }

    public boolean existePedido (int idPedido) {
        return pedidoDAO.existe(idPedido);
    }

    /**
     * Valida los datos de un pedido y los guarda.
     * @param idPedido Identificador del pedido.
     * @param tipoPedido Tipo de pedido.
     * @param descripcion Descripción del pedido.
     * @param direccionEntrega Dirección de entrega del pedido.
     * @param distanciaKm Distancia al lugar de entrega expresado en kilómetros.
     * @param validacion Valida el tipo de pedido.
     * @param prioridadPedido Define la prioridad del pedido.
     * @return true si el pedido se guardó correctamente, de lo contrario arroja un false.
     */
    public boolean registrarPedido(int idPedido, String tipoPedido, String descripcion, DireccionEntrega direccionEntrega, int distanciaKm, boolean validacion, String prioridadPedido) {

        if (tipoPedido.isBlank() || descripcion.isBlank() || prioridadPedido.isBlank()) {
            return false;
        }

        if (idPedido <= 0 || distanciaKm <= 0) {
            return false;
        }

        if (direccionEntrega == null || direccionEntrega.getNumero() <= 0 || direccionEntrega.getCalle().isBlank() || direccionEntrega.getCiudad().isBlank()) {
            return false;
        }

        PrioridadPedido prioridad;
        try {
            prioridad = PrioridadPedido.valueOf(prioridadPedido);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        Pedido pedido;

        switch (tipoPedido) {
            case "Pedido Express" -> pedido = new PedidoExpress(
                    tipoPedido, idPedido, descripcion, direccionEntrega, distanciaKm, validacion, prioridad);
            case "Pedido Comida" -> pedido = new PedidoComida(
                    tipoPedido, idPedido, descripcion, direccionEntrega, distanciaKm, validacion, prioridad);
            case "Pedido Encomienda" -> pedido = new PedidoEncomienda(
                    tipoPedido, idPedido, descripcion, direccionEntrega, distanciaKm, validacion, prioridad);
            default -> {
                return false;
            }
        }

        return pedidoDAO.guardar(pedido);
    }

    /**
     * Metodo obtener pedidos
     * @return Lista de pedidos registrados.
     */
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listarTodos();
    }

    /**
     * Metodo registrar entrega
     * @param idPedido Identificador del pedido.
     * @param idRepartidor Identifiador del repartidor.
     * @return true si la entrega se registró, de lo contrario arroja false.
     */
    public boolean registrarEntrega(int idPedido, int idRepartidor) {

        Entrega entrega = new Entrega(idPedido, idRepartidor);

        return entregaDAO.guardar(entrega);
    }

    /**
     * Metodo despachar pedido que cambia el estado del mismo.
     * @param idPedido Identificador del pedido
     * @return true si se actualizó la fila, de lo contrario arroja false.
     */
    public boolean despacharPedido(int idPedido) {
        return pedidoDAO.actualizarEstado(idPedido, EstadoPedido.EN_REPARTO.name());
    }
}
