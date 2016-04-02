package org.joseloarca.db;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
public class Conexion {
    private Connection conexion;
    private Statement sentencia;
    private static Conexion instancia;
    public Conexion() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            conexion = DriverManager.getConnection("jdbc:sqlserver://LABC23-24:0;instanceName=SQLEXPRESS;dataBaseName=PuntoVenta2015;user=quinto;password=informatica;");
            //conexion = DriverManager.getConnection("jdbc:sqlserver://ATIV700PRO-JOSE:0;instanceName=SQLEXPRESS;dataBaseName=PuntoVenta2015;user=jose;password=informatica;");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }   
    }
    public static Conexion getInstancia(){
        if(instancia ==null){
           instancia = new Conexion();
        }
        return instancia;
    }
    public ResultSet ejecutarProcedimiento(String nombreProcedimiento){
        ResultSet resultado = null;
        try{
            CallableStatement procedimiento = conexion.prepareCall(nombreProcedimiento);
            resultado = procedimiento.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    public Connection getConexion() {
        return conexion;
    }  
}