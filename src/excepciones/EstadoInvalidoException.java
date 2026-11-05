package excepciones;

/**
 * Excepción de tipo Unchecked (hereda de RuntimeException).
 * Se lanza cuando se intenta realizar una transición de estado ilícita en un pedido
 * (ej. pasar de EN_CAMINO a CANCELADO). Al ser Unchecked, representa un error lógico
 * en el flujo de la aplicación.
 */
public class EstadoInvalidoException extends RuntimeException {
    /**
     * Constructor de la excepción.
     * @param mensaje Detalle del estado actual y el salto inválido intentado.
     */
    public EstadoInvalidoException(String mensaje) { 
        super(mensaje); 
    }
}