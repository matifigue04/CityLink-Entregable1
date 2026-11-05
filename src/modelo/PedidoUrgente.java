package modelo;

/**
 * Pedido prioritario. Hereda de Pedido y aplica un multiplicador de urgencia.
 */
public class PedidoUrgente extends Pedido {
    
    /**
     * Constructor de pedido urgente. Pasa parámetros a la clase base.
     */
    public PedidoUrgente(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Regla de negocio: Suma un 50% extra sobre el costo total calculado.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        double costoBase = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm());
        this.costoFinal = costoBase * 1.50; 
        return this.costoFinal;
    }

    /**
     * @return false, no tiene restricciones físicas de fragilidad.
     */
    @Override
    public boolean esFragil() { 
        return false; 
    }
}