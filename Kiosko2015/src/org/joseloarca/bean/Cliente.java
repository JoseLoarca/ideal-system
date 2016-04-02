package org.joseloarca.bean;
public class Cliente {
    private int idCliente;
    private String nit;
    private String dpi;
    private String nombre;
    public Cliente() {
    }
    public Cliente(int idCliente, String nit, String dpi, String nombre) {
        this.idCliente = idCliente;
        this.nit = nit;
        this.dpi = dpi;
        this.nombre = nombre;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getDpi() {
        return dpi;
    }
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }   
    public String toString(){
        return getNombre();
    }
}
