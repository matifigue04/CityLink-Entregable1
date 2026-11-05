package modelo;

public class PedidoUrgente extends Pedido {

    public PedidoUrgente(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        this.costoFinal = costoBase * 1.50; 
        return this.costoFinal;
    }

    @Override
    public boolean esFragil() {
        return false;
    }
}