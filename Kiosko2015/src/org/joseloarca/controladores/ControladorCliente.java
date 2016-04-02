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
import org.joseloarca.bean.Cliente;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorCliente implements Initializable{
    private Principal principal;
    @FXML private ImageView imgGuardar;
    @FXML private ImageView imgCancelar;
    @FXML private ImageView imgModificar;
    @FXML private TextField txtNit;
    @FXML private TextField txtDpi;
    @FXML private TextField txtNombre;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colIdCliente;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colDpi;
    @FXML private TableColumn colNombre;
    public void initialize (URL url, ResourceBundle rb){  
        actualizarDatos();
    }
    public void actualizarDatos(){
        tblClientes.setItems(getClientes());
        colIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("idCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
        colDpi.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
    }
    public ObservableList getClientes(){
        ObservableList listaClientes = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_ListarClientes}");
        try{
            ArrayList<Cliente> lista = new ArrayList<Cliente>();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("idCliente"), resultado.getString("nit"),
                resultado.getString("dpi"), resultado.getString("nombre")));
            }
            listaClientes = FXCollections.observableArrayList(lista);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void nuevo(){
        txtNit.setDisable(false);
        txtDpi.setDisable(false);
        txtNombre.setDisable(false);
        imgGuardar.setDisable(false);
        imgCancelar.setDisable(false);
        txtNit.clear();
        txtDpi.clear();
        txtNombre.clear();
    }
    public void cancelar(){
        txtNit.setDisable(true);
        txtDpi.setDisable(true);
        txtNombre.setDisable(true);
        txtNit.clear();
        txtDpi.clear();
        txtNombre.clear();
        imgGuardar.setDisable(true);
        imgCancelar.setDisable(true);
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
             prepareCall("{call sp_AgregarCliente(?,?,?)}");
            procedimiento.setString("nit",txtNit.getText());
            procedimiento.setString("dpi",txtDpi.getText());
            procedimiento.setString("nombre",txtNombre.getText());
            procedimiento.execute();
            actualizarDatos();
            cancelar();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void ventanaMenu(){
        principal.ventanaMenu();
    } 
    public void seleccionarElemento(){
        Cliente cliente = (Cliente)tblClientes.getSelectionModel().selectedItemProperty().get();
        txtNit.setText(cliente.getNit());
        txtDpi.setText(cliente.getDpi());
        txtNombre.setText(cliente.getNombre());
    }
     public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblClientes.getSelectionModel().getSelectedItem() !=null){
                Cliente cliente = (Cliente)tblClientes.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarCliente(?)}");
                    procedimiento.setInt("idCliente",cliente.getIdCliente());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Clientes",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtNit.clear();
        txtDpi.clear();
        txtNombre.clear();
    }
    public void botonModificar(){
        txtNit.setDisable(false);
        txtDpi.setDisable(false);
        txtNombre.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        imgGuardar.setDisable(true);
    }
    public void modificar(){
        Cliente cliente = (Cliente)tblClientes.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_ModificarCliente (?,?,?,?)}");
            procedimiento.setInt("idCliente",cliente.getIdCliente());
            procedimiento.setString("nit",txtNit.getText());
            procedimiento.setString("dpi",txtDpi.getText());
            procedimiento.setString("nombre",txtNombre.getText());
            procedimiento.execute();
            actualizarDatos();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
    }    
}
