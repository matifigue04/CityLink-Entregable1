package excepciones;
public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String mensaje) { super(mensaje); }
}