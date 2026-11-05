package excepciones;

/**
 * Excepción de tipo Checked (hereda de Exception).
 * Se lanza cuando se intenta buscar un cliente por código o nombre y este no existe en los registros.
 * Obliga al programa a capturarla (try-catch) para evitar que se caiga el sistema.
 */
public class ClienteNoEncontradoException extends Exception {
    /**
     * Constructor que recibe el mensaje de error.
     * @param mensaje Detalle del cliente no encontrado.
     */
    public ClienteNoEncontradoException(String mensaje) { 
        super(mensaje); 
    }
}