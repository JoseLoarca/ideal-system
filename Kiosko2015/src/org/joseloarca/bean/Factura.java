package org.joseloarca.bean;
public class Factura {
    private int idFactura;
    private String numeroDeFactura;
    private String fecha;
    private String descripcion;
    private double total;
    private Cliente cliente;
    public Factura() {
    }
    public Factura(int idFactura, String numeroDeFactura, String fecha, String descripcion, double total, Cliente cliente) {
        this.idFactura = idFactura;
        this.numeroDeFactura = numeroDeFactura;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.total = total;
        this.cliente = cliente;
    }
    public int getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }
    public void setNumeroDeFactura(String numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
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
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
