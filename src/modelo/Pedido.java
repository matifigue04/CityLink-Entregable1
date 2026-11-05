package modelo;

import excepciones.EstadoInvalidoException;
import java.time.LocalDateTime;

public abstract class Pedido implements Rastreable, Facturable {
    private String codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg;
    private double distanciaKm;
    private LocalDateTime fechaHora;
    private String estado;
    protected double costoFinal; // Se almacena una vez que se le asigna un vehículo

    public Pedido(String codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.destino = destino;
        this.pesoKg = pesoKg;
        this.distanciaKm = distanciaKm;
        this.fechaHora = LocalDateTime.now();
        this.estado = "PENDIENTE"; // Todo pedido ingresa en este estado
    }

    // Metodo abstracto: cada hijo decide su propia formula de costo
    public abstract double calcularCosto(Vehiculo vehiculo);
    
    // Metodo abstracto para evitar usar "instanceof" cuando la Bicicleta valide si puede transportarlo
    public abstract boolean esFragil();

    // Logica estricta de transicion de estados
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

    @Override
    public String obtenerEstadoActual() {
        return this.estado;
    }

    @Override
    public double calcularImporte() {
        return this.costoFinal;
    }

    // Getters necesarios para que los vehiculos validen capacidad
    public String getCodigo() { return codigo; }
    public double getPesoKg() { return pesoKg; }
    public double getDistanciaKm() { return distanciaKm; }
    public Cliente getCliente() { return cliente; }
}