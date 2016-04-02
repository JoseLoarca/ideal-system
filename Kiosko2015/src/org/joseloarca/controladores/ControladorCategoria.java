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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.joseloarca.db.Conexion;
import org.joseloarca.bean.Categoria;
import org.joseloarca.sistema.Principal;
public class ControladorCategoria implements Initializable {
    private Principal principal;
    @FXML private ImageView imgCancelar;
    @FXML private ImageView imgGuardar;
    @FXML private ImageView imgModificar;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblCategorias;
    @FXML private TableColumn colIdCategoria;
    @FXML private TableColumn colDescripcion;
    public void initialize(URL url, ResourceBundle rb){
        actualizarDatos();
    }
    public void actualizarDatos(){
        tblCategorias.setItems(getCategorias());
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,Integer>("idCategoria"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Categoria, String>("descripcion"));
    }
    public ObservableList getCategorias(){
        ObservableList listaCategorias = null;
        ResultSet resultado = Conexion.getInstancia().
                ejecutarProcedimiento("{call sp_ListarCategorias}");
        try{
            ArrayList<Categoria> lista = new ArrayList<Categoria>();
            while(resultado.next()){
                lista.add(new Categoria(resultado.getInt("idCategoria"), 
                        resultado.getString("descripcion")));
            }
            listaCategorias = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaCategorias;
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void nuevo(){
        txtDescripcion.setDisable(false);
        imgCancelar.setDisable(false);
        imgGuardar.setDisable(false);
        imgModificar.setDisable(true);
    }
    public void cancelar(){
        txtDescripcion.setDisable(true);
        imgCancelar.setDisable(true);
        imgGuardar.setDisable(true);
        imgModificar.setDisable(true);
        txtDescripcion.clear();
    }
    public void guardar(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().  
                prepareCall("{call sp_AgregarCategoria(?)}");
            procedimiento.setString("descripcion",txtDescripcion.getText());
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
        Categoria categoria = (Categoria)tblCategorias.getSelectionModel().selectedItemProperty().get();
        txtDescripcion.setText(categoria.getDescripcion());
    }
    public void eliminarRegistro(){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro?","Eliminar",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblCategorias.getSelectionModel().getSelectedItem() !=null){
                Categoria categoria = (Categoria)tblCategorias.getSelectionModel().selectedItemProperty().get();
                try{
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                            prepareCall("{call sp_EliminarCategoria(?)}");
                    procedimiento.setInt("idCategoria",categoria.getIdCategoria());
                    procedimiento.execute();
                    actualizarDatos();
                }catch(SQLException e){
                    e.printStackTrace();
                }                 
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro","Categorias",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txtDescripcion.clear();
    }
    public void botonModificar(){
        txtDescripcion.setDisable(false);
        imgCancelar.setDisable(false);
        imgModificar.setDisable(false);
        imgGuardar.setDisable(true);
    }
    public void modificar(){
        Categoria categoria = (Categoria)tblCategorias.
                getSelectionModel().selectedItemProperty().get();
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_ModificarCategoria (?,?)}");
            procedimiento.setInt("idCategoria",categoria.getIdCategoria());
            procedimiento.setString("descripcion",txtDescripcion.getText());
            procedimiento.execute();
            actualizarDatos();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cancelar();
    }
}
