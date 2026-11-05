package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;

public class Main {
    public static void main(String[] args) {
        CityLink gestor = new CityLink();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- Menú CityLink ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar repartidor con su vehículo");
            System.out.println("3. Registrar pedido (normal, urgente o frágil)");
            System.out.println("4. Asignar un pedido a un repartidor");
            System.out.println("5. Cambiar el estado de un pedido");
            System.out.println("6. Buscar un cliente por código o por nombre");
            System.out.println("7. Listar pedidos con su costo y estado");
            System.out.println("8. Consultar el estado de un pedido o repartidor (Rastreable)");
            System.out.println("9. Ver importe total de pedidos y comisiones (Facturable)");
            System.out.println("10. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.print("Código (entero): ");
                        int codCli = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre: ");
                        String nomCli = sc.nextLine();
                        System.out.print("Teléfono: ");
                        String telCli = sc.nextLine();
                        System.out.print("Zona: ");
                        String zonaCli = sc.nextLine();
                        gestor.registrarCliente(new Cliente(codCli, nomCli, telCli, zonaCli));
                        System.out.println("Cliente registrado.");
                        break;

                    case 2:
                        System.out.print("Legajo (entero): ");
                        int legRep = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre del repartidor: ");
                        String nomRep = sc.nextLine();
                        System.out.print("Tipo de vehículo (1: Bici, 2: Moto, 3: Camioneta): ");
                        int tipoVehiculo = Integer.parseInt(sc.nextLine());
                        System.out.print("Patente: ");
                        String patente = sc.nextLine();
                        
                        Vehiculo v;
                        if (tipoVehiculo == 1) v = new Bicicleta(patente);
                        else if (tipoVehiculo == 2) v = new Moto(patente);
                        else v = new Camioneta(patente);
                        
                        gestor.registrarRepartidor(new Repartidor(legRep, nomRep, v));
                        System.out.println("Repartidor registrado.");
                        break;

                    case 3:
                        System.out.print("Tipo de pedido (1: Normal, 2: Urgente, 3: Frágil): ");
                        int tipoPed = Integer.parseInt(sc.nextLine());
                        System.out.print("Código del pedido (ej. P10): ");
                        String codPed = sc.nextLine();
                        System.out.print("Código del cliente que lo solicita: ");
                        int idCli = Integer.parseInt(sc.nextLine());
                        
                        // Si el cliente no existe, buscarCliente lanza la excepción y corta la operación
                        Cliente clientePedido = gestor.buscarCliente(idCli); 
                        
                        System.out.print("Destino: ");
                        String destino = sc.nextLine();
                        System.out.print("Peso en kg: ");
                        double peso = Double.parseDouble(sc.nextLine());
                        System.out.print("Distancia en km: ");
                        double distancia = Double.parseDouble(sc.nextLine());
                        
                        Pedido nuevoPedido;
                        if (tipoPed == 1) nuevoPedido = new PedidoNormal(codPed, clientePedido, destino, peso, distancia);
                        else if (tipoPed == 2) nuevoPedido = new PedidoUrgente(codPed, clientePedido, destino, peso, distancia);
                        else nuevoPedido = new PedidoFragil(codPed, clientePedido, destino, peso, distancia);
                        
                        gestor.registrarPedido(nuevoPedido);
                        System.out.println("Pedido registrado.");
                        break;

                    case 4:
                        System.out.print("Ingrese código del pedido (ej: P01): ");
                        String cod = sc.nextLine();
                        System.out.print("Ingrese legajo del repartidor: ");
                        int legajo = Integer.parseInt(sc.nextLine());
                        gestor.asignarPedidoAlRepartidor(cod, legajo);
                        System.out.println("Pedido asignado con éxito.");
                        break;

                    case 5:
                        System.out.print("Ingrese código del pedido: ");
                        String codEst = sc.nextLine();
                        System.out.print("Nuevo estado (EN_CAMINO, ENTREGADO, CANCELADO): ");
                        String estado = sc.nextLine();
                        gestor.cambiarEstadoPedido(codEst, estado);
                        System.out.println("Estado actualizado.");
                        
                        // Si el estado es ENTREGADO, le indicamos al repartidor que registre su entrega para cobrar comisión
                        if (estado.equalsIgnoreCase("ENTREGADO")) {
                            for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                                gestor.getRepartidores()[i].registrarEntrega(codEst);
                            }
                        }
                        break;

                    case 6:
                        System.out.print("Buscar por (1: Código, 2: Nombre): ");
                        int tipoBusqueda = Integer.parseInt(sc.nextLine());
                        if (tipoBusqueda == 1) {
                            System.out.print("Ingrese código: ");
                            System.out.println(gestor.buscarCliente(Integer.parseInt(sc.nextLine())));
                        } else {
                            System.out.print("Ingrese nombre: ");
                            System.out.println(gestor.buscarCliente(sc.nextLine()));
                        }
                        break;

                    case 7:
                        gestor.listarPedidos();
                        break;

                    case 8:
                        System.out.print("Consultar estado de (1: Pedido, 2: Repartidor): ");
                        int tipoCons = Integer.parseInt(sc.nextLine());
                        boolean encontrado = false;
                        
                        if (tipoCons == 1) {
                            System.out.print("Código del pedido: ");
                            String cp = sc.nextLine();
                            for (int i = 0; i < gestor.getCantPedidos(); i++) {
                                if (gestor.getPedidos()[i].getCodigo().equalsIgnoreCase(cp)) {
                                    // Demostración de Polimorfismo con la Interfaz Rastreable
                                    Rastreable r = gestor.getPedidos()[i];
                                    System.out.println("Estado (Rastreable): " + r.obtenerEstadoActual());
                                    encontrado = true; 
                                    break;
                                }
                            }
                        } else {
                            System.out.print("Legajo del repartidor: ");
                            int lr = Integer.parseInt(sc.nextLine());
                            for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                                if (gestor.getRepartidores()[i].getLegajo() == lr) {
                                    // Demostración de Polimorfismo con la Interfaz Rastreable
                                    Rastreable r = gestor.getRepartidores()[i];
                                    System.out.println("Estado (Rastreable): " + r.obtenerEstadoActual());
                                    encontrado = true; 
                                    break;
                                }
                            }
                        }
                        if (!encontrado) System.out.println("No se encontró el elemento.");
                        break;

                    case 9:
                        double totalPedidos = 0;
                        System.out.println("\n--- Importes (Uso de interfaz Facturable) ---");
                        
                        for (int i = 0; i < gestor.getCantPedidos(); i++) {
                            // Demostración de Polimorfismo con la Interfaz Facturable
                            Facturable f = gestor.getPedidos()[i];
                            totalPedidos += f.calcularImporte();
                        }
                        System.out.println("Importe total de todos los pedidos registrados: $" + totalPedidos);
                        
                        System.out.println("Comisiones acumuladas (10% de lo entregado) por repartidor:");
                        for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                            Facturable f = gestor.getRepartidores()[i];
                            System.out.println("- " + gestor.getRepartidores()[i].getNombre() + ": $" + f.calcularImporte());
                        }
                        break;

                    case 10:
                        System.out.println("Cerrando el sistema CityLink...");
                        break;

                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            } catch (CapacidadExcedidaException | PedidoInvalidoException | ClienteNoEncontradoException | EstadoInvalidoException | AlmacenamientoLlenoException e) {
                System.out.println("Error de negocio: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            } finally {
                System.out.println("[Operación finalizada]");
            }
        } while (opcion != 10);
        
        sc.close();
    }
}