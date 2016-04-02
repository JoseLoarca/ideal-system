package org.joseloarca.bean;
public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private double precioPorDocena;
    private double precioPorMayor;
    private Empaque empaque;
    private Stock stock;
    private Categoria categoria;
    public Producto() {
    }
    public Producto(int idProducto, String nombre, String descripcion, double precioUnitario, double precioPorDocena, double precioPorMayor, Empaque empaque, Stock stock, Categoria categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.precioPorDocena = precioPorDocena;
        this.precioPorMayor = precioPorMayor;
        this.empaque = empaque;
        this.stock = stock;
        this.categoria = categoria;
    }
    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public double getPrecioPorDocena() {
        return precioPorDocena;
    }
    public void setPrecioPorDocena(double precioPorDocena) {
        this.precioPorDocena = precioPorDocena;
    }
    public double getPrecioPorMayor() {
        return precioPorMayor;
    }
    public void setPrecioPorMayor(double precioPorMayor) {
        this.precioPorMayor = precioPorMayor;
    }
    public Empaque getEmpaque() {
        return empaque;
    }
    public void setEmpaque(Empaque empaque) {
        this.empaque = empaque;
    }
    public Stock getStock() {
        return stock;
    }
    public void setStock(Stock stock) {
        this.stock = stock;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }   
}