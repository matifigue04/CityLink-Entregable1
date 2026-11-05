package modelo;

/**
 * Clase Concreta que hereda de Vehiculo. Representa una Moto.
 */
public class Moto extends Vehiculo {
    
    /**
     * Constructor que fija los valores de referencia para la moto:
     * Capacidad 30kg, velocidad 40km/h y costo por km $120.
     * @param patente Patente de la moto.
     */
    public Moto(String patente) {
        super(patente, 30.0, 40.0, 120.0);
    }

    /**
     * Implementación del método abstracto.
     * Regla de negocio: La moto suma 5 minutos extras de estacionamiento.
     * @param km Distancia a recorrer.
     * @return Tiempo en minutos.
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60 + 5.0;
    }
}