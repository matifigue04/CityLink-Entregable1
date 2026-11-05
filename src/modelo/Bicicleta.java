package modelo;

/**
 * Clase Concreta que hereda de Vehiculo. Representa una Bicicleta.
 */
public class Bicicleta extends Vehiculo {
    
    /**
     * Constructor que fija los valores de referencia para la bicicleta:
     * Capacidad 10kg, velocidad 15km/h y costo por km $50.
     * @param patente Identificador de la bicicleta.
     */
    public Bicicleta(String patente) {
        super(patente, 10.0, 15.0, 50.0);
    }

    /**
     * Implementación (Redefinición) del método abstracto.
     * La bicicleta no tiene tiempos extra.
     * @param km Distancia en kilómetros.
     * @return Tiempo en minutos (Distancia / Velocidad * 60).
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60;
    }

    /**
     * Redefinición (Polimorfismo de métodos) de la regla de transporte.
     * Agrega la restricción del negocio: la bicicleta no puede llevar pedidos frágiles.
     * 
     * @param pedido El pedido a evaluar.
     * @return true solo si no excede el peso Y no es un pedido frágil.
     */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        // Llama a la regla base de peso (super) y le suma la verificación de fragilidad.
        return super.puedeTransportar(pedido) && !pedido.esFragil();
    }
}