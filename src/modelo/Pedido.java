package modelo;

import excepciones.EstadoInvalidoException;
import java.time.LocalDateTime;

/**
 * Clase Abstracta que encapsula la información común de todos los envíos.
 * Implementa múltiples interfaces (Rastreable, Facturable) para cumplir
 * con el requisito de pseudo-herencia múltiple exigido por la rúbrica.
 */
public abstract class Pedido implements Rastreable, Facturable {
    private String codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private LocalDateTime fechaHora;
    private String estado;
    protected double costoFinal; // Protected para que las hijas puedan acceder y asignar el valor.

    /**
     * Constructor base de un Pedido.
     * Inicializa siempre el estado en "PENDIENTE" (Regla de negocio).
     * 
     * @param codigo      Código alfanumérico único (Ej: P01).
     * @param cliente     Objeto cliente solicitante.
     * @param destino     Lugar de destino.
     * @param pesoKg      Peso total del envío.
     * @param distanciaKm Distancia en kilómetros.
     */
    public Pedido(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.destino = destino;
        this.pesoKg = pesoKg;
        this.distanciaKm = distanciaKm;
        this.fechaHora = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    /**
     * Método abstracto para el cálculo de costos usando el polimorfismo del vehículo.
     * Cada tipo de pedido (Normal, Urgente, Frágil) aplicará sus recargos correspondientes.
     * 
     * @param vehiculo El vehículo encargado del reparto.
     * @return El costo monetario final del envío.
     */
    public abstract double calcularCosto(Vehiculo vehiculo);

    /**
     * Método abstracto que evita el uso de "instanceof".
     * Permite consultar a cualquier pedido si requiere trato frágil.
     * 
     * @return true si es frágil, false si es normal o urgente.
     */
    public abstract boolean esFragil();

    /**
     * Aplica el diagrama de estados estrictos del negocio.
     * Flujo válido: PENDIENTE -> EN_CAMINO -> ENTREGADO.
     * Solo se cancela desde PENDIENTE.
     * 
     * @param nuevoEstado El estado al que intenta transicionar.
     * @throws EstadoInvalidoException Excepción Unchecked si se rompe la regla de transición.
     */
    public void cambiarEstado(String nuevoEstado) {
        if (this.estado.equals("PENDIENTE")) {
            if (!nuevoEstado.equals("EN_CAMINO") && !nuevoEstado.equals("CANCELADO")) {
                throw new EstadoInvalidoException("Desde PENDIENTE solo se puede pasar a EN_CAMINO o CANCELADO.");
            }
        } else if (this.estado.equals("EN_CAMINO")) {
            if (!nuevoEstado.equals("ENTREGADO")) {
                throw new EstadoInvalidoException("Desde EN_CAMINO solo se puede pasar a ENTREGADO.");
            }
        } else {
            throw new EstadoInvalidoException("El pedido ya está " + this.estado + " y no admite cambios.");
        }
        this.estado = nuevoEstado;
    }

    // --- IMPLEMENTACIÓN DE INTERFACES ---

    /**
     * Implementación de la interfaz Rastreable.
     * @return El estado actual de la máquina de estados.
     */
    @Override
    public String obtenerEstadoActual() { 
        return this.estado; 
    }

    /**
     * Implementación de la interfaz Facturable.
     * @return El costo final guardado en la variable protected.
     */
    @Override
    public double calcularImporte() { 
        return this.costoFinal; 
    }

    // --- GETTERS ---
    public String getCodigo() { return codigo; }
    public double getPesoKg() { return pesoKg; }
    public double getDistanciaKm() { return distanciaKm; }
    public Cliente getCliente() { return cliente; }
}