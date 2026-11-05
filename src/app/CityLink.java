package app;

import modelo.*;
import excepciones.*;

public class CityLink {
    private final int MAX_CLIENTES = 50;
    private final int MAX_PEDIDOS = 100;
    private final int MAX_REPARTIDORES = 10;

    private Cliente[] clientes;
    private Pedido[] pedidos;
    private Repartidor[] repartidores;

    private int cantClientes;
    private int cantPedidos;
    private int cantRepartidores;

    public CityLink() {
        this.clientes = new Cliente[MAX_CLIENTES];
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.repartidores = new Repartidor[MAX_REPARTIDORES];
        this.cantClientes = 0;
        this.cantPedidos = 0;
        this.cantRepartidores = 0;
        
        precargarDatos(); // Exigido por el enunciado al iniciar el sistema
    }

    // --- MÉTODOS DE REGISTRO ---
    
    public void registrarCliente(Cliente c) throws AlmacenamientoLlenoException {
        if (cantClientes >= MAX_CLIENTES) {
            throw new AlmacenamientoLlenoException("No hay espacio para más clientes.");
        }
        clientes[cantClientes] = c;
        cantClientes++;
    }

    public void registrarRepartidor(Repartidor r) throws AlmacenamientoLlenoException {
        if (cantRepartidores >= MAX_REPARTIDORES) {
            throw new AlmacenamientoLlenoException("No hay espacio para más repartidores.");
        }
        repartidores[cantRepartidores] = r;
        cantRepartidores++;
    }

    public void registrarPedido(Pedido p) throws AlmacenamientoLlenoException, PedidoInvalidoException {
        if (cantPedidos >= MAX_PEDIDOS) {
            throw new AlmacenamientoLlenoException("No hay espacio para más pedidos.");
        }
        // Validacion de codigo duplicado
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo().equals(p.getCodigo())) {
                throw new PedidoInvalidoException("El código de pedido " + p.getCodigo() + " ya existe.");
            }
        }
        pedidos[cantPedidos] = p;
        cantPedidos++;
    }

    // --- SOBRECARGA DE METODOS (Exigencia del Entregable 1) ---

    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con el código: " + codigo);
    }

    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con el nombre: " + nombre);
    }

    // --- PRECARGA DE DATOS ---

    private void precargarDatos() {
        try {
            // 5 Clientes
            registrarCliente(new Cliente(1, "Juan Perez", "2657111111", "Centro"));
            registrarCliente(new Cliente(2, "Maria Lopez", "2657222222", "Sur"));
            registrarCliente(new Cliente(3, "Carlos Diaz", "2657333333", "Norte"));
            registrarCliente(new Cliente(4, "Ana Gomez", "2657444444", "Este"));
            registrarCliente(new Cliente(5, "Luis Silva", "2657555555", "Oeste"));

            // 3 Repartidores (uno de cada tipo de vehículo)
            registrarRepartidor(new Repartidor(101, "Pedro (Bici)", new Bicicleta("BIC-01")));
            registrarRepartidor(new Repartidor(102, "Laura (Moto)", new Moto("MOT-123")));
            registrarRepartidor(new Repartidor(103, "Diego (Camioneta)", new Camioneta("CAM-999")));

            // 8 Pedidos (incluyendo los tres tipos)
            registrarPedido(new PedidoNormal("P01", buscarCliente(1), "Plaza Central", 2.5, 3.0));
            registrarPedido(new PedidoUrgente("P02", buscarCliente(2), "Terminal", 1.0, 5.5));
            registrarPedido(new PedidoFragil("P03", buscarCliente(3), "Hospital", 5.0, 2.0));
            registrarPedido(new PedidoNormal("P04", buscarCliente(4), "Shopping", 15.0, 8.0));
            registrarPedido(new PedidoUrgente("P05", buscarCliente(5), "Universidad", 0.5, 1.2));
            registrarPedido(new PedidoFragil("P06", buscarCliente(1), "Estadio", 8.0, 4.0));
            registrarPedido(new PedidoNormal("P07", buscarCliente(2), "Parque", 12.0, 6.5));
            registrarPedido(new PedidoNormal("P08", buscarCliente(3), "Municipalidad", 3.0, 1.0));

        } catch (Exception e) {
            System.out.println("Error al precargar datos: " + e.getMessage());
        }
    }

    // Getters provisorios para que Main pueda acceder a los arreglos
    public Pedido[] getPedidos() { return pedidos; }
    public int getCantPedidos() { return cantPedidos; }
    public Repartidor[] getRepartidores() { return repartidores; }
    public int getCantRepartidores() { return cantRepartidores; }

    // --- LÓGICA DE ASIGNACIÓN Y NEGOCIO ---
    
    public void asignarPedidoAlRepartidor(String codigoPedido, int legajo) throws Exception {
        Pedido p = buscarPedido(codigoPedido);
        Repartidor r = buscarRepartidor(legajo);

        if (!r.getVehiculo().puedeTransportar(p)) {
            throw new CapacidadExcedidaException("El vehículo " + r.getVehiculo().getPatente() + " no puede transportar este pedido (excede peso o restricción frágil).");
        }
        r.asignarPedido(p);
    }

    public void cambiarEstadoPedido(String codigo, String nuevoEstado) throws Exception {
        Pedido p = buscarPedido(codigo);
        p.cambiarEstado(nuevoEstado);
    }

    public void listarPedidos() {
        System.out.println("--- Listado de Pedidos (Polimorfismo) ---");
        for (int i = 0; i < cantPedidos; i++) {
            Pedido p = pedidos[i];
            // Se asume 15 km de distancia de prueba para el cálculo de tiempo si no tiene vehículo asignado
            System.out.println(p.getCodigo() + " | Estado: " + p.obtenerEstadoActual() + 
                               " | Costo actual calculado: $" + p.calcularImporte());
        }
    }

    // Métodos auxiliares de búsqueda
    private Pedido buscarPedido(String codigo) throws PedidoInvalidoException {
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo().equalsIgnoreCase(codigo)) return pedidos[i];
        }
        throw new PedidoInvalidoException("El pedido " + codigo + " no existe.");
    }

    private Repartidor buscarRepartidor(int legajo) throws Exception {
        for (int i = 0; i < cantRepartidores; i++) {
            if (repartidores[i].getLegajo() == legajo) return repartidores[i];
        }
        throw new Exception("El repartidor " + legajo + " no existe.");
    }
    /**
     * Elimina un pedido del arreglo estático y desplaza los elementos para no dejar huecos.
     */
    public void eliminarPedido(String codigo) throws PedidoInvalidoException {
        int indice = -1;
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo().equalsIgnoreCase(codigo)) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            throw new PedidoInvalidoException("El pedido " + codigo + " no se encontró para eliminar.");
        }
        
        // Desplazamiento hacia la izquierda para tapar el hueco
        for (int i = indice; i < cantPedidos - 1; i++) {
            pedidos[i] = pedidos[i + 1];
        }
        pedidos[cantPedidos - 1] = null;
        cantPedidos--;
    }
}