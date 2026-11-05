package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

/**
 * Clase Concreta que vincula un Vehículo con los Pedidos asignados.
 * Implementa Rastreable para ver su carga actual, y Facturable para su comisión.
 */
public class Repartidor implements Rastreable, Facturable {
    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidosAsignados; // Arreglo estático requerido en Entregable 1
    private int cantidadPedidos;
    private double comisionAcumulada;

    /**
     * Constructor del Repartidor.
     * Prepara el arreglo estático con un tope fijo de 20 pedidos simultáneos.
     * 
     * @param legajo   Número de legajo laboral.
     * @param nombre   Nombre completo.
     * @param vehiculo El medio de transporte que opera.
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.pedidosAsignados = new Pedido[20];
        this.cantidadPedidos = 0;
        this.comisionAcumulada = 0.0;
    }

    /**
     * Asigna un pedido a la carga del repartidor si hay espacio.
     * Transiciona el estado a EN_CAMINO y calcula los costos monetarios.
     * 
     * @param pedido El pedido a agregar.
     * @throws AlmacenamientoLlenoException Si el arreglo llega a 20.
     */
    public void asignarPedido(Pedido pedido) throws AlmacenamientoLlenoException {
        if (cantidadPedidos >= pedidosAsignados.length) {
            throw new AlmacenamientoLlenoException("El repartidor " + nombre + " no puede aceptar más pedidos.");
        }
        pedidosAsignados[cantidadPedidos] = pedido;
        cantidadPedidos++;
        
        pedido.cambiarEstado("EN_CAMINO");
        pedido.calcularCosto(this.vehiculo);
    }

    /**
     * SOBRECARGA DE MÉTODOS (Exigencia de rúbrica).
     * Asigna un pedido forzando el uso de un vehículo alternativo en la fórmula de costos.
     * 
     * @param pedido El pedido a agregar.
     * @param vehiculoAlternativo Vehículo temporal usado para el cálculo.
     * @throws AlmacenamientoLlenoException Si no hay espacio.
     * @throws CapacidadExcedidaException Si el vehículo temporal no soporta el peso/fragilidad.
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculoAlternativo) throws AlmacenamientoLlenoException, CapacidadExcedidaException {
        if (!vehiculoAlternativo.puedeTransportar(pedido)) {
            throw new CapacidadExcedidaException("El vehículo alternativo no puede transportar este pedido.");
        }
        if (cantidadPedidos >= pedidosAsignados.length) {
            throw new AlmacenamientoLlenoException("El repartidor " + nombre + " no puede aceptar más pedidos.");
        }
        pedidosAsignados[cantidadPedidos] = pedido;
        cantidadPedidos++;
        
        pedido.cambiarEstado("EN_CAMINO");
        pedido.calcularCosto(vehiculoAlternativo); 
    }

    /**
     * Actualiza un pedido asignado a entregado e incrementa el pozo monetario.
     * Regla de negocio: La comisión es el 10% del importe total del envío.
     * 
     * @param codigoPedido El código a marcar como entregado.
     */
    public void registrarEntrega(String codigoPedido) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosAsignados[i].getCodigo().equals(codigoPedido)) {
                pedidosAsignados[i].cambiarEstado("ENTREGADO");
                comisionAcumulada += (pedidosAsignados[i].calcularImporte() * 0.10);
                return;
            }
        }
    }

    /**
     * Búsqueda auxiliar utilizada por el listado global.
     * @param codigo Código del pedido.
     * @return true si el repartidor transporta o transportó ese código.
     */
    public boolean tienePedido(String codigo) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosAsignados[i].getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }

    /**
     * Implementación de interfaz Rastreable.
     * @return Texto con resumen de la carga laboral y vehículo.
     */
    @Override
    public String obtenerEstadoActual() {
        return "Repartidor [" + legajo + "] " + nombre + " | Vehículo: " + vehiculo.getPatente() + " | Pedidos a cargo: " + cantidadPedidos;
    }

    /**
     * Implementación de interfaz Facturable.
     * @return Dinero generado para el bolsillo del repartidor.
     */
    @Override
    public double calcularImporte() { 
        return comisionAcumulada; 
    }

    // --- GETTERS ---
    public int getLegajo() { return legajo; }
    public String getNombre() { return nombre; }
    public Vehiculo getVehiculo() { return vehiculo; }
}