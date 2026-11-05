package modelo;

/**
 * Pedido delicado. Hereda de Pedido, aplica un costo fijo extra y 
 * restringe los vehículos compatibles.
 */
public class PedidoFragil extends Pedido {
    
    /**
     * Constructor de pedido frágil. Pasa parámetros a la clase base.
     */
    public PedidoFragil(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Regla de negocio: Suma un recargo fijo de $800 sobre el costo total.
     */
    @Override
    public double calcularCosto(Vehiculo vehiculo) {
        this.costoFinal = 1000 + (getDistanciaKm() * vehiculo.getCostoPorKm()) + 800;
        return this.costoFinal;
    }

    /**
     * Informa al polimorfismo que este paquete es delicado.
     * (Será usado por la Bicicleta para rechazarlo).
     * @return true.
     */
    @Override
    public boolean esFragil() { 
        return true; 
    }
}