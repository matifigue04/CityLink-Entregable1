package modelo;

public abstract class Vehiculo {
    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    public Vehiculo(String patente, double capacidadKg, double velocidadKmH, double costoPorKm) {
        this.patente = patente;
        this.capacidadKg = capacidadKg;
        this.velocidadKmH = velocidadKmH;
        this.costoPorKm = costoPorKm;
    }

    public String getPatente() { return patente; }
    public double getCapacidadKg() { return capacidadKg; }
    public double getVelocidadKmH() { return velocidadKmH; }
    public double getCostoPorKm() { return costoPorKm; }

    public abstract double calcularTiempoEntrega(double km);

    public boolean puedeTransportar(Pedido pedido) {
        return pedido.getPesoKg() <= this.capacidadKg;
    }
}