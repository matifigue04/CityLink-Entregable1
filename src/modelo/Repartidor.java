package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;

public class Repartidor implements Rastreable, Facturable {
    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidosAsignados;
    private int cantidadPedidos;
    private double comisionAcumulada;

    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.pedidosAsignados = new Pedido[20]; // Arreglo estático (capacidad fija)
        this.cantidadPedidos = 0;
        this.comisionAcumulada = 0.0;
    }

    public void asignarPedido(Pedido pedido) throws AlmacenamientoLlenoException {
        if (cantidadPedidos >= pedidosAsignados.length) {
            throw new AlmacenamientoLlenoException("El repartidor " + nombre + " no puede aceptar más pedidos.");
        }
        pedidosAsignados[cantidadPedidos] = pedido;
        cantidadPedidos++;
        
        pedido.cambiarEstado("EN_CAMINO");
        pedido.calcularCosto(this.vehiculo); // Calcula y guarda el costo final usando las matemáticas del vehículo
    }
    /**
     * Sobrecarga exigida: Asigna un pedido forzando el cálculo con un vehículo alternativo.
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
     * Método auxiliar para verificar si este repartidor tiene un pedido específico.
     */
    public boolean tienePedido(String codigo) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosAsignados[i].getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }

    public void registrarEntrega(String codigoPedido) {
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidosAsignados[i].getCodigo().equals(codigoPedido)) {
                pedidosAsignados[i].cambiarEstado("ENTREGADO");
                // Suma el 10% del costo final del pedido a la comisión del repartidor
                comisionAcumulada += (pedidosAsignados[i].calcularImporte() * 0.10);
                return;
            }
        }
    }

    @Override
    public String obtenerEstadoActual() {
        return "Repartidor [" + legajo + "] " + nombre + " | Vehículo: " + vehiculo.getPatente() + " | Pedidos a cargo: " + cantidadPedidos;
    }

    @Override
    public double calcularImporte() {
        return comisionAcumulada;
    }

    // Getters
    public int getLegajo() { return legajo; }
    public String getNombre() { return nombre; }
    public Vehiculo getVehiculo() { return vehiculo; }
}