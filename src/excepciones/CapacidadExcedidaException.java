package excepciones;

/**
 * Excepción de tipo Checked.
 * Se dispara en el momento de la asignación si el vehículo no puede llevar el pedido.
 * Ejemplo: El pedido pesa más que la capacidad máxima o es frágil y el vehículo es una bicicleta.
 */
public class CapacidadExcedidaException extends Exception {
    /**
     * Constructor que recibe el mensaje de error.
     * @param mensaje Explicación de por qué el vehículo rechaza el pedido.
     */
    public CapacidadExcedidaException(String mensaje) { 
        super(mensaje); 
    }
}