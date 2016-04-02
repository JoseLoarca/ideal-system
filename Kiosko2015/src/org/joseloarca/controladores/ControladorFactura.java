package org.joseloarca.controladores;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.joseloarca.bean.Cliente;
import org.joseloarca.bean.Factura;
import org.joseloarca.reportes.GenerarReporte;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorFactura implements Initializable{
    private Principal principal;
    @FXML TableView tblFacturas;
    @FXML TableColumn colIdFacturas;
    @FXML TableColumn colNoFacturas;
    @FXML TableColumn colFecha;
    @FXML TableColumn colDescripcion;
    @FXML TableColumn colIdCliente;
    @FXML TableColumn colTotal;
    @FXML TextField txtNoFactura;
    @FXML TextField txtFecha;
    @FXML TextField txtDescripcion;
    @FXML TextField txtTotal;
    @FXML ComboBox cmbIdCliente;
    @FXML ImageView imgGuardar;
    @FXML ImageView imgCancelar;
    @FXML ImageView imgModificar;
    private ObservableList<Cliente> listaCliente;
    @Override
    public void initialize (URL url, ResourceBundle rb){
        actualizarDatos();
    }
    public void actualizarDatos(){
        tblFacturas.setItems(getFacturas());
        colIdFacturas.setCellValueFactory(new PropertyValueFactory<Factura,Integer>("idFactura"));
        colNoFacturas.setCellValueFactory(new PropertyValueFactory<Factura,String>("numeroDeFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura,String>("fecha"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Factura,String>("descripcion"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura,Float>("total"));  
        colIdCliente.setCellValueFactory( new PropertyValueFactory<Factura,Integer>("Cliente"));    
        try{
            ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarClientes}");
            ArrayList<Cliente> cliente = new ArrayList<Cliente>();
            while(resultado.next()){
                cliente.add(new Cliente(resultado.getInt("idCliente"), 
                resultado.getString("nit"), resultado.getString("dpi"),
                resultado.getString("nombre")));
            }
            listaCliente = FXCollections.observableArrayList(cliente);
        }catch(SQLException e){
            e.printStackTrace();
        }
        cmbIdCliente.getItems().clear();
        cmbIdCliente.getItems().addAll(listaCliente);
    }
    public ObservableList getFacturas(){
        ObservableList listaFacturas = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_FacturasListar}");
        try{
            ArrayList<Factura> lista = new ArrayList<Factura>();
            while(resultado.next()){
                lista.add(new Factura(resultado.getInt("idFactura"), resultado.getString("numeroDeFactura"),
                resultado.getString("fecha"), resultado.getString("descripcion"), resultado.getDouble("total"),
                new Cliente(resultado.getInt("idCliente"), resultado.getString("nit"), resultado.getString("dpi"),
                resultado.getString("nombre"))));
            }
            listaFacturas = FXCollections.observableArrayList(lista);         
        }catch(SQLException e){
            e.printStackTrace();          
        }
        return listaFacturas;
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
        txtNoFactura.setDisable(false);
        txtFecha.setDisable(false);
        txtDescripcion.setDisable(false);
        txtTotal.setDisable(false);
        cmbIdCliente.setDisable(false);
        cmbIdCliente.getItems().clear();
        cmbIdCliente.getItems().addAll(listaCliente);
        imgGuardar.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        txtNoFactura.clear();
        txtFecha.clear();
        txtDescripcion.clear();
        txtTotal.clear();
    }
    public void cancelar(){
        txtNoFactura.setDisable(true);
        txtNoFactura.clear();
        txtDescripcion.setDisable(true);
        txtDescripcion.clear();
        txtTotal.setDisable(true);
        txtTotal.clear();
        txtFecha.setDisable(true);
        txtFecha.clear();
        imgGuardar.setDisable(true);
        imgCancelar.setDisable(true);
        imgModificar.setDisable(true);
        cmbIdCliente.setDisable(true);
        cmbIdCliente.getItems().clear();
        cmbIdCliente.getItems().addAll(listaCliente);
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_AgregarFacturas (?,?,?,?,?)}");
            procedimiento.setString("numeroDeFactura",txtNoFactura.getText());
            procedimiento.setString("fecha",txtFecha.getText());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.setInt("idCliente",((Cliente)cmbIdCliente.getValue()).getIdCliente());
            procedimiento.setString("total",txtTotal.getText());
            procedimiento.execute();
            actualizarDatos();
            cancelar();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
      public void seleccionarElemento(){
        Factura factura = (Factura)tblFacturas.getSelectionModel().selectedItemProperty().get();
        txtNoFactura.setText(factura.getNumeroDeFactura());
        txtFecha.setText(factura.getFecha());
        txtDescripcion.setText(factura.getDescripcion());
        cmbIdCliente.setValue(factura.getCliente());
        txtTotal.setText(Double.toString(factura.getTotal()));
    }
    public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblFacturas.getSelectionModel().getSelectedItem() !=null){
                Factura factura = (Factura)tblFacturas.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarFacturas(?)}");
                    procedimiento.setInt("idFactura",factura.getIdFactura());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Facturas",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtNoFactura.clear();
        txtDescripcion.clear();
        txtFecha.clear();
        txtTotal.clear();
        cmbIdCliente.getItems().clear();
        cmbIdCliente.getItems().addAll(listaCliente);
    }
    public void botonModificar(){
        txtNoFactura.setDisable(false);
        txtDescripcion.setDisable(false);
        txtTotal.setDisable(false);
        txtFecha.setDisable(false);
        cmbIdCliente.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        imgGuardar.setDisable(true);
    }
    public void modificar(){
        Factura factura = (Factura)tblFacturas.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_ModificarFacturas (?,?,?,?,?,?)}");
            procedimiento.setInt("idFactura",factura.getIdFactura());
            procedimiento.setString("numeroDeFactura",txtNoFactura.getText());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.setString("fecha",txtFecha.getText());
            procedimiento.setString("total",txtTotal.getText());
            procedimiento.setInt("idCliente", ((Cliente)cmbIdCliente.getValue()).getIdCliente());
            procedimiento.execute();
            actualizarDatos();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
    }  
    public void reporte(){
        Map parametros = new HashMap();
        parametros.put("_numeroDeFactura", 1234);
        GenerarReporte.getInstancia().generarReporte(parametros, "CabFactura.jasper", "Reporte Factura");
    }
}