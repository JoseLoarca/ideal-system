package org.joseloarca.bean;
public class Usuario {
    private int idUsuario;
    private String usuario;
    private String contrasena;
    public Usuario() {
    }
    public Usuario(int idCliente, String usuario, String contrasena) {
        this.idUsuario = idCliente;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
    public int getIdCliente() {
        return idUsuario;
    }
    public void setIdCliente(int idCliente) {
        this.idUsuario = idCliente;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
