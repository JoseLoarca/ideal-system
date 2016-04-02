package org.joseloarca.bean;
public class Empaque {
    private int idEmpaque;
    private String descripcion;
    public Empaque() {
    }
    public Empaque(int idEmpaque, String descripcion) {
        this.idEmpaque = idEmpaque;
        this.descripcion = descripcion;
    }
    public int getIdEmpaque() {
        return idEmpaque;
    }
    public void setIdEmpaque(int idEmpaque) {
        this.idEmpaque = idEmpaque;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    } 
    public String toString(){
        return getDescripcion();
    }   
}