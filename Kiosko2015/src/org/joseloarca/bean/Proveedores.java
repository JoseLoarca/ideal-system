package org.joseloarca.bean;
public class Proveedores {
    private int idProveedor;
    private String nit;
    private String nombre;
    private String paginaWeb;
    private String contacto;
    public Proveedores() {
    }
    public Proveedores(int idProveedor, String nit, String nombre, String paginaWeb, String contacto) {
        this.idProveedor = idProveedor;
        this.nit = nit;
        this.nombre = nombre;
        this.paginaWeb = paginaWeb;
        this.contacto = contacto;
    }
    public int getIdProveedor() {
        return idProveedor;
    }
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPaginaWeb() {
        return paginaWeb;
    }
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
    public String getContacto() {
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }  
    public String toString(){
        return getNombre();
    }
}
