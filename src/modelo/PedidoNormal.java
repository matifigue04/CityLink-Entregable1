package modelo;

public class PedidoNormal extends Pedido {
    
    public PedidoNormal(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        this.costoFinal = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        return this.costoFinal;
    }

    @Override
    public boolean esFragil() {
        return false;
    }
}