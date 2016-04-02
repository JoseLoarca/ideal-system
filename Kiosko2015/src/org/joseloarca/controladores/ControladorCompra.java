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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.joseloarca.bean.Compra;
import org.joseloarca.bean.Proveedores;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorCompra implements Initializable{
    private Principal principal;
    @FXML ImageView imgGuardar;
    @FXML ImageView imgCancelar;
    @FXML ImageView imgModificar;
    @FXML TextField txtNoCompra;
    @FXML TextField txtDescripcion;
    @FXML TextField txtTotal;
    @FXML ComboBox cmbIdProveedor;
    @FXML TextField txtFecha;
    @FXML TableView tblCompras;
    @FXML TableColumn colIdCompras;
    @FXML TableColumn colNoCompras;
    @FXML TableColumn colFecha;
    @FXML TableColumn colDescripcion;
    @FXML TableColumn colIdProveedor;
    @FXML TableColumn colTotal; 
    private ObservableList<Proveedores> listaProveedores;
    @Override
    public void initialize (URL url, ResourceBundle rb){
        actualizarDatos();
    }
    public void actualizarDatos(){
        tblCompras.setItems(getCompras());
        colIdCompras.setCellValueFactory(new PropertyValueFactory<Compra,Integer>("idCompra"));
        colNoCompras.setCellValueFactory(new PropertyValueFactory<Compra,String>("numeroDeCompra"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Compra,String>("fecha"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compra,String>("descripcion"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compra,Float>("total"));  
        colIdProveedor.setCellValueFactory( new PropertyValueFactory<Compra,Integer>("Proveedores"));
        try{
            ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarProveedores}");
            ArrayList<Proveedores> proveedores = new ArrayList<Proveedores>();
            while(resultado.next()){
                proveedores.add(new Proveedores(resultado.getInt("idProveedor"),
                resultado.getString("nit"), resultado.getString("nombre"),
                resultado.getString("paginaWeb"), resultado.getString("contacto")));
            }
            listaProveedores = FXCollections.observableArrayList(proveedores);
        }catch(SQLException e){
            e.printStackTrace();
        }
        cmbIdProveedor.getItems().clear();
        cmbIdProveedor.getItems().addAll(listaProveedores);
    }
    public ObservableList getCompras(){
        ObservableList listaCompras = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_ComprasListar}");
        try{
            ArrayList<Compra> lista = new ArrayList<Compra>();
            while(resultado.next()){
                lista.add(new Compra(resultado.getInt("idCompra"), resultado.getString("numeroDeCompra"),
                resultado.getString("fecha"), resultado.getString("descripcion"), resultado.getDouble("total"),
                new Proveedores(resultado.getInt("idProveedor"), resultado.getString("nit"), resultado.getString("nombre"),
                resultado.getString("paginaWeb"), resultado.getString("contacto"))));
            }
            listaCompras = FXCollections.observableArrayList(lista);         
        }catch(SQLException e){
            e.printStackTrace();          
        }
        return listaCompras;
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal pincipal) {
        this.principal = pincipal;
    }
    public void ventanaMenu(){
        principal.ventanaMenu();
    }
    public void nuevo(){
        txtNoCompra.setDisable(false);
        txtDescripcion.setDisable(false);
        txtTotal.setDisable(false);
        txtFecha.setDisable(false);
        cmbIdProveedor.setDisable(false);
        imgCancelar.setDisable(false);
        imgGuardar.setDisable(false);
        txtNoCompra.clear();
        txtDescripcion.clear();
        txtTotal.clear();
        txtFecha.clear();
        cmbIdProveedor.getItems().clear();
        cmbIdProveedor.getItems().addAll(listaProveedores);
    }
    public void cancelar(){
        txtNoCompra.setDisable(true);
        txtDescripcion.setDisable(true);
        txtTotal.setDisable(true);
        txtFecha.setDisable(true);
        cmbIdProveedor.setDisable(true);
        imgCancelar.setDisable(true);
        imgGuardar.setDisable(true);
        txtNoCompra.clear();
        txtDescripcion.clear();
        txtTotal.clear();
        txtFecha.clear();
        cmbIdProveedor.getItems().clear();
        cmbIdProveedor.getItems().addAll(listaProveedores);
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_AgregarCompras (?,?,?,?,?)}");
            procedimiento.setString("numeroDeCompra",txtNoCompra.getText());
            procedimiento.setString("fecha",txtFecha.getText());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.setInt("idProveedor", ((Proveedores)cmbIdProveedor.getValue()).getIdProveedor());
            procedimiento.setString("total",txtTotal.getText());
            procedimiento.execute();
            actualizarDatos();
            cancelar();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void seleccionarElemento(){
        Compra compra = (Compra)tblCompras.getSelectionModel().selectedItemProperty().get();
        txtNoCompra.setText(compra.getNumeroDeCompra());
        txtFecha.setText(compra.getFecha());
        txtDescripcion.setText(compra.getDescripcion());
        cmbIdProveedor.setValue(compra.getProveedores());
        txtTotal.setText(Double.toString(compra.getTotal()));
    }
    public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblCompras.getSelectionModel().getSelectedItem() !=null){
                Compra compra = (Compra)tblCompras.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarCompra(?)}");
                    procedimiento.setInt("idCompra",compra.getIdCompra());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Compras",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtNoCompra.clear();
        txtDescripcion.clear();
        txtTotal.clear();
        txtFecha.clear();
        cmbIdProveedor.getItems().clear();
    }
    public void botonModificar(){
        txtNoCompra.setDisable(false);
        txtDescripcion.setDisable(false);
        txtTotal.setDisable(false);
        txtFecha.setDisable(false);
        cmbIdProveedor.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        imgGuardar.setDisable(true);
    }
    public void modificar(){
        Compra compra = (Compra)tblCompras.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_ModificarCompra (?,?,?,?,?,?)}");
            procedimiento.setInt("idCompra",compra.getIdCompra());
            procedimiento.setString("numeroDeCompra",txtNoCompra.getText());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.setString("fecha",txtFecha.getText());
            procedimiento.setString("total",txtTotal.getText());
            procedimiento.setInt("idProveedor", ((Proveedores)cmbIdProveedor.getValue()).getIdProveedor());
            procedimiento.execute();
            actualizarDatos();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
    }
    public void  ventanaDetalleCompras(){
        principal.ventanaDetalleCompras();
    }
}