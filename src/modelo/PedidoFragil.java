package modelo;

public class PedidoFragil extends Pedido {

    public PedidoFragil(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        this.costoFinal = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm()) + 800;
        return this.costoFinal;
    }

    @Override
    public boolean esFragil() {
        return true;
    }
}