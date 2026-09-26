package vista;

import controlador.Controlador;
import modelo.Pedido;
import modelo.ZonaDeCarga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa la ventana secundaria donde se visualiza el historial de pedidos.
 */

public class VentanaListaPedidos extends  JFrame {

    private final Controlador controlador;

    //Modelo de la tabla.
    private DefaultTableModel tableModel;

    //Gestor que almacena y administra los pedidos.
    private JPanel panelLista;
    private JTable tablaPedidos;
    private JButton botonActualizar;
    private JScrollBar scrollBar1;

    /**
     * Constructor de la clase VentanaListaPedidos
     *
     * @param controlador
     */
    public VentanaListaPedidos(Controlador controlador) {
        this.controlador = controlador;

        setContentPane(panelLista);

        configurarVentana();
        configurarTabla();
        configurarBoton();

        actualizarTabla();
    }

    //Configura la  ventana donde se aloja la tabla
    private void configurarVentana() {
        setTitle("SPEEDFAST - HISTORIAL DE PEDIDOS");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Configura la tabla que muestra el historial.
    private void configurarTabla() {
        String[] columnas = {"Tipo de Pedido", "Id Pedido", "Descripción", "N°", "Calle", "Ciudad", "Distancia (km)", "Prioridad", "Estado"};

        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos.setModel(tableModel);
    }

    private void configurarBoton() {
        botonActualizar.addActionListener(e -> actualizarTabla());
    }

    //Vacía la tabla y la vuelve a llenar con los pedidos de la zondaDeCarga.
    private void actualizarTabla() {
        tableModel.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {
            Object[] fila = {
                    pedido.getTipoPedido(),
                    pedido.getIdPedido(),
                    pedido.getDescripcion(),
                    pedido.getDireccionEntrega().getNumero(),
                    pedido.getDireccionEntrega().getCalle(),
                    pedido.getDireccionEntrega().getCiudad(),
                    pedido.getDistanciaKm(),
                    pedido.getPrioridadPedido(),
                    pedido.getEstadoPedido()
            };

            tableModel.addRow(fila);
        }
    }
}