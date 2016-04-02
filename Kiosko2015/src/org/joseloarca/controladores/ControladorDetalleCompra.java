package org.joseloarca.controladores;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorDetalleCompra implements Initializable{
    private Principal principal;
    @FXML TableView tblCompra;
    @FXML TextField txtCompra;
    @FXML TextField txtDescripcion;
    @FXML TextField txtFecha;
    @FXML ComboBox cmbProductos;   
    @Override
public void initialize(URL url, ResourceBundle rb) { 
    }
public void tabla(){
    
}
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void ventanaCompras(){
        principal.ventanaCompras();
    }
}
