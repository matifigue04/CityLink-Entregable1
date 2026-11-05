package modelo;

public class Cliente {
    private int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    public Cliente() {}

    public Cliente(int codigo, String nombre, String telefono, String zona) {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    @Override
    public String toString() {
        return "Cliente [" + codigo + "] " + nombre + " - Tel: " + telefono + " - Zona: " + zona;
    }
}