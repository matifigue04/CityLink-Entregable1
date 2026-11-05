package modelo;

/**
 * Clase Abstracta en la raíz de la jerarquía de vehículos.
 * Demuestra herencia y abstracción. No puede ser instanciada directamente.
 */
public abstract class Vehiculo {
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    /**
     * Constructor base protegido lógicamente para ser llamado solo por sus clases hijas.
     * 
     * @param patente      Matrícula o identificador del vehículo.
     * @param capacidadKg  Peso máximo soportado en kilogramos.
     * @param velocidadKmH Velocidad promedio de desplazamiento.
     * @param costoPorKm   Tarifa cobrada por kilómetro recorrido.
     */
    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        this.patente = patente;
        this.capacidadKg = capacidadKg;
        this.velocidadKmH = velocidadKmH;
        this.costoPorKm = costoPorKm;
    }

    // --- GETTERS (Encapsulamiento) ---
    public String getPatente() { return patente; }
    public double getCapacidadKg() { return capacidadKg; }
    public double getVelocidadKmH() { return velocidadKmH; }
    public double getCostoPorKm() { return costoPorKm; }

    /**
     * Método abstracto. Obliga a cada clase hija (Bici, Moto, Camioneta) a implementar 
     * su propia fórmula para estimar el tiempo, contemplando sus tiempos extra particulares.
     * 
     * @param km Distancia a recorrer.
     * @return Tiempo estimado en minutos.
     */
    public abstract double calcularTiempoEntrega(double km);

    /**
     * Método concreto que evalúa si el vehículo tiene capacidad para el pedido.
     * Las clases hijas pueden redefinirlo (sobreescribirlo) si necesitan reglas adicionales.
     * 
     * @param pedido El pedido a evaluar.
     * @return true si el peso del pedido es menor o igual a la capacidad del vehículo.
     */
    public boolean puedeTransportar(Pedido pedido) {
        return pedido.getPesoKg() <= this.capacidadKg;
    }
}