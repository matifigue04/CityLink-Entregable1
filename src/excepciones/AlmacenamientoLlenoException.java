package excepciones;

/**
 * Excepción de tipo Checked.
 * Utilizada para controlar los límites de los arreglos estáticos del sistema
 * (50 clientes, 100 pedidos, 10 repartidores o 20 pedidos por repartidor).
 */
public class AlmacenamientoLlenoException extends Exception {
    /**
     * Constructor de la excepción.
     * @param mensaje Mensaje indicando qué almacenamiento está lleno.
     */
    public AlmacenamientoLlenoException(String mensaje) { 
        super(mensaje); 
    }
}