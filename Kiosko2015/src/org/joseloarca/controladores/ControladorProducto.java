package org.joseloarca.controladores;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.net.URL;
import java.sql.CallableStatement;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.joseloarca.bean.Categoria;
import org.joseloarca.bean.Empaque;
import org.joseloarca.db.Conexion;
import org.joseloarca.bean.Producto;
import org.joseloarca.bean.Stock;
import org.joseloarca.sistema.Principal;
public class ControladorProducto implements Initializable {
    @FXML private ComboBox cmbCategorias;
    @FXML private ComboBox cmbEmpaques;
    @FXML private ComboBox cmbStocks;
    @FXML private Principal principal;
    @FXML private TableView tblProducto;
    @FXML private TableColumn colIdProducto;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colPrecioDocena;
    @FXML private TableColumn colPrecioMayor;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colEmpaque;
    @FXML private TableColumn colCategoria;
    @FXML private TableColumn colStock;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPUnitario;
    @FXML private TextField txtPMayor;
    @FXML private TextField txtPDocena;
    @FXML private ImageView imgCancelar;
    @FXML private ImageView imgGuardar;
    @FXML private ImageView imgModificar;
    private ObservableList<Categoria> listaCategorias;
    private ObservableList<Empaque> listaEmpaques;
    private ObservableList<Stock> listaStocks;
    @Override
    public void initialize(URL url, ResourceBundle rb){  
        actualizarDatos();
    }
    public void actualizarDatos(){         
        tblProducto.setItems(getProductos());
        colIdProducto.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioPorDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioPorMayor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto,String>("descripcion"));
        colEmpaque.setCellValueFactory(new PropertyValueFactory<Producto, String>("empaque"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoria"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto, String>("stock"));
        try{
            ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarCategorias}");
            ArrayList<Categoria> categoria = new ArrayList<Categoria>();
            while(resultado.next()){
                categoria.add(new Categoria(resultado.getInt("idCategoria"),
                resultado.getString("descripcion")));
            }
            resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarEmpaque}");
            ArrayList<Empaque> empaque = new ArrayList<Empaque>();
            while(resultado.next()){
                empaque.add(new Empaque(resultado.getInt("idEmpaque"),
                resultado.getString("descripcion")));
            }
            resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarStocks}");
            ArrayList<Stock> stock = new ArrayList<Stock>();
            while(resultado.next()){
                stock.add(new Stock(resultado.getInt("idStock"),
                resultado.getString("stock")));
            }
            listaCategorias = FXCollections.observableArrayList(categoria);
            listaEmpaques = FXCollections.observableArrayList(empaque);
            listaStocks = FXCollections.observableArrayList(stock);
        }catch(SQLException e){
            e.printStackTrace();
        }
        cmbCategorias.getItems().clear();
        cmbCategorias.getItems().addAll(listaCategorias);
        cmbEmpaques.getItems().clear();
        cmbEmpaques.getItems().addAll(listaEmpaques);
        cmbStocks.getItems().clear();
        cmbStocks.getItems().addAll(listaStocks);
    }
    public ObservableList getProductos(){
        ObservableList listaProductos = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_ProductoListar}");
        try{
            ArrayList<Producto> lista = new ArrayList<>();
            while(resultado.next()){
                lista.add(new Producto(resultado.getInt("idProducto"),
                    resultado.getString("nombre"), resultado.getString("descripcion"), resultado.getDouble("precioUnitario"),
                    resultado.getDouble("precioPorDocena"), resultado.getDouble("precioPorMayor"),
                    new Empaque(resultado.getInt("idEmpaque"), resultado.getString("empaque")),
                    new Stock(resultado.getInt("idStock"), resultado.getString("stock")),
                    new Categoria(resultado.getInt("idCategoria"), resultado.getString("categoria"))));
            }
            listaProductos = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaProductos;
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
    public void modificar(){
        txtNombre.setDisable(false);
        txtDescripcion.setDisable(false);
        cmbCategorias.setDisable(false);
        cmbEmpaques.setDisable(false);
        cmbStocks.setDisable(false);
        imgModificar.setDisable(false);
        txtPUnitario.setDisable(false);
        txtPMayor.setDisable(false);
        txtPDocena.setDisable(false);
        imgGuardar.setDisable(true);
        imgCancelar.setDisable(false);
    }
    public void nuevo(){
        txtNombre.setDisable(false);
        txtDescripcion.setDisable(false);
        cmbCategorias.setDisable(false);
        cmbEmpaques.setDisable(false);
        cmbStocks.setDisable(false);
        imgCancelar.setDisable(false);
        imgGuardar.setDisable(false);
        imgModificar.setDisable(true);
        txtPUnitario.setDisable(true);
        txtPMayor.setDisable(true);
        txtPDocena.setDisable(true);
        txtPUnitario.clear();
        txtPMayor.clear();
        txtPDocena.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        cmbCategorias.getItems().clear();
        cmbCategorias.getItems().addAll(listaCategorias);
        cmbEmpaques.getItems().clear();
        cmbEmpaques.getItems().addAll(listaEmpaques);
        cmbStocks.getItems().clear();
        cmbStocks.getItems().addAll(listaStocks);
    }
    public void cancelar(){
        cmbCategorias.getItems().clear();
        cmbCategorias.getItems().addAll(listaCategorias);
        cmbEmpaques.getItems().clear();
        cmbEmpaques.getItems().addAll(listaEmpaques);
        cmbStocks.getItems().clear();
        cmbStocks.getItems().addAll(listaStocks);
        txtPUnitario.setDisable(true);
        txtPMayor.setDisable(true);
        txtPDocena.setDisable(true);
        txtPUnitario.clear();
        txtPMayor.clear();
        txtPDocena.clear();
        txtNombre.setDisable(true);
        txtDescripcion.setDisable(true);
        cmbCategorias.setDisable(true);
        cmbEmpaques.setDisable(true);
        cmbStocks.setDisable(true);
        imgCancelar.setDisable(true);
        imgGuardar.setDisable(true);
        imgModificar.setDisable(true);
        txtNombre.clear();
        txtDescripcion.clear();
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_AgregarProducto (?,?,?,?,?)}");
            procedimiento.setString("nombre",txtNombre.getText());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.setInt("idCategoria",((Categoria)cmbCategorias.getValue()).getIdCategoria());
            procedimiento.setInt("idEmpaque",((Empaque)cmbEmpaques.getValue()).getIdEmpaque());
            procedimiento.setInt("idStock",((Stock)cmbStocks.getValue()).getIdStock());  
            procedimiento.execute();
            actualizarDatos();
            cancelar();
        }catch(SQLException e){
            e.printStackTrace();            
        }
    }
   public void modificarRegistro(){
        Producto producto = (Producto)tblProducto.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion()
                    .prepareCall("{call sp_ModificarProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setInt("idProducto", producto.getIdProducto());     
            procedimiento.setString("nombre", txtNombre.getText());
            procedimiento.setString("descripcion", txtDescripcion.getText());
            procedimiento.setDouble("precioUnitario", Double.parseDouble(txtPUnitario.getText()));
            procedimiento.setDouble("precioPorDocena", Double.parseDouble(txtPDocena.getText()));
            procedimiento.setDouble("precioPorMayor", Double.parseDouble(txtPMayor.getText()));
            procedimiento.setInt("idEmpaque",((Empaque)cmbEmpaques.getValue()).getIdEmpaque());
            procedimiento.setInt("idCategoria",((Categoria)cmbCategorias.getValue()).getIdCategoria());
            procedimiento.setInt("idStock",((Stock)cmbStocks.getValue()).getIdStock());
            procedimiento.execute();
            actualizarDatos();          
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
   }
    public void seleccionarElemento(){
        Producto producto = (Producto)tblProducto.getSelectionModel().selectedItemProperty().get();
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        cmbEmpaques.setValue(producto.getEmpaque());
        cmbCategorias.setValue(producto.getCategoria());
        cmbStocks.setValue(producto.getStock());
        txtPUnitario.setText(Double.toString(producto.getPrecioUnitario()));
        txtPMayor.setText(Double.toString(producto.getPrecioPorMayor()));
        txtPDocena.setText(Double.toString(producto.getPrecioPorDocena()));
    }
    public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblProducto.getSelectionModel().getSelectedItem() !=null){
                Producto producto = (Producto)tblProducto.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarProducto(?)}");
                    procedimiento.setInt("idProducto",producto.getIdProducto());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Productos",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtNombre.clear();
        txtDescripcion.clear();
        txtPUnitario.clear();
        txtPMayor.clear();
        txtPDocena.clear();
        cmbCategorias.getItems().clear();
        cmbCategorias.getItems().addAll(listaCategorias);
        cmbEmpaques.getItems().clear();
        cmbEmpaques.getItems().addAll(listaEmpaques);
        cmbStocks.getItems().clear();
        cmbStocks.getItems().addAll(listaStocks);
    }
}