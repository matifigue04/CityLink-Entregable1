package modelo;

/**
 * Entidad Concreta que representa a un cliente del sistema CityLink.
 * Demuestra el uso de Encapsulamiento mediante atributos privados y métodos de acceso (getters/setters).
 */
public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    /**
     * Constructor vacío.
     */
    public Cliente() {}

    /**
     * Constructor completo. Utiliza la sobrecarga de constructores.
     * Delega la inicialización a los setters para aprovechar sus validaciones.
     * 
     * @param codigo   Código numérico único del cliente.
     * @param nombre   Nombre completo.
     * @param telefono Teléfono de contacto.
     * @param zona     Zona de residencia o entrega.
     */
    public Cliente(int codigo, String nombre, String telefono, String zona) {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    // --- GETTERS Y SETTERS CON VALIDACIÓN (Encapsulamiento) ---

    /** @return El código único del cliente. */
    public int getCodigo() { return codigo; }

    /**
     * Asigna un código al cliente.
     * @param codigo Debe ser mayor a cero.
     * @throws IllegalArgumentException Si el código es 0 o negativo.
     */
    public void setCodigo(int codigo) {
        if (codigo <= 0) throw new IllegalArgumentException("El código debe ser mayor a cero.");
        this.codigo = codigo;
    }

    /** @return El nombre del cliente. */
    public String getNombre() { return nombre; }

    /**
     * Asigna un nombre al cliente validando que no esté vacío.
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre;
    }

    /** @return El teléfono del cliente. */
    public String getTelefono() { return telefono; }

    /**
     * Asigna el teléfono validando que tenga contenido.
     * @param telefono Teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        this.telefono = telefono;
    }

    /** @return La zona de la ciudad del cliente. */
    public String getZona() { return zona; }

    /**
     * Asigna la zona de residencia validando que no esté vacía.
     * @param zona Zona a asignar.
     */
    public void setZona(String zona) {
        if (zona == null || zona.trim().isEmpty()) throw new IllegalArgumentException("La zona no puede estar vacía.");
        this.zona = zona;
    }

    /**
     * Redefinición (Override) del método toString heredado de la clase Object.
     * @return Una representación en texto amigable del cliente.
     */
    @Override
    public String toString() {
        return "Cliente [" + codigo + "] " + nombre + " - Tel: " + telefono + " - Zona: " + zona;
    }
}