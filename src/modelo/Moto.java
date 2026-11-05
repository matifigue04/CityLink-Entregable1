package modelo;

public class Moto extends Vehiculo {
    public Moto(String patente) {
        super(patente, 30.0, 40.0, 120.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60 + 5.0;
    }
}