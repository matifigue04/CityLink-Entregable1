package modelo;

public class Bicicleta extends Vehiculo {
    public Bicicleta(String patente) {
        super(patente, 10.0, 15.0, 50.0);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return (km / getVelocidadKmH()) * 60;
    }

    @Override
    public boolean puedeTransportar(Pedido pedido) {
        return super.puedeTransportar(pedido) && !pedido.esFragil();
    }
}