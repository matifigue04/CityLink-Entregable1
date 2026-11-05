package excepciones;

/**
 * Excepción de tipo Checked.
 * Se lanza cuando los datos de un pedido no cumplen con las reglas de negocio,
 * como un código duplicado, destino vacío, o peso/distancia negativos.
 */
public class PedidoInvalidoException extends Exception {
    /**
     * Constructor que recibe el mensaje descriptivo.
     * @param mensaje Detalle de la regla de negocio violada.
     */
    public PedidoInvalidoException(String mensaje) { 
        super(mensaje); 
    }
}