package model;

// Clase que representa un producto del inventario de la clínica
// Por ejemplo: vacunas, medicamentos, alimentos, etc.
public class Producto {
    // Atributos privados (encapsulamiento)
    private String id;
    private String nombre;
    private String categoria; // Vacunas, Medicamentos, Alimentos, etc.
    private int cantidad;
    private int cantidadMinima; // Para alertas de stock bajo
    private double precio;
    
    // Constructor de la clase Producto
    public Producto(String id, String nombre, String categoria, int cantidad, int cantidadMinima, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;
        this.precio = precio;
    }
    
    // ===== GETTERS Y SETTERS (Encapsulamiento) =====
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getCantidadMinima() {
        return cantidadMinima;
    }
    
    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    // Verifica si el producto tiene stock bajo
    // Retorna true si la cantidad actual es menor o igual a la cantidad mínima
    public boolean tieneStockBajo() {
        return cantidad <= cantidadMinima;
    }
    
    // Sobrescribe el método toString para representación en texto
    @Override
    public String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", cantidad=" + cantidad +
                ", cantidadMinima=" + cantidadMinima +
                ", precio=$" + precio +
                '}';
    }
}
