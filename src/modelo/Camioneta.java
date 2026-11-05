package modelo;

/**
 * Clase Concreta que hereda de Vehiculo. Representa una Camioneta.
 */
public class Camioneta extends Vehiculo {
    
    /**
     * Constructor que fija los valores de referencia para la camioneta:
     * Capacidad 500kg, velocidad 30km/h y costo por km $300.
     * @param patente Patente de la camioneta.
     */
    public Camioneta(String patente) {
        super(patente, 500.0, 30.0, 300.0);
    }

    /**
     * Implementación del método abstracto.
     * Regla de negocio: La camioneta suma 10 minutos extras por carga y descarga.
     * @param km Distancia a recorrer.
     * @return Tiempo en minutos.
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60 + 10.0;
    }
}