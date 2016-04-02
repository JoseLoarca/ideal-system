package org.joseloarca.controladores;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.joseloarca.sistema.Principal;
public class ControladorMenu implements Initializable {
    private Principal principal;
    @Override
    public void initialize(URL location, ResourceBundle rb){
        
    } 
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void cerrarPrograma(){
        principal.cerrarPrograma();
    }
    public void ventanaProductos(){
        principal.ventanaProductos();
    }
    public void ventanaCategorias(){
        principal.ventanaCategorias();
    }
    public void ventanaClientes(){
        principal.ventanaClientes();
    }
    public void ventanaProveedores(){
        principal.ventanaProveedores();
    }
    public void ventanaCompras(){
        principal.ventanaCompras();
    }
    public void ventanaFacturas(){
        principal.ventanaFacturas();
    }
}
