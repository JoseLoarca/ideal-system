package org.joseloarca.bean;
public class Compra {
    private int idCompra;
    private String numeroDeCompra;
    private String Fecha;
    private String descripcion;
    private double total;
    private Proveedores proveedores;
    public Compra() {
    }
    public Compra(int idCompra, String numeroDeCompra, String Fecha, String descripcion, double total, Proveedores proveedores) {
        this.idCompra = idCompra;
        this.numeroDeCompra = numeroDeCompra;
        this.Fecha = Fecha;
        this.descripcion = descripcion;
        this.total = total;
        this.proveedores = proveedores;
    }
    public int getIdCompra() {
        return idCompra;
    }
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    public String getNumeroDeCompra() {
        return numeroDeCompra;
    }
    public void setNumeroDeCompra(String numeroDeCompra) {
        this.numeroDeCompra = numeroDeCompra;
    }
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public Proveedores getProveedores() {
        return proveedores;
    }
    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }
}
