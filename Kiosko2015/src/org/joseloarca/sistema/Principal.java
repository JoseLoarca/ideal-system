package org.joseloarca.sistema;
import java.io.InputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import org.joseloarca.controladores.ControladorCategoria;
import org.joseloarca.controladores.ControladorCliente;
import org.joseloarca.controladores.ControladorCompra;
import org.joseloarca.controladores.ControladorDetalleCompra;
import org.joseloarca.controladores.ControladorFactura;
import org.joseloarca.controladores.ControladorLogin;
import org.joseloarca.controladores.ControladorMenu;
import org.joseloarca.controladores.ControladorProducto;
import org.joseloarca.controladores.ControladorProveedor;
public class Principal extends Application {
    private Stage escenarioPrincipal;
    @Override
    public void start(Stage escenarioPrincipal) {    
        try{
            escenarioPrincipal.setResizable(false);
            this.escenarioPrincipal = escenarioPrincipal;
            this.escenarioPrincipal.setTitle("Punto de Venta 2015");
            this.escenarioPrincipal.getIcons().add(new Image("/org/joseloarca/recursos/Shop-100.png"));
            this.escenarioPrincipal.initStyle(StageStyle.DECORATED);
            ventanaLogin();
            this.escenarioPrincipal.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaFacturas(){
        ControladorFactura factura = (ControladorFactura)cambiarEscena("/org/joseloarca/vista/vistaFacturas.fxml",479,569);
        factura.setPrincipal(this);
    }
    public void ventanaCompras(){
        ControladorCompra compra = (ControladorCompra)cambiarEscena("/org/joseloarca/vista/vistaCompras.fxml", 536,695);
        compra.setPrincipal(this);
    }
    public void ventanaDetalleCompras(){
        ControladorDetalleCompra detalleCompra = (ControladorDetalleCompra)cambiarEscena("/org/joseloarca/vista/vistaDetalleCompras.fxml",544,564);
        detalleCompra.setPrincipal(this);        
    }
    public void ventanaProveedores(){
        ControladorProveedor proveedor = (ControladorProveedor)cambiarEscena("/org/joseloarca/vista/vistaProveedores.fxml", 426,649);
        proveedor.setPrincipal(this);
    }
    public void ventanaClientes(){
        ControladorCliente cliente = (ControladorCliente)cambiarEscena("/org/joseloarca/vista/vistaClientes.fxml", 486,552);
        cliente.setPrincipal(this);
    }
    public void ventanaProductos(){
        ControladorProducto producto = (ControladorProducto)cambiarEscena("/org/joseloarca/vista/vistaProductos.fxml", 781,642);
        producto.setPrincipal(this);
    }
    public void ventanaMenu(){
        ControladorMenu menu = (ControladorMenu)cambiarEscena("/org/joseloarca/vista/vistaMenu.fxml", 575, 381);
        menu.setPrincipal(this);
    }
    public void ventanaLogin(){
        ControladorLogin login = (ControladorLogin)cambiarEscena("/org/joseloarca/vista/vistaLogin.fxml", 426,146);
        login.setPrincipal(this);
    }
    public void ventanaCategorias(){
        ControladorCategoria categorias = (ControladorCategoria)cambiarEscena("/org/joseloarca/vista/vistaCategorias.fxml", 344,503);
        categorias.setPrincipal(this);
    }
    public Initializable cambiarEscena(String nombreFXML, int ancho, int alto){
        FXMLLoader cargar = new FXMLLoader();
        try{
            InputStream archivoFXML = Principal.class.getResourceAsStream(nombreFXML);
            cargar.setBuilderFactory(new JavaFXBuilderFactory());
            cargar.setLocation(Principal.class.getResource(nombreFXML));
            AnchorPane panel = cargar.load(archivoFXML);
            Scene nuevaEscena = new Scene(panel,ancho,alto);
            escenarioPrincipal.setScene(nuevaEscena);
            escenarioPrincipal.sizeToScene();
        }catch(Exception e){
            e.printStackTrace();
        }  
        return(Initializable)cargar.getController();
    }
    public void cerrarPrograma(){
        escenarioPrincipal.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public Stage getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }   
}