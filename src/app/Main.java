package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;

/**
 * Clase principal que actúa como Vista (Consola).
 * Se encarga puramente de la interacción con el usuario, captura de teclado
 * y propagación estructurada de errores. No contiene reglas de negocio.
 */
public class Main {
    
    /**
     * Punto de entrada principal (Entry point) de la máquina virtual de Java.
     * @param args Argumentos de la línea de comandos (No utilizados).
     */
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

            // Bloque global TRY-CATCH-FINALLY (Exigencia técnica)
            // Asegura que excepciones inesperadas o errores de tipado no colapsen la consola.
            try {
                // Se lee como String y se parsea. Evita bucles infinitos de la clase Scanner si meten letras.
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
                        System.out.println("Cliente registrado exitosamente.");
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
                        
                        // Creación polimórfica del vehículo
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
                        System.out.print("Código del cliente solicitante (1-5 precargados): ");
                        int idCli = Integer.parseInt(sc.nextLine());
                        
                        // Si no se encuentra, salta al CATCH y frena el alta automáticamente
                        Cliente clientePedido = gestor.buscarCliente(idCli); 
                        
                        System.out.print("Destino: ");
                        String destino = sc.nextLine();
                        System.out.print("Peso total en kg: ");
                        double peso = Double.parseDouble(sc.nextLine());
                        System.out.print("Distancia calculada en km: ");
                        double distancia = Double.parseDouble(sc.nextLine());
                        
                        // Polimorfismo de jerarquía: Variable base apuntando a hijos distintos
                        Pedido nuevoPedido;
                        if (tipoPed == 1) nuevoPedido = new PedidoNormal(codPed, clientePedido, destino, peso, distancia);
                        else if (tipoPed == 2) nuevoPedido = new PedidoUrgente(codPed, clientePedido, destino, peso, distancia);
                        else nuevoPedido = new PedidoFragil(codPed, clientePedido, destino, peso, distancia);
                        
                        gestor.registrarPedido(nuevoPedido);
                        System.out.println("Pedido registrado con estado inicial PENDIENTE.");
                        break;

                    case 4:
                        System.out.print("Ingrese código del pedido (ej: P01): ");
                        String cod = sc.nextLine();
                        System.out.print("Ingrese legajo del repartidor (ej: 101): ");
                        int legajo = Integer.parseInt(sc.nextLine());
                        
                        // El gestor delega en los objetos validar pesos y lanzar excepciones (CapacidadExcedidaException)
                        gestor.asignarPedidoAlRepartidor(cod, legajo);
                        System.out.println("El pedido ha sido cargado en el vehículo con éxito.");
                        break;

                    case 5:
                        System.out.print("Ingrese código del pedido: ");
                        String codEst = sc.nextLine();
                        System.out.print("Nuevo estado (EN_CAMINO, ENTREGADO, CANCELADO): ");
                        String estado = sc.nextLine();
                        
                        // Lanza EstadoInvalidoException (RuntimeException) si rompe el flujo estricto
                        gestor.cambiarEstadoPedido(codEst, estado);
                        System.out.println("Actualización de tracking exitosa.");
                        
                        // Operación acoplada: Si se entregó, el repartidor cobra la comisión
                        if (estado.equalsIgnoreCase("ENTREGADO")) {
                            for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                                gestor.getRepartidores()[i].registrarEntrega(codEst);
                            }
                        }
                        break;

                    case 6:
                        System.out.print("Seleccione método de búsqueda (1: Por Código entero, 2: Por Nombre exacto): ");
                        int tipoBusqueda = Integer.parseInt(sc.nextLine());
                        
                        // Demostración práctica de la SOBRECARGA de métodos (Requisito E1)
                        if (tipoBusqueda == 1) {
                            System.out.print("Código: ");
                            System.out.println(gestor.buscarCliente(Integer.parseInt(sc.nextLine())));
                        } else {
                            System.out.print("Nombre: ");
                            System.out.println(gestor.buscarCliente(sc.nextLine()));
                        }
                        break;

