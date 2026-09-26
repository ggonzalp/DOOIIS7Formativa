package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa la entrega de un pedido.
 */

public class Entrega {
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Constructor de la clase Entrega
     * @param idPedido     Número de identificación del pedido.
     * @param idRepartidor Número de identificación del repartidor.
     * Fecha y hora locales
     */
    public Entrega(int idPedido, int idRepartidor) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
    }

    //Metodos getter
    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }
}


