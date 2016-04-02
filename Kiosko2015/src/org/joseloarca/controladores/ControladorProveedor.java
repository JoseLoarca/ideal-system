package org.joseloarca.controladores;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.joseloarca.bean.Proveedores;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorProveedor implements Initializable{
    private Principal principal;
    @FXML private TextField txtNit;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPaginaWeb;
    @FXML private TextField txtContacto;
    @FXML private ImageView imgCancelar;
    @FXML private ImageView imgGuardar;
    @FXML private ImageView imgModificar;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colIdProveedor;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colPaginaWeb;
    @FXML private TableColumn colContacto;
    public void initialize (URL url, ResourceBundle rb){  
        actualizarDatos();
    }
    public void actualizarDatos(){
        tblProveedores.setItems(getProveedores());
        colIdProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,Integer>("idProveedor"));
        colNit.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nit"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombre"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
        colContacto.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contacto"));
    }
    public ObservableList getProveedores(){
        ObservableList listaProveedores = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_ListarProveedores}");
        try{
            ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
            while(resultado.next()){
                lista.add(new Proveedores(resultado.getInt("idProveedor"), resultado.getString("nit"),
                resultado.getString("nombre"), resultado.getString("paginaWeb"),
                resultado.getString("contacto")));
            }
            listaProveedores = FXCollections.observableArrayList(lista);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return listaProveedores;
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void ventanaMenu(){
        principal.ventanaMenu();
    }
    public void nuevo(){
        txtNit.setDisable(false);
        txtNombre.setDisable(false);
        txtPaginaWeb.setDisable(false);
        txtContacto.setDisable(false);
        imgCancelar.setDisable(false);
        imgGuardar.setDisable(false);
        txtNit.clear();
        txtNombre.clear();
        txtPaginaWeb.clear();
        txtContacto.clear();
    }
    public void cancelar(){
        txtNit.setDisable(true);
        txtNombre.setDisable(true);
        txtPaginaWeb.setDisable(true);
        txtContacto.setDisable(true);
        imgCancelar.setDisable(true);
        imgGuardar.setDisable(true);
        txtNit.clear();
        txtNombre.clear();
        txtPaginaWeb.clear();
        txtContacto.clear();
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_AgregarProveedor (?,?,?,?)}");
            procedimiento.setString("nit",txtNit.getText());
            procedimiento.setString("nombre",txtNombre.getText());
            procedimiento.setString("paginaWeb",txtPaginaWeb.getText());
            procedimiento.setString("contacto",txtContacto.getText());
            procedimiento.execute();
            actualizarDatos();
            cancelar();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void seleccionarElemento(){
        Proveedores proveedores = (Proveedores)tblProveedores.getSelectionModel().selectedItemProperty().get();
        txtNit.setText(proveedores.getNit());
        txtNombre.setText(proveedores.getNombre());
        txtPaginaWeb.setText(proveedores.getPaginaWeb());
        txtContacto.setText(proveedores.getContacto());
    }
     public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblProveedores.getSelectionModel().getSelectedItem() !=null){
                Proveedores proveedores = (Proveedores)tblProveedores.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarProveedor(?)}");
                    procedimiento.setInt("idProveedor",proveedores.getIdProveedor());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Proveedores",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtNit.clear();
        txtNombre.clear();
        txtPaginaWeb.clear();
        txtContacto.clear();
    }
    public void botonModificar(){
        txtNit.setDisable(false);
        txtNombre.setDisable(false);
        txtPaginaWeb.setDisable(false);
        txtContacto.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        imgGuardar.setDisable(true);
    }
    public void modificar(){
        Proveedores proveedores = (Proveedores)tblProveedores.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_ModificarProveedor (?,?,?,?,?)}");
            procedimiento.setInt("idProveedor",proveedores.getIdProveedor());
            procedimiento.setString("nit",txtNit.getText());
            procedimiento.setString("nombre",txtNombre.getText());
            procedimiento.setString("paginaWeb",txtPaginaWeb.getText());
            procedimiento.setString("contacto",txtContacto.getText());
            procedimiento.execute();
            actualizarDatos();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
    }    
}
