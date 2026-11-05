package modelo;

public class Camioneta extends Vehiculo {
    public Camioneta(String patente) {
        super(patente, 500.0, 30.0, 300.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60 + 10.0;
    }
}