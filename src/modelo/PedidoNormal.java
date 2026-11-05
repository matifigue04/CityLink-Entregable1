package modelo;

/**
 * Pedido estándar sin recargos adicionales. Hereda de Pedido.
 */
public class PedidoNormal extends Pedido {
    
    /**
     * Constructor de pedido normal. Pasa parámetros a la clase base.
     */
    public PedidoNormal(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Regla de negocio: Costo base $1000 + (distancia * costoKM). Sin recargos.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        this.costoFinal = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        return this.costoFinal;
    }

    /**
     * @return false, no tiene restricciones de fragilidad.
     */
    @Override
    public boolean esFragil() { 
        return false; 
    }
}