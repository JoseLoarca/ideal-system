package org.joseloarca.controladores;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.joseloarca.bean.Usuario;
import org.joseloarca.db.Conexion;
import org.joseloarca.sistema.Principal;
public class ControladorLogin implements Initializable{
    private Principal principal;
    Usuario usuario;
    @FXML private TextField txtLogin;
    @FXML private TextField txtContrasena;
    @FXML private ImageView imgLogin;
    @Override
    public void initialize(URL location, ResourceBundle rb){    
    } 
    public Principal getPrincipal() {
        return principal;
    }
    public void login(){
        try{
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_AutenticarUsuario (?,?)}");
            procedimiento.setString("usuario", txtLogin.getText());
            procedimiento.setString("contrasena", txtContrasena.getText());
            procedimiento.execute();
            ResultSet resultado = procedimiento.getResultSet();
            if(resultado != null){
                while(resultado.next()){
                    usuario = new Usuario(resultado.getInt("idUsuario"),
                    resultado.getString("usuario"), resultado.getString("contrasena"));
                }
            }
            if(usuario != null) {
                JOptionPane.showMessageDialog(null, "Bienvenido al sistema: "+txtLogin.getText(), "PUNTO VENTA 2015",
                        JOptionPane.INFORMATION_MESSAGE);
                ventanaMenu();       
            }else {
                JOptionPane.showMessageDialog(null,"Usuario y/o Contraseña inválidos.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                txtLogin.clear();
                txtContrasena.clear();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void ventanaMenu(){
        principal.ventanaMenu();
    }
    public void cerrarPrograma(){
        principal.cerrarPrograma();
    }
}