                    case 7:
                        // Listado polimórfico iterando los arreglos base. Muestra costo calculado y tiempo estimado.
                        gestor.listarPedidos();
                        break;

                    case 8:
                        System.out.print("Consultar auditoría de estado (1: Pedido, 2: Repartidor): ");
                        int tipoCons = Integer.parseInt(sc.nextLine());
                        boolean encontrado = false;
                        
                        if (tipoCons == 1) {
                            System.out.print("Código del pedido: ");
                            String cp = sc.nextLine();
                            for (int i = 0; i < gestor.getCantPedidos(); i++) {
                                if (gestor.getPedidos()[i].getCodigo().equalsIgnoreCase(cp)) {
                                    // Demostración: Tratar un Pedido exclusivamente bajo el lente de Rastreable
                                    Rastreable r = gestor.getPedidos()[i];
                                    System.out.println("Respuesta del sensor (Rastreable): " + r.obtenerEstadoActual());
                                    encontrado = true; 
                                    break;
                                }
                            }
                        } else {
                            System.out.print("Legajo del operario: ");
                            int lr = Integer.parseInt(sc.nextLine());
                            for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                                if (gestor.getRepartidores()[i].getLegajo() == lr) {
                                    // Demostración: Tratar un Repartidor exclusivamente bajo el lente de Rastreable
                                    Rastreable r = gestor.getRepartidores()[i];
                                    System.out.println("Respuesta del sensor (Rastreable): " + r.obtenerEstadoActual());
                                    encontrado = true; 
                                    break;
                                }
                            }
                        }
                        if (!encontrado) System.out.println("No se encontró el elemento en los registros.");
                        break;

                    case 9:
                        double totalPedidos = 0;
                        System.out.println("\n--- Auditoría Financiera (Polimorfismo con interfaz Facturable) ---");
                        
                        for (int i = 0; i < gestor.getCantPedidos(); i++) {
                            Facturable f = gestor.getPedidos()[i]; // Tratar pedido como Facturable
                            totalPedidos += f.calcularImporte();
                        }
                        System.out.println(">> Total de ingresos brutos proyectados por envíos: $" + totalPedidos);
                        
                        System.out.println("\nDesglose de comisiones operativas (10% de lo entregado):");
                        for (int i = 0; i < gestor.getCantRepartidores(); i++) {
                            Facturable f = gestor.getRepartidores()[i]; // Tratar repartidor como Facturable
                            System.out.println("- " + gestor.getRepartidores()[i].getNombre() + ": $" + f.calcularImporte());
                        }
                        break;

                    case 10:
                        System.out.println("Apagando módulos y guardando variables... Cerrando CityLink.");
                        break;

                    default:
                        System.out.println("Opción fuera de rango. Por favor, introduzca un número del 1 al 10.");
                }
                
            // === CAPTURA DE EXCEPCIONES Y PROPAGACIÓN (Requisito E1) ===
            } catch (NumberFormatException e) {
                // Captura el ingreso de letras donde va un entero sin cortar el programa
                System.out.println("ERROR DE FORMATO: Ingrese un valor numérico válido, no texto.");
            } catch (CapacidadExcedidaException | PedidoInvalidoException | ClienteNoEncontradoException | AlmacenamientoLlenoException e) {
                // Bloque multicatch atrapando los errores propios heredados de Exception (Checked)
                System.out.println("VIOLACIÓN DE NEGOCIO: " + e.getMessage());
            } catch (EstadoInvalidoException e) {
                // Atrapa el error lógico de transición de estados (Unchecked)
                System.out.println("ERROR LÓGICO DE SISTEMA: " + e.getMessage());
            } catch (Exception e) {
                // Atrapa errores no contemplados que de otra forma crashearían la JVM
                System.out.println("FALLA INESPERADA CRÍTICA: " + e.getMessage());
            } finally {
                // Bloque obligatorio: se ejecuta sí o sí al final de cada iteración del menú
                System.out.println("[Transacción finalizada]");
            }
        } while (opcion != 10);
        
        sc.close();
    }
}