package vista;

import controlador.Controlador;
import modelo.*;
import modelo.PrioridadPedido;

import javax.swing.*;

public class VentanaRegistroPedido extends JFrame {

    private final Controlador controlador;

    private JPanel panelRegistro;
    private JTextField txtIdPedido;
    private JTextField txtDescripcion;
    private JTextField txtNumero;
    private JTextField txtCalle;
    private JTextField txtCiudad;
    private JTextField txtDistancia;
    private JComboBox<String> comboTipoPedido;
    private JComboBox<String> comboPrioridad;
    private JButton botonGuardar;
    private JButton botonLimpiar;
    private JCheckBox checkValidacion;

    public VentanaRegistroPedido(Controlador controlador) {
        this.controlador = controlador;

        setContentPane(panelRegistro);

        configurarVentana();
        configurarComponentes();
        configurarBotones();
    }

    //Configura la ventana donde se visualizará el formulario de registro.
    private void configurarVentana() {
        setTitle("SPEEDFAST - REGISTRAR PEDIDO.");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void configurarComponentes() {
        //Opciones del JComboBox
        comboTipoPedido.setModel(new DefaultComboBoxModel<>(
                new String[]{"Pedido Express", "Pedido Comida", "Pedido Encomienda"}));

        comboPrioridad.setModel((new DefaultComboBoxModel<>(
                new String[]{"ALTA", "MEDIA", "BAJA"})));
    }

    //Conecta los botones visuales con el código que se ejecutará al hacer clic.
    private void configurarBotones() {
        botonGuardar.addActionListener(e -> registrarPedido());
        botonLimpiar.addActionListener(e -> limpiar());
    }

    //REGISTRO: Registra el ingreso de un pedido.
    private void registrarPedido() {
        try {
            //Recibe información del formulario.
            String textoIdPedido = txtIdPedido.getText().trim();
            String textoNumero = txtNumero.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String calle = txtCalle.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String textoDistancia = txtDistancia.getText().trim();

            //Valida campos obligatorios
            if (textoIdPedido.isEmpty() || descripcion.isEmpty() || textoNumero.isEmpty() || calle.isEmpty() || ciudad.isEmpty() || textoDistancia.isEmpty()) {
                throw new IllegalArgumentException("Todos son campos obligatorios");
            }

            //Convierte el texto a número.
            int idPedido = leerEntero(textoIdPedido, "Id del pedido");
            int numero = leerEntero(textoNumero, "Número dirección de entrega");
            int distancia = leerEntero(textoDistancia, "Distancoa en km");

            //Validar números.
            if (idPedido <= 0) {
                throw new IllegalArgumentException("El número de pedido ingresado no es válido.");
            }
            if (numero <= 0) {
                throw new IllegalArgumentException("El número de domicilio ingresado no es válido.");
            }
            if (distancia <= 0) {
                throw new IllegalArgumentException("La distancia ingresada no es válida.");
            }

            //Registra el pedido
            DireccionEntrega direccionEntrega = new DireccionEntrega(numero, calle, ciudad);

            //Lee los combos
            String tipoElegido = (String) comboTipoPedido.getSelectedItem();
            String prioridad = (String) comboPrioridad.getSelectedItem();
            boolean validacion = checkValidacion.isSelected();


            //Lee el checkbox
            boolean guardado = controlador.registrarPedido(
                    idPedido, tipoElegido, descripcion, direccionEntrega, distancia, validacion, prioridad);

            if (guardado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Pedido registrado correctamente.",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiar();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "El número de pedido ingresado ya existe.",
                        "Registro fallido",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException exception) {
            //Se ejecuta para validar la entrega del pedido.
            JOptionPane.showMessageDialog(this, exception.getMessage(),
                    "Datos no válidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int leerEntero(String texto, String nombreCampo) {
        try {
            return Integer.parseInt(texto);

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
              "El campo " + nombreCampo + "debe contener solo números enteros mayores a 0");
        }
    }


    //Limpia los datos en tabla.
    private void limpiar() {
        comboTipoPedido.setSelectedIndex(0);
        txtIdPedido.setText("");
        txtDescripcion.setText("");
        txtNumero.setText("");
        txtCalle.setText("");
        txtCiudad.setText("");
        txtDistancia.setText("");
        comboPrioridad.setSelectedIndex(0);
        checkValidacion.setSelected(false);
    }
}
