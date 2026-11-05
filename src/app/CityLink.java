package app;

import modelo.*;
import excepciones.*;

/**
 * Clase "Controladora" o "Gestora". Centraliza las operaciones, 
 * maneja los arreglos estáticos de almacenamiento y conecta el modelo con la interfaz.
 */
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

    /**
     * Constructor que inicializa los arreglos con capacidades fijas (Requisito E1)
     * y precarga los datos de prueba.
     */
    public CityLink() {
        this.clientes = new Cliente[MAX_CLIENTES];
        this.pedidos = new Pedido[MAX_PEDIDOS];
        this.repartidores = new Repartidor[MAX_REPARTIDORES];
        this.cantClientes = 0;
        this.cantPedidos = 0;
        this.cantRepartidores = 0;
        
        precargarDatos();
    }

    // --- MÉTODOS DE REGISTRO (Altas) ---

    /**
     * Agrega un nuevo cliente al sistema si hay espacio disponible.
     * @param c Objeto Cliente ya construido.
     * @throws AlmacenamientoLlenoException Si superó los 50 clientes.
     */
    public void registrarCliente(Cliente c) throws AlmacenamientoLlenoException {
        if (cantClientes >= MAX_CLIENTES) {
            throw new AlmacenamientoLlenoException("No hay espacio para más clientes.");
        }
        clientes[cantClientes] = c;
        cantClientes++;
    }

    /**
     * Agrega un nuevo repartidor al sistema si hay capacidad operativa.
     * @param r Objeto Repartidor (incluyendo su vehículo).
     * @throws AlmacenamientoLlenoException Si superó los 10 repartidores.
     */
    public void registrarRepartidor(Repartidor r) throws AlmacenamientoLlenoException {
        if (cantRepartidores >= MAX_REPARTIDORES) {
            throw new AlmacenamientoLlenoException("No hay espacio para más repartidores.");
        }
        repartidores[cantRepartidores] = r;
        cantRepartidores++;
    }

    /**
     * Ingresa un pedido nuevo a la central.
     * Ejecuta la regla de negocio que prohíbe tener códigos de pedido duplicados.
     * 
     * @param p Pedido polimórfico (Normal, Urgente o Frágil).
     * @throws AlmacenamientoLlenoException Si se llenaron los 100 cupos globales.
     * @throws PedidoInvalidoException Si el código alfanumérico ya existe.
     */
    public void registrarPedido(Pedido p) throws AlmacenamientoLlenoException, PedidoInvalidoException {
        if (cantPedidos >= MAX_PEDIDOS) {
            throw new AlmacenamientoLlenoException("No hay espacio para más pedidos.");
        }
        // Validacion de codigo duplicado (Búsqueda lineal en arreglo)
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo().equalsIgnoreCase(p.getCodigo())) {
                throw new PedidoInvalidoException("El código de pedido " + p.getCodigo() + " ya existe.");
            }
        }
        pedidos[cantPedidos] = p;
        cantPedidos++;
    }

    // --- SOBRECARGA DE BÚSQUEDAS ---

    /**
     * Sobrecarga 1: Busca un cliente por su identificador numérico.
     * @param codigo Entero a buscar.
     * @return El objeto Cliente.
     * @throws ClienteNoEncontradoException Si no existe coincidencia.
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con el código: " + codigo);
    }

    /**
     * Sobrecarga 2: Busca un cliente ignorando mayúsculas/minúsculas por su nombre exacto.
     * @param nombre Cadena de texto a buscar.
     * @return El objeto Cliente.
     * @throws ClienteNoEncontradoException Si no existe coincidencia.
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un cliente con el nombre: " + nombre);
    }

    /**
     * Busca de manera privada un pedido por código string.
     * Utilizado internamente por los demás métodos del gestor.
     */
    private Pedido buscarPedido(String codigo) throws PedidoInvalidoException {
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo().equalsIgnoreCase(codigo)) return pedidos[i];
        }
        throw new PedidoInvalidoException("El pedido " + codigo + " no existe.");
    }

    /**
     * Busca de manera privada un repartidor por su legajo.
     */
    private Repartidor buscarRepartidor(int legajo) throws Exception {
        for (int i = 0; i < cantRepartidores; i++) {
            if (repartidores[i].getLegajo() == legajo) return repartidores[i];
        }
        throw new Exception("El repartidor " + legajo + " no existe.");
    }

    // --- LÓGICA DE NEGOCIO PRINCIPAL ---
    
    /**
     * Asigna un pedido global a la bolsa de un repartidor específico.
     * Evalúa las reglas de capacidades del vehículo antes de materializar la asignación.
     * 
     * @param codigoPedido Identificador único del pedido.
     * @param legajo Identificador del empleado repartidor.
     * @throws Exception Propaga excepciones (CapacidadExcedida, AlmacenamientoLleno, etc.) hacia Main.
     */
    public void asignarPedidoAlRepartidor(String codigoPedido, int legajo) throws Exception {
        Pedido p = buscarPedido(codigoPedido);
        Repartidor r = buscarRepartidor(legajo);

        // Validación polimórfica del vehículo
        if (!r.getVehiculo().puedeTransportar(p)) {
            throw new CapacidadExcedidaException("El vehículo " + r.getVehiculo().getPatente() + " no puede transportar este pedido (excede peso o restricción frágil).");
        }
        
        // Efectiviza la asignación (el repartidor internamente calcula costos y cambia estado)
        r.asignarPedido(p);
    }

    /**
     * Modifica el estado de un pedido (PENDIENTE, EN_CAMINO, ENTREGADO, CANCELADO).
     * @param codigo ID del pedido.
     * @param nuevoEstado Estado texto destino.
     * @throws Exception Si el salto es inválido o no existe el pedido.
     */
    public void cambiarEstadoPedido(String codigo, String nuevoEstado) throws Exception {
        Pedido p = buscarPedido(codigo);
        p.cambiarEstado(nuevoEstado);
    }

    /**
     * Elimina un pedido del arreglo estático.
     * Demuestra el requisito de desplazar los elementos contiguos hacia la izquierda
     * para no dejar espacios en memoria (huecos 'null').
     * 
     * @param codigo ID del pedido a borrar.
     * @throws PedidoInvalidoException Si no se encuentra.
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
        
        // Bucle de desplazamiento hacia la izquierda
        for (int i = indice; i < cantPedidos - 1; i++) {
            pedidos[i] = pedidos[i + 1];
        }
        // Limpieza de la última celda duplicada
        pedidos[cantPedidos - 1] = null;
        cantPedidos--;
    }

    /**
     * Muestra la totalidad de pedidos evidenciando el uso de polimorfismo.
     * Calcula en tiempo de ejecución el tiempo estimado (usando la fórmula propia del vehículo).
     */
    public void listarPedidos() {
        System.out.println("--- Listado de Pedidos (Polimorfismo en Costo y Tiempo) ---");
        for (int i = 0; i < cantPedidos; i++) {
            Pedido p = pedidos[i];
            String tiempoStr = "N/A (Sin asignar)";
            
            // Si tiene vehículo asignado, polimórficamente calculamos su tiempo
            if (!p.obtenerEstadoActual().equals("PENDIENTE")) {
                for (int j = 0; j < cantRepartidores; j++) {
                    if (repartidores[j].tienePedido(p.getCodigo())) {
                        double tiempo = repartidores[j].getVehiculo().calcularTiempoEntrega(p.getDistanciaKm());
                        tiempoStr = String.format("%.1f min", tiempo);
                        break;
                    }
                }
            }
            
            System.out.println(p.getCodigo() + " | Estado: " + p.obtenerEstadoActual() + 
                               " | Costo: $" + p.calcularImporte() + " | Tiempo est.: " + tiempoStr);
        }
    }

    /**
     * Población de datos inicial obligatoria solicitada por el enunciado.
     * Utiliza bloques try-catch internos para no interrumpir el arranque del constructor.
     */
    private void precargarDatos() {
        try {
            // 5 Clientes
            registrarCliente(new Cliente(1, "Juan Perez", "2657111111", "Centro"));
            registrarCliente(new Cliente(2, "Maria Lopez", "2657222222", "Sur"));
            registrarCliente(new Cliente(3, "Carlos Diaz", "2657333333", "Norte"));
            registrarCliente(new Cliente(4, "Ana Gomez", "2657444444", "Este"));
            registrarCliente(new Cliente(5, "Luis Silva", "2657555555", "Oeste"));

            // 3 Repartidores con su polimorfismo de vehículos
            registrarRepartidor(new Repartidor(101, "Pedro (Bici)", new Bicicleta("BIC-01")));
            registrarRepartidor(new Repartidor(102, "Laura (Moto)", new Moto("MOT-123")));
            registrarRepartidor(new Repartidor(103, "Diego (Camioneta)", new Camioneta("CAM-999")));

            // 8 Pedidos variados (uso de herencia)
            registrarPedido(new PedidoNormal("P01", buscarCliente(1), "Plaza Central", 2.5, 3.0));
            registrarPedido(new PedidoUrgente("P02", buscarCliente(2), "Terminal", 1.0, 5.5));
            registrarPedido(new PedidoFragil("P03", buscarCliente(3), "Hospital", 5.0, 2.0));
            registrarPedido(new PedidoNormal("P04", buscarCliente(4), "Shopping", 15.0, 8.0));
            registrarPedido(new PedidoUrgente("P05", buscarCliente(5), "Universidad", 0.5, 1.2));
            registrarPedido(new PedidoFragil("P06", buscarCliente(1), "Estadio", 8.0, 4.0));
            registrarPedido(new PedidoNormal("P07", buscarCliente(2), "Parque", 12.0, 6.5));
            registrarPedido(new PedidoNormal("P08", buscarCliente(3), "Municipalidad", 3.0, 1.0));

        } catch (Exception e) {
            System.out.println("Error interno al precargar datos: " + e.getMessage());
        }
    }

    // --- GETTERS PARA LA VISTA MAIN ---
    public Pedido[] getPedidos() { return pedidos; }
    public int getCantPedidos() { return cantPedidos; }
    public Repartidor[] getRepartidores() { return repartidores; }
    public int getCantRepartidores() { return cantRepartidores; }
}