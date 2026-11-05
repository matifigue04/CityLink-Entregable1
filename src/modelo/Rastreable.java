package modelo;

/**
 * Interfaz que define la capacidad de un objeto de reportar su estado actual.
 * Aplica el concepto de Abstracción e Interfaces.
 * Será implementada tanto por Pedido como por Repartidor (Polimorfismo).
 */
public interface Rastreable {
    /**
     * Obtiene una representación en texto del estado actual de la entidad.
     * @return Cadena de texto con el estado.
     */
    String obtenerEstadoActual();
}