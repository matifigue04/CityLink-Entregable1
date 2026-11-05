package modelo;

/**
 * Interfaz que define el comportamiento para objetos que involucran cálculos de dinero.
 * Permite manejar pedidos (costo de envío) y repartidores (comisiones) de forma polimórfica.
 */
public interface Facturable {
    /**
     * Calcula y devuelve el importe monetario correspondiente a la entidad.
     * @return Importe en formato numérico de punto flotante (double).
     */
    double calcularImporte();
}